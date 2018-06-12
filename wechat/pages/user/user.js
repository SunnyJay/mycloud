// pages/user/user.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    toView: 'red',
    scrollTop: 100,
    instanceList: [],
    menu_item: [
      {
        "id": 0,
        "text": "停止"
      },
      {
        "id": 1,
        "text": "重启"
      },
      {
        "id": 2,
        "text": "重置密码"
      },
      {
        "id": 3,
        "text": "SSH连接"
      },
    ],
    winWidth: 0,
    winHeight: 0,
    // tab切换  
    currentTab: 0,
    "menu_hide": true
  },

  open_menu: function () {
    this.setData({
      menu_hide: false
    });

    console.log('sssss');

    var animation = wx.createAnimation({
      duration: 200,
    })
    animation.bottom("10rpx").step();
    this.setData({
      animation: animation.export()
    });


  },
  /** 
     * 滑动切换tab 
     */

  /*退出菜单*/
  close_menu: function () {
    //菜单动画
    var animation = wx.createAnimation({
      duration: 200,
    })
    animation.bottom("-400rpx").step();
    this.setData({
      animation: animation.export()
    });

    setTimeout(function () {
      this.setData({
        menu_hide: true
      })
    }.bind(this), 200)
  }
  ,
  bindChange: function (e) {

    var that = this;
    that.setData({ currentTab: e.detail.current });
  },

  swichNav: function (e) {

    var that = this;

    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
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
        that.setData({ instanceList: instanceList });



        // console.log(res.data)
      },
      fail: function (err) {
        console.log(err.data)
      },//请求失败
      complete: function () {

      }
    });


    wx.getSystemInfo({

      success: function (res) {
        that.setData({
          winWidth: res.windowWidth,
          winHeight: res.windowHeight
        });
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