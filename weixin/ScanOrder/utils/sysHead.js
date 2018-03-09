var sysDate = new Date();

var userId = wx.getStorageSync('userId')

var sysHead = {

  SOURCE_TYPE:'hhq_dc',

  SERVICE_CODE:'hhq',

  USER_ID: userId
};

module.exports = sysHead