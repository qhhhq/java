var sysHead = require('../../../../utils/sysHead')
var appInstance = getApp()

Page({
  data: {
    noEnterprise:true,
    list:[],
    enterprise:null
  },
  onLoad: function () {
    var self = this;
    self.loadUserShops()
    self.loadEnterprise()
  },

  /**
   * 添加商户
   */
  addEnterprise:function() {
    wx.navigateTo({
      url: '../add-enterprise/index',
    })
  },

  /**
   * 添加店铺
   */
  addShop:function() {
    var self = this
    wx.navigateTo({
      url: '../add-shop/index?enterpriseId=' +self.data.enterprise.id
    })
  },

  /**
   * 发起网络请求,加载用户店铺列表
   */
  loadUserShops: function () {
    var self = this
    var shopService = appInstance.shopService()
    shopService.getUserShopList(appInstance, function (res) {
      if (res.data.SYS_HEAD.retCode == "000000") {
        var count = res.data.DATA.count
        if (count == 0) {
        } else {
          self.setData({
            list: JSON.parse(res.data.DATA.shops)
          })
        }
        console.log("data", res.data.DATA.count)
      } else {
        wx.showToast({
          title: res.data.SYS_HEAD.retMsg,
          duration: 2000
        })
      }
    })
  },

  /**
   * 发起网络请求,加载企业信息
   */
  loadEnterprise: function () {
    wx.showToast({
      title: "loading",
      icon: "loading",
      duration: 5000
    })
    var self = this
    var enterpriseService = appInstance.enterpriseService()
    enterpriseService.getUserEnterprise(appInstance,
      function (res) {
        wx.hideToast()
        if (res.data.SYS_HEAD.retCode == "000000") {
          var enterprise = JSON.parse(res.data.DATA.enterprise)
          var hasEnterprise = res.data.DATA.hasEnterprise
          if (hasEnterprise == true) {
            self.setData({
              noEnterprise: true,
              enterprise: JSON.parse(res.data.DATA.enterprise)
            })
          } else {
            self.setData({
              noEnterprise: false
            })
          }
        } else {
          wx.showToast({
            title: res.data.SYS_HEAD.retMsg,
            duration: 2000
          })
        }
      })
  },

  /**
   * 企业信息审核失败，修改信息
   */
  updateEpterprise:function() {
    var self = this
    if (self.data.enterprise.status == false) {
      wx.navigateTo({
        url: '../add-enterprise/index?enterpriseId=' + self.data.enterprise.id
      })
    }
  },

  /**
   * 点击店铺事件
   */
  shopIndex:function(e) {
    var self = this
    var shopStatus = e.currentTarget.dataset.text
    if (self.data.enterprise.status == true) {
      if (shopStatus == "0") {  //正常状态，打开店铺
        wx.navigateTo({
          url: '../shop-index/index?shopId=' + e.currentTarget.id
        })
      } else if (shopStatus == "2") {   //审核失败，修改信息
        wx.navigateTo({
          url: '../add-shop/index?shopId=' + e.currentTarget.id
        })
      }
    }
  }
})
