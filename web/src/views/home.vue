<template>
  <el-container>
    <el-aside width="150px">
      <el-menu style="border-right: 0"
               @select="handleChildCategoryChange"
               @open="handleParentCategoryChange"
               unique-opened
      >
        <el-sub-menu v-for="parent in category" :key="parent.id" :index="parent.id">
          <template #title>
            <el-check-tag checked>{{parent.name}}</el-check-tag>
          </template>
          <el-menu-item v-for="child in parent.children" :key="child.id" :index="child.id">
            <el-check-tag>{{child.name}}</el-check-tag>
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
  import { ElNotification } from 'element-plus';

  export default defineComponent({
    name: 'Home',
    setup() {
      const goods = ref([]);
      const category = ref();
      let cid = 300;
      
      const handleQueryCategory = () => {
        axios.get("/category/tree").then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: '消息', message: respBean.msg, type: 'error', duration: 1000});
          }
          category.value = respBean.data;
        });
      };
      
      const handleQueryGoods = () => {
        axios.get("/goods/list", {
          params: {
            page: 1,
            size: 1000,
            categoryId: cid
          }
        }).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: '消息', message: respBean.msg, type: 'error', duration: 1000});
            return;
          }
          let pageBean = respBean.data;
          goods.value = pageBean.list;
        });
      };
      
      const handleChildCategoryChange = (index:any) => {
        cid = index;
        handleQueryGoods();
      };
      
      const handleParentCategoryChange = (index:any) => {
        cid = index;
        handleQueryGoods();
      };
      
      onMounted(() => {
        handleQueryCategory();
        handleQueryGoods();
      });
     
      return {
        goods,
        category,
        handleChildCategoryChange,
        handleParentCategoryChange
      }
    }
  });
</script>

<style scoped>
.el-aside {
  overflow-x: hidden;
}
.grid {
  overflow-x: hidden;
  padding: 0 30px;
  box-sizing: border-box;
}
</style>
