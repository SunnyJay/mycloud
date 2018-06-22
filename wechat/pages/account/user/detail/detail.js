// pages/account/user/detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    accountDetail: '',
    id:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

    this.setData({
      id: options.id
    });   


    var that = this;
    console.log(that.data.id)
    wx.request({
      url: 'http://127.0.0.1:8801/tangyuan/account/accounts/' + that.data.id,
      method: 'GET',
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function(res) {
        that.setData({
          accountDetail: res.data
        });
      },
      fail: function(err) {
        console.log(err.data)
      }, //请求失败

      complete: function() {

      } //请求完成后执行的函数
    })



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