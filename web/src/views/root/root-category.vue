<template>
  <el-container>
    <el-main>
      <p>
        <el-check-tag checked @click="dialogVisible = true; record = {};">新增</el-check-tag>
      </p>
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

      <div class="dialog">
        <el-dialog
            v-model="dialogVisible"
            width="40%"
        >
          <el-form :model="record"
                   :label-position="right"
                   label-width="100px"
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
            <el-button type="info" size="small" @click="dialogVisible = false">不了</el-button>
            <el-button type="success" size="small" :loading="loading" @click="handleAddOrUpd">保存</el-button>
          </template>
        </el-dialog>
      </div>
    </el-main>
  </el-container>
</template>

<style scoped>
.dialog ::v-deep .el-dialog__body {
  padding-bottom: 0;
}
</style>
<script lang="ts">
  import {defineComponent,onMounted,ref} from 'vue';
  import axios from 'axios';
  import {message} from 'ant-design-vue';

  export default defineComponent({
    name: 'RootGoods',
    setup() {
      const category = ref();
      const dialogVisible = ref(false);
      const record = ref({});
      const loading = ref(false);
      
      const handleQuery = () => {
        axios.get("/category/list").then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            message.error(respBean.msg);
          }
          message.success("success");
          
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
        axios.get("/category/del/" + id).then((response) => {
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