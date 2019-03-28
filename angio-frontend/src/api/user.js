import axios from "axios";
import root from "loglevel";

const log = root.getLogger("api/user");

export default {
  getToken({ username, password }) {
    const data = {
      client_id: process.env.VUE_APP_CLIENT_ID,
      client_secret: process.env.VUE_APP_CLIENT_SECRET,
      grant_type: "password",
      username,
      password
    };
    log.debug(`create getToken request with data: ${data}`);
    return axios.post("/oauth/token", data);
  },

  refreshToken(refreshToken) {
    const data = {
      client_id: process.env.VUE_APP_CLIENT_ID,
      client_secret: process.env.VUE_APP_CLIENT_SECRET,
      grant_type: "refresh_token",
      refresh_token: refreshToken
    };
    log.debug(`create refreshToken request with data: ${data}`);
    return axios.post("/oauth/token", data);
  },

  getMe() {
    log.debug("create getMe request");
    return axios.get("/user/me");
  },

  getSettings() {
    log.debug("create getSettings request");
    return axios.get("/user/me/settings");
  }
};
