<template>
  <v-app-bar app color="white" flat>

    <!-- Logo -->
    <v-badge color="#D5F0DB" dot>
      <v-img src="1.png" contain width="30"></v-img>
    </v-badge>

    <!-- Título -->
    <v-toolbar-title class="ml-4">
      <span class="green--text">e</span><strong>Grocery</strong>
    </v-toolbar-title>

    <!-- Esto empuja todo a la derecha -->
    <v-spacer></v-spacer>

    <!-- Wishlist -->
    <v-badge color="#FF6D59" overlap content="2" class="mr-2 mt-1">
      <v-avatar color="#FFF0EE" size="40">
        <v-btn icon to="/wishlist">
          <v-icon x-small color="#FF6D59">far fa-heart</v-icon>
        </v-btn>
      </v-avatar>
    </v-badge>

    <!-- Cart -->
    <v-badge color="#41AB55" overlap content="3" class="mr-2 mt-1">
      <v-avatar color="#ECF7EE" size="40">
        <v-btn icon to="/cart">
          <v-icon x-small color="#41AB55">fas fa-shopping-cart</v-icon>
        </v-btn>
      </v-avatar>
    </v-badge>

    <v-btn icon>
      <v-icon x-small color="#878A94">fas fa-chevron-down</v-icon>
    </v-btn>

  </v-app-bar>
</template>

<script>
export default {};
</script>

<style>
.v-toolbar__title {
  font-size: 1rem !important;
}
</style>