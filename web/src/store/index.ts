import { createStore } from 'vuex'

const store = createStore({
  state: {
    authUser: {}
  },
  mutations: {
    setAuthUser(state, user) {
      state.authUser = user;
    }
  },
  actions: {
  },
  modules: {
  }
});

export default store;
