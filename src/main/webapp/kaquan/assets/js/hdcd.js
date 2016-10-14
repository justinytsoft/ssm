      function SetTab(tab,id,cnt){
            var menus=document.getElementById(tab).getElementsByTagName("li");            
            for(i=0;i<cnt;i++){           
                i==id?menus[i].className="on":menus[i].className=" ";
                i==id?document.getElementById(tab+"-content"+i).className="block":document.getElementById(tab+"-content"+i).className="none";
            }
        }
