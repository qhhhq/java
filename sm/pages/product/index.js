const requestUrl =`https://14592619.qcloud.la/testRequest`

Page({
  data: {
    resultCode: ''
  },
  scanCode: function () {
    var that = this
    wx.scanCode({
      success: function (res) {
        that.setData({
          resultCode: res.result
        })
      },
      fail: function (res) {
      }
    })
  },
  findProduct:function() {
    var self = this

    self.setData({
      loading: true
    })

    wx.request({
      url: requestUrl,
      data: {
        noncestr: Date.now()
      },
      success: function (result) {
        wx.showToast({
          title: '请求成功',
          icon: 'success',
          mask: true,
          duration: duration
        })
        self.setData({
          loading: false
        })
        console.log('request success', result)
      },

      fail: function ({ errMsg }) {
        console.log('request fail', errMsg)
        self.setData({
          loading: false
        })
      }
    })
  }
})
