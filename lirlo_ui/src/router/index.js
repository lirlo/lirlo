import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

const routers =  new Router({
    routes:[
      {
        path: '/',
        name: 'HelloWorld',
        component: HelloWorld
      },
      {
        path: '/websocket',
        name: 'websocket',
        component: () => import('@/page/websocket.vue')
      }
  ]
})

export default routers
