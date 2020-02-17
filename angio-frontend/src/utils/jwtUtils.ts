import JwtDecode from 'jwt-decode';

export default {
    decodeJwtToken(token: string): Promise<any> {
        return new Promise((resolve, reject) => {
            resolve(JwtDecode(token));
        });
    },
};
