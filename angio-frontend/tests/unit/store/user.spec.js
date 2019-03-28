import Vuex from "vuex";
import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import ls from "local-storage";
import flushPromises from "flush-promises";
import { createLocalVue } from "@vue/test-utils";
import { cloneDeep } from "lodash";
import { storeConfig } from "@/store/index";
import { types as userTypes } from "@/store/modules/user";

describe("store/modules/user.js", () => {
  const accessToken = "jwt1";
  const refreshToken = "jwt2";
  const credentials = {
    username: "user@example.com",
    password: "password"
  };
  const user = {
    data: {
      id: "ae158b49-3040-4c7f-8837-60a55dc089f6",
      email: "user@example.com",
      firstname: "Иван",
      lastname: "Иванов",
      patronymic: "Иванович",
      permissions: ["VIEW_ANALYSE"]
    }
  };
  const settings = {
    data: {
      darkThemeEnabled: true
    }
  };
  const mock = new MockAdapter(axios);
  axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;

  beforeEach(() => {
    axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;
    ls.clear();
    mock.reset();
  });

  it("initialize auth and user from local storage", async () => {
    //given
    ls.set("accessToken", accessToken);
    ls.set("refreshToken", refreshToken);
    ls.set("id", user.data.id);
    ls.set("email", user.data.email);
    ls.set("firstname", user.data.firstname);
    ls.set("lastname", user.data.lastname);
    ls.set("patronymic", user.data.patronymic);
    ls.set("permissions", user.data.permissions);
    ls.set("settings.darkThemeEnabled", settings.data.darkThemeEnabled);

    // when
    const localVue = createLocalVue();
    localVue.use(Vuex);
    const store = new Vuex.Store(cloneDeep(storeConfig));

    // then
    expect(store.getters[userTypes.IS_AUTHENTIFICATED]).toBeTruthy();
    expect(store.state.user.accessToken).toBe(accessToken);
    expect(store.state.user.refreshToken).toBe(refreshToken);
    expect(store.state.user.id).toBe(user.data.id);
    expect(store.state.user.email).toBe(user.data.email);
    expect(store.state.user.firstname).toBe(user.data.firstname);
    expect(store.state.user.lastname).toBe(user.data.lastname);
    expect(store.state.user.patronymic).toBe(user.data.patronymic);
    expect(store.state.user.permissions).toEqual(user.data.permissions);
    expect(store.state.user.settings.darkThemeEnabled).toEqual(
      settings.data.darkThemeEnabled
    );
  });

  it("auth user when credentials is ok", async () => {
    const localVue = createLocalVue();
    localVue.use(Vuex);
    const store = new Vuex.Store(cloneDeep(storeConfig));

    // given
    const mock = new MockAdapter(axios);
    mock
      .onPost("/oauth/token")
      .reply(200, { access_token: accessToken, refresh_token: refreshToken })
      .onGet("/user/me")
      .reply(200, user)
      .onGet("/user/me/settings")
      .reply(200, settings);

    // when
    await store.dispatch(userTypes.AUTH_USER, credentials);

    // then
    await flushPromises();
    expect(store.getters[userTypes.IS_AUTHENTIFICATED]).toBeTruthy();
    expect(store.state.user.accessToken).toBe(accessToken);
    expect(store.state.user.refreshToken).toBe(refreshToken);
    expect(store.state.user.id).toBe(user.data.id);
    expect(store.state.user.email).toBe(user.data.email);
    expect(store.state.user.firstname).toBe(user.data.firstname);
    expect(store.state.user.lastname).toBe(user.data.lastname);
    expect(store.state.user.patronymic).toBe(user.data.patronymic);
    expect(store.state.user.permissions).toEqual(user.data.permissions);
    expect(store.state.user.settings.darkThemeEnabled).toEqual(
      settings.data.darkThemeEnabled
    );

    expect(ls.get("accessToken")).toBe(accessToken);
    expect(ls.get("refreshToken")).toBe(refreshToken);
    expect(ls.get("id")).toBe(user.data.id);
    expect(ls.get("email")).toBe(user.data.email);
    expect(ls.get("firstname")).toBe(user.data.firstname);
    expect(ls.get("lastname")).toBe(user.data.lastname);
    expect(ls.get("patronymic")).toBe(user.data.patronymic);
    expect(ls.get("permissions")).toEqual(user.data.permissions);
    expect(ls.get("settings.darkThemeEnabled")).toEqual(
      settings.data.darkThemeEnabled
    );
  });
});
