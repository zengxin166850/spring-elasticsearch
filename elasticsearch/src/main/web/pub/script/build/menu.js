/*
 *  assets菜单功能
 *  author: tter
 * 	date: 2018-08-08
 * 
 * */

define("menu",["urlConfig"],function(_url){
	
	function Menu(){};
	
	var currentUrl = _tool.parseUrl();
	
	Menu.prototype.init = function(cb){
		
		var _this = this;
		_tool.ajax({
			url: _url.assets.menu,
			type: "get",
			success: function(data){
				
				_this.renderLeftMenu(data.data, $("#nav"), 1);
				
				typeof(cb)=="function"?cb(data):null;
				
			}
		});
		
	}
	
	Menu.prototype.renderLeftMenu = function(data,el,level){
		
		if(el.length<1) return false;
		if(!$.isArray(data)||data.length<1){
			data = [];
		}
		var _this = this,
			menuIco = (level==1?"fa-tv":"");
		
		$.each(data, function(idx, list) {
			
			var li = $("<li id='_"+ list.id +"' data-sys='"+ list.sys +"' class='level_"+ level +"'></li>");
			li.attr("data-control",list.control?list.control:"1");
			li.data("menu",list);
			
			var a = $("<a href='"+ ( $.trim(list.url)?list.url:"#") +"'></a>");
			list.control == "2"?a.attr("target","_blank"):null;
			a.append("<i class='menu-icon fa "+ (list.ico?list.ico:menuIco) +"'></i>");
			a.append("<span class='"+(level==1?"menu-text":"")+"'>"+list.name+"</span>");
			
			li.append(a);
			li.append("<b class='arrow'></b>");
			
			if(list.children&&list.children.length>0){
				if(!$.trim(list.url)){
					a.addClass("no-page");
				}
				a.addClass("dropdown-toggle");
				a.append("<i class='arrow fa fa-angle-left'></i>");
				var ul = $("<ul class='submenu'></ul>");
				li.append(ul);
				_this.renderLeftMenu(list.children, ul, level+1);
			}
			
			//菜单显示方式为left
			if(list.sys==currentUrl.sys && list.url && list.url==currentUrl.url){
				li.addClass("selected");
			}
			
			el.append(li);
		});
		
		_this.initActive();
		
	}
	
	
	Menu.prototype.initActive = function(){ 
		
		var li = $("#nav").find("li.selected");
		if(li.length<1) return;
		li.addClass("active");
		
		li.parents("li").each(function(){
			$(this).addClass("active");
			if($(this).children("ul.submenu").length){
				$(this).addClass("open");
			}
		});
	}

	
	
	return new Menu();
	
});