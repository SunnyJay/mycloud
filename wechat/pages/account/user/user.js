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
    isLogin: false,
    phone: ''
  },

  login() {
    wx.navigateTo({
      url: '../login/login'
    })
  },

  showDetail: function (e) {
    console.log(wx.getStorageSync('userId'))
    var that = this;
    wx.navigateTo({
      url: 'detail/detail?id=' + wx.getStorageSync('userId')
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

    const app = getApp();
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

          var status = app.getSessionStatus();
          
          if (status == 1) {
            //登录过则设置isLogin 
            that.setData({
              isLogin: true,
              phone: wx.getStorageSync('phone')
            })
          } else{
            //未登录过则什么都不做
            console.log("未登录")
          }
        } else {
          //未授权，去授权界面授权
          wx.redirectTo({
            url: '../auth/auth'
          })
        }
      }
    });

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    console.log("onshow")
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