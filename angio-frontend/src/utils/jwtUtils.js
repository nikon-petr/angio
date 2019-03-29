import JwtDecode from "jwt-decode";

export default {
  decodeJwtToken(token) {
    return new Promise((resolve, reject) => {
      resolve(JwtDecode(token));
    });
  }
};
