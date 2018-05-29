<template>
    <div class="buycart">
        <div class="head-title">我的购物车</div>
        <div class="kind-list">
          <div class="kind-list-item">
            <div class="list-head">111</div>
            <div class="buy-items" v-for="item in list">
              <i class="icon iconfont icon-guanbi1" style="color:red;"></i>
              <img class="product-img" :src="item.image">
              <div class="product-info">
                <div class="product-name">{{item.name}}</div>
                <div class="product-title">{{item.title}}</div>
                <div class="product-price">￥{{item.price}}</div>
                <div class="number-box">
                  {{list.bb}}
                </div>
              </div>
            </div>
          </div>
        </div>
    </div>
</template>

<script type="text/javascript">

  import Buycart from '../utils/Buycart';
  var obj = {id:'123123'};
  Buycart.set(obj);
  //Buycart.clear('123');
  export default {
      name: 'buycart',
      props: ['list']
  }
</script>

<style scoped>
@import '../iconfont/iconfont.css';
input[type="radio"] {
  background-image: url(../assets/radio.png);
}

.buycart{
  margin-top: 50px;
}
.buy-items{
  display: flex;
  padding: 8px;
  align-items: center;
}
.product-img{
  width: 80px;
  height: 80px;
  margin: 0 8px;
}
.product-info{
  width: 100%;
  position: relative;
}
.number-box{
  position: absolute;
  bottom: 8px;
  right: 8px;
}
</style>
