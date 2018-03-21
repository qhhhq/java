var sysHead = require('../../../../utils/sysHead')
const requestUrl = require('../../../../config').requestUrl

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
  loadUserShops:function() {
    wx.showNavigationBarLoading()
    var that = this
    sysHead.MESSAGE_TYPE = '0004';
    sysHead.MESSAGE_CODE = '0001';
    wx.request({
      url: requestUrl,
      method: "POST",
      header: { 'content-type': 'application/x-www-form-urlencoded' },
      data: {
        SYS_HEAD: JSON.stringify(sysHead)
      },
      success: function (res) {
        wx.hideNavigationBarLoading()
        console.log(res)
        if (res.data.SYS_HEAD.retCode == "000000") {
          var count = res.data.DATA.count
          if (count == 0) {
          } else {
            that.setData({
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
      },
      fail: function (res) {
        wx.hideNavigationBarLoading()
        console.log(res)
      }
    })
  },

  /**
   * 发起网络请求,加载企业信息
   */
  loadEnterprise:function() {
    var self = this
    sysHead.MESSAGE_TYPE = '0006'
    sysHead.MESSAGE_CODE = '0002'
    wx.showNavigationBarLoading()
    wx.request({
      url: requestUrl,
      method: "POST",
      header: { 'content-type': 'application/x-www-form-urlencoded' },
      data: {
        SYS_HEAD: JSON.stringify(sysHead)
      },
      success: function (res) {
        wx.hideNavigationBarLoading()
        console.log(res)
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
      },
      fail: function (res) {
        wx.hideNavigationBarLoading()
        console.log(res)
      }
    })
  }
})
