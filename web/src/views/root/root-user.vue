<template>
  <el-container class="container">
    <el-main>
      <el-form :inline="true" :model="param">
        <el-form-item>
          <el-input v-model="param.name" size="mini"></el-input>
        </el-form-item>
        <el-form-item>
          <el-check-tag checked @click="handleQueryUser" style="margin-right: 8px">查询</el-check-tag>
          <el-check-tag checked @click="dialogVisible = true; record = {};">新增</el-check-tag>
        </el-form-item>
      </el-form>
      <div class="table">
        <el-table :data="users" stripe style="width: 100%">
          <el-table-column label="登录名" prop="loginName" min-width="15%" />
          <el-table-column label="昵称" prop="name" min-width="15%" />
          <el-table-column label="密码" prop="password" min-width="15%" />
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
          <el-form :model="record"
                   :rules="rules"
                   ref="uploadForm"
                   label-position="right"
                   label-width="80px"
          >
            <el-form-item label="登录名" prop="loginName">
              <el-input v-model="record.loginName" :disabled="!!record.id"/>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="record.name"/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="record.password"/>
            </el-form-item>
          </el-form>
          <template #footer>
            <el-check-tag style="margin-right: 5px" @change="dialogVisible = false">不了</el-check-tag>
            <el-check-tag style="margin-right: 5px" @click="record.password = null">重置密码</el-check-tag>
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
  
  declare let hexMd5: any;
  declare let KEY: any;

  export default defineComponent({
    name: 'RootUser',
    setup() {
      const users = ref();
      const pagination = ref({
        current: 1,
        pageSize: 10,
        total: 0
      });
      
      const uploadForm = ref(); //表单DOM
      const rules = ref({
        loginName: [
          { required: true, message: '未输入登录名',trigger: 'blur' }
        ],
        password: [
          { required: true, message: '未输入密码',trigger: 'blur' },
          { pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$/, message: '至少包含数字和英文 长度6~32', trigger: 'blur'}
        ]
      });

      const dialogVisible = ref(false);
      const record = ref({password: ''});
      const loading = ref(false);
      const param = ref({name:''});
      
      const handleQueryUser = () => {
        axios.get("/user/all", {
          params: {
            page: pagination.value.current,
            size: pagination.value.pageSize,
            loginName: param.value.name
          }
        }).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: '消息', message: respBean.msg, type: 'error', duration: 1000});
          }
          let pageBean = respBean.data;
          users.value = pageBean.list;
          pagination.value.total = pageBean.total;
        });
      };
      
      const handlePageChange = (page: any) => {
        pagination.value.current = page;
        handleQueryUser();
      };

      const handleEdit = (obj: any) => {
        record.value = JSON.parse((JSON.stringify(obj)));
        dialogVisible.value = true;
      };
      
      const handleAddOrUpd = () => {
        //表单校验
        uploadForm.value.validate((valid: any) => {
          if (!valid) {
            ElNotification({ title: '消息', message: '表单数据非法', type: 'error'});
            return;
          }
          loading.value = true;
          record.value.password = hexMd5(record.value.password + KEY);
          axios.post("/user/addOrUpd", record.value).then((response) => {
            let respBean = response.data;
            if (respBean.code != 0) {
              ElNotification({ title: '消息', message: respBean.msg, type: 'error'});
              return;
            }
            loading.value = false;
            dialogVisible.value = false;
            handleQueryUser();
          })
        })
      };
      
      const handleDel = (id: any) => {
        axios.get("/user/del/" + id).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: '消息', message: respBean.msg, type: 'error'});
            return;
          }
          handleQueryUser();
        })
      };

      onMounted(() => {
        handleQueryUser();
      });
      
      return {
        users,
        pagination,
        record,
        dialogVisible,
        loading,
        param,
        rules,
        uploadForm,
        handlePageChange,
        handleEdit,
        handleAddOrUpd,
        handleDel,
        handleQueryUser
      }
    }
  })
</script>