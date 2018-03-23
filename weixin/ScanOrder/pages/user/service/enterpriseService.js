var sysHead = require('../../../utils/sysHead')

class enterpriseService {

  constructor() {

  }

  /**
   * 获取用户的商铺列表
   */
  getUserEnterprise(appInstance, successCallback) {
    sysHead.MESSAGE_TYPE = '0006'
    sysHead.MESSAGE_CODE = '0002'
    appInstance.postRequest(sysHead, null,
      function (res) {
        typeof successCallback == "function" && successCallback(res)
      })
  }

  mergeEnterprise(appInstance, data, successCallback) {
    sysHead.MESSAGE_TYPE = '0006'
    sysHead.MESSAGE_CODE = '0001'
    appInstance.postRequest(sysHead, data,
      function (res) {
        typeof successCallback == "function" && successCallback(res)
      })
  }
}

export default enterpriseService