const requestUrl = require('./config').requestUrl
var sysHead = require('./utils/sysHead')

App({
  onLaunch: function () {
    var self = this
    var userId = wx.getStorageSync('userId')

    if (userId == null || userId == "") {
      // 登录
      wx.login({
        success: function (resl) {
          if (resl.code) {
            wx.getUserInfo({
              success: function (res) {
                self.globalData.userInfo = res.userInfo
                sysHead.MESSAGE_TYPE = '0002';
                sysHead.MESSAGE_CODE = '0001';
                var obj = new Object()
                var user = res.userInfo
                user.code = resl.code
                obj.userInfo = user
                //发起网络请求
                wx.request({
                  url: requestUrl,
                  method: "POST",
                  header: { 'content-type': 'application/x-www-form-urlencoded' },
                  data: {
                    data: JSON.stringify(obj),
                    SYS_HEAD: JSON.stringify(sysHead)
                  },
                  success: function (u) {
                    console.log(u.data.SYS_HEAD.userId)
                    wx.setStorageSync('userId', u.data.SYS_HEAD.userId)
                    self.globalData.userId = u.data.SYS_HEAD.userId
                  }
                })
              }
            })
          } else {
            console.log('获取用户登录态失败！' + res.errMsg)
          }
        }
      })
    } else {
      console.log("userid:", userId)
    }
  },
  globalData: {
    userInfo: null,
    userId:null
  }
})