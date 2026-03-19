<template>
  <v-container class="login-container">
    <v-card class="pa-6 login-card">

      <div class="logo-container">
        <img src="/frutero.png" class="logo"/>
      </div>

      <h2 class="mb-4 text-center">Inicio de sesión</h2>

      <v-text-field
        v-model="username"
        label="Usuario"
        outlined
        dense
      />

      <v-text-field
        v-model="password"
        :type="showPassword ? 'text' : 'password'"
        label="Contraseña"
        :append-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
        @click:append="showPassword = !showPassword"
        outlined
        dense
      />

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
export default {
  data() {
    return {
      username: "",
      password: "",
      showPassword: false,
      loading: false,
      errorMessage: "",
      successMessage: ""
    };
  },
  methods: {
    async login() {
      this.errorMessage = "";
      this.successMessage = "";

      if (!this.username || !this.password) {
        this.errorMessage = "Ingresa usuario y contraseña";
        return;
      }

      this.loading = true;

      try {
        // Enviamos credenciales como URL-encoded
        const params = new URLSearchParams();
        params.append("username", this.username);
        params.append("password", this.password);

        const res = await fetch("http://localhost:8081/auth/login", {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: params.toString()
        });

        const data = await res.json();

        if (res.ok) {
          this.successMessage = "Login correcto!";
          setTimeout(() => this.$router.push("/admin"), 500);
        } else {
          this.errorMessage = data.message || "Usuario o contraseña incorrectos";
        }

      } catch (err) {
        console.error("Error login:", err);
        this.errorMessage = "No se pudo conectar con el servidor";
      } finally {
        this.loading = false;
      }
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