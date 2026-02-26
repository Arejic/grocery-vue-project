<template>
  <div>
    <!-- Botón flotante -->
    <v-btn
      fab
      fixed
      bottom
      right
      color="green"
      dark
      @click="open = true"
    >
      <v-icon>mdi-robot</v-icon>
    </v-btn>

    <!-- Ventana del chat -->
    <v-dialog v-model="open" width="400">
      <v-card>
        <v-card-title class="green white--text">
          Asistente Virtual
        </v-card-title>

        <v-card-text style="height:300px; overflow-y:auto;">
          <div v-for="(msg, i) in messages" :key="i">
            <strong>{{ msg.role }}:</strong>
            {{ msg.text }}
          </div>
        </v-card-text>

        <v-card-actions>
          <v-text-field
            v-model="input"
            label="Escribe tu mensaje"
            dense
            @keyup.enter="sendMessage"
          ></v-text-field>

          <v-btn color="green" dark @click="sendMessage">
            Enviar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  name: "AIAssistant",

  data() {
    return {
      open: false,
      input: "",
      messages: []
    };
  },

  methods: {
    async sendMessage() {
      if (!this.input) return;

      const userMessage = this.input;

      this.messages.push({
        role: "Usuario",
        text: userMessage
      });

      const response = await fetch("http://localhost:8080/chat", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: userMessage })
      });

      const data = await response.json();

      this.messages.push({
        role: "IA",
        text: data.reply
      });

      if (data.action === "addToCart") {
        this.$store.commit("ADD_TO_CART", data.product);
      }

      this.input = "";
    }
  }
};
</script>