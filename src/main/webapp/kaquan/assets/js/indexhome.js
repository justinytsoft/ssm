/**
 *@author readkid
 *@dependence jquery 1.2
 *@since 2010/1/15
 *@version 1.0
 */ 
(function () {
	//using namespace $.suning;
	$.suning || ($.suning = {});
	//Extends
	$.suning.extend = function (subClass, superClass) {
		var F = function() {};
		F.prototype = superClass.prototype;
		subClass.prototype = new F();
		superClass.prototype.constructor = superClass;
		subClass.prototype.constructor = subClass;
		subClass.base = superClass.prototype;
	}
})();

//SideNav
$.suning.MenuNav = function() {
	var timer;
 	if($("#SideNav").css("display") == "none"){
		$(".sn_Menu").hover(function(){
			clearInterval(timer);					 
			$("#SideNav").css("display","block");	
			$(this).find(".sn_Menuicon").addClass("on");
						
			$("#SideNav").find("dl").each(function(i){
				$(this).mouseover(function() {
					navshow(i);
				});				   
			});
			
			function navshow(i){
				$("#SideNav").find(".icon").removeClass('on').eq(i).addClass("on");
				$("#SideNav").find(".sidenavchild").hide().eq(i).show();
			};
			
		},function(){
			timer = setInterval(function(){
				$("#SideNav").css("display","none");
			},1);
			$("#SideNav").find(".icon").removeClass('on');	
			$(this).find(".sn_Menuicon").removeClass("on");
			$(this).find(".sidenavchild").hide();
		});
		
	}else{	
		$("#SideNav").find("dl").each(function(i){
			$(this).mouseover(function(){
				$(this).find(".icon").addClass("on");	
				$(this).find(".sidenavchild").css("display","block");
			}).mouseout(function(){
				$(this).find(".icon").removeClass("on");	
				$(this).find(".sidenavchild").css("display","none");				
			})										   
		});		
	}
	
}


/* 首页八联版 */
$.suning.SNTabFlash = function(){
	var len = $("#SNflashBtn").find("li").length ;
	var index = 0 ;
	var timer = setInterval(Auto,4800);
	$("#SNflashContent").find("ul").width( 580*len );
	
	$("#SNflash").hover(function(){
		 clearInterval(timer);
	},function(){
		 timer = setInterval(Auto,5200);
	});
	
	function Auto(){
		$("#SNflashBtn").find("li").removeClass("on").eq(index).addClass("on");				   
		$("#SNflashContent").find("ul").stop().animate({"marginLeft":-580*index},300,function(){ index++;if(index==len){index=0;} });	
	}
	
	$("#SNflashBtn").find("li").mouseover(function(){
		index  =  $("#SNflashBtn li").index(this);
		setTimeout(function(){
			$("#SNflashBtn").find("li").removeClass("on").eq(index).addClass("on");				   
			$("#SNflashContent").find("ul").stop().animate({"marginLeft":-580*index},300,function(){ index++;if(index==len){index=0;} });	
		},150);
	});
		
}


/* 热销专区 */
$.suning.SNProlist = function(){
	$("#SNProlistBtn").find("a").mouseover(function(){
		index  =  $("#SNProlistBtn a").index(this);
		$("#SNProlistBtn").find("a").removeClass("on").eq(index).addClass("on");				   
		$("#SNProlistContent").stop().animate({"marginLeft":-562*index},600);	
	});
}

/* tab点击 */
$.suning.TabClick = function(id,showId){
	$("#"+id).find("li").each(function(i){
		$(this).find("a").mouseover(function(e){
			$(this).addClass("on").parent().siblings().find("a").removeClass("on");	
			$("."+showId).hide().eq(i).show();
		})
	});	
}


