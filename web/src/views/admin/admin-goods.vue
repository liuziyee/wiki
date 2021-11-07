<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <a-table
        :column="columns"
        :row-key="record => record.id"
        :data-source="goods"
        :pagination="pagination"
        :loading="loading"
        @change="handlePageChange"
      >
       <template #cover="{ text: cover }">
         <img v-if="cover" :src="cover" alt="avatar"/>
       </template>
       <template v-slot:action="{ text, record}">
         <a-space size="small">
           <a-button type="primary">
             编辑
           </a-button>
           <a-button type="danger">
             删除
           </a-button>
         </a-space>
       </template> 
      </a-table>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
  import {defineComponent,onMounted,ref} from 'vue';
  import axios from 'axios';
  
  export default defineComponent({
    name: 'AdminEbook',
    setup() {
      const goods = ref();
      const pagination = ref({
        current: 1,
        pagesize: 2,
        total: 0
      });
      const loading = ref(false);
      
      const columns = [
        {
          title: '封面',
          dataIndex: 'cover',
          slots: { customRender: 'cover' }
        },
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title:'类目',
          dataIndex: 'pid'
        },
        {
          title: '浏览数',
          dataIndex: 'viewCount'
        },
        {
          title: '关注数',
          dataIndex: 'viewCount'
        },
        {
          title: '评论数',
          dataIndex: 'commentCount'
        },
        {
          title: 'Action',
          key: 'action',
          slots: { customRender: 'action'}
        }
      ]
      
      const handleQuery = (params: any) => {
        loading.value = true;
        axios.get("/goods/list", params).then((response) => {
          loading.value = false;
          goods.value = response.data.data;
          
          pagination.value.current = params.page;
        });
      };
      
      const handlePageChange = (pagination: any) => {
        console.log(pagination);
        handleQuery({
          page: pagination.current,
          size: pagination.pageSize
        });
      };

      onMounted(() => {
        handleQuery({});
      });
      
      return {
        goods,
        pagination,
        columns,
        loading,
        handlePageChange
      }
      
    }
  })
</script>