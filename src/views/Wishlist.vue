<template>
<v-container>

<h1 class="mb-6">Mis favoritos ❤️</h1>

<v-alert v-if="wishlist.length === 0" type="info">
No tienes productos guardados
</v-alert>

<v-card
v-for="item in wishlist"
:key="item.id"
class="wishlist-item mb-3 pa-3"
outlined
>

<v-row align="center">

<!-- Imagen -->
<v-col cols="2">
<v-img :src="item.img" height="60" contain/>
</v-col>

<!-- Nombre -->
<v-col cols="5">
<div class="font-weight-medium">{{ item.name }}</div>
<div class="grey--text text-caption">{{ item.weight }}</div>
</v-col>

<!-- Precio -->
<v-col cols="2">
${{ item.price }}
</v-col>

<!-- Botones -->
<v-col cols="3" class="d-flex">

<!-- agregar al carrito -->
<v-btn
color="green"
small
class="mr-2"
@click="addToCart(item)"
>
<v-icon left>mdi-cart</v-icon>
Agregar
</v-btn>

<!-- eliminar -->
<v-btn icon @click="remove(item.id)">
<v-icon color="red">mdi-delete</v-icon>
</v-btn>

</v-col>

</v-row>

</v-card>

</v-container>
</template>

<script>
export default {

data(){
return{
wishlist:[]
}
},

methods:{

loadWishlist(){
this.wishlist = JSON.parse(localStorage.getItem("wishlist")) || []
},

remove(id){

this.wishlist = this.wishlist.filter(p => p.id !== id)

localStorage.setItem("wishlist", JSON.stringify(this.wishlist))

},

async addToCart(item){

try{

const response = await fetch("http://localhost:8081/api/carrito/agregar",{
method:"POST",
headers:{ "Content-Type":"application/x-www-form-urlencoded" },
body:new URLSearchParams({
nombre:item.name,
cantidad:1
})
})

if(!response.ok) throw new Error("Error agregando al carrito")

alert("Producto agregado al carrito 🛒")

}catch(error){
console.error(error)
alert("No se pudo agregar al carrito")
}

}

},

mounted(){
this.loadWishlist()
}

}
</script>

<style scoped>

.wishlist-item{
border-radius:10px;
}

</style>