<template>
  <v-container>
    <h1>Carrito de Compras</h1>

    <v-alert v-if="!carrito.length" type="info" class="mt-4">
      El carrito está vacío
    </v-alert>

    <v-row v-else dense>
      <v-col v-for="item in carrito" :key="item.nombre" cols="12">
        <v-card class="pa-2 product-card d-flex justify-space-between align-center" outlined>
          <div>
            <div class="text-subtitle-1 font-weight-medium">{{ item.nombre }}</div>
            <div class="grey--text text-caption">
              Precio: ${{ item.precio }} | Subtotal: ${{ (item.precio * item.cantidad).toFixed(2) }}
            </div>
          </div>

          <!-- Controles de cantidad -->
          <div class="d-flex align-center">
            <v-btn icon small @click="modificarCantidad(item, -1)">
              <v-icon small>mdi-minus</v-icon>
            </v-btn>
            <span class="mx-2">{{ item.cantidad }}</span>
            <v-btn icon small @click="modificarCantidad(item, 1)">
              <v-icon small color="green">mdi-plus</v-icon>
            </v-btn>

            <v-btn icon color="red" @click="eliminarProducto(item.nombre)">
              <v-icon small>mdi-delete</v-icon>
            </v-btn>
          </div>
        </v-card>
      </v-col>
    </v-row>

    <h2 v-if="carrito.length" class="mt-4">Total: ${{ total.toFixed(2) }}</h2>

    <div v-if="carrito.length" class="mt-4">
      <v-btn color="red" @click="vaciarCarrito">Vaciar carrito</v-btn>
      <v-btn color="green" class="ml-4" @click="$router.push('/checkout')">Pagar</v-btn>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "Cart",
  data() {
    return {
      carrito: [],
      total: 0
    };
  },
  mounted() {
    this.cargarCarrito();
  },
  methods: {
    async cargarCarrito() {
      try {
        const res = await fetch("http://localhost:8081/api/carrito");
        if (!res.ok) throw new Error("Error cargando carrito");
        let data = await res.json();

        // Fusionar items repetidos
        const fusionado = {};
        data.forEach(p => {
          if (fusionado[p.nombre]) {
            fusionado[p.nombre].cantidad += p.cantidad;
          } else {
            fusionado[p.nombre] = { ...p };
          }
        });
        this.carrito = Object.values(fusionado);

        const totalRes = await fetch("http://localhost:8081/api/carrito/total");
        if (!totalRes.ok) throw new Error("Error cargando total");
        this.total = await totalRes.json();

      } catch (error) {
        console.error(error);
      }
    },

    async modificarCantidad(item, delta) {
      const nuevaCantidad = item.cantidad + delta;
      if (nuevaCantidad < 1) return;

      try {
        await fetch("http://localhost:8081/api/carrito/agregar", {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({
            nombre: item.nombre,
            cantidad: delta
          })
        });
        item.cantidad = nuevaCantidad;
        this.cargarCarrito();
      } catch (error) {
        console.error(error);
        alert("No se pudo actualizar la cantidad");
      }
    },

    async eliminarProducto(nombre) {
      try {
        const item = this.carrito.find(p => p.nombre === nombre);
        if (!item) return;

        await fetch(`http://localhost:8081/api/carrito/${item.id}`, { method: "DELETE" });
        this.cargarCarrito();
      } catch (error) {
        console.error(error);
      }
    },

    async vaciarCarrito() {
      try {
        await fetch("http://localhost:8081/api/carrito", { method: "DELETE" });
        this.cargarCarrito();
      } catch (error) {
        console.error(error);
      }
    }
  }
};
</script>

<style scoped>
.product-card {
  transition: 0.2s;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px;           /* más delgado que antes */
  min-height: 60px;       /* altura más compacta */
}
.product-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0,0,0,0.08);
}
.mx-2 {
  min-width: 28px;
  text-align: center;
  font-weight: bold;
}
</style>