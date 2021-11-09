<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
        <a-table
          :column="columns"
          :data-source="goods"
          :rowKey="(record,index)=>index"
          :pagination="pagination"
          :loading="loading"
          @change="handlePageChange"
        >
          <template #cover="{ text: cover }">
            <img v-if="cover" :src="cover" alt="avatar"/>
          </template>
          <template #action="{ text, record }">
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
    name: 'RootGoodsAnt',
    setup() {
      const goods = ref();
      const pagination = ref({
        current: 1,
        pageSize: 3,
        total: 0
      });
      const loading = ref(false);
      const columns = [
        {
          title: '封面',
          dataIndex: 'cover',
        },
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title:'类目',
          dataIndex: 'categoryId'
        },
        {
          title: '浏览数',
          dataIndex: 'viewCount'
        },
        {
          title: '关注数',
          dataIndex: 'followCount'
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
      ];
      
      const handleQuery = (params: any) => {
        loading.value = true;
        axios.get("/goods/list", {
          params: {
            page: params.page,
            size: params.size
          }
        }).then((response) => {
          loading.value = false;
          let pageBean = response.data.data;
          goods.value = pageBean.list;
          
          pagination.value.current = params.page;
          pagination.value.total = pageBean.total;
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
        handleQuery({
          page: pagination.value.current,
          size: pagination.value.pageSize
        });
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