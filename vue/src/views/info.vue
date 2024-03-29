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
          <el-tab-pane :label="total">
            <el-row :gutter="10">
              <el-col :span="5">
                <el-input size="mini" type="textarea" :rows="3" v-model="comment"></el-input>
              </el-col>
              <el-col :span="5">
                <el-check-tag @change="handleComment">评论</el-check-tag>
              </el-col>
            </el-row>
            <el-tree :data="commentTree" 
                     :props="{ children: 'children', label: 'label' }" 
                     empty-text=""
                     default-expand-all
                     :expand-on-click-node="false"
                     @node-click="prepareReplyData"
            >
              <template #default="{ node, data }">
                <div class="tree-node">
                  <span style="margin-right: 5px">{{node.label}}</span>
                  <el-popover
                      placement="top-start"
                      :width="500"
                      trigger="click"
                  >
                    <div class="container">
                      <el-input type="textarea" :row="2" v-model="reply.content"></el-input>
                      <div style="text-align: right;margin-top: 5px">
                        <el-check-tag class="data-tag" @change="handleReply">回复</el-check-tag>
                      </div>
                    </div>
                    <template #reference>
                      <el-check-tag class="data-tag">回复</el-check-tag>
                    </template>
                  </el-popover>
                </div>
              </template>
            </el-tree>
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
  font-size: 5px;
  margin-right: 5px;
  padding: 5px;
}
.container ::v-deep .el-textarea__inner {
  border: 0;
  background-color: #f4f6f9;
  resize: none;
}
.container ::v-deep .el-tree-node__content {
  padding: 15px 0;
}
.el-tree {
  margin-top: 10px;
}
.tree-node {
  font-size: 14px;
  margin-bottom: 5px;
}
</style>

<script lang="ts">
import {computed, defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {ElNotification} from 'element-plus';
import {useRoute} from 'vue-router';
import store from "@/store";

export default defineComponent({
    name: 'GoodsInfo',
    
    setup() {
      const user = computed(() => store.state.user);
      const token = computed(() => store.state.token);

      let route = useRoute();
      const goods = ref({});
      const commentTree = ref({});
      const comment = ref();
      const goodsId = route.query.id;
      const total = ref();
      const reply = ref({
        toUid: 0, 
        commentId: 0,
        relaId: 0,
        type: 0,
        content: ''
      });
      const popoverVisible = ref(false);

      const handleQueryGoods = () => {
        axios.get("/goods/all", {
          params: {
            page: 1,
            size: 1000,
            id: goodsId
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
        axios.get("/comment/all/" + goodsId).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: '消息', message: respBean.msg, type: 'error', duration: 1000});
            return;
          }
          let pageBean = respBean.data;
          commentTree.value = pageBean.list;
          total.value = "评论(" + pageBean.total + ")";
        });
      };
      
      const handleComment = () => {
        
        
        axios.get("/comment/addComment/", {
          params: {
            goodsId: goodsId,
            content: comment.value
          }
        }).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: '消息', message: respBean.msg, type: 'error', duration: 1000});
            return;
          }
          ElNotification({ title: '消息', message: '看看你的评论吧...', type: 'success'});
          comment.value = null;
          handleQueryComment();
        });
      };

      const prepareReplyData = (item: any, node: any) => {
        console.log(item);
        console.log(node);
        console.log(node.parent.data instanceof Array);
        reply.value.relaId = item.id;
        if (node.parent.data instanceof Array) {
          reply.value.type = 1;
          reply.value.toUid = item.userId;
        } else {
          reply.value.type = 2;
          reply.value.toUid = item.fromUid;
        }
        while (!(node.parent.data instanceof Array)) {
          node = node.parent;
        }
        reply.value.commentId = node.data.id;
        console.log(reply.value);
      };
      
      const handleReply = () => {
        axios.post("/comment/addReply", reply.value).then((response) => {
          let respBean = response.data;
          if (respBean.code != 0) {
            ElNotification({ title: '消息', message: respBean.msg, type: 'error'});
            return;
          }
          
          reply.value.content = '';
          ElNotification({ title: '消息', message: '看看你的回复吧...', type: 'success'});
          handleQueryComment();
        })
      };

      onMounted(() => {
        handleQueryGoods();
      });
      
      return {
        goods,
        commentTree,
        comment,
        reply,
        total,
        popoverVisible,
        handleComment,
        prepareReplyData,
        handleReply
      }
    }
  });
</script>