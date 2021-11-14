<template>
  <el-container>
    <el-aside width="150px">
      <el-menu router="true">
        <el-menu-item index="/root/goods">
          <el-check-tag>好物管理</el-check-tag>
        </el-menu-item>
        <el-menu-item index="/root/category">
          <el-check-tag>分类管理</el-check-tag>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <router-view/>
  </el-container>
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
      const loading = ref(false);
      const param = ref({name: ''});
      
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
        handlePageChange,
        handleEdit,
        handleAddOrUpd,
        handleDel,
        handleQuery
      }
    }
  })
</script>