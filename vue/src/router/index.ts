import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import Home from '../views/home.vue'
import Mine from '../views/mine.vue'
import Root from '../views/root/root.vue'
import RootGoods from '../views/root/root-goods.vue'
import RootCategory from '../views/root/root-category.vue'
import RootUser from '../views/root/root-user.vue'
import Info from '../views/info.vue'
import store from "@/store";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/mine',
    name: 'Mine',
    component: Mine,
    meta: {
      authRequire: true
    }
  },
  {
    path: '/info',
    name: 'Info',
    component: Info
  },
  {
    path: '/root',
    name: 'Root',
    component: Root,
    meta: {
      authRequire: true
    },
    children: [
      {
        path: 'goods',
        name: 'Goods',
        component: RootGoods
      },
      {
        path: 'category',
        name: 'Category',
        component: RootCategory
      },
      {
        path: 'user',
        name: 'User',
        component: RootUser
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some((route) => route.meta.authRequire)) {
    const token = store.state.token;
    if (typeof token == 'string' && token.length > 0) {
      next();
      return;
    }
    next("/");
  } else {
    next();
  }
})

export default router
