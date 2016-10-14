
var online = new Array();
var qq = new Array();
qq[0] = new Array(); 
qq[0][0] = "售前客服咨询";
qq[0][1] = "648878587";
qq[0][2] = "648878587";
qq[0][3] = "648878587";


/*qq[1] = new Array(); 
qq[1][0] = "售后服务支持";
qq[1][1] = "648878587";
qq[1][2] = "648878587";



qq[2] = new Array(); 
qq[2][0] = "合作加盟";
qq[2][1] = "648878587";*/

var script_src = "http:\/\/webpresence.qq.com\/getonline?Type=1&"
// dynamic generate script src
for(i=0;i<qq.length;i++){
	for(j=1;j<qq[i].length;j++){
		script_src += qq[i][j];
		script_src += ":";
	}
}

document.write( "<script language=\"javascript\" src=\"" + script_src + "\"> <\/script>");

lastScrollY=0;

function heartBeat()
{ 
	var diffY;
	var ioc = document.getElementById("full");
	if(ioc == null || ioc == undefined)
			return;
	
	if (document.documentElement && document.documentElement.scrollTop)
			diffY = document.documentElement.scrollTop;
	else if (document.body)
	    diffY = document.body.scrollTop
	else
	    {/*Netscape stuff*/}

	percent = .1 * (diffY - lastScrollY); 
	if(percent>0){
		percent=Math.ceil(percent); 
	}
	else{
		percent=Math.floor(percent); 
	}
	ioc.style.top = ((isNaN(parseInt(ioc.style.top)) ? 0 :parseInt(ioc.style.top))  + percent).toString() + "px";
	lastScrollY = lastScrollY+percent; 
}
function mClk(){ //自
	event.srcElement.click();
}

var suspendcode;
var contactHandler;
contactHandler = setInterval("checkData()", 2000);

function definedData(varData){
	if(varData == null && varData == undefined){
		return false;
	}
	return true;
}
function checkData(){
		clearInterval(contactHandler);
		suspendcode="<div id=\"full\" style='right:3px; top:180px; width:93px;position:absolute;z-index:1000;text-align:center;'>\n"
		+ "<img  border='0' align='absmiddle' src='/kaquan/assets/img/fdqq.gif' /><a href='tencent://message/?uin=676133829&Site=www.jifencun.com&Menu=yes' class='qq'><img  border='0' align='absmiddle' src='/kaquan/assets/img/qq1.gif' /></a><a href='tencent://message/?uin=676133829&Site=www.jifencun.com&Menu=yes' class='qq'><img  border='0' align='absmiddle' src='/kaquan/assets/img/qq2.gif' /></a>"
		+ "</div>\n";
		document.getElementById("contactContanier").innerHTML = suspendcode;
}


document.write("<div id=\"contactContanier\"><div id=\"toTop\"></div></div>"); 
window.setInterval("heartBeat()", 1);
