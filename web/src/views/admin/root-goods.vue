<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <el-table :data="goods" stripe style="width: 100%">
        <el-table-column label="封面" min-width="10%">
          <template #default="scope">
            <el-image
                style="width: 40px; height: 30px"
                :src="scope.row.cover"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column label="名称" prop="name" min-width="10%" />
        <el-table-column label="类目" prop="categoryId" min-width="10%" />
        <el-table-column label="浏览数" prop="followCount" min-width="10%" />
        <el-table-column label="关注数" prop="followCount" min-width="10%" />
        <el-table-column label="评论数" prop="commentCount" min-width="10%" />
        <el-table-column fixed="right" label="做点什么" width="120">
          <template #default>
            <el-button type="text" size="small">编辑</el-button>
            <el-button type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          background
          :current-page="pagination.current"
          :page-size="pagination.pageSize"
          :total="pagination.total"
          layout="prev, pager, next"
          @current-change="handlePageChange"
      >
      </el-pagination>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
  import {defineComponent,onMounted,ref} from 'vue';
  import axios from 'axios';
  import {ElMessage} from 'element-plus';
  
  export default defineComponent({
    name: 'RootGoods',
    setup() {
      const goods = ref();
      const pagination = ref({
        current: 1,
        pageSize: 5,
        total: 0
      });
      const loading = ref(false);
      
      const handleQuery = (params: any) => {
        loading.value = true;
        axios.get("/goods/list", {
          params: {
            page: params.page,
            size: params.size
          }
        }).then((response) => {
          loading.value = false;
          let respBean = response.data;
          if (respBean.code != 0) {
            ElMessage.error(respBean.msg);
          }
          let pageBean = respBean.data;
          goods.value = pageBean.list;
          ElMessage.success("success");
          
          
          pagination.value.current = params.page;
          pagination.value.total = pageBean.total;
        });
      };
      
      const handlePageChange = (page: any) => {
        handleQuery({
          page: page,
          size: pagination.value.pageSize
        });
      };

      onMounted(() => {
        handleQuery({
          page: pagination.value.current,
          size: pagination.value.pageSize
        });
      });
      
      return {
        goods,
        pagination,
        loading,
        handlePageChange
      }
    }
  })
</script>