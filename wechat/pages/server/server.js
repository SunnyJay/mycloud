// pages/server/server.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    multiIndex2: [0, 0],
    objectMultiArray: [
      [
        {
          id: 0,
          name: '无脊柱动物'
        },
        {
          id: 1,
          name: '脊柱动物'
        }
      ], [
        {
          id: 0,
          name: '扁性动物'
        },
        {
          id: 1,
          name: '线形动物'
        },
        {
          id: 2,
          name: '环节动物'
        },
        {
          id: 3,
          name: '软体动物'
        },
        {
          id: 3,
          name: '节肢动物'
        }
      ]
    ],
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
  bindMultiPickerChange2: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      multiIndex2: e.detail.value
    })
    console.log(multiIndex2)
  },
  bindMultiPickerColumnChange2: function (e) {
    console.log('修改的列为', e.detail.column, '，值为', e.detail.value);
    var data = {
      objectMultiArray: this.data.objectMultiArray,
      multiIndex2: this.data.multiIndex2
    };
    data.multiIndex2[e.detail.column] = e.detail.value;
    switch (e.detail.column) {
      case 0:
        switch (data.multiIndex2[0]) {
          case 0:
            data.objectMultiArray[1] = [
              { id: 0, name: '扁性动物' },
              { id: 1, name: '线形动物' },
              { id: 2, name: '环节动物' },
              { id: 3, name: '软体动物' },
              { id: 3, name: '节肢动物' }
            ];
            // data.multiArray[2] = ['猪肉绦虫', '吸血虫'];
            break;
          case 1:
            data.objectMultiArray[1] = [
              { id: 0, name: '鱼' },
              { id: 1, name: '线形两栖动物' },
              { id: 2, name: '爬行动物' }
            ];
            break;
        }
        data.multiIndex2[1] = 0;
        // data.multiIndex[2] = 0;
        break;
    }
    this.setData(data);
  }
  ,
  formSubmit: function (e) {
    console.log('sssssssssss')

    wx.request({
      url: 'http://127.0.0.1:8804/tangyuan/manage/instances',
      method: 'POST',
      data: {
        sshPassword: e.detail.value.pass,
        cpuSize: this.data.cpuSize,
        diskSize: this.data.diskSize,
        memorySize: this.data.memorySize,
        bandwidth: this.data.bandwidth,
        baseOS: this.data.bandwidth
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