/*
 *  自定义 基于jq和ztree的下拉树插件
 *  author: tter
 * 	date: 2018-08-08
 *  param: {
 * 		position: [left, right]
 * 		setting: object //ztree的setting
 * 		nodes: //ztree的nodes配置
 * 		trigger: //选中元素之后需要触发的事件，结合backbone使用时有用
 * 		isHighLight: true/false  [true] 高亮显示匹配词
 * 		isExpand: true/false [fasle] 是否展开节点
 * 		cb:  渲染完成之后的回调函数
 * 	}
 * */

(function(root,factory){
	if(typeof define === "function" && define.amd){
		define(['jquery','zTree','zTree.exhide'], function (jquery) {
            if (!jquery.fn) jquery.fn = {}; // webpack server rendering
            return factory(jquery);
        });
	}else if(typeof module === 'object' && module.exports){
		var jQuery = (typeof window != 'undefined') ? window.jQuery : undefined;
        if (!jQuery) {
            jQuery = require('jquery');
            if (!jQuery.fn) jQuery.fn = {};
        }
       (typeof window != 'undefined' && typeof jQuery.fn.zTree != 'undefined') ? null : require('zTree');
        module.exports = factory(jQuery);
	}else{
		root.selecttree = factory(root.jQuery);
	}
}(this,function($){
	
	var Selecttree = function(el,options){
		
		var el = $(el);
		var set = $.extend(true,{},$.fn.selectTree.defaultOptions, options);
		
		this.element = $(el);
		this.parent = el.parent().addClass("clearfix select-tree-container");
		this.textEl = el.clone();
		this.dropTree = $("<ul id='_"+ _tool.generateUUID() +"' class='ztree select-tree-dropdown hide'></ul>")
		this.dropIcon = $("<i class='ace-icon fa fa-angle-down'></i>");
		
		var id = el.attr("id"),
			name = el.attr("name"),
			classname= el.attr("class");
		
		this.textEl.addClass((classname?classname:"") + " select-tree-txt");
		this.textEl.attr("name",name?name+"-text":(id?id+"-text":"")).attr("autocomplete","off");
		this.textEl.removeAttr("id");
		set.trigger?this.textEl.attr("data-trigger",set.trigger):null;
		
		this.version = "0.0.1";
		this.isHighLight = options.isHighLight===false?false:true;
		this.isExpand = options.isExpand?true:false; 
		
		this.init = function(){
			
			if(el.attr("disabled")) return;
			if(el[0].tagName != "INPUT" && el[0].tagName != "input") return;
			if(!name&&!id) return;
			
			this.element.attr("type", "hidden");
			this.parent.append(this.dropIcon);
			this.parent.append(this.textEl);
			this.parent.append(this.dropTree);
			
			
			//设置dropdown 位置
			this.dropTree.css("top", this.textEl.outerHeight(true));
			if(set.position=="right"){
				this.dropTree.removeClass("left").addClass("right");
			}else{
				this.dropTree.removeClass("right").addClass("left");
			}
			
			this.triggerDropTree();
			this.zTree = $.fn.zTree.init(this.dropTree, set.setting, set.nodes);
			this.fuzzySearch();
			typeof(options.cb) === "function"?options.cb(this):null;
		}
		
		this.triggerDropTree = function(){ //绑定触发下拉树的方式
			
			var readonly = this.element.attr("readonly"),
				_this = this;
			
			if($.trim(readonly)){
				this.textEl.on("click",function(){
					if(_this.dropTree.hasClass("hide")){
						_this.dropTree.removeClass("hide");
						$("body").on("mousedown", function(e){
							_this.hideDropTree(_this,e);
						});
					}
				});	
				this.dropIcon.on("click",function(){
					_this.textEl.trigger("click");
				});
			}else{
				this.textEl.on("focus",function(){
					if(_this.dropTree.hasClass("hide")){
						_this.dropTree.removeClass("hide");
						$("body").on("mousedown", function(e){
							_this.hideDropTree(_this,e);
						});
					}
				});
				this.dropIcon.on("click",function(){
					_this.textEl.trigger("focus");
				});
			}
		}
		
		this.hideDropTree = function(obj,e){
			if (!(
				$(e.target).is(obj.textEl)||
				$(e.target).is(obj.dropIcon)||
				$(e.target).hasClass("select-tree-dropdown")||
				$(e.target).parents("ul.select-tree-dropdown").length>0
			)) {
				obj.dropTree.addClass("hide");
				$("body").off("mousedown", function(e){
					obj.hideDropTree(obj,e);
				});
			}
		}
		
		this.fuzzySearch = function(){
			var _this = this;
			var zTreeObj = this.zTree;
			if(!zTreeObj){
				alter("fail to get ztree object");
			}
			var nameKey = zTreeObj.setting.data.key.name; 
			zTreeObj.setting.view.nameIsHTML = this.isHighLight; 
			var metaChar = '[\\[\\]\\\\\^\\$\\.\\|\\?\\*\\+\\(\\)]'; 
			var rexMeta = new RegExp(metaChar, 'gi');
			var timeoutId = null;
			
			this.textEl.on('input propertychange', function() {
				var _keywords = $(this).val();
				timeoutId = _this.searchNodeLazy(_keywords,timeoutId,rexMeta,nameKey);
			});
			
		};
		
		this.searchNodeLazy = function(_keywords,timer,matchRules,nameKey) {
			var _this = this,
				ztreeobj = this.zTree;
			if(!ztreeobj){
				alter("fail to get ztree object");
			}
			if(!_keywords){
				_keywords ='';
			}
			if (timer) { 
				clearTimeout(timer);
			}
			return setTimeout(function() {
					
				var nodesShow = ztreeobj.getNodesByFilter(function(node){
					if(node && node.oldname && node.oldname.length>0){
						node[nameKey] = node.oldname; 
					}
					ztreeobj.updateNode(node); 
					if (_keywords.length == 0) {
						ztreeobj.showNode(node);
						ztreeobj.expandNode(node,_this.isExpand);
						return true;
					}
			
					if (node[nameKey] && node[nameKey].toLowerCase().indexOf(_keywords.toLowerCase())!=-1) {
						if(_this.isHighLight){ 
							
							var newKeywords = _keywords.replace(matchRules,function(matchStr){
								return '\\' + matchStr;
							});
							node.oldname = node[nameKey]; 
							var rexGlobal = new RegExp(newKeywords, 'gi');
							node[nameKey] = node.oldname.replace(rexGlobal, function(originalText){
								var highLightText =
									'<span style="color: #fff;background-color:#f77979;">'
									+ originalText
									+'</span>';
								return 	highLightText;					
							});
							ztreeobj.updateNode(node); 
						}
						ztreeobj.showNode(node);
						return true; 
					}
			
					ztreeobj.hideNode(node); 
					return false; 
				}); 
				_this.processShowNodes(nodesShow, _keywords);
				_this.textEl.focus();
			}, 500);
		};
		
		this.processShowNodes = function(nodesShow,_keywords){
			var zTreeObj = this.zTree;
			if(nodesShow && nodesShow.length>0){
				if(_keywords.length>0){ 
					$.each(nodesShow, function(n,obj){
						var pathOfOne = obj.getPath();
						if(pathOfOne && pathOfOne.length>0){ 
							for(var i=0;i<pathOfOne.length-1;i++){
								zTreeObj.showNode(pathOfOne[i]); 
								zTreeObj.expandNode(pathOfOne[i],true); 
							}
						}
					});	
				}else{ 
					var rootNodes = zTreeObj.getNodesByParam('level','0');
					$.each(rootNodes,function(n,obj){
						zTreeObj.expandNode(obj,true); 
					});
				}
			}
		};
		
		this.init();
	};
	
	Selecttree.prototype = {
		getValue: function(){
			var obj={
				key: this.element.attr("name")?this.element.attr("name"):this.element.attr("id"),
				id: this.element.attr("value"),
				name: this.textEl.attr("value")
			};
			return obj;
		},
		setValue: function(id,name){
			if(!id||!name) return;
			this.element.attr("value",id);
			this.textEl.attr("value",name);
		},
		empty: function(){
			this.element.attr("value","");
			this.textEl.attr("value","");
		}
	};
	
	$.fn.extend({
		selectTree: function(option,callback){
			
			return this.each(function(){
				var el = $(this);
				if(!el.hasClass("select-tree-txt")&&!el.data("selectTree")){
					var selecttree = new Selecttree(this,option,callback);
					selecttree.element.data("selectTree",selecttree);
				}
			});
				
			/*if(typeof(option)==="string"){
				var obj={};
				this.each(function(){
					var el = $(this);
					if(el.hasClass("select-tree-txt")||!el.data("selectTree")) return;
					var value = typeof(el.data("selectTree")[option]) === "function"?el.data("selectTree")[option]():null;
					$.isEmptyObject(value)?null:obj[value.key] = value;
				});
				if(!$.isEmptyObject(obj)) return obj;
			}else{
				return this.each(function(){
					var el = $(this);
					var selecttree = new Selecttree(this,option,callback);
					selecttree.element.data("selectTree",selecttree);
				});
			}*/
		}
	})
	
	$.fn.selectTree.defaultOptions = {
	 	position: "left",
	 	setting: {
	 		callback: {
				beforeClick: function(id, node){
					var check = (node && !node.isParent);
					if (!check){
						layer.msg("不能选择父节点");
					};
					return check;
				},
				onClick: function(e, id, node){
					var ul = $("#"+id),
						txtEl = ul.siblings("input.select-tree-txt"),
						name = txtEl.attr("name").split("-text")[0],
						valueElByName = ul.siblings("input[name="+name+"]"),
						valueElById = ul.siblings("#"+name),
						dom = valueElByName.length>0?valueElByName: valueElById,
						triggerName = txtEl.attr("data-trigger"),
						treeObj = $.fn.zTree.getZTreeObj(id),
						oldId = dom.val(),
						newId = node.mineId;
						
					txtEl.attr("value", node[treeObj.setting.data.key.name]);
					dom.attr("value", node[treeObj.setting.data.simpleData.idKey]);
					triggerName?dom.trigger(triggerName,[oldId,newId]):null;
				}
			}
	 	},
	 	trigger: ""
	};
	
	return Selecttree;
}));
