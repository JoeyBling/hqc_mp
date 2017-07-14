//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    toView: "",
    current: 0,
    userInfo: {},
    banner: [], //滚动图片数据
    tickList: [],
    topSwiper: {
      indicatorDots: true,
      autoplay: true,
      interval: 3000,
      duration: 100,
    },
    swiper: {
      indicatorDots: false,
      autoplay: false,
      interval: 5000,
      duration: 1000
    },
  },
  onPullDownRefresh: function () {
    console.log('onPullDownRefresh')
  },
  changeSlider: function (e) {
    this.setData({
      current: e.detail.current
    })
  },
  switchSlider: function (e) {
    this.setData({
      current: e.target.dataset.index
    })
  },
  scroll: function (e) {
    if (this.data.toView == "top") {
      this.setData({
        toView: ""
      })
    }
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  tap: function () {
    this.setData({
      toView: "top"
    })
  },
  //商品详情页跳转
  lookdetail: function (e) {
    var lookid = e.currentTarget.dataset;
    console.log(e.currentTarget.dataset.id);
    wx.navigateTo({
      url: "/pages/detail/detail?id=" + lookid.id
    })
  },
  //转到商品预订界面
  scheduledGoods: function (e) {
    var lookid = e.currentTarget.dataset;
    console.log(e.currentTarget.dataset.id);
    wx.navigateTo({
      url: "/pages/scheduledGoods/scheduledGoods?id=" + lookid.id
    })
  },
  onLoad: function () {
    //调用应用实例的方法获取全局数据
    var that = this;
    //获取滚动图片信息
    wx.request({
      url: app.globalData.domain + '/hqc_mp/ws/banner',
      method: "GET",
      header: {
        "content-type": "application/json"
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            banner: JSON.parse(res.data.banner)
          })
        }
        console.info(that.data.banner)
      },
      fail: function (err) {
        console.log(err)
      }
    });

    //查询门票信息
    wx.request({
      url: app.globalData.domain + '/hqc_mp/ws/ticket',
      method: "GET",
      header: {
        "content-type": "application/json"
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            tickList: JSON.parse(res.data.page).list
          })
        }
        console.info(that.data.tickList)
      },
      fail: function (err) {
        console.log(err)
      }
    })

  }
})
