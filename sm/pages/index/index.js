//index.js
//获取应用实例
const app = getApp()

Page({
  data:{
    hiddenmodalput:true,
    price:'',
    num:'',
    commend:false
  },
  login: function (e) {
    wx.navigateTo({
      url: '../product/index'
    });

  },
  //点击按钮痰喘指定的hiddenmodalput弹出框  
    modalinput: function () {
    this.setData({
      hiddenmodalput: !this.data.hiddenmodalput
    })
  },
  //取消按钮  
  cancel: function () {
    this.setData({
      hiddenmodalput: true
    });
  },
  //确认  
  confirm: function () {
    var that = this;
    console.log('price', that.data.price);
    console.log('num', that.data.num);
    console.log('commend', that.data.commend);
    this.setData({
      hiddenmodalput: true,
      price: '',
      num: '',
      commend: false
    });
  },
  priceInput: function (e) {
    this.setData({
      price: e.detail.value
    })
  },
  numInput: function (e) {
    this.setData({
      num: e.detail.value
    })
  },
  checkboxChange: function (e) {
    if (e.detail.value[0] != null) {
      this.setData({
        commend: true
      })
    }
  }
})
