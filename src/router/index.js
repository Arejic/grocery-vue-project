import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Cart from "@/views/Cart.vue";
import Wishlist from "@/views/Wishlist.vue";
import MeatFish from "@/views/MeatFish.vue";
import Vegetables from "@/views/Vegetables.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart
  },
  {
    path: '/wishlist',
    name: 'Wishlist',
    component: Wishlist
  },
  {
    path: '/category/meat-fish',
    name: 'MeatFish',
    component: MeatFish
  },
  {
    path: '/category/vegetables',
    name: 'Vegetables',
    component: Vegetables
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router