<template>
  <div id="app">

    <router-view></router-view>
    <div class="tarBar">
      <router-link to="/view1">Go to view1</router-link>
      <router-link to="/view2">Go to view2</router-link>
      <router-link to="/buycart">buycart</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.tarBar{
  position:fixed;
  bottom: 0;
}
</style>
