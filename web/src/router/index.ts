import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home.vue'
import About from '../views/about.vue'
import Root from '../views/root/root.vue'
import RootGoods from '../views/root/root-goods.vue'
import RootCategory from '../views/root/root-category.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    // component: () => import(/* webpackChunkName: "about" */ '../views/about.vue')
  },
  {
    path: '/root',
    name: 'Root',
    component: Root,
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
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
