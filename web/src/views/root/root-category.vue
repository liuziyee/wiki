<template>
  <el-container class="container">
    <el-main>
      <p>
        <el-check-tag checked @click="dialogVisible = true; record = {};">新增</el-check-tag>
      </p>
      <div class="table">
        <el-table :data="category"
                  style="width: 100%"
                  row-key="id"
                  :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                  stripe
        >
          <el-table-column label="名称" prop="name" min-width="10%" />
          <el-table-column label="父分类" prop="parentId" min-width="10%" />
          <el-table-column label="排序权重" prop="sortFlag" min-width="10%" />
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
      
      <div class="dialog">
        <el-dialog
            v-model="dialogVisible"
            width="40%"
        >
          <el-form :model="record"
                   label-position="right"
                   label-width="80px"
          >
            <el-form-item label="名称">
              <el-input v-model="record.name"/>
            </el-form-item>
            <el-form-item label="父分类">
              <el-input v-model="record.parentId"/>
            </el-form-item>
            <el-form-item label="排序权重">
              <el-input v-model="record.sortFlag"/>
            </el-form-item>
          </el-form>
          <template #footer>
            <el-check-tag style="margin-right: 5px" @change="dialogVisible = false">不了</el-check-tag>
            <el-check-tag :loading="loading" @change="handleAddOrUpd">保存</el-check-tag>
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
  import {ElNotification} from "element-plus";

  export default defineComponent({
    name: 'RootGoods',
    setup() {
      const category = ref();
      const dialogVisible = ref(false);
      const record = ref({});
      const loading = ref(false);
      
      const handleQuery = () => {
        axios.get("/category/tree").then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: 'info', message: respBean.msg, type: 'error'});
          }
          category.value = respBean.data;
        });
      };
      
      const handleEdit = (obj: any) => {
        record.value = JSON.parse((JSON.stringify(obj)));
        dialogVisible.value = true;
      };
      
      const handleAddOrUpd = () => {
        loading.value = true;
        axios.post("/category/addOrUpd", record.value).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: 'info', message: respBean.msg, type: 'error'});
            return;
          }
          loading.value = false;
          dialogVisible.value = false;
          handleQuery();
        })
      };
      
      const handleDel = (id: any) => {
        axios.get("/category/del/" + id).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: 'info', message: respBean.msg, type: 'error'});
            return;
          }
          handleQuery();
        });
      };

      onMounted(() => {
        handleQuery();
      });
      
      return {
        category,
        record,
        dialogVisible,
        loading,
        handleEdit,
        handleAddOrUpd,
        handleDel,
        handleQuery
      }
    }
  })
</script>