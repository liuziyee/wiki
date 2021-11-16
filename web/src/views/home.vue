<template>
  <el-container>
    <el-aside width="150px">
      <el-menu
          default-active="1"
          style="border-right: 0"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-check-tag>数码</el-check-tag>
          </template>
          <el-menu-item index="1-1">
            <el-check-tag>硬盘</el-check-tag>
          </el-menu-item>
          <el-menu-item index="1-2">
            <el-check-tag>可穿戴</el-check-tag>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-check-tag>玩具</el-check-tag>
          </template>
          <el-menu-item index="2-1">
            <el-check-tag>可动</el-check-tag>
          </el-menu-item>
          <el-menu-item index="2-2">
            <el-check-tag>扭蛋</el-check-tag>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-main>
      <div class="grid">
        <el-row :gutter="30">
          <el-col :span="4" v-for="(item, index) in goods" :key="index" style="margin-bottom: 30px">
            <div class="card">
              <el-card :body-style="{ padding: '0px'}" shadow="hover">
                <el-image :src="item.cover"/>
                <div style="padding:15px">
                  <el-form>
                    <el-form-item style="margin-bottom: 0px;">
                      <el-check-tag checked>{{ item.name }}</el-check-tag>
                    </el-form-item>
                    <el-form-item  style="margin-bottom: 0px;">
                      <el-tag size="mini" type="info" style="margin-right: 8px">浏览{{item.viewCount}}</el-tag>
                      <el-tag size="mini" type="info" style="margin-right: 8px">关注{{item.followCount}}</el-tag>
                      <el-tag size="mini" type="info" style="margin-right: 8px">评论{{item.commentCount}}</el-tag>
                    </el-form-item>
                  </el-form>
                </div>
              </el-card>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-main>
  </el-container>
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
  import {defineComponent,onMounted,ref} from 'vue';
  import axios from 'axios';
  import {message} from 'ant-design-vue';

  export default defineComponent({
    name: 'Home',
    setup() {
      const goods = ref([]);
      const data = ref();
      onMounted(() => {
        axios.get("/goods/list", {
          params: {
            page: 1,
            size: 1000
          }
        }).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            message.error(respBean.msg);
            return;
          }
          let pageBean = respBean.data;
          goods.value = pageBean.list;
          
          message.success("success");
        })
      });
  
      const pagination = {
        onChange: (page: number) => {
          console.log(page);
        },
        pageSize: 3,
      };
      const info: any = [
        { type: 1, count: '156' },
        { type: 2, count: '156' },
        { type: 3, count: '2' },
      ];
  
      return {
        goods,
        pagination,
        info
      }
    }
  });
</script>

<style scoped>
  .grid {
    overflow-x: hidden;
    padding: 0 30px;
    box-sizing: border-box;
  }
</style>
