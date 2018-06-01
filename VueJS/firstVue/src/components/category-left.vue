<template>
  <div id="category">
    <ul>
      <li v-for="item in categoryList" :class="[typeId == item.id ? 'active' : '']" v-on:click="clickType(item.id)">{{item.name}}</li>
    </ul>
  </div>
</template>

<script type="text/javascript">
  export default {
    data() {
      return {
        typeId:this.categoryList[0].id
      }
    },
    props: ['categoryList'],
    methods:{
      clickType:function(id) {
        if(this.typeId != id) {
          this.typeId = id;
          this.$emit('getTypeId', id);
        }
      }
    }
  }
</script>

<style scoped>

  #category ul{
    width: 100%;
  }
  #category ul li{
    border-bottom: solid 1px #ECECEC;
      border-right: solid 1px #ECECEC;
    text-align: center;
    padding: 10px 5px;
  }
  #category ul .active{
    color: #E64340;
    font-weight: bold;
    border-right: solid 2px #E64340;
  }
</style>
