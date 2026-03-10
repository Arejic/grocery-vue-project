<template>
  <v-app-bar app color="white" flat class="app-bar-spaced">

    <!-- Logo + redirección al inicio -->
    <v-btn icon to="/">
      <v-badge color="#D5F0DB" dot offset-x="5" offset-y="5">
        <v-img src="1.png" contain width="40"></v-img>
      </v-badge>
    </v-btn>

    <!-- Título que también redirige -->
    <v-toolbar-title class="ml-2">
      <v-btn text to="/">
        <span class="green--text">e</span><strong>Grocery</strong>
      </v-btn>
    </v-toolbar-title>

    <v-spacer></v-spacer>

    <!-- Wishlist -->
    <v-badge color="#FF6D59" overlap content="2" class="mr-3">
      <v-avatar color="#FFF0EE" size="40">
        <v-btn icon to="/wishlist">
          <v-icon size="28" color="#FF6D59">far fa-heart</v-icon>
        </v-btn>
      </v-avatar>
    </v-badge>

    <!-- Cart -->
    <v-badge color="#41AB55" overlap content="3" class="mr-3">
      <v-avatar color="#ECF7EE" size="40">
        <v-btn icon to="/cart">
          <v-icon size="28" color="#41AB55">fas fa-shopping-cart</v-icon>
        </v-btn>
      </v-avatar>
    </v-badge>

    <!-- Pedidos -->
    <v-avatar color="#FFF8E1" size="40" class="mr-3">
      <v-btn icon to="/pedidos">
        <v-icon size="28" color="#FFB300">fas fa-box-open</v-icon>
      </v-btn>
    </v-avatar>

    <!-- Perfil -->
    <v-avatar color="#F3F4F6" size="40">
      <v-btn icon>
        <v-icon size="28" color="#878A94">fas fa-user-circle</v-icon>
      </v-btn>
    </v-avatar>

  </v-app-bar>
</template>

<script>
export default {};
</script>

<style>
.v-toolbar__title {
  font-size: 1.2rem !important;
}

.app-bar-spaced {
  margin-top: 10px;
}
</style>