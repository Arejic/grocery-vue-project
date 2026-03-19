import Vue from "vue";
import VueRouter from "vue-router";

import Home from "@/views/Home.vue";
import Cart from "@/views/Cart.vue";
import Wishlist from "@/views/Wishlist.vue";
import Fruts from "@/views/Fruts.vue";
import Vegetables from "@/views/Vegetables.vue";
import Checkout from "@/views/Checkout.vue";
import PagoExitoso from "@/components/PagoExitoso.vue";
import Pedidos from "@/views/Pedidos.vue";
import AdminPanel from "@/views/AdminPanel.vue";
import Login from "@/views/Login.vue";

Vue.use(VueRouter);

const routes = [
  { path: "/", component: Home },

  { path: "/cart", component: Cart },

  { path: "/wishlist", component: Wishlist },

  { path: "/category/fruts", component: Fruts },

  { path: "/category/vegetables", component: Vegetables },

  { path: "/checkout", component: Checkout },

  { path: "/pago-exitoso", component: PagoExitoso },

  { path: "/pedidos", component: Pedidos },

  {
    path: "/login",
    component: Login,
    meta: {
      hideNavbar: true,
      hideChat: true // 🔥 ocultar chatbot en login
    }
  },

  {
    path: "/admin",
    component: AdminPanel,
    meta: {
      hideNavbar: true,
      hideChat: true // 🔥 ocultar chatbot en admin
    }
  }
];

const router = new VueRouter({
  mode: "history",
  routes
});

export default router;