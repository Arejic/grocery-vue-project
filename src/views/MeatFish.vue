<template>
  <app-frame>
    <v-card flat>
      <v-data-iterator
        :items="products"
        :items-per-page="6"
        :search="search"
      >

        <template v-slot:header>
          <v-toolbar flat>
            <v-text-field
              v-model="search"
              placeholder="Buscar carne..."
              prepend-inner-icon="mdi-magnify"
              solo
              clearable
              hide-details
              style="max-width: 300px;"
            ></v-text-field>
          </v-toolbar>
        </template>

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
                <v-card class="pa-4 product-card" outlined>

                  <div class="text-center">
                    <v-img :src="item.img" contain height="180"></v-img>
                  </div>

                  <div class="mt-4 text-h6 font-weight-medium">
                    {{ item.name }}
                  </div>

                  <div class="grey--text text-caption mb-2">
                    {{ item.weight }}
                  </div>

                  <div class="text-h5 font-weight-bold mb-3">
                    ${{ item.price }}
                  </div>

                  <div class="d-flex align-center justify-space-between">

                    <div class="d-flex align-center quantity-box">
                      <v-btn icon small @click="decrease(item)">
                        <v-icon>mdi-minus</v-icon>
                      </v-btn>

                      <span class="mx-2">{{ item.qty }}</span>

                      <v-btn icon small @click="increase(item)">
                        <v-icon color="red">mdi-plus</v-icon>
                      </v-btn>
                    </div>

                    <v-btn
                      color="red"
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
  name: "MeatPage",

  data() {
    return {
      search: "",

      products: [
        {
          name: "Res Premium",
          weight: "1kg",
          price: 18,
          qty: 1,
          img: "/res.webp"
        },
        {
          name: "Pechuga de Pollo",
          weight: "1kg",
          price: 12,
          qty: 1,
          img: "/pollo.webp"
        },
        {
          name: "Chuleta de Cerdo",
          weight: "1kg",
          price: 14,
          qty: 1,
          img: "/cerdo.webp"
        }
      ]
    }
  },

  methods: {
    increase(item) { item.qty++ },
    decrease(item) { if (item.qty > 1) item.qty-- },
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
<script>
export default {
  name: "FishPage",

  data() {
    return {
      search: "",

      products: [
        {
          name: "Salmón",
          weight: "1kg",
          price: 22,
          qty: 1,
          img: "/salmon.webp"
        },
        {
          name: "Atún Fresco",
          weight: "1kg",
          price: 19,
          qty: 1,
          img: "/atun.webp"
        },
        {
          name: "Tilapia",
          weight: "1kg",
          price: 15,
          qty: 1,
          img: "/tilapia.png"
        }
      ]
    }
  },

  methods: {
    increase(item) { item.qty++ },
    decrease(item) { if (item.qty > 1) item.qty-- },
    addToCart(item) {
      alert(`${item.qty} ${item.name} añadido(s) al carrito 🛒`)
    }
  }
}
</script>