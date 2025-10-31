import './assets/common.css'
import './assets/iconfont/iconfont.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import LabTopComponent from './components/LabTopComponent.vue'
import LabFooterComponent from './components/LabFooterComponent.vue'
import TopBtn from './components/TopBtn.vue'

import App from './App.vue'
import router from './router'

import ElementPlus from "element-plus"
import "element-plus/dist/index.css"
import * as ElementPlusIconsVue from "@element-plus/icons-vue"



const app = createApp(App)

app.use(createPinia())
app.use(router)
app.component('LabTopComponent', LabTopComponent)
app.component('LabFooterComponent', LabFooterComponent)
app.component('TopBtn', TopBtn)

app.use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.mount('#app')
