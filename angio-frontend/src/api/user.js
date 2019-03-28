import axios from "axios";

export default {
  getToken({ username, password }) {
    const data = {
      client_id: process.env.VUE_APP_CLIENT_ID,
      client_secret: process.env.VUE_APP_CLIENT_SECRET,
      grant_type: "password",
      username,
      password
    };
    return axios.post("/oauth/token", data);
  },

  refreshToken(refreshToken) {
    const data = {
      client_id: process.env.VUE_APP_CLIENT_ID,
      client_secret: process.env.VUE_APP_CLIENT_SECRET,
      grant_type: "refresh_token",
      refresh_token: refreshToken
    };
    return axios.post("/oauth/token", data);
  },

  getMe() {
    return axios.get("/user/me");
  },

  getSettings() {
    return axios.get("/user/me/settings");
  }
};
