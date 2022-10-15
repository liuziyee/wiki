<template>
  <el-container class="container">
    <el-header style="padding: 0 0">
      <el-menu
          default-active="/"
          class="router-menu"
          mode="horizontal"
          background-color="#708090"
          text-color="#ffffff"
          router
      >
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/root">空腹虫</el-menu-item>
        <el-menu-item index="/mine" @click="user.id ? router.push({path: '/mine'}) : dialogVisible = true">我的</el-menu-item>
      </el-menu>
    </el-header>

    <el-dialog
        v-model="dialogVisible"
        width="25%"
    >
      <el-form :model="record"
               :rules="rules"
               ref="loginForm"
               label-position="right"
               label-width="80px"
      >
        <el-form-item label="登录名" prop="loginName">
          <el-input v-model="record.loginName"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="record.password"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-check-tag style="margin-right: 5px" @change="handleGithubLogin"><GithubFilled/></el-check-tag>
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
.container ::v-deep .el-input__inner {
  border: 0;
  background-color: #f4f6f9;
}
</style>

<script lang="ts">
import {computed, defineComponent, onMounted, ref} from 'vue';
import {ElNotification} from "element-plus";
import axios from "axios";
import store from "@/store";
import {useRouter} from 'vue-router';

declare let hexMd5: any;
declare let KEY: any;
const USER = 'USER';
const TOKEN = 'TOKEN';
  
  export default defineComponent({
    name: 'the-header',
    setup() {
      const dialogVisible = ref(false);
      const record = ref({password: ''});
      const user = computed(() => store.state.user);
      const loginForm = ref();
      const rules = ref({
        loginName: [
          { required: true, message: '未输入登录名',trigger: 'blur' }
        ],
        password: [
          { required: true, message: '未输入密码',trigger: 'blur' },
          { pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$/, message: '至少包含数字和英文 长度6~32', trigger: 'blur'}
        ]
      });
      let router = useRouter();
      let websocket: any;
      
      const initWebSocket = () => {
        websocket.onopen = () => {
          console.log('got a new websocket connection, status code: ', websocket.readyState);
        };
        websocket.onmessage = (event: any) => {
          let msg = event.data;
          console.log('got a message: ', msg);
          if (msg.indexOf("&") != -1) {
            let arr = msg.split("&");
            ElNotification({title: '消息', message: arr[0] + '评论了' + arr[1], type: 'info'});
          } else {
            ElNotification({title: '消息', message: "推送时间戳: " + msg, type: 'info'});
          }
        };
        websocket.onerror = () => {
          console.log('there is a problem, status code: ', websocket.readyState);
        };
        websocket.onclose = () => {
          console.log('close a websocket connection, status code: ', websocket.readyState);
        };
      };

      const handleLogin = () => {
        loginForm.value.validate((valid: any) => {
          if (!valid) {
            ElNotification({ title: '消息', message: '表单数据非法', type: 'error'});
            return;
          }
          record.value.password = hexMd5(record.value.password + KEY);
          axios.post("/user/login", record.value).then((response) => {
            let respBean = response.data;
            if (respBean.code != 0) {
              ElNotification({ title: '消息', message: respBean.msg, type: 'error'});
              return;
            }
            let user = respBean.data;
            let token = respBean.token;
            //put data into session and store
            sessionStorage.setItem(USER, JSON.stringify(user));
            sessionStorage.setItem(TOKEN, token);
            store.commit("setUser", user);
            store.commit("setToken", token);

            openConnection(token);
            
            ElNotification({title: '消息', message: '你好啊 ' + user.name, type: 'success'});
            dialogVisible.value = false;

            router.push({path: '/mine'});
            
          })
        })
      };

      const handleGithubLogin = () => {
        console.log("前往github授权页");
      };
      
      const checkSession = () => {
        let user = sessionStorage.getItem(USER);
        if (user) {
          store.commit("setUser", JSON.parse(user));
        }
        let token = sessionStorage.getItem(TOKEN);
        if (token) {
          store.commit("setToken", token);
          openConnection(token);
        }
      };

      const openConnection = (token: any) => {
        if ('WebSocket' in window) {
          websocket = new WebSocket(process.env.VUE_APP_WS_SERVER + '/ws/' + token);
          initWebSocket();
        }
      };

      onMounted(() => {
        checkSession();
      });
      
      return {
        dialogVisible,
        record,
        user,
        loginForm,
        rules,
        router,
        handleLogin,
        handleGithubLogin
      }
    }
  });
</script>