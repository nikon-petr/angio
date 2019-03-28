import jwtDecode from "jwt-decode";

export default {
  decodeJwtToken(token) {
    return new Promise((resolve, reject) => {
      resolve(jwtDecode(token));
    });
  }
};
