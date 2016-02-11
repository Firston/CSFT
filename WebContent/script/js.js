	function onLoad(){		
		dhtmlLoadScript('/CSFT/script/eventsOverData.js');
		dhtmlLoadScript('/CSFT/script/exchangeData.js');
		dhtmlLoadScript('/CSFT/script/viewData.js');
		dhtmlLoadScript('/CSFT/script/motionpack.js');
	}
	
	
	/**
	 * Подгрузка js - файлов 
	 */
	var jsScriptsLoad = new Array;
	function dhtmlLoadScript(url){
	   if(jsScriptsLoad.join().search(url) == -1){
	      jsScriptsLoad[jsScriptsLoad.length] = url;
	      var e = document.createElement("script");
	      e.src = url;
	      e.type="text/javascript";
	      document.getElementsByTagName("head")[0].appendChild(e);
	   }
	}

	
	/**
	 * Закрытие блока ошибок. Временное решение
	 * @param id
	 */
	function closeError(id){		
		  var elem = document.getElementById(id);
		  var parentElement = elem.parentNode;		
		  parentElement.removeChild(elem);
	}
	 
	 
	 
	 
