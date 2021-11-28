<template>
  <el-container>
    <el-header style="padding: 0 0">
      <el-menu
          class="router-menu"
          mode="horizontal"
          background-color="#708090"
          text-color="#ffffff"
          router
      >
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/root">空腹虫</el-menu-item>
        <el-menu-item index="" @click="() => {authUser.id ? this.$router.push({path: '/mine'}) : dialogVisible = true;}">我的</el-menu-item>
      </el-menu>
    </el-header>

    <el-dialog
        v-model="dialogVisible"
        width="25%"
    >
      <el-form :model="user"
               :rules="rules"
               ref="loginForm"
               label-position="right"
               label-width="80px"
      >
        <el-form-item label="登录名" prop="loginName">
          <el-input v-model="user.loginName"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-check-tag style="margin-right: 5px" @change="dialogVisible = false">不了</el-check-tag>
        <el-check-tag @change="handleLogin">登录</el-check-tag>
      </template>
    </el-dialog>
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
import {defineComponent, onMounted, ref} from 'vue';
import {ElNotification} from "element-plus";
import axios from "axios";
import store from "@/store";

declare let hexMd5: any;
declare let KEY: any;
  
  export default defineComponent({
    name: 'the-header',
    setup() {
      const dialogVisible = ref(false);
      const user = ref({password: ''});
      const authUser = ref({name: ''});
      const loginForm = ref(); //表单DOM
      const rules = ref({
        loginName: [
          { required: true, message: '未输入登录名',trigger: 'blur' }
        ],
        password: [
          { required: true, message: '未输入密码',trigger: 'blur' },
          { pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$/, message: '至少包含数字和英文 长度6~32', trigger: 'blur'}
        ]
      });

      const handleLogin = () => {
        loginForm.value.validate((valid: any) => {
          if (!valid) {
            ElNotification({ title: '消息', message: '表单数据非法', type: 'error'});
            return;
          }
          user.value.password = hexMd5(user.value.password + KEY);
          axios.post("/user/login", user.value).then((response) => {
            let respBean = response.data;
            if (respBean.code != 0) {
              ElNotification({ title: '消息', message: respBean.msg, type: 'error'});
              return;
            }
            authUser.value = respBean.data;
            store.commit("setAuthUser", authUser);
            ElNotification({title: '消息', message: '你好啊 ' + authUser.value.name, type: 'success'});
            dialogVisible.value = false;
          })
        })
      };
      
      
      // onMounted(() => {
      //  
      // });
      return {
        dialogVisible,
        user,
        authUser,
        loginForm,
        rules,
        handleLogin
      }
    }
  });
</script>