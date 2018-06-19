//app.js
App({
  onLaunch: function () {


    console.log('xxxxxxxxxxxx')

    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    var that = this;
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        if (res.code) {
          that.getNeededUserInfo(res.code);
        } else {
          console.log('获取用户登录态失败！' + res.errMsg);
        　　　　　　}  
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        console.log('ffffff')
        //判断是否已经授权
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          console.log('eeeeeeee')


          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },

  getNeededUserInfo: function (code) {
    　　　　wx.request({
          url: 'http://127.0.0.1:8801/tangyuan/account/sessions/',
      　　　　　　method: 'POST',
      　　　　　　data: {
        　　　　　　　　code: code 
      　　　　　　},
      　　　　　　success: function (res) {
        　　　　　　　　// 可以返回前端需要的用户信息（包括unionid、openid、user_id等）  

      　　　　　　}
    　　　　})
  　　},
  globalData: {
    userInfo: null
  }
})