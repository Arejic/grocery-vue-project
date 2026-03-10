<template>
  <v-container class="mt-10" style="max-width:500px">
    <h2 class="mb-6">Paga utilizando una nueva tarjeta</h2>

    <v-form ref="form" v-model="valido">
      <!-- Número tarjeta -->
      <v-text-field
        label="Número de Tarjeta"
        placeholder="1234 5678 9012 3456"
        v-model="tarjeta"
        :rules="[tarjetaValida]"
        required
      />

      <v-row>
        <v-col cols="6">
          <v-text-field
            label="Mes Expiración"
            placeholder="MM"
            v-model="mes"
            :rules="[mesValido]"
            required
          />
        </v-col>

        <v-col cols="6">
          <v-text-field
            label="Año Expiración"
            placeholder="YY"
            v-model="anio"
            :rules="[anioValido]"
            required
          />
        </v-col>
      </v-row>

      <v-text-field
        label="Titular de la Tarjeta"
        placeholder="Nombre completo"
        v-model="titular"
        :rules="[titularValido]"
        required
      />

      <v-text-field
        label="Código de Seguridad"
        placeholder="CVV"
        type="password"
        v-model="cvv"
        :rules="[cvvValido]"
        required
      />

      <v-select
        label="Cuotas"
        :items="cuotas"
        v-model="cuotaSeleccionada"
        :rules="[cuotaValida]"
        required
      />

      <v-checkbox
        label="Guardar los datos de esta tarjeta para pagos futuros"
        v-model="guardar"
      />

      <v-row class="mt-6">
        <v-col cols="6">
          <v-btn color="green" block @click="pagar">
            PAGAR AHORA
          </v-btn>
        </v-col>

        <v-col cols="6">
          <v-btn outlined block @click="$router.push('/cart')">
            ATRÁS
          </v-btn>
        </v-col>
      </v-row>

    </v-form>
  </v-container>
</template>

<script>
export default {
  data() {
    const currentYear = new Date().getFullYear() % 100;

    return {
      tarjeta: "",
      mes: "",
      anio: "",
      titular: "",
      cvv: "",
      guardar: false,
      cuotas: ["1 cuota", "3 cuotas", "6 cuotas", "12 cuotas"],
      cuotaSeleccionada: null,
      valido: false,
      currentYear
    };
  },

  methods: {

    tarjetaValida(v) {
      return /^\d{16}$/.test(v.replace(/\s/g, "")) || "Número de tarjeta inválido";
    },

    mesValido(v) {
      const m = parseInt(v);
      return (m >= 1 && m <= 12) || "Mes inválido";
    },

    anioValido(v) {
      const a = parseInt(v);
      return (a >= this.currentYear) || `Año inválido (>= ${this.currentYear})`;
    },

    titularValido(v) {
      return !!v || "El titular no puede estar vacío";
    },

    cvvValido(v) {
      return /^\d{3,4}$/.test(v) || "CVV inválido";
    },

    cuotaValida(v) {
      return !!v || "Debes seleccionar una cuota";
    },

    async pagar() {

      if (!this.$refs.form.validate()) return;

      try {

        const carritoRes = await fetch("http://localhost:8081/api/carrito");
        if (!carritoRes.ok) throw new Error("Error cargando carrito");

        const productosCarrito = await carritoRes.json();

        if (!productosCarrito || productosCarrito.length === 0) {
          alert("El carrito está vacío");
          return;
        }

        const productos = productosCarrito.map(p => ({
          nombre: p.nombre,
          precio: p.precio,
          cantidad: p.cantidad
        }));

        const pedidoRes = await fetch("http://localhost:8081/api/pedidos", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            tarjeta: this.tarjeta,
            titular: this.titular,
            mes: this.mes,
            anio: this.anio,
            cvv: this.cvv,
            cuota: this.cuotaSeleccionada,
            estado: "EN_PROCESO",
            fecha: new Date(),
            productos: productos
          })
        });

        if (!pedidoRes.ok) throw new Error("Error al registrar el pedido");

        await fetch("http://localhost:8081/api/carrito", {
          method: "DELETE"
        });

        this.$router.push("/pago-exitoso");

      } catch (error) {

        console.error("Error al procesar el pago:", error);
        alert("Ocurrió un error al procesar el pago");

      }

    }

  }
};
</script>