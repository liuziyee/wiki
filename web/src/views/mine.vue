<template>
  <a-layout-content
      :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
  >
    <el-check-tag checked>{{user.name}}</el-check-tag>
  </a-layout-content>
</template>

<script lang="ts">
import {defineComponent,computed} from 'vue';
import store from "@/store";

export default defineComponent({
  name: 'Mine',
  setup() {
    const user = computed(() => store.state.user);
    
    return {
      user
    }
  }
})
</script>