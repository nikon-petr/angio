import {ANGIO_PUBLIC} from './instances'

const API_URL_ANALYSES = 'https://demo6752360.mockable.io/analyse/all'

export default{
  getAnalyses () {
    return ANGIO_PUBLIC.get(API_URL_ANALYSES)
  }
}
