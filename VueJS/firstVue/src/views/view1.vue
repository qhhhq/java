<template>
  <div class="container">
    <div class="search">
      <input class="search-input" type="text" placeholder="sample text"/>
    </div>
    <div class="content">
      <div class="category" :style="{height:fullHeight + 'px'}">
        <leftView :categoryList="categoryList" v-on:getTypeId="getTypeId"></leftView>
      </div>

      <div class="content-content" :style="{height:fullHeight + 'px'}">
        content
      </div>
    </div>
  </div>
</template>

<script type="text/javascript">
import leftView from '../components/category-left.vue'
    export default {
        name: 'view1',
        components: {
          leftView: leftView
        },
        data() {
          return {
            fullHeight: document.documentElement.clientHeight - 46,
            categoryList:[
              {id:1, name:'test1'},
              {id:2, name:'test2'},
              {id:3, name:'test3'},
              {id:4, name:'test4'},
              {id:5, name:'test5'},
              {id:6, name:'test6'},
              {id:7, name:'test7'},
              {id:8, name:'test8'},
              {id:9, name:'test9'},
              {id:10, name:'test10'},
              {id:11, name:'test11'},
              {id:12, name:'test12'},
              {id:13, name:'test13'},
              {id:14, name:'test14'},
              {id:15, name:'test15'},
              {id:16, name:'test16'}
            ]
          }
        },
        methods:{
          getTypeId:function(msg) {
            console.log(msg)
          }
        }
    }
</script>

<style scoped>

  .content{
    display: flex;
  }
  .content .category{
    width: 86px;
    overflow-y: scroll;
  }
  .content .content-content{
    padding: 0 8px;
  }
  .search{
    padding: 8px 15px;
    border-bottom: solid 1px #ECECEC;
  }
  .search-input{
    width: 100%;
    border-radius: 60px;
    border:solid 1px #ECECEC;
    height: 25px;
  }
  /*滚动条样式*/
  ::-webkit-scrollbar {/*滚动条整体样式*/
      width: 1px;     /*高宽分别对应横竖滚动条的尺寸*/
      height: 1px;
  }
</style>
