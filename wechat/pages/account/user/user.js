// pages/manage/manage.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    src: "images/boy.png",
    code: "abc",
    userInfo: '',
    hasUserInfo: false,
    isLogin: false
  },
login()
{
  wx.navigateTo({
    url: '../account/account'
  })
}
,
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

    var that = this;
    // 查看是否授权
    wx.getSetting({
      success: function(res) {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称
          wx.getUserInfo({
            success: function(res) {
              console.log(res.userInfo)
              that.setData({
                userInfo: res.userInfo,
                hasUserInfo: true
              })
            }
          })

        } else {
          wx.redirectTo({
            url: '../login/login'
          })
        }
      }
    });

    var thirdSessionId = wx.getStorageSync('thirdSessionId')
    console.log(thirdSessionId)

    if (thirdSessionId) {
      wx.checkSession({
        // session_key 有效(未过期)
        success: function () {
          // 业务逻辑处理
          console.log("未过期")
          that.setData({
            isLogin: true
          })
        },

        // session_key 过期
        fail: function () {
          // session_key过期，重新登录
          console.log("已过期")
          that.doLogin();
        }
      })
    }
  },


  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    this.onLoad()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {


  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})