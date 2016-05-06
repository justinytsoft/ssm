//自定义校验规则
$.extend($.fn.validatebox.defaults.rules, {
    isNumber: { 
        validator: function(value){
        	var reg=/^\d+$/;
            return reg.test(value);
        },
        message: '只能输入整数'
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
    }
});

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

/*打开dialog*/
function openDialog(id){
	$("#"+id).dialog("open",true);
}

/*关闭dialog*/
function closeDialog(id){
	$("#"+id).dialog("close",true);
}

/*alert*/
function cAlert(msg){
	$.messager.alert("",msg);
	messagerCommon()
}

/*confirm*/
function cConfirm(msg,fun){
	$.messager.confirm('',msg,fun);
	messagerCommon()
}

/*弹出框通用样式*/
function messagerCommon(){
	$(".window-shadow").css("height","0px");
	$(".messager-button *").trigger("blur");
	$(".messager-body").css({"text-align":"center","line-height":"7","background":"#fafafa"});
	$(".messager-icon").remove();
}

/*文本框清除功能*/
/* $('#tt').textbox().textbox('addClearBtn', 'icon-clear');*/
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

//格式化日期框显示。 只显示年月， 参数 obj 必须是jquery对象
function yearAndMonth(obj){
	obj.datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    obj.datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0)
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth()+1);/*getMonth返回的是0开始的，忘记了。。已修正*/ }
    });
    var p = obj.datebox('panel'), //日期选择对象
    tds = false, //日期选择对象中月份
    span = p.find('span.calendar-text'); //显示月份层的触发控件
}

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