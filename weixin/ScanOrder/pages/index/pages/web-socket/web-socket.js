const qcloud = require('../../../../vendor/qcloud-weapp-client-sdk/index')
const requestUrl = require('../../../../config').requestUrl

function showModal(title, content) {
  wx.showModal({
    title,
    content,
    showCancel: false
  })
}

function showSuccess(title) {
  wx.showToast({
    title,
    icon: 'success',
    duration: 1000
  })
}


Page({
  data: {
    socketStatus: 'closed',
    text:'',
    content:'',
    messages:[]
  },
  extraLine: [],

  onLoad: function () {
    var self = this

    qcloud.setLoginUrl("https://10.255.105.225:443/login")

    qcloud.login({
      success: function (result) {
        console.log('登录成功', result)
        self.setData({
          hasLogin: true
        })
        self.openSocket()
      },

      fail: function (error) {
        console.log('登录失败', error)
      }
    })
  },

  onUnload: function () {
    this.closeSocket()
  },

  toggleSocket: function (e) {
    const turnedOn = e.detail.value

    if (turnedOn && this.data.socketStatus == 'closed') {
      this.openSocket()

    } else if (!turnedOn && this.data.socketStatus == 'connected') {
      var showSuccess = true
      this.closeSocket(showSuccess)
    }
  },

  openSocket: function () {
    var socket = this.socket = new qcloud.Tunnel("https://10.255.105.225:443/tunnel")

    socket.on('connect', () => {
      console.log('WebSocket 已连接')
      showSuccess('Socket已连接')
      this.setData({
        socketStatus: 'connected',
        waitingResponse: false
      })
    })

    socket.on('close', () => {
      console.log('WebSocket 已断开')
      this.setData({ socketStatus: 'closed' })
    })

    socket.on('error', error => {
      showModal('发生错误', JSON.stringify(error))
      console.error('socket error:', error)
      this.setData({
        loading: false
      })
    })

    // 监听服务器推送消息
    socket.on('message', message => {
      var self = this
      var who = message.who
      var messages = self.data.messages
      var msg = new Object()
      msg.icon = who.avatarUrl
      msg.msg = message.word
      if (messages.length % 3 == 0) {
        msg.from = "me"
      } else {
        msg.from = "oth"
      }
      messages.push(msg)
      console.log(messages)
      console.log('socket message:', message)
      this.setData({
        loading: false,
        messages: messages
      })
    })

    // 打开信道
    socket.open()
  },

  closeSocket: function (showSuccessToast) {
    if (this.socket) {
      this.socket.close()
    }
    if (showSuccessToast) showSuccess('Socket已断开')
    this.setData({ socketStatus: 'closed' })
  },

  formSubmit: function (e) {
    var self = this
    var text = e.detail.value.messageText
    if (this.socket && this.socket.isActive()) {
      this.socket.emit('message', {
        'content': text
      })
      this.setData({
        loading: true,
        text: ''
      })
    }
  }
})
