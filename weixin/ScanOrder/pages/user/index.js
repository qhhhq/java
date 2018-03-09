var app = getApp()

Page({
  data: {
    list: [
      {
        id: 'api',
        name: '开放接口',
        open: false
      }, {
        id: 'page',
        name: '界面',
        open: false
      }, {
        id: 'device',
        name: '设备',
        open: false
      }, {
        id: 'network',
        name: '网络',
        open: false
      }, {
        id: 'media',
        name: '媒体',
        open: false
      }, {
        id: 'location',
        name: '位置',
        open: false
      }, {
        id: 'storage',
        name: '数据',
        url: 'storage/storage'
      }
    ],
    avatarUrl: "",
    nickName:""
  },
  onShow:function() {
    var that = this
    var user = app.globalData.userInfo
    if (user == null) {
      wx.getUserInfo({
        success:function(res) {
          that.setData({
            avatarUrl: res.userInfo.avatarUrl,
            nickName: res.userInfo.nickName
          })
        }
      })
    } else {
      that.setData({
        avatarUrl: user.avatarUrl,
        nickName: user.nickName
      })
    }
  }
})
