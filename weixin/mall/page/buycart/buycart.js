// page/buycart/buycart.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    carts:[],
    hasList: false,
    totalPrice: 0,
    selectAllStatus: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      hasList: true,        // 既然有数据了，那设为true吧
      carts: [
        { id: 1, title: '店铺1', selected: true,
          items:[
            { id: 11, name: '11111111111', title: '11111', img: '', price: '100.00', num: '1', selected: true },
            { id: 12, name: '22222222222', title: '22222', img: '', price: '100.00', num: '1', selected: true },
          ] 
        },
        {
          id: 1, title: '店铺2', selected: true,
          items: [
            { id: 21, name: '11111111111', title: '11111', img: '', price: '100.00', num: '1', selected: true },
            { id: 22, name: '22222222222', title: '22222', img: '', price: '100.00', num: '1', selected: true },
          ]
        }
      ]
    })
  },

  /**
   * 提交
   */
  submitOrder:function() {
    wx.navigateTo({
      url: '/page/buycart/pages/submit-order/submit-order',
    })
  },

  openChat: function () {
    wx.navigateTo({
      url: '/page/common/pages/chat/chat',
    })
  },

  chooseItem:function(e) {
    console.log(e)
  },
  selectList(e) {
    const index = e.currentTarget.dataset.index;    // 获取data- 传进来的index
    let carts = this.data.carts;                    // 获取购物车列表
    const selected = carts[index].selected;         // 获取当前商品的选中状态
    carts[index].selected = !selected;              // 改变状态
    this.setData({
      carts: carts
    });
    this.getTotalPrice();                           // 重新获取总价
  },
  getTotalPrice() {
    let carts = this.data.carts;                  // 获取购物车列表
    let total = 0;
    for (let i = 0; i < carts.length; i++) {         // 循环列表得到每个数据
      for (let j = 0; j < carts[i].items.length; j++) {
        if (carts[i].items[j].selected) {         // 判断选中才会计算价格
          total += carts[i].items[j].num * carts[i].items[j].price;   // 所有价格加起来
        }
      }
    }
    this.setData({                                // 最后赋值到data中渲染到页面
      carts: carts,
      totalPrice: total.toFixed(2)
    });
  }
})