/* 切换事件 */
$.suning.IndexTab = function(){
	var index = 0 ;
	var timer,settime;
	
	$("#sn_sevice").hover(function(){
		$("#sn_seviceMore").show();						   
	},function(){
		$("#sn_seviceMore").hide();
	});
	
	$("#basket").hover(function(){
		$("#basketShow").show();
	},function(){
		$("#basketShow").hide();
	});
	
	var $basketlist = $("#basketShow ul li")
	var basketlen = $basketlist.length;
	if(basketlen >3){
		$("#basketShow ul li:gt(3)").hide();
		$("#basketShow .basketmore").show();
		}
	
	var len = $("#Package").find("li").length ;
	$("#Package").find(".Package ul").width(576*len);
	$("#Package").find(".Packageright a").click(function(){
		if(index>=len-1){
			return false;
		}												 
		index ++ ;												 
		$("#Package").find(".Package ul").stop().animate({"marginLeft":-576*index},300);
	});
	
	$("#Package").find(".Packageleft a").click(function(){
		if(index<=0){
			return false;
		}												 
		index -- ;												 
		$("#Package").find(".Package ul").stop().animate({"marginLeft":-576*index},300);
	});
	
	$("#Package").hover(function(){
		 clearInterval(timer);
	},function(){
		timer = setInterval(function(){
		   if(index==len-1){index=-1;}						 
		   index++;						
		   $("#Package").find(".Package ul").stop().animate({"marginLeft":-576*index},300);		
		} , 4000);
	}).trigger("mouseleave");
	
	
	$("#promoreTab").find("a").click(function(e){
		e.preventDefault();									  
		index  =  $("#promoreTab a").index(this);	
		scrollAuto(index);
	});	
	
	$("#promoreTab").hover(function(){
		 clearInterval(timer);
	},function(){
		timer = setInterval(function(){
		   scrollAuto(index);			
		   index++;
		   if(index==7){index=0;}
	    } , 3600);
	}).trigger("mouseleave");
	
	function scrollAuto(index){
		$("#promoreTab").find("a").eq(index).addClass("on").siblings().removeClass("on");	
		$("#promoreTabList li").hide().eq(index).show();		
	};
	
	
	var len1 = $("#SpikeListMore").find("li").length;	
	$("#SpikeListMore li").each(function(i){
		$(this).mouseover(function(){
			$(this).addClass('on').siblings().removeClass('on');
			$("#SpikeListMore li").eq(0).find(".Spikeshow").hide();
			$("#SpikeListMore li").eq(0).find(".Spikehide").show();
			for(var j=0;j<len1;j++){
				$("#SpikeListMore li").eq(j).find(".Spikeshow").hide();
				$("#SpikeListMore li").eq(j).find(".Spikehide").show();
			}
			$(this).find(".Spikeshow").show();
			$(this).find(".Spikehide").hide();
		});
	});
	
	
}



//排行榜
var snTopList = function(tabId,id){
	var btn = $('#'+id).find('li');
	var len = btn.length ;
	return{
		init:function(){
			var that = this ;
			$("#"+tabId).find("a").mouseover(function(e){
				e.preventDefault();								 
				$(this).addClass("on").parent().siblings().find("a").removeClass("on");
				var href = $(this).attr("href");
				$("#"+id).html("<div class='snToploading'></div>");
				$.post(href,function(data){
					$("#"+id).html(data);	
					snTopList('indexTab01','indexTopList01').changelist();	
					snTopList('indexTab02','indexTopList02').changelist();	
				});
			}).click(function(e){
				return false;
				}).bind('focus', function(){    
				if(this.blur){ 
				   this.blur();    
				}    
  			});							  
			
			that.changelist();
			
			
			$("#tabIndex01").find("li").each(function(i){
				$(this).find("a").mouseover(function(){
					$(this).addClass("on").parent().siblings().find("a").removeClass('on');							 
					$(".tabIndexContent01").hide().eq(i).show();							 
				})									   
			});
			
			$("#tabIndex02").find("li").each(function(i){
				$(this).find("a").mouseover(function(){
					$(this).addClass("on").parent().siblings().find("a").removeClass('on');								 
					$(".tabIndexContent02").hide().eq(i).show();							 
				})									   
			});
			
		},
		changelist:function(){
			btn.each(function(i){
				$(this).mouseover(function(){
					$(this).addClass('on').siblings().removeClass('on');
					btn.eq(0).find(".topshow").hide();
					btn.eq(0).find(".tophide").show();
					for(var j=0;j<len;j++){
						btn.eq(j).find(".topshow").hide();
						btn.eq(j).find(".tophide").show();
					}
					$(this).find(".topshow").show();
					$(this).find(".tophide").hide();
				});
			});
		}
	}
}

