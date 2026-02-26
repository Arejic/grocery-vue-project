<template>
  <v-app>

    <Navbar />

    <v-main>
      <router-view/>
    </v-main>

    <!-- Global -->
    <AIAssistant />

  </v-app>
</template>

<script>
import Navbar from "@/components/Navbar.vue";
import AIAssistant from "@/components/AIAssistant.vue";

export default {
  components: { Navbar, AIAssistant }
};
</script>