import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home.vue'
import Mine from '../views/mine.vue'
import Root from '../views/root/root.vue'
import RootGoods from '../views/root/root-goods.vue'
import RootCategory from '../views/root/root-category.vue'
import RootUser from '../views/root/root-user.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/mine',
    name: 'Mine',
    component: Mine
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

export default router
