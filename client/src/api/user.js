import {ANGIO_PUBLIC} from './instances'

function registerUser (firstName, lastName, email, password) {
  return ANGIO_PUBLIC.post(
    'v1/user/register',
    {
      firstname: firstName,
      lastname: lastName,
      username: email,
      password: password
    })
}

function checkEmail (email) {
  return ANGIO_PUBLIC.post('v1/user/register/check-username', {username: email})
}

export {
  registerUser,
  checkEmail
}
