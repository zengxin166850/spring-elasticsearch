/*
 *  提供统一控制业务上公共方法的入口文件
 *  author: tter
 * 	date: 2018-08-08
 * 
 * */

define(["urlConfig", "pluginsConfig"],function(_url, _defOption){
	
	return {
		
		getDDIC: function(){
			_tool.ajax({
				url: _url.monitor.data_dictionary,
				type: "get",
				success: function(data){
					if(data.status=="1"){
						_tool.Data.set("data_type",data.data.dataType);
						_tool.Data.set("monitor_type",data.data.sensorType);
						_tool.Data.set("sensor_status",data.data.sensorStatus);
						_tool.Data.set("switch_status",data.data.switchStatus);
					}
				}
			});
			_tool.ajax({
				url: _url.monitor.org,
				type: "get",
				success: function(data){
					if(data.status=="1"){
						_tool.Data.set("org",data.data);
					}
				}
			});
		},
		renderMonitorTypeOption: function(){
			var dataType = _tool.Data.get("data_type"),
				monitorType = _tool.Data.get("monitor_type"),
				select = $("<select></select>"),
				optgroup = {};
			if(!dataType||!monitorType){
				return "";
			}
			_.each(monitorType, function(num,key){
				if(!optgroup[num.dataType]){
					optgroup[num.dataType] = $("<optgroup></optgroup>");	
				}
				optgroup[num.dataType].append("<option value='"+num.id+"'>"+num.name+"</option>");
			});
			_.each(dataType, function(num,key){
				if(optgroup[key]){
					optgroup[key].attr("label", num.name);
					select.append(optgroup[key]);
				}
			});
			return select.html();
		},
		renderSensorStatusOption: function(){
			var sensorStatus = _tool.Data.get("sensor_status"),
				str = "";
			if(!sensorStatus){
				 return "";
			}
			_.each(sensorStatus, function(num,key){
				str+= "<option value='"+num.id+"'>"+num.name+"</option>";
			});
			return str;
		},
		renderOrg: function(orgEl){
			var orgData = _tool.Data.get("org");
			if(orgEl.length<1) return;
			
			var leafletNode = _tool.getLeafNodeInTree(orgData[0],"children");
			
			orgEl.selectTree({
				position: "left",  //默认，
				setting: {
					data: {
						key: {
							name: "deptName"
						},
						simpleData: {
							idKey: "mineId",
						}
					}
				},
				nodes: _tool.Data.get("org"),
				trigger: "clickCB",
				isHighLight: true,
				isExpand: false,
				cb:function(obj){
					obj.setValue(leafletNode[0].mineId,leafletNode[0].deptName);
				}
			});
		},
		renderMonitorType: function(el){
			if(el.length<1) return;
			var optionstr = this.renderMonitorTypeOption();
			
			el.html(el.html()+optionstr).multiselect(
				$.extend({},_defOption.multiselect,{
					enableCollapsibleOptGroups: true,
					nonSelectedText: '监测类型',
					enableFiltering: true,
            		filterBehavior: 'text',
            		filterPlaceholder: "搜索",
            		templates:{
            			filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default multiselect-clear-filter" type="button"><i class="fa fa-times-circle"></i></button></span>'
            		},
					onChange: function(option, checked) {
		                var values = [];
		                this.$select.find("option").each(function() {
	                        if ($(this).val() !== option.val()) {
	                            values.push($(this).val());
	                        }
	                	})    
		                this.$select.multiselect('deselect', values);
		            }
				})
			);
		
		},
		renderSensorStatus: function(el){
			if(el.length<1) return;
			var optionstr = this.renderSensorStatusOption();
			el.html(el.html()+optionstr).multiselect(
				$.extend({},_defOption.multiselect,{
					buttonWidth:'120px',
					nonSelectedText: '状态'
				})
			);
		},
		getTableSelect: function(tableid){
			var el = $("#"+tableid).find(".select-checkbox input:checked"),
				obj={
					el:[],
					key:[]
				};
			if (el.length>=1) {
				for (var i = 0; i < el.length; i++) {
					var key = $(el[i]).attr("data-key");
					key?obj.key.push(key):null;
				}
				obj.el = el;
			}
			return obj;
		},
        creatExchange : function(params){
			if( !params  instanceof Array )throw new Error("params is not a array object");
			for(var i in  params){
				var id = params[i]["id"];
				var callBack = params[i]["callBack"];
				if(listen[id]){
					listen[id].unsubscribe();
				}
				var bind = _tool.Message.subscribe(id,function(msg){
					if(msg==null){
						callBack("");
					}
					try{
						callBack(JSON.parse(msg));
					}catch(e){
						console.log("WARN : parseJSON ERROR",e);
						callBack(msg);
					}
				});
				listen[id]=bind;
			}
		}
		
		/*
		getOrg: function(cb){
			var data = _tool.Data.get("org");
			if(!data){
				_tool.ajax({
					url: _url.monitor.org,
					type: "get",
					success: function(data){
						var org = data.data;
						_tool.Data.set("org",org);
						typeof(cb)=="function"?cb({name: "org",data: org}): null;
					}
				});
			}else{
				typeof(cb)=="function"?cb({name: "sensor_status",data: data}): null;
			}
		},
		getMonitorType: function(cb){
			var data = _tool.Data.get("monitor_type");
			if(!data){
				_tool.ajax({
					url: _url.monitor.type,
					type: "get",
					success: function(data){
						var type = data.data;
						_tool.Data.set("monitor_type",type);
						typeof(cb)=="function"?cb({name: "monitor_type",data: type}): null;
					}
				});
			}else{
				typeof(cb)=="function"?cb({name: "sensor_status",data: data}): null;
			}
		},
		getSensorStatus: function(cb){
			var data = _tool.Data.get("sensor_status");
			if(!data){
				_tool.ajax({
					url: _url.monitor.sensor,
					type: "get",
					success: function(data){
						var status = data.data;
						_tool.Data.set("sensor_status",status);
						typeof(cb)=="function"?cb({name: "sensor_status",data: status}): null;
					}
				});
			}else{
				typeof(cb)=="function"?cb({name: "sensor_status",data: data}): null;
			}
		},
		
	*/	
	}
	
});
