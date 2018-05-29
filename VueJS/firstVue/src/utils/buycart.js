var BUYCART_KEY = 'weapp_buycart_';

var Buycart = {
  get:function(id) {
    return localStorage.getItem(BUYCART_KEY + id) || null;
  },

  set:function(buycart) {
    var id = buycart.id;
    localStorage.setItem(BUYCART_KEY + id, JSON.stringify(buycart));
  },

  clear: function (id) {
    localStorage.removeItem(BUYCART_KEY + id);
  },

  addItem:function(item) {

  }
};

export default Buycart;
