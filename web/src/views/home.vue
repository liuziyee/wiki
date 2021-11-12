<template>
  <el-container>
    <el-aside width="150px">
      <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
      >
        <el-sub-menu index="1">
          <template #title>
            <span>数码</span>
          </template>
            <el-menu-item index="1-1">硬盘</el-menu-item>
            <el-menu-item index="1-2">可穿戴</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <span>玩具</span>
          </template>
          <el-menu-item index="2-1">可动</el-menu-item>
          <el-menu-item index="2-2">扭蛋</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-main>
      <div class="grid">
        <el-row :gutter="30">
          <el-col :span="5" v-for="(item, index) in goods" :key="index" style="margin-bottom:30px">
            <el-card :body-style="{ padding: '0px', height: '300px'}" shadow="hover">
              <el-image
                  :src="item.cover"
              />
              <div style="padding: 15px">
                <el-form>
                  <el-form-item>
                    <span>{{ item.name }}</span>
                  </el-form-item>
                  <el-form-item>
                    <el-tag size="mini" type="info" style="margin-right: 8px">浏览{{item.viewCount}}</el-tag>
                    <el-tag size="mini" type="info" style="margin-right: 8px">关注{{item.followCount}}</el-tag>
                    <el-tag size="mini" type="info" style="margin-right: 8px">评论{{item.commentCount}}</el-tag>
                  </el-form-item>
                </el-form>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-main>
  </el-container>

  <div class="grid">
    <a-layout>
      <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <a-list item-layout="vertical" size="large" :grid="{ gutter: 20, column: 4}" :data-source="goods">
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
                <span v-for="{ type, text } in actions" :key="type">
                  <component v-bind:is="type" style="margin-right: 8px" />
                  {{ text }}
                </span>
              </template>
              <a-list-item-meta :description="item.description">
                <template #title>
                  <a :href="item.href">{{ item.name }}</a>
                </template>
                <template #avatar><a-avatar :src="item.cover" /></template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-layout-content>
    </a-layout>
  </div>
</template>

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
