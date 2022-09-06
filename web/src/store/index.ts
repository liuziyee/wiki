import { createStore } from 'vuex'

const store = createStore({
  state: {
    user: {},
    token: ''
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    setToken(state, token) {
      state.token = token;
    }
  },
  actions: {
  },
  modules: {
  }
});

export default store;
