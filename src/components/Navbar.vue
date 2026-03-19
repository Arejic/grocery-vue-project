<template>
  <v-app-bar app color="white" flat class="app-bar-spaced">

    <!-- Logo -->
    <v-btn icon to="/">
      <v-badge color="#D5F0DB" dot offset-x="5" offset-y="5">
        <v-img src="1.png" contain width="40"></v-img>
      </v-badge>
    </v-btn>

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

    <!-- PERFIL -->
    <v-menu
      v-model="menu"
      :close-on-content-click="false"
      offset-y
      bottom
      left
      transition="scale-transition"
      min-width="300"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-avatar size="40" v-bind="attrs" v-on="on">
          <v-img :src="usuario.imagen || 'https://i.pravatar.cc/150'" />
        </v-avatar>
      </template>

      <v-card class="pa-4 text-center">

        <!-- 👀 VISTA NORMAL -->
        <div v-if="!editando">

          <v-avatar size="70" class="mb-3">
            <v-img :src="usuario.imagen || 'https://i.pravatar.cc/150'" />
          </v-avatar>

          <div class="font-weight-bold text-subtitle-1">{{ usuario.nombre }}</div>
          <div class="grey--text text-caption mb-3">{{ usuario.email }}</div>

          <v-divider class="mb-3"></v-divider>

          <v-btn block text @click="editando = true">✏️ Editar perfil</v-btn>
          <v-btn block color="red" dark @click="logout">🚪 Cerrar sesión</v-btn>

        </div>

        <!-- ✏️ VISTA EDITAR -->
        <div v-else>
          <v-text-field v-model="usuario.imagen" label="URL Imagen" dense />
          <v-text-field v-model="usuario.nombre" label="Nombre" dense />
          <v-text-field v-model="usuario.email" label="Correo" dense />

          <v-btn block color="green" dark class="mb-2" @click="guardarUsuario">💾 Guardar</v-btn>
          <v-btn block text @click="editando = false">Cancelar</v-btn>
        </div>

      </v-card>
    </v-menu>

  </v-app-bar>
</template>

<script>
export default {
  data() {
    return {
      menu: false,
      editando: false,
      usuario: {
        nombre: "",
        email: "",
        imagen: ""
      }
    }
  },

  mounted() {
    this.cargarUsuario();
  },

  methods: {
    cargarUsuario() {
      const user = JSON.parse(localStorage.getItem("user"));
      if (user) {
        this.usuario = user;
      } else {
        this.usuario = {
          nombre: "Invitado",
          email: "",
          imagen: ""
        }
      }
    },

    guardarUsuario() {
      localStorage.setItem("user", JSON.stringify(this.usuario));
      this.editando = false;
      this.menu = false; // cerrar menú al guardar
    },

    logout() {
      localStorage.removeItem("user");
      this.menu = false;
      this.$router.push("/login");
    }
  }
};
</script>

<style>
.v-toolbar__title { font-size: 1.2rem !important; }
.app-bar-spaced { margin-top: 10px; }
</style>