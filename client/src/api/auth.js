import {ANGIO_PUBLIC, ANGIO_AUTHORIZED} from './instances'

function getToken (email, password) {
  return ANGIO_PUBLIC.post('v1/auth/token', {username: email, password: password})
}

function signOut () {
  return ANGIO_AUTHORIZED.post('v1/auth/logout')
}

export {
  getToken,
  signOut
}
