var sysHead = require('../../../utils/sysHead')

class shopService{

  constructor() {

  }
  
  /**
   * 获取用户的商铺列表
   */
  getUserShopList(appInstance, successCallback) {
    sysHead.MESSAGE_TYPE = '0004'
    sysHead.MESSAGE_CODE = '0001'
    appInstance.postRequest(sysHead, null,
      function (res) {
        typeof successCallback == "function" && successCallback(res)
      })
  }

  /**
   * 查询店铺信息
   */
  getShopById(appInstance, data, successCallback) {
    sysHead.MESSAGE_TYPE = '0004'
    sysHead.MESSAGE_CODE = '0003'
    appInstance.postRequest(sysHead, data,
      function (res) {
        typeof successCallback == "function" && successCallback(res)
      })
  }
}

export default shopService