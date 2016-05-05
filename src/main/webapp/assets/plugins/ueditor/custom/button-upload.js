var ueFile = null;

/**
 * 上传按钮事件
 */
function ueUpload(){
	$.ajaxFileUpload({
         url: rootPath + "services/upload/uploadImage?_="+Math.random(), //用于文件上传的服务器端请求地址
         secureuri: false, //是否需要安全协议，一般设置为false
         type : "post", //上传类型
         fileElementId: 'ueFile', //文件上传域的ID
         dataType: 'json', //返回值类型 一般设置为json
         success: function (data, status)  //服务器成功响应处理函数
         {
        	 if(data.status == 200){
        		 img = "<img style='width:98px;height:90px;display:inline;' src='"+data.data.path+"' />";
        		 ue.execCommand('inserthtml', img);
        	 }
        	 $("input").remove(".ueClass");
         },
         error: function (data, status, e)//服务器响应失败处理函数
         {
        	 $("input").remove(".ueClass");
             alert("上传失败");
         }
     });
}

/**
 * 自定义一个上传按钮
 */
UE.registerUI('button',function(editor,uiName){
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(){
        	ueFile = $("<input class='ueClass' type='file' name='file' id='ueFile' onchange='ueUpload()'/>");
        	$("body").append(ueFile);
        	ueFile.trigger("click");
        }
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:"上传图片",
        //提示
        title:"上传图片",
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -380px 0;',
        //点击时执行的命令
        onclick:function () {
            //这里可以不用执行命令,做你自己的操作也可
           editor.execCommand(uiName);
        }
    });

    //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function () {
        var state = editor.queryCommandState(uiName);
        if (state == -1) {
            btn.setDisabled(true);
            btn.setChecked(false);
        } else {
            btn.setDisabled(false);
            btn.setChecked(state);
        }
    });

    //因为你是添加button,所以需要返回这个button
    return btn;
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);



