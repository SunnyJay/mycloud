// pages/user/user.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    toView: 'red',
    scrollTop: 100,
    instanceList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    wx.request({
      url: 'http://localhost:8804/tangyuan/manage/instances/',
      method: 'GET',
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        // utils.formatTime()

        var utils = require('../../utils/util.js');

        var instanceList = res.data;  
        
        for (var i = 0; i < instanceList.length; i++) {
          var date = new Date(instanceList[i].expireTime);
          instanceList[i].expireTime = utils.formatTime(date);
          console.log(instanceList[i].expireTime)
        };

        //一定要在渲染前进行修改
         that.setData({ instanceList: res.data });  


      
       // console.log(res.data)
      },
      fail: function (err) {
        console.log(err.data)
      },//请求失败
      complete: function () {
       
      }
    });
   


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