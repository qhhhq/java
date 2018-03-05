var sysHead = require('../../utils/sysHead')
App({
  onLaunch: function () {
    wx.login({
      success: function (resl) {
        if (resl.code) {
          wx.getUserInfo({
            success: function (res) {
              sysHead.MESSAGE_TYPE = '1200';
              sysHead.MESSAGE_CODE = '0001';
              sysHead.USER_ID = '666666';
              var user = res.userInfo
              user.code = resl.code
              //发起网络请求
              wx.request({
                url: 'http://10.255.105.225:10086',
                data: {
                  data: JSON.stringify(user),
                  SYS_HEAD: JSON.stringify(sysHead)
                }
              })
            }
          })
        } else {
          console.log('获取用户登录态失败！' + res.errMsg)
        }
      }
    });
  }
})
Page({
  data: {
    background: ['demo-text-1', 'demo-text-2', 'demo-text-3'],
    indicatorDots: true,
    vertical: false,
    autoplay: true,
    interval: 3000,
    duration: 1000
  },
  onLoad:function() {
    wx.login
    var obj = new Object();
    sysHead.MESSAGE_TYPE = '1400';
    sysHead.MESSAGE_CODE = '1000';
    sysHead.USER_ID = '666666';
    obj.id = 1;
    obj.name = "小发发";
    /*wx.request({
      url: "http://10.255.105.225:10086",
      method:"POST",
      header: { 'content-type': 'application/x-www-form-urlencoded' },
      data:{
        data: JSON.stringify(obj),
        SYS_HEAD: JSON.stringify(sysHead)
      },
      success:function(result) {
        console.log("result",result)
      },
      fail:function(e) {

      }
    })*/
  },
  changeIndicatorDots: function (e) {
    this.setData({
      indicatorDots: !this.data.indicatorDots
    })
  },
  intervalChange: function (e) {
    this.setData({
      interval: e.detail.value
    })
  },
  durationChange: function (e) {
    this.setData({
      duration: e.detail.value
    })
  }
})
