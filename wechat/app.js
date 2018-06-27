//app.js
App({

    /**
     * 检查Session，
     */
    getSessionStatus: function() {
      var status = 5;
      var thirdSessionId = wx.getStorageSync('token')
      console.log(thirdSessionId)

      if (thirdSessionId) {
        console.log("之前登录过")
        return 1
      } else {
        console.log("之前没有登录过")
        return 0;
      }
    },

    onLaunch: function() {

      //若之前登录过，则以后每次启动小程序则登录一次 无论session是否有效
      if (this.getSessionStatus() == 1) {
        this.doLogin(); //重新登录
      }
    },

    globalData: {
      userInfo: null
    },

    //只是登录 不添加用户
    doLogin: function() {
      var that = this;
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
                success: function(res) {
                  console.log(res.data)
                  wx.setStorageSync('thirdSessionId', res.data.thirdSessionId)
                },
              })
          } else {
            console.log('登录失败 ' + res.errMsg);
          }
        }
      })
    },
  }

)