<template>
  <el-container>
    <el-aside width="200px">
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
    </el-main>
  </el-container>
</template>

<script lang="ts">
  import {defineComponent,onMounted,ref} from 'vue';
  import axios from 'axios';
  import {message} from 'ant-design-vue';

  export default defineComponent({
    name: 'Home',
    setup() {
      const goods = ref();
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
      const actions: Record<string, string>[] = [
        { type: 'EyeOutlined', text: '156' },
        { type: 'GithubOutlined', text: '156' },
        { type: 'SmileOutlined', text: '2' },
      ];
  
      return {
        goods,
        pagination,
        actions
      }
    }
  });
</script>

<style scoped>
  .ant-avatar {
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 10%;
    margin: 5px 0;
  }
</style>
