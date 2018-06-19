// pages/login/login.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },
  formSubmit: function (e) {
    console.log(e)

    var warn = "";//弹框时提示的内容  
    var flag = true;//判断信息输入是否完整  
    if (e.detail.value.phone == "") {
      warn = "请填写您的手机号！";
    } else if (!(/^1(3|4|5|7|8)\d{9}$/.test(e.detail.value.phone))) {
      warn = "手机号格式不正确";
    } else if (e.detail.value.pass == "") {
      warn = "请填写您的密码！";
    }
    else {
      flag = false;//若必要信息都填写，则不用弹框，且页面可以进行跳转  
      wx.request({
        url: 'http://localhost:8899/login/', //仅为示例，并非真实的接口地址
        method: 'POST',
        data: {
          username: e.detail.value.phone,
          password: e.detail.value.pass
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function (res) {
          console.log(res.data)
        },
        fail: function (err) {
          console.log(err.data)
        },//请求失败

        complete: function () {
          wx.navigateTo({
            url: '../manage/manage?phone=' + e.detail.value.phone
          })
        }//请求完成后执行的函数
      })


    }

    if (flag == true) {
      wx.showModal({
        title: '提示',
        content: warn
      })
    }


  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})