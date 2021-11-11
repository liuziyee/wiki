<template>
  <el-container>
    <the-header/>
    <router-view/>
    <el-footer></el-footer>
  </el-container>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import TheHeader from '@/components/the-header.vue';
  
  export default defineComponent({
    name: 'app',
    components: {
      TheHeader
    },
  });
</script>

