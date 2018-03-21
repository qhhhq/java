var sysHead = require('../../../../utils/sysHead')
const uploadFileUrl = require('../../../../config').uploadFileUrl
const requestUrl = require('../../../../config').requestUrl
var util = require('../../../../utils/util.js')
var formatLocation = util.formatLocation
var appInstance = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    enterpriseId:'',
    types: [],
    index: -1,
    provinces:[],
    pIndex:-1,
    citys:[],
    cIndex:-1,
    districts:[],
    dIndex:-1,
    hasLocation: false,
    longitude:0,
    dimension:0,
    loading:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.enterpriseId)
    var self = this
    self.setData({
      enterpriseId: options.enterpriseId
    })

    // 发起网络请求，加载分类
    sysHead.MESSAGE_TYPE = '0007'
    sysHead.MESSAGE_CODE = '0001'
    var reqData = new Object()
    reqData.parent = "423896709274537984"
    wx.showNavigationBarLoading()
    wx.request({
      url: requestUrl,
      method: "POST",
      header: { 'content-type': 'application/x-www-form-urlencoded' },
      data: {
        SYS_HEAD: JSON.stringify(sysHead),
        DATA: JSON.stringify(reqData)
      },
      success: function (res) {
        wx.hideNavigationBarLoading()
        console.log(res)
        if (res.data.SYS_HEAD.retCode == "000000") {
          self.setData({
            types: JSON.parse(res.data.DATA.types)
          })
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

    // 发起网络请求，加载省
    sysHead.MESSAGE_TYPE = '0001'
    sysHead.MESSAGE_CODE = '0001'
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
          self.setData({
            provinces: res.data.DATA.provinces
          })
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

    /**
     * 表单验证
     */
    this.wxValidate = appInstance.wxValidate(
      {
        name: {
          required: true,
          minlength: 2,
          maxlength: 60,
        },
        address: {
          required: true,
          maxlength: 200,
        },
        manager: {
          required: true,
          minlength: 2,
          maxlength: 50,
        },
        contactPhone: {
          required: true,
          tel: true
        },
        contactName: {
          required: true,
          minlength: 2,
          maxlength: 50,
        },
        contactEmail: {
          required: true,
          email: true
        }
      }
      , {
        name: {
          required: "店铺名称不能为空",
          minlength: "请输入正确的店铺名称",
          maxlength: "请输入正确的店铺名称",
        },
        address: {
          required: "详细地址不能为空",
          maxlength: "地址不能超过200字符",
        },
        manager: {
          required: "店铺负责人不能为空",
          minlength: "请输入正确的负责人",
          maxlength: "负责人不能超过50个字符",
        },
        contactPhone: {
          required: "联系电话不能为空",
          tel: "电话格式错误"
        },
        contactName: {
          required: "联系人不能为空",
          minlength: "请输入正确的联系人",
          maxlength: "联系人不能超过50个字符",
        },
        contactEmail: {
          required: "Email不能为空",
          email: "Email格式错误"
        }
      }
    )
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
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },

  /**
   * 选择分类
   */
  bindPickerChange: function (e) {
    this.setData({
      index: e.detail.value
    })
  },

  /**
   * 选择省
   */
  bindProvinceChange: function (e) {
    var self = this
    self.setData({
      pIndex: e.detail.value
    })

    // 发起网络请求，加载市
    sysHead.MESSAGE_TYPE = '0001'
    sysHead.MESSAGE_CODE = '0002'
    var reqData = new Object()
    reqData.parent = self.data.provinces[e.detail.value].id
    wx.request({
      url: requestUrl,
      method: "POST",
      header: { 'content-type': 'application/x-www-form-urlencoded' },
      data: {
        SYS_HEAD: JSON.stringify(sysHead),
        DATA: JSON.stringify(reqData)
      },
      success: function (res) {
        console.log(res)
        if (res.data.SYS_HEAD.retCode == "000000") {
          self.setData({
            citys: res.data.DATA.areas
          })
        } else {
          wx.showToast({
            title: res.data.SYS_HEAD.retMsg,
            duration: 2000
          })
        }
      },
      fail: function (res) {
        console.log(res)
      }
    })
  },

  /**
   * 选择市
   */
  bindCityChange:function(e) {
    var self = this
    self.setData({
      cIndex: e.detail.value
    })

    // 发起网络请求，加载区
    sysHead.MESSAGE_TYPE = '0001'
    sysHead.MESSAGE_CODE = '0002'
    var reqData = new Object()
    reqData.parent = self.data.citys[e.detail.value].id
    wx.request({
      url: requestUrl,
      method: "POST",
      header: { 'content-type': 'application/x-www-form-urlencoded' },
      data: {
        SYS_HEAD: JSON.stringify(sysHead),
        DATA: JSON.stringify(reqData)
      },
      success: function (res) {
        console.log(res)
        if (res.data.SYS_HEAD.retCode == "000000") {
          self.setData({
            districts: res.data.DATA.areas
          })
        } else {
          wx.showToast({
            title: res.data.SYS_HEAD.retMsg,
            duration: 2000
          })
        }
      },
      fail: function (res) {
        console.log(res)
      }
    })
  },

  /**
   * 选择区
   */
  bindDistrictChange:function(e) {
    var self = this
    self.setData({
      dIndex: e.detail.value
    })
  },

  /**
   * 获取位置信息
   */
  getLocation: function () {
    var that = this
    wx.getLocation({
      success: function (res) {
        console.log(res)
        that.setData({
          hasLocation: true,
          longitude: res.longitude,
          dimension: res.latitude,
          location: formatLocation(res.longitude, res.latitude)
        })
      }
    })
  },

  /**
   * 提交表单
   */
  formSubmit: function (e) {
    var self = this

    self.setData({
      loading: true
    })
    
    if (self.checkForm(e)) {
      sysHead.MESSAGE_TYPE = '0004'
      sysHead.MESSAGE_CODE = '0002'
      var reqData = new Object()
      reqData.enterpriseId = self.data.enterpriseId
      reqData.type = self.data.types[self.data.index].id
      reqData.name = e.detail.value.name
      reqData.province = self.data.provinces[self.data.pIndex].province
      reqData.city = self.data.citys[self.data.cIndex].city
      reqData.address = e.detail.value.address
      reqData.district = self.data.districts[self.data.dIndex].district
      reqData.manager = e.detail.value.manager
      reqData.contactPhone = e.detail.value.contactPhone
      reqData.contactName = e.detail.value.contactName
      reqData.contactEmail = e.detail.value.contactEmail
      reqData.longitude = self.data.longitude
      reqData.dimension = self.data.dimension

      //发起网络请求，提交表单
      wx.request({
        url: requestUrl,
        method: "POST",
        header: { 'content-type': 'application/x-www-form-urlencoded' },
        data: {
          SYS_HEAD: JSON.stringify(sysHead),
          DATA: JSON.stringify(reqData)
        },
        success: function (res) {
          console.log(res)
          if (res.data.SYS_HEAD.retCode == "000000") {
            wx.navigateTo({
              url: '../add-shop/index?enterpriseId=' + res.data.DATA.enterpriseId,
            })
          } else {
            wx.showToast({
              title: res.data.SYS_HEAD.retMsg,
              duration: 2000
            })
          }
          self.setData({
            loading: false
          })
        },
        fail: function (res) {
          console.log(res)
          self.setData({
            loading: false
          })
        }
      })

    }
  },

  checkForm: function (e) {
    var self = this
    var valid = true
    var validMsg = ""

    if (self.data.index == -1 || self.data.index == null) {
      validMsg = "门店类别不能为空"
      valid = false
    }
    if (self.data.pIndex == -1 || self.data.pIndex == null) {
      validMsg = "省份不能为空"
      valid = false
    }
    if (self.data.cIndex == -1 || self.data.cIndex == null) {
      validMsg = "城市不能为空"
      valid = false
    }
    if (self.data.dIndex == -1 || self.data.dIndex == null) {
      validMsg = "区域不能为空"
      valid = false
    }
    if (self.data.longitude == 0 || self.data.longitude == null) {
      validMsg = "店铺位置不能为空"
      valid = false
    }
    if (self.data.dimension == 0 || self.data.dimension == null) {
      validMsg = "店铺位置不能为空"
      valid = false
    }
    if (!this.wxValidate.checkForm(e)) {
      const error = this.wxValidate.errorList[0]
      validMsg = `${error.msg} `
      valid = false
    }
    if (valid == false) {
      wx.showModal({
        content: validMsg
      })
    }
    return valid
  }

})