import ls from "local-storage";

export default {
    writeAccessTokenToLocalStorage({access_token}) {
        ls.set("accessToken", access_token);
    },

    writeAuthDataToLocalStorage({access_token, refresh_token}) {
        ls.set("accessToken", access_token);
        ls.set("refreshToken", refresh_token);
    },

    writeUserDataToLocalStorage(user) {
        ls.set("id", user.id);
        ls.set("email", user.email);
        ls.set("firstname", user.firstname);
        ls.set("lastname", user.lastname);
        ls.set("patronymic", user.patronymic);
        ls.set("permissions", user.permissions);
        ls.set("firstname", user.firstname);
    },

    writeUserSettingsToLocalStorage(settings) {
        ls.set("settings.darkThemeEnabled", settings.darkThemeEnabled);
    }
}