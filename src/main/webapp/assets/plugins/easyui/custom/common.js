$(function(){
	var dialogs = $(".easyui-dialog");
	var datagrids = $(".easyui-datagrid")
	
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

/*创建树节点*/
function createTree(id, url){
	$('#'+id).tree({
	    url:url,
	    animate:true,
	    onClick: function(node){
			var url = node.attributes;
	    	if(url != ""){
				$("#right").panel("refresh",rootPath + url);
			}
		},
	    loadFilter: function(rows){
	    	/* 转换树节点 */
			return convert(rows);
		},
		onLoadSuccess: function(node, data){
			/* 树节点的样式 */
			$.each($("#navigate div"),function(){
				var obj = $(this);
				obj.css("font-size","50px");
				obj.css("color","red");
				obj.css("padding","10px 0px");
			});
		}
	}); 
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