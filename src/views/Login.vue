<template>
  <v-container class="login-container">
    <v-card class="pa-6 login-card">

      <div class="logo-container">
        <img src="/frutero.png" class="logo"/>
      </div>

      <h2 class="mb-4 text-center">Inicio de sesión</h2>

      <!-- Email -->
      <v-text-field
        v-model="email"
        label="Correo"
        outlined
        dense
      />

      <!-- Contraseña -->
      <v-text-field
        v-model="password"
        :type="showPassword ? 'text' : 'password'"
        label="Contraseña"
        :append-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
        @click:append="showPassword = !showPassword"
        outlined
        dense
      />

      <!-- Botón -->
      <v-btn
        color="green"
        block
        class="mt-4"
        :loading="loading"
        :disabled="loading"
        @click="login"
      >
        Entrar
      </v-btn>

      <!-- Mensajes -->
      <v-alert
        v-if="errorMessage"
        type="error"
        class="mt-4"
        dense
        outlined
      >
        {{ errorMessage }}
      </v-alert>

      <v-alert
        v-if="successMessage"
        type="success"
        class="mt-4"
        dense
        outlined
      >
        {{ successMessage }}
      </v-alert>

    </v-card>
  </v-container>
</template>

<script>
import store from "@/store"; // Vuex store

export default {
  data() {
    return {
      email: "",
      password: "",
      showPassword: false,
      loading: false,
      errorMessage: "",
      successMessage: ""
    };
  },
  methods: {
    // Función de redirección según rol
    redirectUser(rol) {
      if (rol === "admin") {
        this.$router.push("/admin");
      } else if (rol === "cliente") {
        this.$router.push("/"); // Home para cliente
      } else {
        this.$router.push("/");
      }
    },

    // Login
    async login() {
      this.errorMessage = "";
      this.successMessage = "";
      this.loading = true;

      if (!this.email || !this.password) {
        this.errorMessage = "Ingresa correo y contraseña";
        this.loading = false;
        return;
      }

      try {
        const res = await fetch("http://localhost:8081/auth/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: this.email, password: this.password })
        });

        const data = await res.json();

        if (!res.ok) {
          this.errorMessage = data.message || "Correo o contraseña incorrectos";
          return;
        }

        // Guardar usuario en Vuex y localStorage
        store.dispatch("login", data.user);

        this.successMessage = "Login correcto!";

        // Redirección según rol
        setTimeout(() => this.redirectUser(data.user.rol), 500);

      } catch (err) {
        console.error(err);
        this.errorMessage = "Error de conexión con el servidor";
      } finally {
        this.loading = false;
      }
    }
  },
  mounted() {
    // Si ya hay usuario logueado, cargarlo y redirigir según rol
    const user = localStorage.getItem("user");
    if (user) {
      const parsedUser = JSON.parse(user);
      store.dispatch("login", parsedUser);
      this.redirectUser(parsedUser.rol);
    }
  }
};
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #e8f5e9;
}

.login-card {
  width: 100%;
  max-width: 400px;
  text-align: center;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.logo-container {
  display: flex;
  justify-content: center;
  margin-bottom: 25px;
}

.logo {
  width: 140px;
  animation: float 3s ease-in-out infinite;
}

@keyframes float {
  0% { transform: translateY(0px); }
  50% { transform: translateY(-12px); }
  100% { transform: translateY(0px); }
}
</style>