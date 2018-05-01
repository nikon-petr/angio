import {ANGIO_MOCK} from './instances'

function getAnalyses () {
  return ANGIO_MOCK.get('https://demo6752360.mockable.io/analyse/all')
}

function getDetailAnalyse () {
  return ANGIO_MOCK.get('http://demo6752360.mockable.io/analyse/1')
}

export {
  getAnalyses,
  getDetailAnalyse
}
