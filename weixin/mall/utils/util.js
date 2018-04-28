const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function openLocationSetting(successCallback) {
  wx.openSetting({
    success: (res) => {
      if (!res.authSetting['scope.userLocation']) {
        showLocationRemind(successCallback);
      }
    }, fail: (res) => {
      if (!res.authSetting['scope.userLocation']) {
        showLocationRemind(successCallback);
      }
    }
  })
}

function showLocationRemind(successCallback) {
  wx.showModal({
    title: '温馨提醒',
    content: '需要获取您的地理位置才能使用小程序',
    showCancel: false,
    confirmText: '获取位置',
    success: function (res) {
      if (res.confirm) {
        chooseLocation(successCallback);
      }
    }, fail: (res) => {
      chooseLocation(successCallback);
    }
  })
}

function chooseLocation(successCallback) {
  wx.chooseLocation({
    success: function (res) {
      typeof successCallback == "function" && successCallback(res)
    }
  })
  wx.getSetting({
    success(res) {
      if (!res.authSetting['scope.userLocation']) {
        openLocationSetting(successCallback)
      }
    }
  })
}

module.exports = {
  formatTime: formatTime,
  chooseLocation: chooseLocation
}
