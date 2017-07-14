
var util = require('../../utils/util.js')
var app = getApp();
var WxParse = require('../../utils/wxParse/wxParse.js');
Page({
  data: {
    current: 0,
    ticketNum: 1,
    agreeValue: 0,
    modalHidden: true,//提示框
    finalTime: "",//最终选择的时间
    selectDayIndex: "",//选择哪个使用期限0（明天），1（后天），2（更改日期）
    checkInfo: "",//提交订单的校验信息
    tel: "", //提交的手机号
    items: [
      { value: 0, agreeContent: '我已阅读并同意"预订须知"条款' }
    ],
  },
  swiper: {
    indicatorDots: false,
    autoplay: false,
    interval: 5000,
    duration: 1000
  },
  changeSlider: function (e) {
    this.setData({
      current: e.detail.current
    })
  },
  checkboxChange: function () {
    this.setData({
      agreeValue: !this.data.agreeValue
    })
    console.info("agreeValue==" + this.data.agreeValue);
  },
  switchSlider: function (e) {
    this.setData({
      current: e.target.dataset.index
    })
  },
  reduceTickNum: function (e) {
    var curTicketNum = this.data.ticketNum;
    if (curTicketNum > 1) {
      curTicketNum--;
    }
    this.setData({
      ticketNum: curTicketNum
    })
  },
  addTicketNum: function (e) {
    var curTicketNum = this.data.ticketNum;
    //这里的5代表最多的购票数
    if (curTicketNum < 5) {
      curTicketNum++;
    }
    this.setData({
      ticketNum: curTicketNum
    })
  },
  //对商品预订的信息进行校验
  modalTap: function (e) {
    var finalDate = this.data.finalTime;
    var agree = this.data.agreeValue;
    var checkInfo = "";
    var hideTip = false;
    if (!finalDate) {
      checkInfo = "请选择日期";
    } else if (!this.validateTel(this.data.tel)) {
      checkInfo = "请输入正确的电话号码";
    } else if (!agree) {
      checkInfo = "请阅读预定须知，并确认你已同意";
    } else {
      hideTip = true;
    }
    this.setData({
      checkInfo: checkInfo,
      modalHidden: hideTip
    })
    if (hideTip) {
      this.createOrder();
    }
  },//创建订单
  createOrder: function () {
    console.info("开始创建订单！");
    var that = this;
    var ticketId = this.data.ticketId;
    var orderPhone = this.data.tel;
    var ticketCount = this.data.ticketNum;
    var finalTime = this.data.finalTime
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
                url: app.globalData.domain + '/hqc_mp/ws/createOrder',
                data: {
                  encryptedData: res.encryptedData,
                  iv: res.iv,
                  code: code,
                  ticketId: ticketId,
                  orderPhone: orderPhone,
                  useTime: finalTime,
                  ticketCount: ticketCount
                },
                method: "GET",
                success: function (d) {
                  var data = d.data;
                  if (data.code == 0) {
                    console.info("创建订单成功！");
                    var orderObj = JSON.parse(data.entity);
                    wx.redirectTo({
                      url: '../orderDetail/orderDetail?id=' + orderObj.orderNo,
                    })
                  } else {
                    wx.showModal({
                      title: '温馨提示',
                      content: data.msg,
                      success: function (res) {
                        if (res.confirm) {
                        } else if (res.cancle) {
                        }
                      }
                    });
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
  },
  checkTel: function (e) {
    var tel = e.detail.value;
    if (!this.validateTel(tel)) {
      console.info(tel + "不是一个正确的号码");
    }
    this.setData({
      tel: tel
    })

  },
  //检验手机号码是否正确
  validateTel: function (tel) {
    return /^1[34578]\d{9}$/.test(tel);
  },
  modalChange: function (e) {
    this.setData({
      modalHidden: true
    })
  },
  onLoad: function (options) {
    console.info("onLoad again");
    // 页面初始化 options为页面跳转所带来的参数
    var id = options.id; //门票id
    this.setData({
      ticketId: id //购票的门票ID
    })

    var that = this;
    //查询门票信息
    wx.request({
      url: app.globalData.domain + '/hqc_mp/ws/ticket',
      data: {
        ticketId: id
      },
      method: "GET",
      header: {
        "content-type": "application/json"
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            detailTick: JSON.parse(res.data.entity)
          })
          //初始化使用期限
          that.createUseLife();
          that.parseHtml();
        }
        console.info(that.data.detailTick)
      },
      fail: function (err) {
        console.log(err)
      }
    });
  },
  openCalendar: function (option) {
    var ticketInfo = option.currentTarget.dataset.ticketinfo;
    var useLifeInfo = option.currentTarget.dataset.uselife;
    if (!useLifeInfo.openCalen) {
      this.setData({
        finalTime: useLifeInfo.value
      })
      this.changeOfLife(useLifeInfo.value);
      return;
    }
    var params = {};
    params.ticketId = ticketInfo.id; //门票id
    params.startBuyDate = ticketInfo.startBuyDate;
    params.endBuyDate = ticketInfo.endBuyDate;
    params.weekendType = ticketInfo.weekendType;
    params.weekendPrice = ticketInfo.weekendPrice;
    params.lastDate = useLifeInfo.value; //选择的时间
    params.price = ticketInfo.price;
    console.info(params);
    wx.navigateTo({
      url: "/pages/calendar/calendar?params=" + JSON.stringify(params)
    })
  },
  //改变使用期限
  changeOfLife: function (day) {
    var useLifeArr = this.data.useLifeArr || [];
    var that = this;
    var change = false;
    useLifeArr.map(function (item, index, arr) {
      //处理选中状态的样式
      if (day == item.value) {
        item.className = 'selectTime';
        change = true;
      } else {
        item.className = '';
      }
      if (index == arr.length - 1) {
        if (!change) {
          item.className = 'selectTime'
          item.value = day;
          item.showDay = day.slice(5) + '>'; //截取到时间年份
          that.setData({
            finalTime: day
          })
        } else if (item.value != day) {
          item.className = ''
          item.value = '';
          item.showDay = '更多时间>'; //截取到时间年份
        }
      }
    });
    this.setData({
      useLifeArr
    })
  },
  createUseLife: function () {
    var detailTick = this.data.detailTick;
    var startBuyDateTimes = detailTick.startBuyDate * 1000;
    var endBuyDateTimes = detailTick.endBuyDate * 1000;
    var count = 0;
    var useLifeArr = [];
    var tomorTimes = util.getTomorTimes();
    var startTimes = startBuyDateTimes > tomorTimes ? startBuyDateTimes : tomorTimes;
    while (startTimes <= endBuyDateTimes) {

      var obj = {};
      obj.className = '',
        obj.value = util.formatDate(new Date(startTimes));
      obj.showDay = obj.value.slice(5); //截取掉年份
      obj.openCalen = false;
      count++;
      startTimes += 86400000; //下一天
      useLifeArr.push(obj);
      if (count == 2) {
        break;
      }
    }
    var moreObj = {};
    moreObj.className = '';
    moreObj.showDay = '更多时间>';
    moreObj.value = '';
    moreObj.openCalen = true;
    useLifeArr.push(moreObj);

    this.setData({
      useLifeArr
    })
  },
  onReady: function () {
    // 页面渲染完成
  },
  parseHtml: function () {
    var detailTick = this.data.detailTick;
    var about = detailTick.about;
    var ticketContent = detailTick.ticketContent;
    var that = this;
    WxParse.wxParse('about', 'html', about, that, 5);
    WxParse.wxParse('ticketContent', 'html', ticketContent, that, 5);
  },
  onShow: function () {
    // 页面显示
    var visitInfo = getApp().globalData.visitInfo;
    this.data.detailTick;
    if (visitInfo) {
      this.changeOfLife(visitInfo.day);
    }
    console.info("onShow again");
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    getApp().globalData.visitInfo = {}; //离开时清除游玩日期
  }
})
