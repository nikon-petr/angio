import root from "loglevel";
import ls from "local-storage";

const log = root.getLogger("store/helpers/user");

export default {
  clearLocalStorage() {
    log.debug("clear local storage");
    ls.remove("accessToken");
    ls.remove("refreshToken");
    ls.remove("id");
    ls.remove("email");
    ls.remove("firstname");
    ls.remove("lastname");
    ls.remove("patronymic");
    ls.remove("permissions");
  },

  writeAccessTokenToLocalStorage({ access_token }) {
    log.debug("save access token to local storage");
    ls.set("accessToken", access_token);
  },

  writeAuthDataToLocalStorage({ access_token, refresh_token }) {
    log.debug("save access and refresh token to local storage");
    ls.set("accessToken", access_token);
    ls.set("refreshToken", refresh_token);
  },

  writeUserDataToLocalStorage(user) {
    log.debug("save user data to local storage");
    ls.set("id", user.id);
    ls.set("email", user.email);
    ls.set("firstname", user.firstname);
    ls.set("lastname", user.lastname);
    ls.set("patronymic", user.patronymic);
    ls.set("permissions", user.permissions);
    ls.set("firstname", user.firstname);
  },

  writeUserPermissionsToLocalStorage({permissions}) {
    ls.set("permissions", permissions);
  },

  writeUserSettingsToLocalStorage(settings) {
    log.debug("save user settings to local storage");
    ls.set("settings.darkThemeEnabled", settings.darkThemeEnabled);
    ls.set("settings.locale", settings.locale);
  }
};
