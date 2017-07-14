function formatTime(date,needTime) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds();

  if(needTime)
  {
    return [year, month, day].map(formatNumber).join('.') + ' ' + [hour, minute, second].map(formatNumber).join(':')
  }
  return [year, month, day].map(formatNumber).join('.');
  
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function getTomorTimes() {
  var date = new Date();
  var oneDayTimes = 86400000; 
  return new Date(date.getFullYear(), date.getMonth(), date.getDate()).getTime() + oneDayTimes;
}

function getTodayTimes()
{
  var date = new Date();
  return new Date(date.getFullYear(), date.getMonth(), date.getDate()).getTime();
}

function formatDate(date,split) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  split = split || '-'

  return [year, month, day].map(formatNumber).join(split);
}

/**
 * 计算定时器时间
 * startDate: 开始的时间
 * leftSeconds : 多久后失效（以分钟为单位） 
 * return  返回的格式 “00：12：00”
 */
function caculTimerTime(startDate, leftMinutes){
  leftMinutes = leftMinutes || 40; //默认40分钟
  var nowTimes = new Date().getTime();
  var expireTimes = new Date(startDate).getTime() + leftMinutes*60*1000;
  // 还在有效期内
  if (expireTimes > nowTimes){
    var tempDate = new Date(new Date("1970/1/1").getTime() + expireTimes - nowTimes);
    return [tempDate.getHours(), tempDate.getMinutes(),tempDate.getSeconds()].map(formatNumber).join(":");
  }
  return "00:00:00";
}

module.exports = {
  formatTime: formatTime,
  formatDate: formatDate,
  getTomorTimes: getTomorTimes,
  getTodayTimes, getTodayTimes,
  caculTimerTime,caculTimerTime
}
