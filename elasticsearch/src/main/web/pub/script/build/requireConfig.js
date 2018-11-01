/*
 *  系统全部公共配置
 *  author: tter
 * 	date: 2018-07-06
 * 
 * */


require.config({
	baseUrl: window.location.protocol+"//"+window.location.host+"/assets/",
	waitSeconds: 15,
	map: {
		"*": {
	    	"css": "script/dep/require-v2.3.5/plugins/css.min",
	    	"text": "script/dep/require-v2.3.5/plugins/text.min-v2.0.15"
		}
	},
	shim: {
		"layer": {
			deps: ["css!script/dep/layer-v3.1.1/theme/default/layer.css"]
		},
		"backbone": {
			deps: ['underscore'],
            exports: 'Backbone'
		},
		
		"datatables.net": {
			deps: ["jquery"] //"css!script/dep/dataTables/jquery.dataTables.min.css"
		},
		"datatables.zh-CN": {
			deps: ["datatables.net"]
		},
		"dataTables.bootstrap": {
			deps: ["datatables.net","datatables.zh-CN","css!script/dep/dataTables/theme/bootstrap3/dataTables.bootstrap.min.css"]
		},
		"dataTables.fixedColumns": {
			deps: ["dataTables.bootstrap","css!script/dep/dataTables/FixedColumns-3.2.6/css/fixedColumns.bootstrap.min.css"]
		},
		
		"select2": {
			deps: ["css!script/dep/select2-4.0.6/css/select2.min.css"]
		},
		"select2.zh-CN": {
			deps: ["select2"]
		},
		
		"datatimepicker": {
			deps: ["css!script/dep/bootstrap-datetimepicker-2.0/bootstrap-datetimepicker.min.css"]
		},
		"datatimepicker.zh-CN": {
			deps: ["datatimepicker"]
		},
		"daterangepicker":{
			deps:["css!script/dep/daterangepicker-3.0.3/daterangepicker.css"]
		},
		"daterangepicker.zh-CN":{
			deps:["daterangepicker"]
		},
		"bootstrap.multiselect":{
			deps:["jquery","css!script/dep/bootstrap-multiselect-2.0/bootstrap-multiselect.min.css"]
		},
		
		
		"zTree": {
			deps: ["css!script/dep/ztree-3.5.37/css/zTreeStyle/zTreeStyle.css"]
		},
		"zTree.exhide":{
			deps: ["zTree"]
		},
		"selectTree": {
			deps: ["css!script/dep/selectTree/selectTree.css", "zTree.exhide"]
		}
		
		
		
	},
	paths: {
		
		"app": ["script/build/app"],
		"connect": ["script/build/connect"],
		"menu": ["script/build/menu"],   //assets 菜单功能
		"pluginsConfig": ["script/build/pluginsOptionConfig"],  //统一管理插件默认配置选项
		"urlConfig": ["data/urlConfig"],  //统一管理路由配置
		"common": ["script/build/common"],  //提供公共的可自己开发的入口文件
		
		"underscore": ["script/frame/underscore-v1.9.1.min"],
        "backbone": ["script/frame/backbone-v1.3.3.min"],
		
		"jquery": ["script/dep/jquery-v1.12.4.min"],
		"layer": ["script/dep/layer-v3.1.1/layer.min"],
		"moment": ["script/dep/moment-v2.10.6.min"],
		"jq.cookie": ["script/dep/jquery-plugin/jquery.cookie.min-v1.4.1"],
		"jq.pagination": ["script/dep/jquery-plugin/jqpaginator.min-v1.2.1"],
		"echart": ["script/dep/echart-4.1.0/echarts.min"],
		
		"datatables.net": ["script/dep/dataTables/jquery.dataTables.min"],
		"datatables.zh-CN": ["script/dep/dataTables/locales/zh-CN"],
		"dataTables.bootstrap": ["script/dep/dataTables/theme/bootstrap3/dataTables.bootstrap.min"],
		"dataTables.fixedColumns": ["script/dep/dataTables/FixedColumns-3.2.6/js/dataTables.fixedColumns.min"],
		
		"select2": ["script/dep/select2-4.0.6/js/select2.min"],
		"select2.zh-CN": ["script/dep/select2-4.0.6/js/i18n/zh-CN"],
		
		"datatimepicker": ["script/dep/bootstrap-datetimepicker-2.0/bootstrap-datetimepicker.min"],
		"datatimepicker.zh": ["script/dep/bootstrap-datetimepicker-2.0/locales/bootstrap-datetimepicker.zh-CN"],
		"daterangepicker": ["script/dep/daterangepicker-3.0.3/daterangepicker"],
		"daterangepicker.zh-CN": ["script/dep/daterangepicker-3.0.3/zh-CN"],
		"bootstrap.multiselect": ["script/dep/bootstrap-multiselect-2.0/bootstrap-multiselect.min"],
		"zTree": ["script/dep/ztree-3.5.37/js/jquery.ztree.core.min"],
		"zTree.exhide": ["script/dep/ztree-3.5.37/js/jquery.ztree.exhide.min"],
		"selectTree": ["script/dep/selectTree/selectTree"],
		"easyuiLoad": ["script/dep/jquery-easyui-1.6.2/easyloader"]
	}
	
   
});


jQuery.ajaxSetup({
    "xhrFields": {
        withCredentials: true
    },
    "crossDomain": "true",
    dataType: "json"
});
jQuery.support.cors = true;














































/*"easyui": {
    deps: [
    	"css!pub/dep/jquery-easyui-1.5.5.4/themes/"+base.theme+"/easyui.css",
    	"css!pub/dep/jquery-easyui-1.5.5.4/themes/icon.css",
    	'jquery'
    ]
    //exports: 'eUI'
}*/

