/**
 * 创建五角星
 * @param context
 */
function drawStar(id) {
    var canvas = document.getElementById(id);
    if (canvas == null){
        return false;
    }
    
    var context = canvas.getContext("2d");

	//设置阴影
	context.shadowOffsetX = 5;
	context.shadowOffsetY = 5;
	context.shadowColor = '#FFFFFF';
	context.shadowBlur =5;
	
	//图形绘制
    create5Star(context);
    context.fill();
}

/**
 * 绘制五角星
 */
function create5Star(context) {
	var dx = 150;
	var dy = 70;
	
	//星星的大小
	var s = getRandomNum(25,100);
	
	//创建路径
	context.beginPath();
	
	//填充颜色
	var my_gradient=context.createLinearGradient(0,0,170,0);
	my_gradient.addColorStop(1,"rgba(255, 255, 255, 0.51)");
	context.fillStyle = my_gradient;

	//绘制路径
    var x = Math.sin(0);
    var y = Math.cos(0);
    var dig = Math.PI / 5 * 4;
    for (var i = 0; i < 5; i++) {
        var x = Math.sin(i * dig);
        var y = Math.cos(i * dig);
        context.lineTo(dx + x * s, dy + y * s);

    } 
    
    context.closePath();
}


