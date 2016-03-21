var timerlen = 5;
var slideAniLen = 250;

var timerID = new Array();
var startTime = new Array();
var obj = new Array();
var endHeight = new Array();
var moving = new Array();
var dir = new Array();
var listMenu = ['dictionaries', 'services', 'access', 'tools'];
			

function slidedown(objname, id){
	
        switch(true){
        /*
         * 
         * В очереди на удаление
         * 
          case id == 'idF':{
              var imgTeg = document.getElementById(id);
              imgTeg.setAttribute('src', '/CSFT/image/arrow1.png');
              imgTeg.setAttribute('onmousedown', 'slideup(\'mydiv\',\'idF\')');
              break;
          }
          case id == 'idT':{
              var imgTeg = document.getElementById(id);
              imgTeg.setAttribute('src', '/CSFT/image/arrow1.png');
              imgTeg.setAttribute('onmousedown', 'slideup(\'mydiv2\',\'idT\')');     
              break;
          }
          */
          case id == 'menu_dictionaries':
          case id == 'menu_services':
          case id == 'menu_access':
          case id == 'menu_tools':{
            var table = id.split('_')[1];
            for(var i = 0, count = listMenu.length; i < count; i++){
        	  if(table != listMenu[i])
        		slideup('subMenu_' + listMenu[i], 'menu_' + listMenu[i]);
        	  document.getElementById(id).style.visibility = 'hidden';
        	}
            document.getElementById(id).parentNode.setAttribute('onmousedown', 'slideup(\'subMenu_' + table + '\',\'menu_' + table + '\')');
            break;
          }
        }        
        if(moving[objname])
            return;

	    if(document.getElementById(objname).style.display != "none")
	            return; // cannot slide down something that is already visible  
	    
	    moving[objname] = true;
	    dir[objname] = "down";
	    startslide(objname);
	    document.getElementById(id).style.visibility = 'visible';
}

function slideup(objname, id){
	
        switch(true){
        /*
         * В очереди на удаление
         * 
          case id == 'idF':{
              var imgTeg = document.getElementById(id);
              imgTeg.setAttribute('src', '/CSFT/image/arrow2.png');
              imgTeg.setAttribute('onmousedown', 'slidedown(\'mydiv\',\'idF\')');
              break;
          }
          case id == 'idT':{
              var imgTeg = document.getElementById(id);
              imgTeg.setAttribute('src', '/CSFT/image/arrow2.png');
              imgTeg.setAttribute('onmousedown', 'slidedown(\'mydiv2\',\'idT\')');
              break;
          }
          */
          case id == 'menu_dictionaries':
          case id == 'menu_services':
          case id == 'menu_access':
          case id == 'menu_tools':{        	  
              document.getElementById(id).parentNode.setAttribute('onmousedown', 'slidedown(\'subMenu_' + id.split('_')[1] + '\',\'menu_' + id.split('_')[1] + '\')');     
              break;
          }  
        }

        if(moving[objname])
            return;

	    if(document.getElementById(objname).style.display == "none")
	            return; // cannot slide up something that is already hidden
	
	    moving[objname] = true;
	    dir[objname] = "up";
	    startslide(objname);
}

function startslide(objname){
        obj[objname] = document.getElementById(objname);

        endHeight[objname] = parseInt(obj[objname].style.height);
        startTime[objname] = (new Date()).getTime();

        if(dir[objname] == "down")
           obj[objname].style.height = "1px";

        obj[objname].style.display = "block";

        timerID[objname] = setInterval('slidetick(\'' + objname + '\');',timerlen);
}

function slidetick(objname){
        var elapsed = (new Date()).getTime() - startTime[objname];

        if (elapsed > slideAniLen)
                endSlide(objname);
        else {
                var d =Math.round(elapsed / slideAniLen * endHeight[objname]);
                if(dir[objname] == "up")
                        d = endHeight[objname] - d;

                obj[objname].style.height = d + "px";
        }

        return;
}

function endSlide(objname){		
	
        clearInterval(timerID[objname]);

        if(dir[objname] == "up")
          obj[objname].style.display = "none";

        obj[objname].style.height = endHeight[objname] + "px";

        delete(moving[objname]);
        delete(timerID[objname]);
        delete(startTime[objname]);
        delete(endHeight[objname]);
        delete(obj[objname]);
        delete(dir[objname]);

        return;
}

