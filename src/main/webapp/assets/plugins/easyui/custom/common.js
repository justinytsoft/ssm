$(function(){
	var dialogs = $(".easyui-dialog");
	/*格式化dialog的样式*/
	$.each(dialogs,function(){
		var obj = $(this);
		obj.dialog("dialog").css({
			borderWidth:0,
			padding:0
		});
	});
});

//日期控件的重置按钮
var dateboxReset = $.extend([], $.fn.datebox.defaults.buttons);
dateboxReset.splice(1, 0, {
    text: '重置',
    handler: function(target){
    	$(target).datebox("clear");
    	$(target).datebox("hidePanel");
    }
});

//自定义校验规则  validType:'isNumber'   validType:['isNumber','numberRegion[0,100]']
$.extend($.fn.validatebox.defaults.rules, {
	startThanEnd: {
		validator: function(value, param){
			var s = $("#" + param[0]);
			var e = $("#" + param[1]);
			var sdate = stringToDate(s.datetimebox("getValue")).valueOf();
			var edate = stringToDate(e.datetimebox("getValue")).valueOf();
			return edate >= sdate;
		},
		message: '开始时间不能大于结束时间'
	},
    isNumber: { 
        validator: function(value){
        	var reg=/^\d+$/;
            return reg.test(value);
        },
        message: '只能输入整数'
    },
    numberRegion: { 
        validator: function(value,param){
        	return (value >= param[0] && value <= param[1]);
        },
        message: '输入数值必须在{0}~{1}之间'
    },
    minNum: {
        validator: function(value, param){
            return parseInt(value) >= param[0];
        },
        message: '输入的数值必须大于等于{0}'
    },
    maxNum: {
        validator: function(value, param){
            return parseInt(value) <= param[0];
        },
        message: '输入的数值必须小于等于{0}'
    },
    numMult: {
        validator: function(value, param){
            return value % param[0] == 0;
        },
        message: '输入的数值必须是{0}的倍数'
    },
    lengthRegion: {
    	validator: function(value,param){
    		var length = value.length;
            return (length >= param[0] && length <= param[1]);
        },
        message: '输入长度必须在{0}~{1}之间'
    },
    length: {
		validator: function(value,param){
			var length = value.length;
			return length == param[0];
		},
		message: '输入字符必须等于{0}'
    },
    minLength: {
        validator: function(value, param){
            return value.length >= param[0];
        },
        message: '输入的字符长度必须大于等于{0}'
    },
    maxLength: {
        validator: function(value, param){
            return value.length <= param[0];
        },
        message: '输入的字符长度必须小于等于{0}'
    },
    isPhone: { //手机
    	validator: function(value, param){
    		var reg = /^1[3-9][0-9]{9}$/;
            return reg.test(value);
        },
        message: '手机号格式错误'
	},
	isTel: { //座机
		validator: function(value, param){
			var reg = "^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$"
			return reg.test(value);
		},
		message: '座机号格式错误'
	},
	isTP: { //手机座机
		validator: function(value, param){
			var p = /^1[3-9][0-9]{9}$/;
			var t = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
			return (p.test(value)||t.test(value));
		},
		message: '电话格式错误'
	}
});

/*打开dialog*/
function openDialog(id){
	$("#"+id).dialog("open",true);
}

/*关闭dialog*/
function closeDialog(id){
	$("#"+id).dialog("close",true);
}

/*图片查看*/
function alertImg(msg){
	$.messager.alert("",msg);
	messagerCommon();
	var left = (parseInt($(".messager-window").css("width"))+200) + "px";
	$(".messager-window").css({"width":"600px","left":left});
	$(".messager-body").css("width","600px");
	$(".messager-button").css("width","600px");
}

/*alert*/
function cAlert(msg){
	$.messager.alert("",msg);
	messagerCommon();
}

/*confirm*/
function cConfirm(msg,fun){
	$.messager.confirm('',msg,fun);
	messagerCommon();
}

/*prompt*/
function cPrompt(msg,fun){
	$.messager.prompt('',msg,fun);
	messagerCommon();
}

/*弹出框通用样式*/
function messagerCommon(){
	$(".window-shadow").css("height","0px");
	$(".messager-button *").trigger("blur");
	$(".messager-body").css({"text-align":"center","line-height":"3","background":"#fafafa"});
	$(".messager-icon").remove();
	//$(".l-btn-text").css({"color":"black"});
}

/*文本框清除功能*/
$.extend($.fn.textbox.methods, {
    addClearBtn: function(jq, iconCls){
        return jq.each(function(){
            var t = $(this);
            var opts = t.textbox('options');
            opts.icons = opts.icons || [];
            opts.icons.unshift({
                iconCls: iconCls,
                handler: function(e){
                    $(e.data.target).textbox('clear').textbox('textbox').focus();
                    $(this).css('visibility','hidden');
                }
            });
            t.textbox();
            if (!t.textbox('getText')){
                t.textbox('getIcon',0).css('visibility','hidden');
            }
            t.textbox('textbox').bind('keyup', function(){
                var icon = t.textbox('getIcon',0);
                if ($(this).val()){
                    icon.css('visibility','visible');
                } else {
                    icon.css('visibility','hidden');
                }
            });
        });
    }
});

/*转换树节点*/
function convert(rows){
	//判断是不是根节点, 是, 返回true
	function exists(rows, parentId){
		for(var i=0; i<rows.length; i++){
			if (rows[i].id == parentId) return true;
		}
		return false;
	}
	
	//储存跟节点的数组
	var nodes = [];
	//获取跟节点
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		if (!exists(rows, row.parentId)){
			nodes.push({
				id:row.id,
				text:row.text,
				attributes:row.attributes
			});
		}
	}

	var toDo = [];
	for(var i=0; i<nodes.length; i++){
		toDo.push(nodes[i]);
	}
	
	while(toDo.length){
		//获取根节点
		var node = toDo.shift();	
		//获取根节点的子节点
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (row.parentId == node.id){
				node.state = "closed";
				var child = {id:row.id,text:row.text,attributes:row.attributes};
				if (node.children){
					node.children.push(child);
				} else {
					node.children = [child];
				}
				toDo.push(child);
			}
		}
	}
	return nodes;
}


