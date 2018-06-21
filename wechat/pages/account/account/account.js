//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: '你好',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    username: '',
    password: '',
    getText: '获取验证码',
    getChange: true,
    smsGetTime: '',
    isDisabled:true
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
  },

  getCode()
  {
    var that = this;
    if (that.data.getChange)
    {
      that.setData({
        isDisabled: false,
        getChange: false,
        smsGetTime: Date.parse(new Date())
      })

      var n = 59;
      var time = setInterval(function () {
        var str = '等待' + '(' + n + ')' 
        that.setData({
          getText: str
        })
        if (n <= 0) {
          that.setData({
            getChange: true,
            getText: '获取验证码'
          })
          clearInterval(time);
        }
        n--;
      }, 1000);
    }
      

  },
formSubmit: function (e) {
    console.log(e)

    var warn = "";//弹框时提示的内容  
    var flag = true;//判断信息输入是否完整  
    if (e.detail.value.phone == "") {
      warn = "请填写您的手机号";
    } else if (!(/^1(3|4|5|7|8)\d{9}$/.test(e.detail.value.phone))) {
      warn = "手机号格式不正确";
    } else if (e.detail.value.smscode == "") {
      warn = "请填写短信验证码";
    }
    else {
      flag = false;//若必要信息都填写，则不用弹框，且页面可以进行跳转 
      var smsGetTime = this.data.smsGetTime
      console.log(smsGetTime)
      var that = this;
      wx.login({
        success: res => {

          if (res.code) {
            console.log(res.code)
            wx.request({
              url: 'http://127.0.0.1:8801/tangyuan/account/sessions/', //仅为示例，并非真实的接口地址
              method: 'POST',
              data: {
                phone: e.detail.value.phone,
                smscode: e.detail.value.smscode,
                code: res.code,
                smsGetTime: smsGetTime
              },
              header: {
                'content-type': 'application/json' // 默认值
              },
              success: function (res) {
                console.log(res)
                console.log(res.statusCode)
                if (res.statusCode == 401) {

                  console.log('ssssssss')

                  wx.showModal({
                    showCancel: false,
                    title: '错误',
                    content: res.data.message
                  })

                }

                if (res.statusCode == 200) {
                  wx.setStorageSync('thirdSessionId', res.data.thirdSessionId)
                wx.navigateBack({
                  delta: 1
                })
                }
              },
              fail: function (err) {
              },//请求失败

              complete: function () {

              }//请求完成后执行的函数
            })
          } else {
            console.log('获取用户登录态失败！' + res.errMsg);
          }
        }
      })
      

    }

    if (flag == true) {
      wx.showModal({
        title: '提示',
        content: warn
      })
    }


  },
  
})
