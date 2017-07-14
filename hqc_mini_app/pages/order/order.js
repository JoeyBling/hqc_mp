var app = getApp();
var timer = require('../../plugins/wxTimer.js');
var util = require('../../utils/util.js')
Page({
  data: {
    wxTimerList: {},//涉及到多个定时器时所需
    onPullDownRefresh: function () {
      console.log('onPullDownRefresh')
    },
    statueZh: ['', '已支付', '支付失败', '待支付', '已关闭'], //1、已支付 2、支付失败 3、待支付 4、已关闭(用户取消订单或超时未支付)
    ordersList: [],
    hideloading: false,
  },
  goToPay: function (e) {
    console.info("立即支付");
  },
  cancleOrder: function (e) {
    console.info("开始取消订单");
    var orderNo = e.currentTarget.dataset.id;
    var that = this;
    wx.showModal({
      title: '温馨提示',
      content: '您确定要取消该订单？',
      success: function (res) {
        if (res.confirm) {
          console.log('用户点击确定')
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
                      url: app.globalData.domain + '/hqc_mp/ws/canOrder',
                      data: {
                        encryptedData: res.encryptedData,
                        iv: res.iv,
                        code: code,
                        orderNo: orderNo
                      },
                      method: "GET",
                      success: function (d) {
                        var data = d.data;
                        if (data.code == 0) {
                          console.info("取消订单成功");
                          that.refreshOrders();
                        }
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
            },
            fail: function () {
              callback(false)
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  deleteOrder: function (e) {
    var orderNo = e.currentTarget.dataset.id;
    var that = this;
    wx.showModal({
      title: '温馨提示',
      content: '删除订单不可恢复，确定删除？',
      success: function (res) {
        if (res.confirm) {
          console.log('用户点击确定')
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
                      url: app.globalData.domain + '/hqc_mp/ws/delOrder',
                      data: {
                        encryptedData: res.encryptedData,
                        iv: res.iv,
                        code: code,
                        orderNo: orderNo
                      },
                      method: "GET",
                      success: function (d) {
                        var data = d.data;
                        if (data.code == 0) {
                          that.refreshOrders();
                        }
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
            },
            fail: function () {
              callback(false)
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })

  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
  },
  addTimer: function () {
    var list = this.data.ordersList;
    var that = this;
    var tempList = list;
    list.forEach(function (item, index) {
      if (item.status == 3) {
        var leftTimerTime = util.caculTimerTime(item.createTime * 1000);
        console.info("name=====" + item.orderNo);
        if (leftTimerTime == "00:00:00") { 
          tempList[index].status = 4; //设置为关闭
          return;
        }
        //创建定时器
        new timer({
          beginTime: leftTimerTime,
          name: item.orderNo,
          complete: function () {
            console.log("完成了")
          }
        }).start(that);
      }
    });
    this.setData({
      ordersList: tempList
    })
  },
  lookdetail: function (e) {
    var lookid = e.currentTarget.dataset;
    console.log(e.currentTarget.dataset.id);
    wx.navigateTo({
      url: "/pages/orderDetail/orderDetail?id=" + lookid.id
    })
  },
  gotToBuy: function (e) {
    wx.switchTab({
      url: "/pages/index/index"
    });
  }, //刷新订单列表
  refreshOrders: function () {
    //查询门票信息
    var that = this;
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
                  code: code
                },
                method: "GET",
                header: {
                  "content-type": "application/json"
                },
                success: function (d) {
                  var data = d.data;
                  if (data.code == 0) {
                    debugger;
                    var ordersList = JSON.parse(data.page).list;
                    that.setData({
                      ordersList
                    })
                    //增加定时器
                    that.addTimer();
                    //最后再处理时间                   
                    that.setData({
                      ordersList: that.dealResData(that.data.ordersList),
                      hideLoading:true
                    })
                    //加载数据成功
                  }
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
  onReady: function () {

    // 页面渲染完成
  },
  onShow: function () {
    this.refreshOrders();
  },
  dealResData: function (list) {
    var length = list.length;
    var newList = [];
    list.forEach(function (item, index) {
      if (!item.del) {
        item.startTime = util.formatTime(new Date(item.startTime * 1000));
        item.endTime = util.formatTime(new Date(item.endTime * 1000));
        newList.push(item);
      }
    });
    return newList;
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})