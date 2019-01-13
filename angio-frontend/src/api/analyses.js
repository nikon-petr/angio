import {ANGIO_MOCK} from './instances'

function getDetailAnalyse () {
  return ANGIO_MOCK.get('http://demo6752360.mockable.io/analyse/1')
}

export {
  getDetailAnalyse
}
