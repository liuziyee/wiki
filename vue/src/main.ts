import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as AntIcons from '@ant-design/icons-vue'
import axios from 'axios'

axios.defaults.baseURL = process.env.VUE_APP_SERVER;

/**
 * axios拦截器
 */
axios.interceptors.request.use(config => {
    console.log('请求数据:', config);
    const token = store.state.token;
    if (typeof token == 'string' && token.length > 0) {
        console.log('token:', token);
        config.headers.token = token;
    }
    return config;
}, error => {
    return Promise.reject(error);
});
axios.interceptors.response.use(response => {
    console.log('响应数据:', response);
    return response;
}, error => {
    console.log('错误信息', error);
    return Promise.reject(error);
});

const app = createApp(App);
app.use(store).use(router).use(Antd).use(ElementPlus).mount('#app');

const icons: any = AntIcons;
for (const i in icons) {
    app.component(i, icons[i]);
}

console.log("环境", process.env.NODE_ENV);
console.log("服务器", process.env.VUE_APP_SERVER);