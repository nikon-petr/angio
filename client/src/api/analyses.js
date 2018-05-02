import {ANGIO_MOCK, ANGIO_PUBLIC} from './instances'

function getAnalyses () {
  return ANGIO_PUBLIC.get('v1/all')
}

function getDetailAnalyse () {
  return ANGIO_MOCK.get('http://demo6752360.mockable.io/analyse/1')
}

function newAnalyse (newAnalyse) {
  return ANGIO_PUBLIC.post('v1/new', newAnalyse)
}

function checkPatientByPolicy (policy) {
  return ANGIO_PUBLIC.post('v1/check_policy', {policy: policy})
}

export {
  getAnalyses,
  getDetailAnalyse,
  newAnalyse,
  checkPatientByPolicy
}
