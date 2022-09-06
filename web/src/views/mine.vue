<template>
  <a-layout-content
      :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
  >
    <div class="grid">
      <el-row :gutter="30">
        <el-col :span="4" style="margin-bottom: 30px">
          <div class="card">
            <el-card :body-style="{ padding: '0px'}" shadow="always">
              <el-image src="/image/macmini.png"/>
              <div style="padding:15px">
                <el-form v-if="user.id">
                  <el-form-item style="margin-bottom: 0px;">
                    <el-check-tag>{{user.name}}</el-check-tag>
                  </el-form-item>
                  <el-form-item style="margin-bottom: 0px;">
                    <el-check-tag>token: {{token}}</el-check-tag>
                  </el-form-item>
                  <el-form-item style="margin-bottom: 0px;">
                    <el-check-tag @change="handleLogout">退出登录</el-check-tag>
                  </el-form-item>
                </el-form>
              </div>
            </el-card>
          </div>
        </el-col>
      </el-row>
    </div>
  </a-layout-content>
</template>

<style scoped>
.el-row {
  display: flex;
  flex-wrap: wrap;
}
.el-card {
  height: 100%;
  border: 0;
}
.el-image {
  width: 100%;
  display: block;
}
</style>

<script lang="ts">
import {defineComponent,computed} from 'vue';
import store from "@/store";
import axios from "axios";
import {ElNotification} from "element-plus";

export default defineComponent({
  name: 'Mine',
  setup() {
    const user = computed(() => store.state.user);
    const token = computed(() => store.state.token);
    
    const handleLogout = () => {
      axios.get("/user/logout/" + token.value).then((response) => {
        let respBean = response.data;
        if (respBean.code != 0) {
          ElNotification({ title: '消息', message: respBean.msg, type: 'error', duration: 1000});
          return;
        }
        
        store.commit("setUser", {});
        store.commit("setToken", '');
        sessionStorage.clear();
        ElNotification({ title: '消息', message: '拜拜...', type: 'success', duration: 1000});
      });
    }
    
    return {
      user,
      token,
      handleLogout
    }
  }
})
</script>