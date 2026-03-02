<template>
  <v-container>
    <h1>Shopping Cart</h1>

    <v-list v-if="carrito.length">
      <v-list-item
        v-for="(item, index) in carrito"
        :key="index"
      >
        <v-list-item-content>
          <v-list-item-title>
            {{ item.nombre }}
          </v-list-item-title>
          <v-list-item-subtitle>
            Cantidad: {{ item.cantidad }} |
            Precio: ${{ item.precio }} |
            Subtotal: ${{ item.subtotal }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <h2 v-if="carrito.length">Total: ${{ total }}</h2>

    <v-btn color="red" @click="vaciarCarrito">
      Vaciar carrito
    </v-btn>
  </v-container>
</template>

<script>
export default {
  name: "Cart",
  data() {
    return {
      carrito: [],
      total: 0
    }
  },
  mounted() {
    this.cargarCarrito();
  },
  methods: {
    async cargarCarrito() {
      const res = await fetch("http://localhost:8081/api/carrito");
      this.carrito = await res.json();

      const totalRes = await fetch("http://localhost:8081/api/carrito/total");
      this.total = await totalRes.json();
    },

    async vaciarCarrito() {
      await fetch("http://localhost:8081/api/carrito", {
        method: "DELETE"
      });
      this.cargarCarrito();
    }
  }
}
</script>