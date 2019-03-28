import axios from "axios";
import ls from "local-storage";
import root from "loglevel";
import userApi from "../../api/user";
import storeUtils from "../../utils/storeUtils";
import userHelper from "../helpers/user";

const log = root.getLogger("store/modules/user");

const _types = {
  // mutations
  CLEAR_USER: "CLEAR_USER",
  SET_USER: "SET_USER",
  SET_AUTH: "SET_AUTH",
  SET_ACCESS_TOKEN: "SET_ACCESS_TOKEN",
  SET_SETTINGS: "SET_SETTINGS",
  START_FETCHING: "START_FETCHING",
  END_FETCHING: "END_FETCHING",

  // getters
  IS_AUTHENTIFICATED: "IS_AUTHENTIFICATED",
  HAS_PERMISSIONS: "HAS_PERMISSION",
  HAS_ANY_OF_PERMISSIONS: "HAS_ANY_OF_PERMISSIONS",
  HAS_ANY_PERMISSION: "HAS_ANY_PERMISSION",

  // actions
  AUTH_USER: "AUTH_USER",
  REFRESH_ACCESS_TOKEN: "REFRESH_ACCESS_TOKEN",
  FETCH_USER: "FETCH_USER"
};

const state = () => ({
  fetching: false,
  id: ls.get("id") || null,
  email: ls.get("email") || null,
  firstname: ls.get("firstname") || null,
  lastname: ls.get("lastname") || null,
  patronymic: ls.get("patronymic") || null,
  permissions: ls.get("permissions") || [],
  accessToken: ls.get("accessToken") || null,
  refreshToken: ls.get("refreshToken") || null,
  settings: {
    darkThemeEnabled: ls.get("settings.darkThemeEnabled") || false,
    locale: ls.get("settings.locale") || "ru"
  }
});

const mutations = {
  [_types.START_FETCHING](state) {
    state.fetching = true;
  },

  [_types.END_FETCHING](state) {
    state.fetching = false;
  },

  [_types.CLEAR_USER](state) {
    state.email = null;
    state.id = null;
    state.firstname = null;
    state.lastname = null;
    state.patronymic = null;
    state.permissions = [];
    state.accessToken = null;
    state.refreshToken = null;
  },

  [_types.SET_AUTH](state, { access_token, refresh_token }) {
    state.accessToken = access_token;
    state.refreshToken = refresh_token;
  },

  [_types.SET_ACCESS_TOKEN](state, { access_token }) {
    state.accessToken = access_token;
  },

  [_types.SET_USER](state, payload) {
    state.id = payload.id;
    state.email = payload.email;
    state.firstname = payload.firstname;
    state.lastname = payload.lastname;
    state.patronymic = payload.patronymic;
    state.permissions = payload.permissions;
  },

  [_types.SET_SETTINGS](state, payload) {
    state.settings.darkThemeEnabled = payload.darkThemeEnabled;
    state.settings.locale = payload.locale;
  }
};

const actions = {
  async [_types.AUTH_USER]({ commit }, credentials) {
    commit(_types.START_FETCHING);
    userApi
      .getToken(credentials)
      .then(async resp => {
        commit(_types.CLEAR_USER);
        commit(_types.SET_AUTH, resp.data);

        axios.defaults.headers.common["Authorization"] = `Bearer ${
          resp.data.access_token
        }`;

        await userApi.getMe(credentials).then(resp => {
          commit(_types.SET_USER, resp.data.data);
        });
        await userApi.getSettings().then(resp => {
          commit(_types.SET_SETTINGS, resp.data.data);
        });
      })
      .catch(error => {
        commit(_types.CLEAR_USER);
        delete axios.defaults.headers.common["Authorization"];
        log.error(error);
      })
      .then(() => {
        commit(_types.END_FETCHING);
      });
  },

  async [_types.REFRESH_ACCESS_TOKEN]({ commit, state }) {
    commit(_types.START_FETCHING);
    userApi
      .refreshToken(state.refreshToken)
      .then(resp => {
        commit(_types.SET_ACCESS_TOKEN, resp.data);
      })
      .catch(error => {
        commit(_types.CLEAR_USER);
        delete axios.defaults.headers.common["Authorization"];
        log.error(error);
      })
      .then(() => commit(_types.END_FETCHING));
  },

  async [_types.FETCH_USER]({ commit }, credentials) {
    commit(_types.START_FETCHING);
    await userApi
      .getMe(credentials)
      .then(resp => commit(_types.SET_USER, resp.data.data))
      .catch(error => log.error(error));
    await userApi
      .getSettings()
      .then(resp => commit(_types.SET_SETTINGS, resp.data.data))
      .catch(error => log.error(error));
    commit(_types.END_FETCHING);
  }
};

const getters = {
  [_types.IS_AUTHENTIFICATED]: state => {
    return !!state.accessToken;
  },

  [_types.HAS_PERMISSIONS]: state => permissions => {
    return permissions.every(permission =>
      state.permissions.includes(permission)
    );
  },

  [_types.HAS_ANY_OF_PERMISSIONS]: state => permissions => {
    return permissions.some(permission =>
      state.permissions.includes(permission)
    );
  },

  [_types.HAS_ANY_PERMISSION]: state => {
    return state.permissions.length > 0;
  }
};

export const userMutationInterceptor = (mutation, state) => {
  switch(mutation.type) {
    case types.CLEAR_USER:
      userHelper.clearLocalStorage(mutation.payload);
      break;
    case types.SET_ACCESS_TOKEN:
      userHelper.writeAccessTokenToLocalStorage(mutation.payload);
      break;
    case types.SET_AUTH:
      userHelper.writeAuthDataToLocalStorage(mutation.payload);
      break;
    case types.SET_USER:
      userHelper.writeUserDataToLocalStorage(mutation.payload);
      break;
    case types.SET_SETTINGS:
      userHelper.writeUserSettingsToLocalStorage(mutation.payload);
      break;
  }
};

export const types = storeUtils.addNamespace(_types, "user");

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
