// page/buycart/buycart.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    carts:[],
    hasList: false,
    totalPrice: 0,
    amount:0,
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
            { id: 11, name: '既然有数据了，那设为true吧', title: '生命周期函数--监听页面显示', img: '', price: '100.00', num: '1', selected: true },
            { id: 12, name: '既然有数据了，那设为true吧', title: '生命周期函数', img: '', price: '100.00', num: '1', selected: true },
          ] 
        },
        {
          id: 1, title: '店铺2', selected: true,
          items: [
            { id: 21, name: '既然有数据了，那设为true吧', title: '生命周期函数', img: '', price: '100.00', num: '1', selected: true },
            { id: 22, name: '既然有数据了，那设为true吧', title: '生命周期函数', img: '', price: '100.00', num: '1', selected: true },
          ]
        }
      ],
    })
    this.getTotalPrice();
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
    const cartIndex = e.currentTarget.dataset.id;    // 获取data- 传进来的id
    const index = e.currentTarget.dataset.index;    // 获取data- 传进来的index
    let carts = this.data.carts;                    // 获取购物车列表
    const selected = carts[cartIndex].items[index].selected;         // 获取当前商品的选中状态
    carts[cartIndex].items[index].selected = !selected;              // 改变状态
    this.setData({
      carts: carts
    });
    this.getTotalPrice();                           // 重新获取总价
    this.changeSelectStatus(cartIndex)              //判断是否全选
  },

  selectShopList(e) {
    let carts = this.data.carts
    const cartIndex = e.currentTarget.dataset.index
    const selected = carts[cartIndex].selected
    for (let i = 0; i < carts[cartIndex].items.length; i++) {
      carts[cartIndex].items[i].selected = !selected;
    }
    this.setData({
      carts: carts
    })
    this.getTotalPrice();                           // 重新获取总价
    this.changeSelectStatus(cartIndex)              //判断是否全选
  },

  /**
   * 全选
   */
  selectAll() {
    let carts = this.data.carts
    let selectAllStatus = this.data.selectAllStatus
    for(let i = 0; i<carts.length; i++) {
      carts[i].selected = !selectAllStatus
      for (let j = 0; j < carts[i].items.length; j++) {
        carts[i].items[j].selected = !selectAllStatus
      }
    }
    this.setData({
      carts: carts,
      selectAllStatus: !selectAllStatus
    })
    this.getTotalPrice();                           // 重新获取总价
    this.changeSelectStatus(cartIndex)              //判断是否全选
  },

  /**
   * 增加数量按钮
   */
  addCount(e) {
    const cartIndex = e.currentTarget.dataset.id;    // 获取data- 传进来的id
    const index = e.currentTarget.dataset.index;    // 获取data- 传进来的index
    let carts = this.data.carts;                    // 获取购物车列表
    var num = parseInt(carts[cartIndex].items[index].num)
    carts[cartIndex].items[index].num = num + 1
    this.setData({
      carts: carts
    })
    this.getTotalPrice();                           // 重新获取总价
  },

  /**
   * 减少数量按钮
   */
  minusCount(e) {
    var self = this
    const cartIndex = e.currentTarget.dataset.id;    // 获取data- 传进来的id
    const index = e.currentTarget.dataset.index;    // 获取data- 传进来的index
    let carts = self.data.carts;                    // 获取购物车列表
    var num = parseInt(carts[cartIndex].items[index].num)
    if(num > 1) {
      carts[cartIndex].items[index].num = num - 1
    } else {
      wx.showModal({
        content: "您确定要删除该商品吗？",
        confirmText: "确定",
        cancelText: "取消",
        success: function (res) {
          if (res.confirm) {
            console.log(carts)
            carts[cartIndex].items.splice(index, 1)
            if (carts[cartIndex].items.length == 0) {
              carts.splice(cartIndex, 1)
            }
            self.setData({
              carts: carts
            })
            self.getTotalPrice();                           // 重新获取总价
          }
        }
      })
    }
    self.setData({
      carts: carts
    })
    self.getTotalPrice();                           // 重新获取总价
  },

  /**
   * 计算总数量
   */
  getTotalPrice() {
    let carts = this.data.carts;                  // 获取购物车列表
    let total = 0;
    let mount = 0;
    for (let i = 0; i < carts.length; i++) {         // 循环列表得到每个数据
      for (let j = 0; j < carts[i].items.length; j++) {
        if (carts[i].items[j].selected) {         // 判断选中才会计算价格
          total += carts[i].items[j].num * carts[i].items[j].price;   // 所有价格加起来
          mount += parseInt(carts[i].items[j].num);
        }
      }
    }
    this.setData({                                // 最后赋值到data中渲染到页面
      carts: carts,
      totalPrice: total.toFixed(2),
      amount: mount
    });
  },

  /**
   * 判断是否全选或全不选
   */
  changeSelectStatus:function(index) {
    let carts = this.data.carts
    let cart = carts[index]
    let num = 0
    for(let i = 0; i<cart.items.length; i++) {
      if (cart.items[i].selected) {
        num ++
      }
    }
    if(num == 0) {
      cart.selected = false
    }
    if (num == cart.items.length) {
      cart.selected = true
    }
    carts[index] = cart
    this.setData({
      carts: carts
    })
    this.changeSelectAllStatus()
  },

  /**
   * 判断全选按钮状态
   */
  changeSelectAllStatus:function() {
    let carts = this.data.carts
    let num = 0
    for (let i = 0; i < carts.length; i++) {
      if (carts[i].selected) {
        num ++
      }
    }
    if(num == 0) {
      this.setData({
        selectAllStatus:false
      })
    }
    if (num == carts.length) {
      this.setData({
        selectAllStatus: true
      })
    }
  }
})