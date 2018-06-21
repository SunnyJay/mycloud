//app.js
App({

  checkSession: function()
  {
    var thirdSessionId = wx.getStorageSync('thirdSessionId')
    console.log("啊啊啊啊")
    console.log(thirdSessionId)


    if (thirdSessionId) {
      wx.checkSession({
        // session_key 有效(未过期)
        success: function () {
          // 业务逻辑处理
          console.log("未过期")
        },

        // session_key 过期
        fail: function () {
          // session_key过期，重新登录
          console.log("已过期")
          this.doLogin();
        }
      });
    }
    else {
      this.doLogin()
    }
  },
  onLaunch: function () {
    //this.checkSession()
  },

  globalData: {
    userInfo: null
  }
  ,

  doLogin: function () {
    var that = this;
    // 登录
    wx.login({
      success: res => {

        if (res.code) {
          console.log(res.code),

            wx.request({
              url: 'http://127.0.0.1:8801/tangyuan/account/sessions/',
              method: 'POST',
              data: {
                code: res.code,
              },
              success: function (res) {
                //token写入本地缓存
                console.log(res.data)
                console.log(res.data.thirdSessionId)

                wx.setStorageSync('thirdSessionId', res.data.thirdSessionId)
              },
            })
        } else {
          console.log('获取用户登录态失败！' + res.errMsg);
        }
      }
    })
  },
}

)