import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../views/Home.vue'
import Cart from "@/views/Cart.vue"
import Wishlist from "@/views/Wishlist.vue"
import MeatFish from "@/views/Fruts.vue"
import Vegetables from "@/views/Vegetables.vue"
import Checkout from "@/views/Checkout.vue"
import PagoExitoso from '@/components/PagoExitoso.vue'
import Pedidos from "@/views/Pedidos.vue"

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/cart', name: 'Cart', component: Cart },
  { path: '/wishlist', name: 'Wishlist', component: Wishlist },
  { path: '/category/Fruts', name: 'Fruts', component: MeatFish },
  { path: '/category/vegetables', name: 'Vegetables', component: Vegetables },
  { path: '/checkout', name: 'Checkout', component: Checkout },
  { path: '/pago-exitoso', name: 'PagoExitoso', component: PagoExitoso },
  { path: '/pedidos', name: 'Pedidos', component: Pedidos }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router