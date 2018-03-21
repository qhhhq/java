var appInstance = getApp()
var sysHead = require('../../../../utils/sysHead')
const uploadFileUrl = require('../../../../config').uploadFileUrl
const requestUrl = require('../../../../config').requestUrl

Page({

  /**
   * 页面的初始数据
   */
  data: {
    tpmImg:'',
    tmpImg1:'',
    img:'',
    img1:'',
    //input
    name: '',
    code: '',
    legal: '',
    legalDocId: '',
    address: '',
    phone: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var self = this
    self.initValidate()
    self.loadEnterprise()
  },

  /**
   * 初始化验证组件
   */
  initValidate:function() {
    this.wxValidate = appInstance.wxValidate(
      {
        name: {
          required: true,
          minlength: 2,
          maxlength: 50,
        },
        code: {
          required: true,
          maxlength: 50,
        },
        legal: {
          required: true,
          minlength: 2,
          maxlength: 50,
        },
        legal_doc_id: {
          required: true,
          minlength: 18,
          maxlength: 50,
        },
        address: {
          required: true,
          minlength: 2,
          maxlength: 200,
        },
        phone: {
          required: true,
          tel: true,
        }
      }
      , {
        name: {
          required: '请填写您的姓名姓名',
        },
        code: {
          required: '请填写您的手机号',
        },
        legal: {
          required: '请填写您的手机号',
        },
        legal_doc_id: {
          required: '请填写您的手机号',
        },
        address: {
          required: '请填写您的手机号',
        },
        phone: {
          required: '请填写您的手机号',
        }
      }
    )
  },

  /**
   * 发起网络请求，获取企业信息
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
          console.log("data", res.data.DATA.enterprise)
          self.setData({
            tpmImg: requestUrl + enterprise.img,
            tmpImg1: requestUrl + enterprise.img1,
            img: enterprise.img,
            img1: enterprise.img1,
            name: enterprise.name,
            code: enterprise.code,
            legal: enterprise.legal,
            legalDocId: enterprise.legalDocId,
            address: enterprise.address,
            phone: enterprise.phone
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
   * 表达提交
   */
  formSubmit:function(e) {
    var self = this

    self.setData({
      loading: true
    })

    var img = self.data.img
    var img1 = self.data.img1
    if (!this.wxValidate.checkForm(e)) {
      const error = this.wxValidate.errorList[0]
      // `${error.param} : ${error.msg} `
      wx.showModal({
        content: `${error.msg} `
      })
      return false
    }
    if (img == null || img == "") {
      wx.showModal({
        content: `请选择图片1 `
      })
      return false
    }
    if (img1 == null || img1 == "") {
      wx.showModal({
        content: `请选择图片2 `
      })
      return false
    }

    sysHead.MESSAGE_TYPE = '0006';
    sysHead.MESSAGE_CODE = '0001';
    var reqData = new Object()
    reqData.name = e.detail.value.name
    reqData.code = e.detail.value.code
    reqData.legal = e.detail.value.legal
    reqData.legalDocId = e.detail.value.legal_doc_id
    reqData.address = e.detail.value.address
    reqData.phone = e.detail.value.phone
    reqData.img = img
    reqData.img1 = img1
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
  },

  /**
   * 上传第一张图片
   */
  chooseImage: function () {
    var self = this

    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album'],
      success: function (res) {
        wx.showToast({
          title: "正在上传",
          icon: "loading",
          duration: 10000
        })
        console.log('chooseImage success, temp path is', res.tempFilePaths[0])
        var imageSrc = res.tempFilePaths[0]
        sysHead.MESSAGE_TYPE = '0005';
        sysHead.MESSAGE_CODE = '0002';
        sysHead.FILE_TYPE = 'shop';
        wx.uploadFile({
          url: uploadFileUrl,
          filePath: imageSrc,
          name: 'imageSrc',
          formData:{
            SYS_HEAD: JSON.stringify(sysHead),
            deleteFile: self.data.img
          },
          success: function (res) {
            console.log('uploadImage success, res is:', res)
            wx.hideToast()
            wx.showToast({
              title: '上传成功',
              icon: 'success',
              duration: 1000
            })
            var resData = JSON.parse(res.data)
            self.setData({
              tpmImg: imageSrc,
              img: resData.DATA.fileName
            })
          },
          fail: function ({ errMsg }) {
            wx.hideToast()
            console.log('uploadImage fail, errMsg is', errMsg)
          }
        })

      },
      fail: function ({ errMsg }) {
        console.log('chooseImage fail, err is', errMsg)
      }
    })
  },

  /**
   * 上传第二张图片
   */
  chooseImage1: function () {
    var self = this

    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album'],
      success: function (res) {
        wx.showToast({
          title: "正在上传",
          icon: "loading",
          duration: 10000
        })
        console.log('chooseImage success, temp path is', res.tempFilePaths[0])

        var imageSrc = res.tempFilePaths[0]
        sysHead.MESSAGE_TYPE = '0005';
        sysHead.MESSAGE_CODE = '0002';
        sysHead.FILE_TYPE = 'shop';
        wx.uploadFile({
          url: uploadFileUrl,
          filePath: imageSrc,
          name: 'imageSrc',
          formData: {
            SYS_HEAD: JSON.stringify(sysHead),
            deleteFile:self.data.img1
          },
          success: function (res) {
            console.log('uploadImage success, res is:', res)
            wx.hideToast()
            wx.showToast({
              title: '上传成功',
              icon: 'success',
              duration: 1000
            })

            var resData = JSON.parse(res.data)
            self.setData({
              tpmImg1 : imageSrc,
              img1: resData.DATA.fileName
            })
          },
          fail: function ({ errMsg }) {
            wx.hideToast()
            console.log('uploadImage fail, errMsg is', errMsg)
          }
        })

      },
      fail: function ({ errMsg }) {
        console.log('chooseImage fail, err is', errMsg)
      }
    })
  }
})