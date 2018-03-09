/**
 * 小程序配置文件
 */

var host = "10.255.105.225:10086"

var config = {

  // 下面的地址配合云端 Server 工作
  host,

  // 登录地址，用于建立会话
  loginUrl: `http://${host}/login`,

  // 测试的请求地址，用于测试会话
  requestUrl: `http://${host}`,

  // 用code换取openId
  openIdUrl: `http://${host}/openid`,

  // 测试的信道服务接口
  tunnelUrl: `http://${host}/tunnel`,

  // 生成支付订单的接口
  paymentUrl: `http://${host}/payment`,

  // 发送模板消息接口
  templateMessageUrl: `https://${host}/templateMessage`,

  // 上传文件接口
  uploadFileUrl: `https://${host}/upload`,

  // 下载示例图片接口
  downloadExampleUrl: `https://${host}/static/weapp.jpg`
};

module.exports = config
