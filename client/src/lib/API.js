import ezFetch from 'ez-fetch'

const API_URL_ANALYSES = 'https://demo6752360.mockable.io/analyse/all'

export default{
  getAnalyses(){
    return ezFetch.get(API_URL_ANALYSES)
  }
}
