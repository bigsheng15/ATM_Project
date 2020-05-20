var num = 0; //使用数字键输入的数字

// ATM对象
var atm = {status:0}; // 0:空闲 1:关闭 2:处理中
atm.refresh = function refresh(status){
	this.status = status;
}

// 主屏幕对象
var display = {text:""};
display.refresh = function refresh(text){
	this.text = text;
	$("#display").html(this.text);
}
display.show = function show(text){
	$("#display").html(text);
}

//打印屏幕对象
var area = {text:""};
area.refresh = function refresh(text){
	this.text = text;
	$("#area").html(this.text);
}
area.show = function show(text){
	text=text.replace('<br/>','/n');
	$("#area").html(text);
}

// 按钮对象
var switchButton = {text:"",disable:false}; 
switchButton.refresh = function refresh(text,disable){
	this.text = text;
	this.disable = disable;
	$("#switch").html(text);
	$("#switch").unbind("click");//unbind去除点击动作
	$("#cancel").unbind("click");
	if(atm.status == 0){
		$("#switch").click(turnoff);
	}
	else if(atm.status == 1){
		$("#switch").click(turnon);	
	}
	else if (atm.status == 2){
		//点击关机，当状态为处理中时弹出弹窗不允许关机
		$("#switch").click(Toturnoff);	
		$("#cancel").html("返回首页");
		$("#cancel").click(cancel);	
		
	} 
		
	console.log("0:空闲 1:关闭 2:处理中 atm.status-->"+atm.status);
	$("#switch").attr('disable',disable);
}

function cancel(){
	alert("返回首页按钮");
}

// 插卡孔
var cardSlot = {text:"",inserted:false};
cardSlot.refresh = function refresh(text,inserted){
	this.text = text;
	this.inserted = inserted;
	$("#card").html(text);
	$("#card").attr('disable',inserted);
}

// 数字按钮
var digitButton = {state:2,visibility:0,servletName:""};
digitButton.refresh = function refresh(state,visibility,servletName){
	this.state = state;
	this.visibility = visibility;
	this.servletName = servletName;
}
	
function readNum(obj){
	var digit = Number(obj.value);
	if(digitButton.state == 0){
		submitNum(digit);
	}
	else if(digitButton.state == 1){
		num = 10*num + digit;
		var str = display.text+"<br/>";
		str+=num;
		display.show(str);
	}
	else if(digitButton.state == 2){
		
	}
}

$(document).ready(function() {
	$("#card").click(insertCard);
	getStatus();
});

function refresh(resp){
	atm.refresh(resp.ATM.state);
	display.refresh(resp.display.text);
	area.refresh(resp.area.text);
	switchButton.refresh(resp.switchbutton.text,resp.switchbutton.disable);
	cardSlot.refresh(resp.cardslot.text,resp.cardslot.inserted);
	digitButton.refresh(resp.digitbutton.state,resp.digitbutton.visibility,resp.digitbutton.servletName);
}

function getStatus(){
	$.post('/ATM/GetStatusServlet', function(responseText) {
		console.log("responseText-->"+responseText);
		refresh(responseText);
	});
}

function turnon(){
	$.post('/ATM/TurnOnServlet', function(responseText) {
		refresh(responseText);
	});
}

function turnoff(){
	$.post('/ATM/TurnOffServlet', function(responseText) {
		refresh(responseText);
	});
}

function Toturnoff(){
	alert("系统处理中...请完成操作后退卡再关机");
}

function insertCard(){
	var cardNo = window.prompt("请输入账号", "");
	$.post('/ATM/CardInsertedServlet','cardNo='+cardNo, function(responseText) {
		refresh(responseText);
	});	
}

function submitNum(number){
	$.post('/ATM/'+digitButton.servletName,'num='+number, function(responseText) {
		refresh(responseText);
		num = 0;
	});	
}



function submit(){
	//console.log("num="+num);
	//console.log("servlet=" + digitButton.servletName);
	submitNum(num);
}