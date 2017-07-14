var app = getApp();
var timer = require('../../plugins/wxTimer.js');
var util = require('../../utils/util.js')
Page({
  data: {
    wxTimerList: {},//涉及到多个定时器时所需
    orderDetail: {},
    statueZh: ['', '已支付', '支付失败', '待支付', '已关闭'], //1、已支付 2、支付失败 3、待支付 4、已关闭(用户取消订单或超时未支付)
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    this.setData({
      orderId: options.id
    })
    var that = this;
    //获取传递过来的
    wx.login({
      success: function (r) {
        var code = r.code;//登录凭证
        if (code) {
          //2、调用获取用户信息接口
          wx.getUserInfo({
            success: function (res) {
              console.log({ encryptedData: res.encryptedData, iv: res.iv, code: code })
              //这里进行操作
              wx.request({
                url: app.globalData.domain + '/hqc_mp/ws/order',
                data: {
                  encryptedData: res.encryptedData,
                  iv: res.iv,
                  code: code,
                  orderNo: that.data.orderId
                },
                method: "GET",
                header: {
                  "content-type": "application/json"
                },
                success: function (d) {
                  var data = d.data;
                  if (data.code == 0) {
                    var dataObj = JSON.parse(data.entity);
                    //先设值
                    that.setData({
                      orderDetail: dataObj
                    })
                    //创建定时器
                    that.createTimer(dataObj);
                    that.setData({
                      orderDetail: that.dealResData(that.data.orderDetail)
                    })
                  }
                  console.info(data);
                },
                fail: function (err) {
                  console.log(err)
                }
              });
            },
            fail: function () {
              console.log('获取用户信息失败')
            }
          })
        } else {
          console.log('获取用户登录态失败！' + r.errMsg)
        }
      }
    })
  },
  createTimer: function (orderDetail) {
    var that = this;
    var leftTimerTime = util.caculTimerTime(orderDetail.createTime * 1000);
    //过期了
    if (leftTimerTime == '00:00:00') {
      this.changeStatus(4); //4为已关闭
      return;
    }

    var wxTimer = new timer({
      beginTime: leftTimerTime,
      complete: function () {
        that.changeStatus(4); // 4为已关闭
      }
    })
    wxTimer.start(this);

  },
  changeStatus: function ( status){
    var orderDetail = this.data.orderDetail;
    orderDetail.status = status; 
    this.setData({
      orderDetail
    }) 
  },
  dealResData: function (data) {
    if (data) {
      data.createTime = util.formatTime(new Date(data.createTime * 1000), true);
      data.startTime = util.formatTime(new Date(data.startTime * 1000));
      data.endTime = util.formatTime(new Date(data.endTime * 1000));
    };
    return data;
  },
  onReady: function () {

    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})