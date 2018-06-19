// pages/user/detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // instanceDetail: '',
    // id: 'aaa'
  },

  // //下拉刷新
  // onPullDownRefresh: function () {
  //   wx.showNavigationBarLoading() //在标题栏中显示加载

  //   //模拟加载
  //   setTimeout(function () {



      
  //     // complete
  //     wx.hideNavigationBarLoading() //完成停止加载
      
  //   }, 2000);

    
  // },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      id: options.id
    });   

    var that = this;

    wx.request({
      url: 'http://127.0.0.1:8804/tangyuan/manage/instances/' + that.data.id,
      method: 'GET',
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        that.setData({ instanceDetail: res.data });
      },
      fail: function (err) {
        console.log(err.data)
      },//请求失败

      complete: function () {
        // 转化时间戳为字符串
        var utils = require('../../../utils/util.js');
        var expireTimeDate = new Date(that.data.instanceDetail.expireTime);
        var createTimeDate = new Date(that.data.instanceDetail.createTime);
        var newInstance = that.data.instanceDetail
        newInstance.expireTime = utils.formatTime(expireTimeDate);
        newInstance.createTime = utils.formatTime(createTimeDate);
        that.setData({ instanceDetail: newInstance });
      }//请求完成后执行的函数
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