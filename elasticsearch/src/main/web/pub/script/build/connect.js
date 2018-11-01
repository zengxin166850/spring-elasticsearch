/*
 *  系统消息连接
 *  author: tter
 * 	date: 2018-08-08
 * 
 * */

define("connect", ["urlConfig","layer"],function(_url){
	http://jmesnil.net/stomp-websocket/doc/
	var socket;
	if(window.WebSocket) {
		socket = new WebSocket("ws://"+_url.assets.msgServer+"/ws"); 
	}else{
		socket = new SockJS("http://"+_url.assets.msgServer+"/stomp");
	}
	var client = Stomp.over(socket);
	
	client.connect("admins","admins",function(frame){
		
	},function(error){
		
		_tool.errorMsg({
			type: "custom",
			data: {
				title: "连接到消息服务器失败，请重试或联系管理员",
				message: error
			}
		});
		
	},"/");
	
	_tool.Message = client;
	return client;
	
});