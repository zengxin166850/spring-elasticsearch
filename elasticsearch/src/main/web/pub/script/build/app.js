/*
 *  系统路由配置,视图，路由
 *  author: tter
 * 	date: 2018-08-08
 * 
 * */

define("app",["backbone"],function(){
	
	var viewer = Backbone.View.extend({
		el: "#page-content",
		initialize: function (sys,m,path,param) {
           if (param&&param.pagetype=="page") {
           	_tool.UI.toSinglePage();
           }else{
           	_tool.UI.toSystem();
           }
        }
	});
	
	var router = Backbone.Router.extend({
		routes:{
			"": "home",
			"page/:sys/:module/*path(?*param)": "loadPage"
		},
		home:function(){
			this.loadPage("assets","home", "", "");
		},
		loadPage:function(sys,m,path,param){
			
			var obj = _tool.paramToObject(param),
				arr = [sys,"modules"],
				homeview = new viewer(sys,m,path,obj);
				
            m?arr.push(m):null;
            path?arr.push( path.replace(/^\/*|\/*$/g,"") ):null;    //处理path防止后面拼装路径出错
            arr.push("index.js");
            
        	require(["/"+arr.join("/")], function (cb) {
                if (cb) {
                	typeof(cb)=="function"?cb(obj):null;
                }
        	}, function(err){
           		_tool.errorMsg({
	        		type: "custom",
	        		data: {
	        			title: "模块加载失败！",
	        			message: err.message
	        		}
	        	});
			});  
			
		}
	});
	
	return function(){
		var r = new router();
		Backbone.history.start();
	};
	
});

