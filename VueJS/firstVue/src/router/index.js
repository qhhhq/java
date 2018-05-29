import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import View1 from '@/views/view1'
import View2 from '@/views/view2'
import Buycart from '@/views/buycart'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld,
      meta: {
        title: 'HelloWorld'
      }
    },{
        path: '/view1',
        name: 'view1',
        component: View1,
        meta: {
          title: 'view1'
        }
    }, {
        path: '/view2',
        component: View2,
        meta: {
          title: 'view2'
        }
    }, {
        path: '/buycart',
        component: Buycart,
        meta: {
          title: 'buycart'
        }
    }
  ]
})
