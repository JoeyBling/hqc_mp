/**
 * 移动webapp开发 日历组件 可用于需要日历选择的场景 - 日历范围选择 - 节假日显示 - 自由配置初始化生成多日历 - 各种操作事件自由监听
 * 
 * @author samczhang@tencent.com ----------------------------------------------
 *         对外调用接口及自定义事件
 * @method render 渲染日历
 * @method show 显示日历
 * @method hide 隐藏日历
 * @method setSelectDate 设置当前选中日期
 * 
 * @customEvent selectDate 选择日期时派发事件
 * @customEvent show 显示日历时派发事件
 * @customEvent hide 隐藏日历时派发事件
 */

(function(root, factory) {
	if (typeof define === 'function') {
		define('calendar', ['jQuery'], function($) {
					return factory(root, $);
				});
	} else {
		root.calendar = factory(root, root.$);
	}

})(window, function(root, $) {

	var util = {
		/**
		 * 根据当前年月，计算下一个月的年月
		 * 
		 * @para year {int} eg: 2014 YYYY
		 * @para month {int} eg: 12 MM/M
		 * @return {object}
		 */
		getNextMonthDate : function(year, month) {
			if (month > 12) {
				year = year + Math.floor((month - 1) / 12);

				if (month % 12 == 0) {
					month = 12;
				} else {
					month = month % 12;
				}
			}

			return {
				year : year,
				month : month
			}
		},

		/**
		 * 处理小于10的数字前自动加0
		 * 
		 * @para num {num} int return {string} '02'
		 */
		formatNum : function(num) {
			if (num < 10) {
				num = '0' + num;
			}

			return num;
		},

		/**
		 * 连接年月日，连接符为`-` return {string} yyyy-mm-dd
		 */
		joinDate : function(year, month, day) {
			var formatNum = util.formatNum;

			return year + '-' + formatNum(month) + '-' + formatNum(day);
		},

		/**
		 * 将格式化后日期转化为数字，便于日期计算
		 * 
		 * @para date {string|date object} yyyy-mm-dd|日期对象 return {string}
		 *       yyyymmdd
		 */
		dateToNum : function(date) {
			var rst = '';
			if (typeof date == 'string') {
				rst = date.split('-').join('');
			} else {
				rst = util.formatDate(date).split('-').join('');
			}

			return rst;
		},

		/**
		 * 格式化日期对象
		 * 
		 * @para {date object} 日期对象 return {string} yyyy-mm-dd
		 */
		formatDate : function(dateObj) {
			var year = dateObj.getFullYear(), month = dateObj.getMonth() + 1, day = dateObj
					.getDate();

			return util.joinDate(year, month, day);
		},

		/**
		 * 获取当前日期的下一天
		 * 
		 * @para date {string|date object} yyyy-mm-dd|日期对象
		 * @para num {int} 获取指定日期之后的几天 return {string} yyyy-mm-dd
		 */
		getNextDate : function(date, num) {
			return util.formatDate(new Date(+new Date(date) + num * 86400000));
		}
	};

	var tpl = ['<div class="cal-wrap">', '<h2>{%date%}</h2>', '{%week%}',
			'<ul class="day">', '{%day%}', '</ul>', '</div>'].join('');

	var weekTpl = ['<ul class="week">', '<li>一</li>', '<li>二</li>',
			'<li>三</li>', '<li>四</li>', '<li>五</li>', '<li class="wk">六</li>',
			'<li class="wk">日</li>', '</ul>'].join('');

	var holidaysMap = [{
				name : '今天',
				date : [util.formatDate(new Date())]
			}, {
				name : '明天',
				date : [util.formatDate(new Date(+new Date() + 86400000))]
			}, {
				name : '平安夜',
				date : ['2017-12-24', '2018-12-24', '2019-12-24', '2020-12-24',
						'2021-12-24', '2022-12-24']
			}, {
				name : '圣诞节',
				date : ['2017-12-25', '2018-12-25', '2019-12-25', '2020-12-25',
						'2021-12-25', '2022-12-25']
			}, {
				name : '情人节',
				date : ['2018-02-14', '2019-02-14', '2020-02-14', '2021-02-14',
						'2022-02-14', '2023-02-14']
			}, {
				name : '元旦',
				date : ['2018-01-01', '2019-01-01', '2020-01-01', '2021-01-01',
						'2022-01-01', '2023-01-01']
			}, {
				name : '除夕',
				date : ['2018-02-15', '2019-02-04', '2020-01-24', '2021-02-11',
						'2022-01-31', '2023-01-21']
			}, {
				name : '春节',
				date : ['2018-02-16', '2019-02-05', '2020-01-25', '2021-02-12',
						'2022-02-01', '2023-01-22']
			}, {
				name : '元宵节',
				date : ['2018-03-02', '2019-02-19', '2020-02-18', '2021-02-26',
						'2022-02-15', '2023-02-05']
			}, {
				name : '妇女节',
				date : ['2017-03-08', '2018-03-08', '2019-03-08', '2020-03-08',
						'2021-03-08', '2022-03-08']
			}, {
				name : '植树节',
				date : ['2017-03-12', '2018-03-12', '2019-03-12', '2020-03-12',
						'2021-03-12', '2022-03-12']
			}, {
				name : '愚人节',
				date : ['2017-04-01', '2018-04-01', '2019-04-01', '2020-04-01',
						'2021-04-01', '2022-04-01']
			}, {
				name : '清明节',
				date : ['2017-04-04', '2018-04-05', '2019-04-05', '2020-04-04',
						'2021-04-04', '2022-04-05']
			}, {
				name : '地球日',
				date : ['2017-04-22', '2018-04-22', '2019-04-22', '2020-04-22',
						'2021-04-22', '2022-04-22']
			}, {
				name : '劳动节',
				date : ['2017-05-01', '2018-05-01', '2019-05-01', '2020-05-01',
						'2021-05-01', '2022-05-01']
			}, {
				name : '青年节',
				date : ['2017-05-04', '2018-05-04', '2019-05-04', '2020-05-04',
						'2021-05-04', '2022-05-04']
			}, {
				name : '儿童节',
				date : ['2017-06-01', '2018-06-01', '2019-06-01', '2020-06-01',
						'2021-06-01', '2022-06-01']
			}, {
				name : '端午节',
				date : ['2017-05-30', '2018-06-18', '2019-06-07', '2020-06-25',
						'2021-06-14', '2022-06-03']
			}, {
				name : '建党节',
				date : ['2017-07-01', '2018-07-01', '2019-07-01', '2020-07-01',
						'2021-07-01', '2022-07-01']
			}, {
				name : '七夕节',
				date : ['2017-08-28', '2018-08-17', '2019-08-07', '2020-08-25',
						'2021-08-14', '2022-08-04']
			}, {
				name : '建军节',
				date : ['2017-08-01', '2018-08-01', '2019-08-01', '2020-08-01',
						'2021-08-01', '2022-08-01']
			}, {
				name : '教师节',
				date : ['2017-09-10', '2018-09-10', '2019-09-10', '2020-09-10',
						'2021-09-10', '2022-09-10']
			}, {
				name : '中秋节',
				date : ['2017-10-04', '2018-09-24', '2019-09-13', '2020-10-01',
						'2021-09-21', '2022-09-10']
			}, {
				name : '粮食日',
				date : ['2017-10-16', '2018-10-16', '2019-10-16', '2020-10-16',
						'2021-10-16', '2022-10-16']
			}, {
				name : '万圣节',
				date : ['2017-10-31', '2018-10-31', '2019-10-31', '2020-10-31',
						'2021-10-31', '2022-10-31']
			}, {
				name : '重阳节',
				date : ['2017-10-28', '2018-10-17', '2019-10-07', '2020-10-25',
						'2021-10-14', '2022-10-04']
			}, {
				name : '国庆节',
				date : ['2017-10-01', '2018-10-01', '2019-10-01', '2020-10-01',
						'2021-10-01', '2022-10-01']
			}, {
				name : '光棍节',
				date : ['2017-11-11', '2018-11-11', '2019-11-11', '2020-11-11',
						'2021-11-11', '2022-11-11']
			}, {
				name : '感恩节',
				date : ['2017-11-30', '2018-11-29', '2019-11-28', '2020-11-26',
						'2021-11-25', '2022-11-24']
			}];

	var dayPricemMap = {
		'2017-04-13' : '287',
		'2017-04-14' : '287',
		'2017-04-15' : '287',
		'2017-04-16' : '287',
		'2017-04-17' : '287',
		'2017-04-18' : '287',
		'2017-04-19' : '287',
		'2017-04-20' : '287',
		'2017-04-21' : '287',
		'2017-04-22' : '287',
		'2017-04-23' : '287',
		'2017-04-24' : '287',
		'2017-04-25' : '287',
		'2017-04-26' : '287',
		'2017-04-27' : '287',
		'2017-04-28' : '287',
		'2017-04-29' : '287',
		'2017-04-30' : '287',
		'2017-04-31' : '287',
		'2017-05-01' : '287',
		'2017-05-02' : '287',
		'2017-05-03' : '287',
		'2017-05-04' : '287',
		'2017-05-05' : '287',
		'2017-05-06' : '287',
		'2017-05-07' : '287',
		'2017-05-08' : '287',
		'2017-05-09' : '287'
	};

	var calendar = function(config) {
		this.defaultConfig = {
			/**
			 * 日历外层容器ID type {string|jq object} id字符串或jq对象
			 */
			el : '#calendar',

			/**
			 * 初始化日历显示个数
			 */
			count : 5,

			/**
			 * 用于初始化日历开始年月 type {date object} 日期对象
			 */
			date : new Date(),

			/**
			 * 日期最小值，当小于此日期时日期不可点 type {date object} 日期对象
			 */
			minDate : null,

			/**
			 * 日期最大值，当大于此日期时日期不可点 type {date object} 日期对象
			 */
			maxDate : null, // 日期对象

			/**
			 * 初始化当前选中日期，用于高亮显示 type {date object} 日期对象
			 */
			selectDate : new Date(),

			/**
			 * 选中日期时日期下方文案 type {string}
			 */
			selectDateName : '购票',

			/**
			 * 是否显示节假日 type {boolean}
			 */
			isShowHoliday : true,

			/**
			 * 是否显示票价 type {boolean}
			 */
			isShowTicketPrice : true,

			/**
			 * 传入票价JSON数据 type {boolean}
			 */
			dayPriceData : dayPricemMap[0],

			/**
			 * 无票的提示文本 type {boolean}
			 */
			noTicketText : '',

			/**
			 * 在日历中是否显示星期 type {boolean}
			 */
			isShowWeek : true
		};

		this.config = $.extend({}, this.defaultConfig, config || {});
		this.el = (typeof this.config.el === 'string')
				? $(this.config.el)
				: this.config.el;

		this.init.call(this);
	};

	$.extend(calendar.prototype, {
		init : function() {
			this._initDate();
			this.render(this.config.date);
			this._initEvent();
		},

		_initDate : function() {
			var me = this, config = this.config, dateObj = config.date, dateToNum = util.dateToNum;

			// 初始化日历年月
			this.year = dateObj.getFullYear();
			this.month = dateObj.getMonth() + 1;

			this.minDate = config.minDate;
			this.maxDate = config.maxDate;
			this.selectDate = config.selectDate;

			// 上下月切换步长，根据初始化日历个数决定
			this.step = config.count;
		},

		/**
		 * 根据日期对象渲染日历
		 * 
		 * @para {date object} 日期对象
		 */
		render : function(date) {
			var me = this, config = this.config, date = date || config.date, year = date
					.getFullYear(), month = date.getMonth() + 1, tmpTplArr = [];

			for (var i = 0; i < config.count; i++) {
				var curMonth = month + i, curDate = util.getNextMonthDate(year,
						curMonth), dateStr = curDate.year + '年'
						+ util.formatNum(curDate.month) + '月', dayList = this
						._getDayList(curDate.year, curDate.month), week = '';

				if (config.isShowWeek) {
					week = weekTpl;
				}

				var curTpl = tpl.replace('{%date%}', dateStr).replace(
						'{%week%}', week).replace('{%day%}', dayList);

				tmpTplArr.push(curTpl);
			}

			this.el.html(tmpTplArr.join(''));

			this.setSelectDate(this.selectDate);
		},

		_initEvent : function() {
			var me = this, config = this.config;

			this.el.delegate('ul.day li', 'click', function(event) {

				var curItem = $(this), date = curItem.data('date'), dateName = $(curItem
						.find('i')[1]).text();

				// 更新当前选中日期YYYY-MM-DD
				me.selectDate = date;

				if (!curItem.hasClass('iv')) {
					me.afterSelectDate(me.selectDate);
					var calendarDom = $((me.el.selector).toString());
					calendarDom.trigger('afterSelectDate', [{
										date : date,
										dateName : dateName,
										curItem : curItem
									}]);
				}
			});
		},

		/**
		 * 根据当前年月，获取日期列表html字体串
		 * 
		 * @para year {int} eg: 2014 YYYY
		 * @para month {int} eg: 12 MM/M
		 * @return {string}
		 */
		_getDayList : function(year, month) {
			var me = this, config = this.config,

			days = new Date(year, month, 0).getDate(), firstDay = Math
					.abs(new Date(year, month - 1, 1).getDay()),

			dateToNum = util.dateToNum, joinDate = util.joinDate,

			tmpEptArr = [];
			tmpDayDataArr = [], tmpDayTplArr = [];

			// 如果是星期天
			if (firstDay == 0) {
				firstDay = 7;
			}

			// 插入空白字符
			for (var i = 0; i < firstDay; i++) {
				tmpEptArr.push('<li class="ept"></li>');
			}

			for (var i = 0; i < days; i++) {
				var day = i + 1, date = joinDate(year, month, day), wkDay = new Date(date)
						.getDay(), dateNum = dateToNum(date), jrClassName = 'dl jr';

				// 默认不做任何处理
				tmpDayDataArr.push({
							class : '',
							date : date,
							day : day,
							name : '',
							price : ''
						});

				// 双休单独标注出
				if (wkDay == 6 || wkDay == 0) {
					jrClassName = 'dl jr wk';
					tmpDayDataArr[i]['class'] = 'wk';
				}

				// 不在指定范围内的日期置灰
				if ((me.minDate && dateNum < dateToNum(me.minDate))
						|| (me.maxDate && dateNum > dateToNum(me.maxDate))) {
					jrClassName = 'dl iv';
					tmpDayDataArr[i]['class'] = 'iv';
				}

				// 节假日处理
				if (config.isShowHoliday) {
					for (var k = 0, hlen = holidaysMap.length; k < hlen; k++) {
						var name = holidaysMap[k]['name'], dateArr = holidaysMap[k]['date'];

						for (var j = 0, len = dateArr.length; j < len; j++) {
							var item = dateArr[j];

							if (dateNum == dateToNum(item)) {
								tmpDayDataArr[i]['class'] = jrClassName;
								tmpDayDataArr[i]['name'] = name;
								break;
							}
						}
					}
				}

				// 票价处理
				if (config.isShowTicketPrice) {
					tmpDayDataArr[i]['price'] = config.dayPriceData[date] ? "￥"
							+ config.dayPriceData[date] : config.noTicketText;
				}

				// 初始化当前选中日期状态
				if (config.selectDate) {
					if (dateNum == dateToNum(me.selectDate)) {
						tmpDayDataArr[i]['class'] += ' cur';
					}
				}
			}

			// 生成日期模板字符串
			for (var i = 0, len = tmpDayDataArr.length; i < len; i++) {
				var item = tmpDayDataArr[i];

				tmpDayTplArr.push('<li class="' + item.class + '" data-date="'
								+ item.date + '">' + '<i class="w_day">'
								+ item.day + '</i><i class="w_title">'
								+ item.name + '</i>' + '<em class="w_price">'
								+ item.price + '</em>' + '</li>');
			}

			return tmpEptArr.concat(tmpDayTplArr).join('');
		},

		/**
		 * 设置选中日期格式
		 * 
		 * @para {date object|date string} YYYY-MM-DD
		 */
		setSelectDate : function(date) {
			var me = this, config = this.config, date = (typeof date == 'string')
					? date
					: util.formatDate(date), dateNum = util.dateToNum(date),

			lastSltItem = this.el.find('li.cur'), curSltItem = $(this.el[0]
					.querySelector('li[data-date="' + date + '"]'));

			// 先移到上次选中日期高亮
			if (lastSltItem.length) {
				var lastDateNameEl = $(lastSltItem.find('i')[1]);

				lastSltItem.removeClass('cur');
				if (!lastSltItem.hasClass('jr')) {
					lastSltItem.removeClass('dl');
					lastDateNameEl.text('');
				}
			}

			// 添加当前选中日期高亮
			if (curSltItem.length) {
				var curDateNameEl = $(curSltItem.find('i')[1]);

				curSltItem.addClass('cur');
				if (!curSltItem.hasClass('jr')) {
					curSltItem.addClass('dl');
					curDateNameEl.text(config.selectDateName);
				}
			}
		},

		/**
		 * 选择日期的后置函数
		 * 
		 * @para {date object|date string} YYYY-MM-DD
		 */
		afterSelectDate : function(date) {
			var me = this, config = this.config, date = (typeof date == 'string')
					? date
					: util.formatDate(date), curSltItem = $(this.el[0]
					.querySelector('li[data-date="' + date + '"]'));
			if (curSltItem.hasClass("cur")) {
				return false;
			}
			// 添加当前选中日期高亮
			if (curSltItem.length) {
				var curDateNameEl = $(curSltItem.find('i')[1]);
				$(this.el[0].querySelector('.cur')).removeClass('cur');
				curSltItem.addClass('cur');
			}
		},

		nextMonth : function() {
			var step = this.step;
			this.render(new Date(this.year, this.month + step - 1, 1));
			this.month += step;
		},

		prevMonth : function() {
			var step = this.step;
			this.render(new Date(this.year, this.month - step - 1, 1));
			this.month -= step;
		},

		show : function() {
			this.el.show();
		},

		hide : function() {
			this.el.hide();
		}
	});

	return {
		calendar : calendar,
		util : util
	};
});
