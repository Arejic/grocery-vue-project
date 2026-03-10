<template>
<v-container>

<h1 class="mb-6">📦 Mis Pedidos</h1>

<v-alert v-if="!pedidos.length" type="info">
No tienes pedidos todavía
</v-alert>

<v-card
v-for="pedido in pedidos"
:key="pedido.id"
class="mb-4 pa-4"
outlined
>

<div class="d-flex justify-space-between">

<div>
<strong>Pedido #{{ pedido.id }}</strong>
<div class="grey--text text-caption">
{{ new Date(pedido.fecha).toLocaleDateString() }}
</div>
</div>

<v-chip color="orange" dark>
🚚 En proceso de envío
</v-chip>

</div>

<v-divider class="my-3"></v-divider>

<div
v-for="prod in pedido.productos"
:key="prod.nombre"
class="d-flex justify-space-between"
>

<span>{{ prod.nombre }} x{{ prod.cantidad }}</span>

<span>${{ (prod.precio * prod.cantidad).toFixed(2) }}</span>

</div>

</v-card>

</v-container>
</template>

<script>
export default{

name:"Pedidos",

data(){
return{
pedidos:[]
}
},

mounted(){
this.cargarPedidos()
},

methods:{

async cargarPedidos(){

try{

const res = await fetch("http://localhost:8081/api/pedidos")

if(!res.ok) throw new Error("Error cargando pedidos")

this.pedidos = await res.json()

}catch(e){
console.error(e)
}

}

}

}
</script>