import {ANGIO_MOCK} from './instances'

function getAnalyses () {
  return ANGIO_MOCK.get('https://demo6752360.mockable.io/analyse/all')
}

export {
  getAnalyses
}
