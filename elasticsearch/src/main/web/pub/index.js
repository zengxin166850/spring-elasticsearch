
var socket;
if(window.WebSocket) {
    socket = new WebSocket("ws://192.168.1.105:15674/ws");
}else{
    socket = new SockJS("http://192.168.1.105:15674/stomp");
}
var client = Stomp.over(socket);

client.connect("admins","admins",function() {
    //client.subscribe("/queue/bajie/",function(data){})
    client.subscribe("/exchange/WEB_BAJIE/"+"test", function(data) {
        var msg = data.body;
        alert("收到数据：" + msg);
    })},function() {
    console.log('error');
},"/");
