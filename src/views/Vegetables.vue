<template>
  <app-frame>
    <v-card flat>
      <v-data-iterator
        :items="vegetables"
        :items-per-page="6"
        :search="search"
      >

        <!-- HEADER -->
        <template v-slot:header>
          <v-toolbar flat>
            <v-text-field
              v-model="search"
              placeholder="Buscar verdura..."
              prepend-inner-icon="mdi-magnify"
              solo
              clearable
              hide-details
              style="max-width: 300px;"
            ></v-text-field>
          </v-toolbar>
        </template>

        <!-- PRODUCTOS -->
        <template v-slot:default="{ items }">
          <v-container fluid>
            <v-row>
              <v-col
                v-for="item in items"
                :key="item.name"
                cols="12"
                sm="6"
                md="4"
              >
                <v-card
                  class="pa-4 product-card"
                  outlined
                >

                  <!-- Imagen -->
                  <div class="text-center">
                    <v-img
                      :src="item.img"
                      contain
                      height="180"
                    ></v-img>
                  </div>

                  <!-- Nombre -->
                  <div class="mt-4 text-h6 font-weight-medium">
                    {{ item.name }}
                  </div>

                  <!-- Peso -->
                  <div class="grey--text text-caption mb-2">
                    {{ item.weight }}
                  </div>

                  <!-- Precio -->
                  <div class="text-h5 font-weight-bold mb-3">
                    ${{ item.price }}
                  </div>

                  <!-- Controles -->
                  <div class="d-flex align-center justify-space-between">

                    <!-- Selector cantidad -->
                    <div class="d-flex align-center quantity-box">
                      <v-btn icon small @click="decrease(item)">
                        <v-icon>mdi-minus</v-icon>
                      </v-btn>

                      <span class="mx-2">{{ item.qty }}</span>

                      <v-btn icon small @click="increase(item)">
                        <v-icon color="green">mdi-plus</v-icon>
                      </v-btn>
                    </div>

                    <!-- Botón carrito -->
                    <v-btn
                      color="green"
                      dark
                      fab
                      small
                      @click="addToCart(item)"
                    >
                      <v-icon>mdi-shopping</v-icon>
                    </v-btn>

                  </div>

                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </template>

      </v-data-iterator>
    </v-card>
  </app-frame>
</template>

<script>
export default {
  name: "VegetablesPage",

  data() {
    return {
      search: "",

      vegetables: [
        {
          name: "Cabbage",
          weight: "1kg",
          price: 13,
          qty: 1,
          img: "/cabbage.webp"
        },
        {
          name: "Tomate",
          weight: "1kg",
          price: 8,
          qty: 1,
          img: "/tomate_0.webp"
        },
        {
          name: "Zanahoria",
          weight: "1kg",
          price: 6,
          qty: 1,
          img: "/Zanahoria.webp"
        },
        {
          name: "Pepino",
          weight: "1kg",
          price: 5,
          qty: 1,
          img: "/pep.png"
        }
      ]
    }
  },

  methods: {

    increase(item) {
      item.qty++
    },

    decrease(item) {
      if (item.qty > 1) {
        item.qty--
      }
    },

    addToCart(item) {
      alert(`${item.qty} ${item.name} añadido(s) al carrito 🛒`)
    }

  }
}
</script>

<style scoped>

.product-card {
  border-radius: 20px;
  transition: 0.3s;
}

.product-card:hover {
  box-shadow: 0 10px 25px rgba(0,0,0,0.08);
}

.quantity-box {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 2px 6px;
}

</style>