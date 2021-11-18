<template>
  <el-container class="container">
    <el-main>
      <el-form :inline="true" :model="param">
        <el-form-item>
          <el-input v-model="param.name" size="mini"></el-input>
        </el-form-item>
        <el-form-item>
          <el-check-tag checked @click="handleQuery" style="margin-right: 8px">查询</el-check-tag>
          <el-check-tag checked @click="dialogVisible = true; record = {};">新增</el-check-tag>
        </el-form-item>
      </el-form>
      <div class="table">
        <el-table :data="goods" stripe style="width: 100%">
          <el-table-column label="封面" min-width="10%">
            <template #default="scope">
              <el-image
                  style="width: 40px; height: 30px"
                  :src="scope.row.cover"
              ></el-image>
            </template>
          </el-table-column>
          <el-table-column label="名称" prop="name" min-width="25%" />
          <el-table-column label="分类" prop="categoryId" min-width="10%" />
          <el-table-column label="浏览数" prop="followCount" min-width="10%" />
          <el-table-column label="关注数" prop="followCount" min-width="10%" />
          <el-table-column label="评论数" prop="commentCount" min-width="10%" />
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button type="text" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              <el-popconfirm
                  confirm-button-text="嗯"
                  cancel-button-text="不了"
                  title="要删掉吗?"
                  @confirm="handleDel(scope.row.id)"
              >
                <template #reference>
                  <el-button type="text" size="mini">删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-pagination
          background
          :current-page="pagination.current"
          :page-size="pagination.pageSize"
          :total="pagination.total"
          layout="prev, pager, next"
          @current-change="handlePageChange"
      >
      </el-pagination>
      
      <div class="dialog">
        <el-dialog
            v-model="dialogVisible"
            width="40%"
        >
          <el-form :model="record">
            <el-form-item label="封面">
              <el-input v-model="record.cover"/>
            </el-form-item>
            <el-form-item label="名称">
              <el-input v-model="record.name"/>
            </el-form-item>
            <el-form-item label="分类">
              <el-cascader
                  :options="options"
                  :props="{ multiple: true }"
                  v-model="categoryIds"
                  clearable
              />
            </el-form-item>
            <el-form-item label="描述">
              <el-input v-model="record.description"/>
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button type="info" size="small" @click="dialogVisible = false">不了</el-button>
            <el-button type="success" size="small" :loading="loading" @click="handleAddOrUpd">保存</el-button>
          </template>
        </el-dialog>
      </div>
    </el-main>
  </el-container>
</template>

<style scoped>
.container ::v-deep .el-input__inner {
  border: 0;
  background-color: #f4f6f9;
}
.dialog ::v-deep .el-dialog__body {
  padding-bottom: 0;
}
.table ::v-deep .el-table__row>td{
  border: none;
}
</style>

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
        pageSize: 10,
        total: 0
      });

      const dialogVisible = ref(false);
      const record = ref({});
      const loading = ref(false);
      const param = ref({name: ''});
      const categoryIds = ref({});
      
      const handleQuery = () => {
        axios.get("/goods/list", {
          params: {
            page: pagination.value.current,
            size: pagination.value.pageSize,
            name: param.value.name
          }
        }).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            message.error(respBean.msg);
          }
          message.success("success");
          
          let pageBean = respBean.data;
          goods.value = pageBean.list;
          pagination.value.total = pageBean.total;
        });
      };
      
      const handlePageChange = (page: any) => {
        pagination.value.current = page;
        handleQuery();
      };

      const handleEdit = (obj: any) => {
        record.value = JSON.parse((JSON.stringify(obj)));
        dialogVisible.value = true;
      };
      
      const handleAddOrUpd = () => {
        loading.value = true;
        axios.post("/goods/addOrUpd", record.value).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            message.error(respBean.msg);
            return;
          }
          loading.value = false;
          message.success("success");
          dialogVisible.value = false;
          handleQuery();
        })
      };
      
      const handleDel = (id: any) => {
        axios.get("/goods/del/" + id).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            message.error(respBean.msg);
            return;
          }
          message.success("success");
          handleQuery();
        });
      };

      onMounted(() => {
        handleQuery();
      });
      
      return {
        goods,
        pagination,
        record,
        dialogVisible,
        loading,
        param,
        categoryIds,
        handlePageChange,
        handleEdit,
        handleAddOrUpd,
        handleDel,
        handleQuery
      }
    }
  })
</script>