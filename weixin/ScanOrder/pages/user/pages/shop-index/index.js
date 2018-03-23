var appInstance = getApp()

Page({
  data: {
    shop:null
  },
  onLoad: function (options) {
    var self = this
    console.log(options.shopId)
    if(options.shopId != null) {
      self.getShopInfo(options.shopId)
    }
  },

  /**
   * 获取店铺信息
   */
  getShopInfo:function(shopId) {
    var self = this
    var shopService = appInstance.shopService()
    var reqData = new Object()
    reqData.shopId = shopId
    shopService.getShopById(appInstance, reqData, function(res) {
      if (res.data.SYS_HEAD.retCode == "000000") {
        self.setData({
          shop: JSON.parse(res.data.DATA.shop)
        })
      } else {
        wx.showToast({
          title: res.data.SYS_HEAD.retMsg,
          duration: 2000
        })
      }
    })
  },

  /**
   * 设置按钮事件
   */
  setting:function() {

  }
})
