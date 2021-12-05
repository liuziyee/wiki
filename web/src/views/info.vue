<template>
  <el-container class="container">
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
      <el-card :body-style="{ padding: '15px'}" shadow="always" style="margin-top: 15px">
        <el-tabs>
          <el-tab-pane label="详情" style="font-size: 15px">
            <el-row :gutter="10">
              <el-col :span="5">
                <el-image src="/image/emoji.png"/>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="评论">
            <el-row :gutter="10">
              <el-col :span="5">
                <el-input size="mini" type="textarea" :rows="3"></el-input>
              </el-col>
              <el-col :span="5">
                <el-check-tag>评论</el-check-tag>
              </el-col>
            </el-row>
            <el-tree :data="comment" :props="{ children: 'children', label: 'label' }" empty-text=""/>
          </el-tab-pane>
        </el-tabs>
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
.container ::v-deep .el-textarea__inner {
  border: 0;
  background-color: #f4f6f9;
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
      const comment = ref({});

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

          handleQueryComment();
        });
      };
      
      const handleQueryComment = () => {
        axios.get("/comment/all/" + route.query.id).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: '消息', message: respBean.msg, type: 'error', duration: 1000});
            return;
          }
          comment.value = respBean.data;
        });
      };

      onMounted(() => {
        handleQueryGoods();
      });
      
      return {
        goods,
        comment,
        
      }
    }
  });
</script>