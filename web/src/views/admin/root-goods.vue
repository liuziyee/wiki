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
          <template #default="scope">
            <el-button type="text" size="small" @click="edit(scope.row)">编辑</el-button>
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

      <el-dialog
          v-model="dialogVisible"
          width="40%"
          :before-close="handleClose"
      >
        <el-form :model="record">
          <el-form-item label="封面">
            <el-input v-model="record.cover"/>
          </el-form-item>
          <el-form-item label="名称">
            <el-input v-model="record.name"/>
          </el-form-item>
          <el-form-item label="类目">
            <el-input v-model="record.categoryId"/>
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="record.description"/>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button type="info" @click="dialogVisible = false">取消</el-button>
          <el-button type="success" @click="addOrUpd">保存</el-button>
        </template>
      </el-dialog>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
  import {defineComponent,onMounted,ref} from 'vue';
  import axios from 'axios';
  import {message} from 'ant-design-vue';
  
  export default defineComponent({
    name: 'RootGoods',
    setup() {
      const goods = ref();
      const pagination = ref({
        current: 1,
        pageSize: 5,
        total: 0
      });

      const dialogVisible = ref(false);
      const record = ref({});
      
      const handleQuery = (params: any) => {
        axios.get("/goods/list", {
          params: {
            page: params.page,
            size: params.size
          }
        }).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            message.error(respBean.msg);
          }
          let pageBean = respBean.data;
          goods.value = pageBean.list;
          message.success("success");
          
          
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

      const edit = (row: any) => {
        record.value = row;
        dialogVisible.value = true;
      };
      
      const addOrUpd = () => {
        axios.post("/goods/addOrUpd", record.value).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            message.error(respBean.msg);
            return;
          }
          message.success("success");
          dialogVisible.value = false;
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          })
        })
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
        record,
        dialogVisible,
        handlePageChange,
        edit,
        addOrUpd
      }
    }
  })
</script>