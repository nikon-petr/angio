import axios from 'axios'

const ANGIO_AUTHORIZED = axios.create({
  baseURL: 'https://localhost/api',
  headers: {
    Authorization: 'Bearer ' + localStorage.getItem('bearer-token')
  },
  transformRequest: [function (data, headers) {
    headers.Authorization = 'Bearer ' + localStorage.getItem('bearer-token')
    return data
  }]
})

const ANGIO_PUBLIC = axios.create({
  baseURL: 'https://localhost/api'
})

export {
  ANGIO_AUTHORIZED,
  ANGIO_PUBLIC
}
