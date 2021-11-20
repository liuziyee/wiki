<template>
  <el-container>
    <el-header style="padding: 0 0">
      <el-menu
          class="router-menu"
          mode="horizontal"
          background-color="#708090"
          text-color="#ffffff"
          router="true"
      >
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/root">你瞅啥</el-menu-item>
        <el-menu-item index="/about">关于</el-menu-item>
      </el-menu>
    </el-header>
  </el-container>
</template>

<style scoped>
.el-header {
  padding: 0 0
}
.el-menu {
  border-bottom: 0;
}
</style>

<script lang="ts">
  import { defineComponent, ref } from 'vue';
  
  export default defineComponent({
    name: 'the-header',
  });
</script>