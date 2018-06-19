// pages/server/server.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    multiIndex: [0, 0],
    objectMultiArray: [
      [{
          id: 0,
          name: 'CentOS'
        },
        {
          id: 1,
          name: 'Ubuntu'
        }
      ],
      [{
          id: 0,
          name: '7.4'
        },
        {
          id: 1,
          name: '7.3'
        },
        {
          id: 2,
          name: '6.9'
        },
      ]
    ],
    cpuSize: 1,
    diskSize: 1,
    memorySize: 1,
    bandwidth: 1
  },

  changeCpuSize(e) {
    this.setData({
      cpuSize: e.detail.value
    })
  },
  changeDiskSize(e) {
    this.setData({
      diskSize: e.detail.value
    })
  },
  changeMemorySize(e) {
    this.setData({
      memorySize: e.detail.value
    })
  },
  changeBandwidth(e) {
    this.setData({
      bandwidth: e.detail.value
    })
  },

  bindMultiPickerChange: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      multiIndex: e.detail.value
    })
    console.log(this.data.multiIndex)
  },

  bindMultiPickerColumnChange: function(e) {
    console.log('修改的列为', e.detail.column, '，值为', e.detail.value);
    var data = {
      objectMultiArray: this.data.objectMultiArray,
      multiIndex: this.data.multiIndex
    };
    data.multiIndex[e.detail.column] = e.detail.value;
    switch (e.detail.column) {
      case 0:
        switch (data.multiIndex[0]) {
          case 0:
            data.objectMultiArray[1] = [{
                id: 0,
                name: '7.4'
              },
              {
                id: 1,
                name: '7.3'
              },
              {
                id: 2,
                name: '6.9'
              },
            ];
            break;
          case 1:
            data.objectMultiArray[1] = [{
                id: 0,
                name: '16.04'
              },
              {
                id: 1,
                name: '14.04'
              },
            ];
            break;
        }
        data.multiIndex[1] = 0;
        break;
    }
    this.setData(data);
  },
  formSubmit: function(e) {
    var warn = ""; //弹框时提示的内容  
    var flag = true; //判断信息输入是否完整  
    if (e.detail.value.pass == '') {
      warn = '请输入登录密码'
    } else if (e.detail.value.passconfirm == '') {
      warn = '请输入确认密码'
    } else if (e.detail.value.pass != e.detail.value.passconfirm) {
      console.log(e.detail.value.pass)
      console.log(e.detail.value.passconfirm)
      warn = '登录密码与确认密码不一致'
    } else {
      flag = false;
      wx.request({
        url: 'http://127.0.0.1:8804/tangyuan/manage/instances',
        method: 'POST',
        data: {
          sshPassword: e.detail.value.pass,
          cpuSize: this.data.cpuSize,
          diskSize: this.data.diskSize,
          memorySize: this.data.memorySize,
          bandwidth: this.data.bandwidth,
          baseOS: this.data.multiIndex[0],
          baseOSVersion: this.data.multiIndex[1],
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function(res) {
          console.log(res.data)
        },
        fail: function(err) {
          console.log(err.data)
        }, //请求失败
        complete: function() {
          wx.navigateTo({
            url: '../user/user'
          })
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

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

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