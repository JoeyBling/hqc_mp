var util = require('../../utils/util.js')
var conf = {
  data: {
    hasEmptyGrid: false,
  },
  onLoad(options) {
    debugger;
    var ticketInfo = JSON.parse(options.params);
    this.setData({
      ticketInfo
    })
    //获得今天，明天，后天的对应关系
    this.getThreeDay();
    var date;
    //初始化时间
    if(ticketInfo.lastDate){
      date = new Date(ticketInfo.lastDate);
    }else{
      date = new Date();
    }
    var cur_year = date.getFullYear();
    var cur_month = date.getMonth();
    this.calculateEmptyGrids(cur_year, cur_month);
    this.calculateDays(cur_year, cur_month);
    this.setData({
      cur_year,
      cur_month
    })

  },
  getThisMonthDays(year, month) {
    return new Date(year, month+1, 0).getDate();
  },
  getFirstDayOfWeek(year, month) {
    return new Date(Date.UTC(year, month, 1)).getDay();
  },
  calculateEmptyGrids(year, month) {
    var firstDayOfWeek = this.getFirstDayOfWeek(year, month);
    var empytGrids = [];
    if (firstDayOfWeek > 0) {
      for (var i = 0; i < firstDayOfWeek; i++) {
        empytGrids.push(i);
      }
      this.setData({
        hasEmptyGrid: true,
        empytGrids
      });
    } else {
      this.setData({
        hasEmptyGrid: false,
        empytGrids: []
      });
    }
  },
  calculateDays(cur_year, cur_month) {
    var daysInfo = [];
    daysInfo = this.createDate(cur_year, cur_month);

    this.setData({
      daysInfo
    })
  },
  getThreeDay: function(){
    var threeDayArr = ['今天','明天','后天'];
    var threeDayObj = {};
    for(var i =0;i<3;i++)
    {
      var date = new Date(new Date().getTime() + i*(24*60*60*1000));
      var tempDateStr = util.formatDate(date);
      threeDayObj[tempDateStr] = threeDayArr[i]
    }
    this.setData({
      threeDayObj
    })   
  },
  createDate: function (year, month) {
    var ticketInfo = this.data.ticketInfo
    var returnValue = [];
    var beginDay = new Date(year, month, 1).getDay();
    var nDays = new Date(year, month + 1, 0).getDate();
    var pushObj = {
      day: "",
      value: "",
      showDay: "",
      price: "",
    };
    var len = 43 - (42 - nDays - beginDay);
    for (var i = 1; i < len; i++) {
      var tempDate = new Date(year, month, (i - beginDay), 0, 0, 0);
      var tempDateStr = util.formatDate(tempDate);
      if (i > beginDay && i <= nDays + beginDay) {
        var _day = this.getDay(tempDate, tempDateStr, i - beginDay);
        var price = this.getPrice(tempDate, ticketInfo);
        pushObj = {
          day: tempDateStr,
          value: _day,
          showDay: this.getShowDate(tempDateStr),
          price: price,
        };
        returnValue.push(pushObj);
      }
    }
    return returnValue;
  },
  getPrice: function (date, ticketInfo){
    //不在可购买区域内不显示价格信息
    var tomorTimes = util.getTomorTimes();
    var startBuyDateTimes = ticketInfo.startBuyDate * 1000;
    var endBuyDateTimes = ticketInfo.endBuyDate * 1000;
    var startTimes = startBuyDateTimes > tomorTimes ? startBuyDateTimes : tomorTimes;
    if (date.getTime() < startTimes || date.getTime() > endBuyDateTimes){
      return "";
    }
    if (!ticketInfo.weekendType)
    {
      return '￥'+ticketInfo.price;
    }
    //周末价格另算的情况
    if (date.getDay() == 0 || date.getDay() == 6)
    {
      return '￥' +ticketInfo.weekendPrice;
    }
    return '￥' +ticketInfo.price;
  },
  getShowDate: function (day) {
    var date = new Date(day);
    if (!date) {
      return;
    }
    var dateStr = util.formatDate(date);
    return this.data.threeDayObj[dateStr] || date.getDate();
  },
  getDay: function (tempDate, tempDateStr, day) {
    var ret = "";
    ret = day;
    return ret;
  },
  getDateString: function (date, split) {
    if (typeof (date) == "string") {
      date = new Date(date);
    }
    split = split || '-';
    var tempArr = [date.getFullYear(), date.getMonth() + 1, date.getDate()];
    return tempArr.join(split);
  },
  selectDate: function (e) {
    var date = e.currentTarget.dataset.select; //选择的时间
    var limit = e.currentTarget.dataset.limit; //为0则代码不可点
    if (!limit)
    {
      return;
    }
    var selectInfo = e.currentTarget.dataset.info;
    //本地存储选择的时间
    wx.setStorage({
      key: "traveltime",
      data: selectInfo,
    });

    getApp().globalData.visitInfo = selectInfo;
    console.info("info===" + JSON.stringify(getApp().globalData.visitInfo));
    wx.navigateBack({
      delta: 1
    })
  },
  handleCalendar(e) {
    var handle = e.currentTarget.dataset.handle;
    var cur_year = this.data.cur_year;
    var cur_month = this.data.cur_month;
    var today = new Date();
    var now_month = today.getMonth();
    var now_year = today.getFullYear();
    if (handle === 'prev') {
      if (now_year == cur_year && now_month == cur_month)
      {
        return;
      }
      var newMonth = cur_month - 1;
      var newYear = cur_year;
      if (newMonth < 1) {
        newYear = cur_year - 1;
        newMonth = 12;
      }

      this.calculateDays(newYear, newMonth);
      this.calculateEmptyGrids(newYear, newMonth);

      this.setData({
        cur_year: newYear,
        cur_month: newMonth
      })

    } else {
      // 最长三个月
      if((((cur_year - now_year) * 12 + cur_month) - now_month) >=3)
      {
        return;
      } 
      var newMonth = cur_month + 1;
      var newYear = cur_year;
      if (newMonth > 12) {
        newYear = cur_year + 1;
        newMonth = 1;
      }

      this.calculateDays(newYear, newMonth);
      this.calculateEmptyGrids(newYear, newMonth);

      this.setData({
        cur_year: newYear,
        cur_month: newMonth
      })
    }
  }
};

Page(conf);
