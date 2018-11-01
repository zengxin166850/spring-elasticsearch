/**
 * Simplified Chinese translation for jquery-daterangepicker
 * author: tter
 */
;(function($){
	if($.fn.daterangepicker){
		$.fn.daterangepicker.defaultOptions = {
			locale: {
	            format: "YYYY-MM-DD",
		        separator: " ~ ",
		        applyLabel: "确定",
		        cancelLabel: "取消",
		        fromLabel: "从",
		        toLabel: "至",
		        customRangeLabel: "自定义",
		        weekLabel: "周",
		        daysOfWeek: [ "日", "一", "二", "三", "四", "五", "六" ],
		        monthNames: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"]
			}
			
		}	
	};
}(jQuery));