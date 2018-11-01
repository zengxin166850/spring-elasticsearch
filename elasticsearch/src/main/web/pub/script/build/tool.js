/*
 *  工具类
 *  author: tter
 * 	date: 2018-08-08
 * 
 * */

if(window.$||window.jQuery){
	window._$ = (window.$||window.jQuery);
}
var listen ={};
var _tool=window._tool=(function(win,$){
	
	var Tool = function(){
		this.data = {};
	};
	Tool.prototype.Data = {
		get: function(name){
			if(!name) return null;
			var data = _tool.data[name],
				broswer = _tool.broswer();
			if(!data){
				if(!(broswer.name==="IE" && broswer.version<11)){
					data = JSON.parse(window.localStorage.getItem(name));
				}
			}
			return data;
			/*var broswer = _tool.broswer();
			if(broswer.name==="IE" && broswer.version<11){
				return _tool.data[name];
			}else{
				return JSON.parse();
			}*/
		},
		set: function(name,data){
			if(!name||!data) return;
			var broswer = _tool.broswer();
			_tool.data[name] = data;
			if(!(broswer.name==="IE" && broswer.version<11)){
				window.localStorage.setItem(name,JSON.stringify(data));
			}
		},
		remove: function(name){
			if(!name) return;
			var broswer = _tool.broswer();
			delete _tool.data[name];
			if(!(broswer.name==="IE" && broswer.version<11)){
				window.localStorage.removeItem(name);
			}
		},
		getDDICName: function(objname, key){
			if(!objname||!key) return "";
			var data = _tool.Data.get(objname);
			if(!data) return "";
			var list = data[key];
			if(!list) return "";
			return list.name;
		}
	}
	
	
	Tool.prototype.UI = {
		
		breadCrumbs: {
			hide: function(){
				var dom = $("#breadcrumbs");
				dom.hide();
				dom.parent().css("padding-top","0");
				_tool.UI.pageScrollInit();
			},
			show: function(){
				var dom = $("#breadcrumbs");
				dom.show();
				dom.parent().css("padding-top","41px");
				_tool.UI.pageScrollInit();
			},
			//参数格式 { name: "首页", url: "http://www.baidu.com", active: true }
			set: function(data){
				//待优化，考虑跳转时菜单状态也改变
				if (!$.isArray(data)||data.length<1) return;
				var el = $("#breadcrumbs").children(".breadcrumb");
				el.html("");
				el.append("<li><i class='ace-icon fa fa-home home-icon'></i><a href='/'>首页</a></li>");			
				$.each(data, function(idx,list) {
					var li = $("<li class=''></li>");
					if(list.active){
						li.addClass("active");
						li.append(list.name);
					}else{
						var url = $.trim(list.url);
						li.append("<a href='"+(url?url:"javascript:;")+"'>"+list.name+"</a>");
					}
					el.append(li);
				});
			}
		},
		pageScrollInit: function(){
			var dom = $("#page-content");
	    	dom.ace_scroll({
	    		size: dom.height()
	    	});
		},
		// li是jq对象
		menuToActive:function(li){
			if(li.length<1) return;
			if(li.length>1) li = li[0];
			if(li.hasClass("selected")) return;
			var nav = $("#nav");
			
			nav.find("li.active").removeClass("active");
			nav.find("li.selected").removeClass("selected");
			
			li.addClass("active selected");
			li.parents("li").each(function(){
				$(this).addClass("active");
			});
		},
		toSinglePage: function(){
			$("#navbar").addClass("hide");
			$("#main-container").css("padding-top","0");
			$("#sidebar").css({
				"width":"0",
				"overflow-x": "hidden"
			}).siblings("div.main-content").css("margin-left","0");
			_tool.UI.breadCrumbs.hide();
			
		},
		toSystem: function(){
			$("#navbar").removeClass("hide");
			$("#main-container").css("padding-top","45px");
			$("#sidebar").css({
				"width":"190px",
				"overflow-x": "visible"
			}).siblings("div.main-content").css("margin-left","190px");
			_tool.UI.breadCrumbs.show();
		}
		
	}
		
		
	Tool.prototype.broswer = function () {
    	var userAgent = window.navigator.userAgent;
    	var isChrome = userAgent.indexOf('Chrome') > -1;
    	var isFF = userAgent.indexOf("Firefox") > -1;
    	var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //是否 IE<11
        var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //是否是 IE的Edge  
        var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;  //是否 IE 11
        
    	
    	if(isChrome){
    		userAgent.match(/chrome\/([\d.]+)/gi);
    		return {
        		name: "Chrome",
        		version: RegExp["$1"]
        	}
    	}else if(isFF){
    		userAgent.match(/firefox\/([\d.]+)/gi);
    		return {
        		name: "Firefox",
        		version: RegExp["$1"]
        	}
    	}else if(isEdge){
        	return {
        		name: "IE",
        		version: "Edge"
        	}
        }else if(isIE11){
        	return {
        		name: "IE",
        		version: "11"
        	}
        }else if(isIE){
        	var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
            reIE.test(userAgent);
            var fIEVersion = parseFloat(RegExp["$1"]);
            return {
            	name: "IE",
        		version: fIEVersion
            }
        }
    };
	
	//ajax的参数
	Tool.prototype.ajax = function(option){
		
		var dataType = $.trim(option.dataType),
			cType = $.trim(option.contentType),
			success = option.success,
			err = option.error,
			_this = this;
			
		if(_.isEmpty(dataType)||dataType == "undefined"||dataType == "null"){
			option.dataType = "json";
		}
		//联网监测不使用json传输数据
		/*if(_.isEmpty(cType)||cType == "undefined"||cType == "null"){
			option.contentType = "application/json";
		}*/
		option.beforeSend = function(xhr){
			//同域名下面cookie会自动带上，是否跨域使用
			/*var cookie = this.getCookie('login_token');
            if (cookie) {
                xhr.setRequestHeader('pt_token', cookie);
            }*/
		}
		option.success = function(data){
			//不用系统使用全局ajax方法，业务代码表示含义可能不一样，看是否需要统一处理业务上的提示，临时处理如下
			if(data.status != "1"){
				_this.errorMsg({
	        		type: "business",
	        		data: data,
	        		time: 8000
	        	});
			}
			typeof(success) == "function"?success(data):null;
		};
		option.error = function(xhr,status,error){
			if (status === "timeout") {
	            layer.msg('请求超时，请稍后重试！');
	    	} else {
	        	_this.errorMsg({
	        		type: "ajax",
	        		data: {
	        			"xhr": xhr,
	        			"status": status,
	        			"error": error
	        		},
	        		time: 5000
	        	});
	        }
	    	typeof(err) == "function"?err(xhr,status,error):null;
		};
		return $.ajax(option);
	}
	
	//option参数： type["business","ajax"]  time[5000] cb[function] 
	//           data[object{如果是business，message,data,status,如果是ajax，xhr,status,error, 如果是custom, title ,message}]
	Tool.prototype.errorMsg = function(option){
		
		var str="<div class='txt-l'>";
		//请求的业务错误
		if(option.type=="business"){
			str += "<div>发生错误，请稍后重试或联系管理员！</div><div>错误信息："
			    +(option.data.message?option.data.message:"")
			    +"</div><div>错误代码："+option.data.status+"</div>";
		}
		//ajax请求错误
		if(option.type==="ajax"){
			str += "<div>错误信息："+ option.data.error +"</div><div>错误代码："+ option.data.xhr.status +"</div>";
		}
		
		if(option.type==="custom"){
			str += "<div>"+ option.data.title +"</div><div>错误信息："+ option.data.message +"</div>";
		}
		
    	/*layer.msg(str,{time: (option.time?option.time:5000)},function(){
    		typeof(option.cb) == "function"?option.cb(option.data):null;
    	});*/
    	layer.msg(str+"</div>",{time: (option.time?option.time:5000)});
    	
	}
	
	//url参数（name:value形式）对象化
	Tool.prototype.paramToObject = function(param){ 
		
		var obj = {};
		if (param && param.indexOf(':') > -1) { 
			param.replace(/(\w+)\s*:\s*([%\w-]+)/g, function (a, b, c) {
				b && (obj[b] = c);
			});
		} else {
			obj = param;
		}
		return obj;
		
	};
	
	Tool.prototype.parseUrl = function(){
		
		var hash = window.location.hash,
			obj = {
				url: hash
			};
		
		if(hash === ""){
			obj.sys = "assets";
			obj.module = "home";
			return obj
		}
		var arr = hash.split("/",3);
		obj.way = arr[0]?arr[0].replace(/#/g,""):"";
		obj.sys = arr[1]?arr[1]:"";
		obj.module = arr[2]?arr[2]:"";
		
		return obj;
		
	};
	
	//array{id,text,disabled:true,selected:true}
	Tool.prototype.renderOption = function(data){
		var str="";
		if(!$.isArray(data)||data.length<1) str = "";
		$.each(data, function(idx,val){
			var disable = (val.disabled)?' disabled="disabled"':"";
			var select = (val.selected)?' selected="selected"':"";
			str += "<option value='"+ val +"'"+ disable + select +">"+ val + "</option>";
		});
		return str;
	};
	
	//生成唯一标识
	Tool.prototype.generateUUID = function(){
		var d = new Date().getTime();
		var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			var r = (d + Math.random()*16)%16 | 0;
			d = Math.floor(d/16);
			return (c=='x' ? r : (r&0x3|0x8)).toString(16);
		});
		return uuid;
	};
	
	//tree：树根节点    childName：子节点name   keyName:value ：键值对      findCB：匹配的回调函数
	Tool.prototype.getNodeInTree = function(tree,childName,keyName,value,findCB){
		if(!tree) return {};
		if(typeof(tree)!="object"||$.isEmptyObject(tree)){
			layer.msg("The tree node must be a non-empty object!");
			return false;
		}
		if(!childName) childName = "children";
		if(!tree[childName]) return {};
		if(!value) return {};
		var _this = this;
			success  = false;
		for (var i = 0; i < tree[childName].length; i++) {
			var val = tree[childName][i];
			if(val[keyName] == value){
				success = true;
				typeof(findCB)=="function"?findCB(val):null;
				return false;
			};
			if(val[childName])
			_this.getNodeInTree(val,childName,keyName,value,findCB);
		}
		return success;
	};
	
	Tool.prototype.getLeafNodeInTree = function(tree,childName,leafNodes){
		if(!tree) return [];
		if(typeof(tree)!="object"||$.isEmptyObject(tree)){
			layer.msg("The tree node must be a non-empty object!");
			return {};
		}
		if(!childName) childName = "children";
		if(!tree[childName]) return [];
		var _this = this,
			nodes = leafNodes?leafNodes:[];
			
		for (var i = 0; i < tree[childName].length; i++) {
			var val = tree[childName][i];
			if(val[childName]){
				var leafs = _this.getLeafNodeInTree(val,childName,nodes);
				nodes.concat(leafs);
			}else{
				nodes.push(val);
			}
		}
		return nodes;
	}
	
	
	//table: DataTable对象化后的对象， id: 元素id不带选择器
	/*Tool.prototype.tableOnCheckbox = function(table,id){
		
		var dom = $("#"+id);
		if(dom.length<1) return false;
		if(!table) return false;;
		
		table.on('select',function(e, dt, type, index){
			if(type === 'row') {
				$( table.row(index).node()).find('td.select-checkbox input:checkbox').prop('checked', true);
			}
		});
		table.on('deselect',function(e, dt, type, index){
			if (type === 'row'){
				$(table.row(index).node()).find('td.select-checkbox input:checkbox').prop('checked', false);
			}
		});
		dom.on('click', 'td.select-checkbox input[type=checkbox]' , function(e){
			e.stopPropagation();
			var row = $(this).parents('tr').get(0);
			if(this.checked) {
				table.row(row).deselect();
			}else{
				table.row(row).select();
			}
		});
	}*/
	
	
	return new Tool();
	
	
})(window,window.jQuery);
