//index.js

// 获取显示区域长宽
const device = wx.getSystemInfoSync()
const W = device.windowWidth
//const H = device.windowHeight - 50
const H = device.windowWidth

let cropper = require('../../../../welCropper/welCropper.js');

console.log(device)

Page({
  data: {
  },
  onLoad: function () {
    var self = this
    // 初始化组件数据和绑定事件
    cropper.init.apply(self, [W, H]);
  },
  selectTap(e) {
    var self = this
    let mode = e.currentTarget.dataset.mode
    console.log(e)

    wx.chooseImage({
      count: 3,
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success(res) {
        const images = res.tempFilePaths
        console.log(images)
        self.imagesCropper(mode, images, 0)
      }
    })
  },

  imagesCropper: function (mode, images, index) {
    console.log("index", index)
    var self = this
    if (images.length > index) {
      // 将选取图片传入cropper，并显示cropper
      self.showCropper({
        src: images[index],
        mode: mode,
        sizeType: ['original', 'compressed'],   //'original'(default) | 'compressed'
        callback: (res) => {
          if (mode == 'rectangle') {
            console.log("crop callback:" + res)
            wx.previewImage({
              current: '',
              urls: [res]
            })
          }
          else {
            wx.showModal({
              title: '',
              content: JSON.stringify(res),
            })

            console.log(res)
          }
          
          self.hideCropper() //隐藏，我在项目里是点击完成就上传，所以如果回调是上传，那么隐藏掉就行了，不用previewImage
          self.imagesCropper(mode, images, ++index)
        }
      })
    }
  }
})
