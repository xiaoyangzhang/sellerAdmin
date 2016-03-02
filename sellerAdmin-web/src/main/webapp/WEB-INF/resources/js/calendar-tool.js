/**
 * 日期工具
 */
var CalendarY = {
	// 日期+天
	AddDays : function(d, n) {
		var m = moment(d);
		return m.add(n, 'd').toDate();
	},
	// 日期+月。日对日，若目标月份不存在该日期，则置为最后一日
	AddMonths : function(d, n) {
		var m = moment(d);
		return m.add(n, 'M').toDate();
	},
	// 日期+年。月对月日对日，若目标年月不存在该日期，则置为最后一日
	AddYears : function(d, n) {
		var m = moment(d);
		return m.add(n, 'y').toDate();
	},
	// 月初
	getMonthStartDate : function(date) {
		return moment(date).startOf('month').toDate();
	},
	/*
	 * // 获得本季度的开始月份 getQuarterStartMonth : function() { if (nowMonth <= 2) {
	 * return 0; } else if (nowMonth <= 5) { return 3; } else if (nowMonth <= 8) {
	 * return 6; } else { return 9; } }, // 周一 getWeekStartDate : function() {
	 * return AddDays(now, -nowDayOfWeek); }, // 周日。本周一+6天 getWeekEndDate :
	 * function() { return AddDays(getWeekStartDate(), 6); }, // 月末。下月初-1天
	 * getMonthEndDate : function() { return
	 * AddDays(AddMonths(getMonthStartDate(), 1), -1); }, // 季度初
	 * getQuarterStartDate : function() { return new Date(nowYear,
	 * this.getQuarterStartMonth(), 1); }, // 季度末。下季初-1天 getQuarterEndDate :
	 * function() { return
	 * this.AddDays(this.AddMonths(this.getQuarterStartDate(), 3), -1); },
	 */
	// 格式化
	format : function(date, fmt) {
		return moment(date).format(fmt);
	},
	// 解析
	parse :function(date) {
		return moment(date).toDate();
	},
	// 得到按月分类的树
	getMonthTree : function(startDate, endDate) {
		var dateJson = {};
		for (var i = startDate; i.getTime() <= endDate.getTime(); i = this
				.AddDays(i, 1)) {
			var key = this.getMonthStartDate(i).getTime();
			if (dateJson[key] != null) {
				dateJson[key].push(i.getTime());
			} else {
				dateJson[key] = [ i.getTime() ];
			}
		}
		return dateJson;
	},
	getTimeDifferenceByMinute : function(date1, date2) {
		var date3 = date2.getTime() - date1.getTime(); // 时间差的毫秒数
		// 计算出相差天数
		var days = Math.floor(date3 / (24 * 3600 * 1000));
		// 计算出相差天数
		var days = Math.floor(date3 / (24 * 3600 * 1000));
		// 计算出小时数
		var leave1 = date3 % (24 * 3600 * 1000); // 计算天数后剩余的毫秒数
		var hours = Math.floor(leave1 / (3600 * 1000));
		// 计算相差分钟数
		var leave2 = leave1 % (3600 * 1000); // 计算小时数后剩余的毫秒数
		var minutes = Math.floor(leave2 / (60 * 1000));
		// 计算相差秒数
		var leave3 = leave2 % (60 * 1000); // 计算分钟数后剩余的毫秒数
		// var seconds = Math.round(leave3 / 1000);
		return days + "天 " + hours + "小时 " + minutes + " 分钟";
	}
};