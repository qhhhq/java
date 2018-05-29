<template>
    <div >
        <h1>我是View1</h1>
        <a> 我是View1 </a>
        <buycart :list="list"></buycart>
    </div>
</template>

<script type="text/javascript">
import buycart from './buycart.vue'
    export default {
        name: 'view1',
        data () {
          return {
            list: [
              {
                image:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527497616083&di=024bfef8f494bbb6f72d0baf3854c34b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F728da9773912b31bb0e8070d8c18367adab4e10a.jpg",
                name:"列表渲染",
                title:"用 v-for 把一个数组对应为一组元素",
                price:100.00
              },{
                image:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527497616083&di=024bfef8f494bbb6f72d0baf3854c34b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F728da9773912b31bb0e8070d8c18367adab4e10a.jpg",
                name:"列表渲染",
                title:"用 v-for 把一个数组对应为一组元素",
                price:100.00
              },{
                image:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527497616083&di=024bfef8f494bbb6f72d0baf3854c34b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F728da9773912b31bb0e8070d8c18367adab4e10a.jpg",
                name:"列表渲染",
                title:"用 v-for 把一个数组对应为一组元素",
                price:100.00
              },
            ]
          }
        },
        components: {
          buycart: buycart
        }
    }
</script>