//tickcount
$.suning.spiketTime = function(startTime, endTime) {
	if (endTime <=0) 
		return;
	var base = $("#Sn_spike_time span")
	var s1 = base.eq(1);
	var s2 = base.eq(2);
	var s3 = base.eq(3);
	var type = 1;
	
	if (startTime < 0) {
		base.eq(0).html("距离结束：")
	
	} else {
			base.eq(0).html("距离开始：")
	}
	
	
	function formatTime(t) {
			if (t > 0) {
				var totalSecond = t / 1000;
				var minute = Math.floor(totalSecond / 60);
				var hour = Math.floor(minute / 60);
				minute = minute % 60;
				var second = Math.floor(totalSecond % 60);
				return [hour, minute, second];
				
			} else {
				return [0, 0, 0];
				
			}
	}
	
	function changeType() {
		base.eq(0).html("距离结束：")
	}
	
	var timerLeft = function(t, e) {
		var start = (new Date()).getTime();
		
		return {
			getLeft: function() {
				var now = (new Date()).getTime();
				var s = t - (now - start);
				if (s <=0 && type == 1) {
					changeType();
					
					this.getLeft = function() {
						var now = (new Date()).getTime();
						var s = e - (now-start);
						return s;
					}
					return this.getLeft();
				}
				return s;
			},
			render: function() {
				var arr = formatTime(this.getLeft());
				if(arr[0] > 9){
					s1.css('letter-spacing','0');
				}else{
					s1.css('letter-spacing','0');	
				}
				if(arr[1] > 9){
					s2.css('letter-spacing','0');
				}else{
					s2.css('letter-spacing','0');
				}
				if(arr[2] > 9){
					s3.css('letter-spacing','1px');
				}else{
					s3.css('letter-spacing','0');	
				}
				s1.html(arr[0]);
				s2.html(arr[1]);
				s3.html(arr[2])
			}
		}
	};
	
	var tl = timerLeft(startTime, endTime);
	tl.render();
	setInterval(function() {
		tl.render();
	},1000);
}





//返回顶部效果
var goTop = function(){
	var box = $('#gotop');
	var isIE=!!window.ActiveXObject;
	var isIE6=isIE&&!window.XMLHttpRequest;
	var width = 0 ;
	if( document.documentElement.clientHeight > document.body.scrollHeight ){
		var height = (document.body.scrollHeight-box.height()) /2 ;
	}else{
		var height = (document.documentElement.clientHeight-box.height()) /2 ;
	}
	
	if( isIE6 ){
		box.css({right:width,top:height+$(window).scrollTop()-50});
		$(window).scroll(function(){
			box.css({top: height+$(window).scrollTop()-50 });					  
		});
	}else{
		box.css({right:width});
	}
	
}


var Bgscroll = function(){
	var box = $('#pageBg');
	var isIE=!!window.ActiveXObject;
	var isIE6=isIE&&!window.XMLHttpRequest;
	var width = document.documentElement.clientWidth ;
	box.css("width",width);
	if( document.documentElement.clientHeight > document.body.scrollHeight ){
		var height = (document.body.scrollHeight-box.height()) /2 ;
	}else{
		var height = (document.documentElement.clientHeight-box.height()) /2 ;
	}
	
	if( isIE6 ){
		box.css({right:width,top:$(window).scrollTop()+210});
		$(window).scroll(function(){
			if( $(window).scrollTop() > document.body.scrollHeight-800 ){
				return false;
			}else{
				box.css({top: $(window).scrollTop()+210});		
			}
			//alert(  $(window).scrollTop() );
		});
	}else{
		box.css({right:width});
	}
	
}



$(document).ready(function() {
	goTop();						   
	Bgscroll();
	
	$.suning.MenuNav();					   
	$.suning.SNTabFlash();
	$.suning.SNProlist();
	
	
	$.suning.TabClick('snTab','sn_noice_list');
	$.suning.IndexTab();
	snTopList('indexTab01','indexTopList01').init();	
	snTopList('indexTab02','indexTopList02').init();
		
	$(".allProItem").mouseover(function(){$(this).addClass("allProItemFocus");}).mouseout(function(){$(this).removeClass("allProItemFocus");});	
});

	function keywords(){
		if($("#keytext").val()==""){
		$(".keywords").hide();}
		else{
		$(".keywords").show();
		$.get('keywordsdata.html',function(data){
			$(".keywords").html(data);	
			$(".keywords").find('li').each(function(){
				$(this).hover(function(){
					$(this).addClass('on');	
				},function(){
					$(this).removeClass('on');							   
				})
				$(this).click(function(){
					text = $(this).children(".keyname").text();
					$('#keytext').val(text);
					$(".keywords").hide();
				})
			})
		})
	}
		}		