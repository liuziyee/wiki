<template>
  <el-container>
    <el-main>
      <el-card :body-style="{ padding: '0px'}" shadow="always">
        <el-row :gutter="10">
          <el-col :span="3">
            <el-image :src="goods.cover"/>
          </el-col>
          <el-col :span="5">
            <el-form style="margin-top: 30px">
              <el-form-item>
                <el-check-tag style="font-size: 15px">{{goods.name}}</el-check-tag>
              </el-form-item>
              <el-form-item>
                <el-check-tag class="data-tag">浏览{{goods.viewCount}}</el-check-tag>
                <el-check-tag class="data-tag">关注{{goods.followCount}}</el-check-tag>
                <el-check-tag class="data-tag">评论{{goods.commentCount}}</el-check-tag>
              </el-form-item>
              <el-form-item>
                <el-check-tag checked>关注</el-check-tag>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </el-card>
      <el-card :body-style="{ padding: '0px'}" shadow="always" style="margin-top: 15px">
        <el-row :gutter="10">
          <el-col :span="5">
            <el-image src="/image/finder.png"/>
          </el-col>
        </el-row>
      </el-card>
    </el-main>
  </el-container>
</template>

<style scoped>
.el-main {
  height: 100%;
}
.el-card {
  width: 100%;
  height: 100%;
  border: 0;
}
.el-image {
  width: 100%;
  display: block;
}
.el-form-item {
  margin-bottom: 0px;
}
.data-tag {
  font-size: x-small;
  margin-right: 5px;
  padding: 5px;
}
</style>

<script lang="ts">
  import {defineComponent,onMounted,ref} from 'vue';
  import axios from 'axios';
  import {message} from 'ant-design-vue';
  import { ElNotification } from 'element-plus';
  import {useRoute} from 'vue-router';

  export default defineComponent({
    name: 'GoodsInfo',
    
    setup() {
      let route = useRoute();
      const goods = ref({});

      const handleQueryGoods = () => {
        axios.get("/goods/all", {
          params: {
            page: 1,
            size: 1000,
            id: route.query.id
          }
        }).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: '消息', message: respBean.msg, type: 'error', duration: 1000});
            return;
          }
          let pageBean = respBean.data;
          console.log(pageBean.list[0]);
          goods.value = pageBean.list[0];
        });
      };

      onMounted(() => {
        handleQueryGoods();
      });
      
      return {
        goods
      }
    }
  });
</script>