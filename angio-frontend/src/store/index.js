import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

// example: https://github.com/vuejs/vuex/tree/dev/examples/shopping-cart
export default new Vuex.Store({
  modules: {
  },
  strict: debug
})
