import wxValidate from './utils/wxValidate'
import shopService from './pages/user/service/shopService'
import enterpriseService from './pages/user/service/enterpriseService'
const requestUrl = require('./config').requestUrl
var sysHead = require('./utils/sysHead')

App({
  wxValidate: (rules, messages) => new wxValidate(rules, messages),
  shopService: () => new shopService(),
  enterpriseService: () => new enterpriseService(),
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
                    DATA: JSON.stringify(obj),
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
  },

  /**
   * POST提交数据
   */
  postRequest: function (head, body, successCallback) {
    var bodyData = new Object()
    if(body != null && body != "") {
      bodyData = body
    }
    wx.request({
      url: requestUrl, 
      method: "POST",
      header: { 'content-type': 'application/x-www-form-urlencoded' },
      data: {
        SYS_HEAD: JSON.stringify(head),
        DATA: JSON.stringify(bodyData)
      },
      success:function(res) {
        if(res.data) {
          typeof successCallback == "function" && successCallback(res)
        } else {
          var message = res.errMsg;
          if(message.length == 0) {
            message = "访问服务器发生错误。"
          }
          wx.showModal({
            content: message
          })
        }
      },
      fail:function(res) {
        wx.showModal({
          content: "似乎没网络连接，请确认后重试。"
        })
      }
    })
  }
})