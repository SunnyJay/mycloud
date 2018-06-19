// pages/manage/manage.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    src: "resources/boy.png",
    code: "abc",
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      phone: options.phone,
      userInfo: options.userInfo

    }),
      wx.login({
        success: function (res) {
          if (res.code) {
            //发起网络请求
            wx.request({
              url: 'http://localhost:8899/get_user_by_code/' + res.code, //仅为示例，并非真实的接口地址
              method: 'GET',
              data: {
                code: res.code,
              },
              header: {
                'content-type': 'application/json' // 默认值
              },
              success: function (res) {
              },
              fail: function (err) {
                console.log(err.data)
              },//请求失败
            })
          } else {
            console.log('登录失败！' + res.errMsg)
          }
        }
      })

     
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