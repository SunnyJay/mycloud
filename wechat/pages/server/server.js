// pages/server/server.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    multiIndex: [0, 0, 0],
    multiArray: [['CentOS', 'Ubuntu'], ['7.4', '7.3', '6.9', '16.04', '14.04']],
    cpuSize: 1,
    diskSize: 1,
    memorySize: 1,
    bandwidth: 1
  },

  changeCpuSize(e) {
    this.setData({ cpuSize: e.detail.value })
  },
  changeDiskSize(e) {
    this.setData({ diskSize: e.detail.value })
  },
  changeMemorySize(e) {
    this.setData({ memorySize: e.detail.value })
  },
  changeBandwidth(e) {
    this.setData({ bandwidth: e.detail.value })
  },

  formSubmit: function (e) {
    wx.request({
      url: 'http://localhost:8804/tangyuan/manage/instances',
      method: 'POST',
      data: {
        sshPassword: e.detail.value.pass,
        cpuSize: this.data.cpuSize,
        diskSize: this.data.diskSize,
        memorySize: this.data.memorySize,
        bandwidth: this.data.bandwidth
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
          url: '../user/user'
        })
      }
    })

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