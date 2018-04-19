// page/buycart/buycart.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
  
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
  }
})