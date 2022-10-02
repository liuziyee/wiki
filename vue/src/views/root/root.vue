<template>
  <el-container>
    <el-aside width="150px">
      <el-menu style="border-right: 0" router>
        <el-menu-item index="/root/goods">
          <el-check-tag>好物管理</el-check-tag>
        </el-menu-item>
        <el-menu-item index="/root/category">
          <el-check-tag>分类管理</el-check-tag>
        </el-menu-item>
        <el-menu-item index="/root/user">
          <el-check-tag>用户管理</el-check-tag>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <router-view/>
  </el-container>
</template>

<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
    name: 'Root',
  })
</script>