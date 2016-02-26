	/**
	 * Удаление записи
	 * @param record
	 * @param element
	 */
	function del(record, element){
	  var idRecord = record.getAttribute('value');	  
	  var isDel = confirm("Вы действительно хотите удалить запись?");
      if(isDel)
	  if(idRecord != null){
		var atr = idRecord.split('_');
		
	    var req = getXmlHttp();
		req.onreadystatechange = function() { 

		        if (req.readyState == 4) {
		           
		            if(req.status == 200) {
		            	//alert(req.responseText);
		                var line = req.responseText.split('&');		                
		                var atrLine = line[0].split('=');
		                if(atrLine[1] == 'true'){
		                  if(element == 'tree'){
			                  remove(document.getElementById(atr[1] + '_' + atr[2]).parentNode);
		                  }else if(element == 'table'){		                	  
		                	  remove(document.getElementById('tr' + atr[2]));		
		                  }	
		                }else messageInfo(line);
		                
		                var table = document.getElementById('table_body_' + atr[1]);
		                if(table != null){
		                  var trs = table.getElementsByTagName('tr');
			              var tds;
			              for(var i = 0, countTR = trs.length; i < countTR; i++){
		                	tds = trs[i].getElementsByTagName('td');
		                	  if(i % 2 != 0){
			                	for(var j = 1, countTD = tds.length; j < countTD; j++)
						          tds[j].setAttribute('class', 'c_table_td');  
		                	  }else{
		                		for(var j = 1, countTD = tds.length; j < countTD; j++)
					              tds[j].setAttribute('class', 'c_table_td2');
		                	  }		
			              }
		                }
		             /*
		              * Очистка индекса при успешном удалении записи
		              */   
		       		 record.removeAttribute('value');
		       		 idRecord = record.id.replace('del', 'edit'); 
		    		 document.getElementById(idRecord).removeAttribute('value');		                
		            } 
		        }
		 };

		 var data = "table=" + atr[1];
		 data += "&typeEvent=delete";
		 data += "&id=" + atr[2];
		 req.open('GET', '/CSFT/constructor?' + data, false); 
		 req.send(null);
		 
	  }else{
			var tablename = record.id.split('_')[1];
			messageInfo('Не выбрано записи для удаления в ' + listTables['table_' + tablename].value);
	  }
	}
	/**
	 * Кодирование данных перед отправкой
	 */
	function formatSendData(id){
		var element = document.getElementById(id);
		var div = element.getElementsByTagName("div");
		c_form_blok: for(var i = 0; i < div.length-1; i++){
			if(div[i].getAttribute('class') == 'c_form_blok'){
				var element = div[i].getElementsByTagName("input")[0] != undefined ? div[i].getElementsByTagName("input") 
																				   : div[i].getElementsByTagName("textarea");
				if(element[0] == undefined) continue c_form_blok;
				
				var encoded = encodeURIComponent(element[0].value);
				//alert('element[0] : ' + element[0]);
				//alert('element[0].id : ' + element[0].id);
				//alert('encoded : ' + encoded);
				
				document.getElementById(element[0].id).setAttribute('value', encoded);
			}
		}
	}	
	
	/**
	 * @param id - идентификатор формы
	 * @param isSetOnly - только заполненные поля
	 * @returns {Object}
	 */
    function getMapForm(idForm, isSetOnly) {
        var map = new Object();
        var nodes = document.getElementById(idForm).elements;
        for (var i1 in nodes) {
            if (!nodes[i1].nodeName) { continue; }
            
            var name = nodes[i1].nodeName.toLowerCase();
            if ('input' == name && nodes[i1].value.length) { 
                map[nodes[i1].name] = nodes[i1].value;
                continue;
            }
            
            if ('textarea' == name && nodes[i1].value.length) {
                map[nodes[i1].name] = nodes[i1].value;
                continue;
            }
            
            if ('select' == name) {
                var select_nodes = nodes[i1].childNodes;
                var coll = new Array();
                for (var i2 in select_nodes) {
                    if (!select_nodes[i2].nodeName) { continue; }
                    if ('option' == select_nodes[i2].nodeName.toLowerCase() && select_nodes[i2].selected) {
                        coll.push(select_nodes[i2].value);
                    }
                }
                
                if(coll.length) {
                	if(coll.length == 1)
                		map[nodes[i1].name] = coll[0];
                	else
                	  map[nodes[i1].name] = coll; 
                }
                continue;
            }
        }
        
        return map;
    }
    
    /**
     * Формирвание строки запроса
     * @param map
     * @returns {String}
     */
    function getUrlFromMap(map) {
        var amp = '&';
        var url = '';
        //alert(map);
        for (var i1 in map) {
        	//alert('url : ' + url);
        	//alert('i1 : ' + i1);
            if (map[i1] instanceof Object) {
                for (var i2 in map[i1]) {
              //  	alert('i2 : '  + i2);
                    //url += i1 + '=' + map[i1][i2] + amp;
                //	alert(i2 + ' : ' + map[i1][i2]);
                    url += encodeURIComponent(i2) + "=" + encodeURIComponent(map[i1][i2]) + amp;
                  //  alert("url : " + url);
                }
                continue;
            }            
            //alert(i1 + ' : ' + map[i1]);
            url += encodeURIComponent(i1) + "=" + encodeURIComponent(map[i1]) + amp;
            //url += i1 + "=" + map[i1] + amp;    
        }
        
        url = url.substring(0, url.length - amp.length);
        return url;
    }  

	
	function getXmlHttp(){
	    try {
	        return new ActiveXObject("Msxml2.XMLHTTP");
	    } catch (e) {
	        try {
	            return new ActiveXObject("Microsoft.XMLHTTP");
	        } catch (ee) {
	        }
	    }
	    if (typeof XMLHttpRequest!='undefined') {
	        return new XMLHttpRequest();
	    }
	}
	
	/**
	 * Окно справки
	 */
	function openInfo(){
		
	    var req = getXmlHttp();
		req.onreadystatechange = function() { 

		        if (req.readyState == 4) {
		           
		            if(req.status == 200) {
		            	//alert(req.responseText);
		            	if(document.getElementById('main_info_body') == null){
				              var newDiv = document.createElement('div');
				              newDiv.id = 'main_info_body';
				              newDiv.innerHTML = req.responseText;
				              var content = document.getElementById('id_main_div');
				              content.appendChild(newDiv);		            		
		            	}

		            }
		        }
		 };
		 
		 var data = "typeEvent=info";

		 req.open('GET', '/CSFT/constructor?' + data, false); 
		 req.send(null); 
	}
	
	
	/**
	 * Сохранение изменений или добавлений
	 * 
	 * СКРИПТ выполнятся для блока id_blockAddElement
	 * 
	 * НУЖНА ПЕРЕРАБОТКА
	 */
	
	function save(){

		var arrayValue = new Array();
		var subArray; 
		var typeEvent;
		
		/*
		 * определяется с учетом поддержки старой реализации
		 * labelTable - НЕ ИСПОЛЬЗОВАТЬ В НОВОМ
		 */
		var element = document.getElementById('labelTable');
		element = (element != null) ? element : document.getElementById('id_blockAddElement'); 
		//var table = document.getElementById('id_blockAddElement');// id_blockAddElement labelTable
		var atrTable = element.getElementsByTagName('input')[0].value.split('_');
		switch(true){
		  case atrTable[0] =='contentSubServices' : {
			  
			var obj = document.getElementById('id_blockAddElement').getElementsByTagName('select');
			  switch(true){
			  	case obj[0].id == 'id_typeObject' : {
					subArray = new Array();
					subArray.push('id_typeObject', document.getElementById('id_typeObject').value);
					arrayValue.push(subArray);
					subArray = new Array();
					subArray.push('id_subService', atrTable[1]);
					arrayValue.push(subArray);
					typeEvent = "insert";					
			  		break;
			  	}
			  	case obj[0].id == 'id_listGroupObject' : {
			  		/*
			  		 * Не универсальная обработка
			  		 */
				    atrTable[0] = 'contentSubServices';
					subArray = new Array();
					subArray.push('id_listGroupObject', document.getElementById('id_listGroupObject').value);
					arrayValue.push(subArray);
					subArray = new Array();
					subArray.push('id_subService', atrTable[1]);
					arrayValue.push(subArray);
					subArray = new Array();
					subArray.push('valueDefault', 'id_subService');
					arrayValue.push(subArray);
					typeEvent = 'insert';
			  		break;
			  	}
			  	case obj[0].id == 'id_listPrioritie': {
					/*
					 * Функционал перенесен в форму редактирования
					 */
			  		alert("НЕЖДАНЧИК! Функционал перенесен в форму редактирования!");
			  		break;
				/*	subArray = new Array();				
					subArray.push('id_listPrioritie', document.getElementById('id_listPrioritie').value);
					arrayValue.push(subArray);
					subArray = new Array();
					subArray.push('id', document.getElementById('id_blockAddElement').getElementsByTagName('input')[0].value.split('_')[1]);
					arrayValue.push(subArray);
					typeEvent = "update";*/
			  	}
			  }
			  /*
			var obj = document.getElementById('id_typeObject');
			if(obj != null){
				subArray = new Array();
				subArray.push('id_typeObject', obj.value);
				arrayValue.push(subArray);
				subArray = new Array();
				subArray.push('id_subService', atrTable[1]);
				arrayValue.push(subArray);
				typeEvent = "insert";
			}else{
				/*
				 * Функционал перенесен в форму редактирования
				 */
			/*	subArray = new Array();				
				subArray.push('id_listPrioritie', document.getElementById('id_listPrioritie').value);
				arrayValue.push(subArray);
				subArray = new Array();
				subArray.push('id', document.getElementById('id_blockAddElement').getElementsByTagName('input')[0].value.split('_')[1]);
				arrayValue.push(subArray);
				typeEvent = "update";
			}*/
			break;
		  }
		  case atrTable[0] == 'scriptsObject' : {

			subArray = new Array();
			subArray.push('id_script', document.getElementById('id_script').value);
			arrayValue.push(subArray);
			subArray = new Array();
			subArray.push('id_typeObject', atrTable[1]);
			arrayValue.push(subArray);
			typeEvent = 'insert';
			break;
		  }
		  /*
		   * не универсальная обработка!
		   */
		  case atrTable[0] == 'services' : {
			  
			    var el = document.getElementsByTagName('select')[0];
			    //alert(el.id);
			    if(el.id == 'id_commission'){
				    atrTable[0] = 'commissionServices';
					subArray = new Array();
					subArray.push('id_commission', document.getElementById('id_commission').value);
					arrayValue.push(subArray);			    	
			    }else if(el.id == 'id_classService'){
				    atrTable[0] = 'classServices';
					subArray = new Array();
					subArray.push('id_classService', document.getElementById('id_classService').value);
					arrayValue.push(subArray);			    	
			    }
				subArray = new Array();
				subArray.push('id_service', elementId);
				arrayValue.push(subArray);
				subArray = new Array();
				subArray.push('valueDefault', 'id_service');
				arrayValue.push(subArray);
				typeEvent = 'insert';
				break;
		  }
		  case atrTable[0] == 'contentGroupObjects' : {
			  subArray = new Array();
			  subArray.push('id_listGroupObject', document.getElementById('id_listGroupObjects').value);
			  arrayValue.push(subArray);
			  subArray = new Array();
			  subArray.push('id_typeObject', document.getElementById('id_typeObject').value);
			  arrayValue.push(subArray);
			  typeEvent = 'insert';
			  break;
		  }
		}
		
	    var req = getXmlHttp();
		req.onreadystatechange = function() { 

		        if (req.readyState == 4) {
		           
		            if(req.status == 200) {
		              //alert(req.responseText);
		            	var errors = getMapResponse(req.responseText, 'error');
		            	if(!isError(errors)){
				          var line = req.responseText.split('&');
				          var resultTypeQuery = line[line.length-1].split('=')[1];
			              if(resultTypeQuery == 'insert' || resultTypeQuery == 'update'){
				              var infoDiv = document.getElementById('infoDiv');
				              if(infoDiv != null && resultTypeQuery =='update'){
				            	  remove(infoDiv);
				            	  createElement('div', document.getElementById('id_blockAddElement'), 'infoDiv', null);
				              }else createElement('div', document.getElementById('id_blockAddElement'), 'infoDiv', null);
				              
				              
				              var pInfoDiv = document.getElementById('pInfoDiv');
				              if(pInfoDiv == null){
					              if(resultTypeQuery == 'insert') createElement('p', document.getElementById('infoDiv'), 'pInfoDiv', 'Добавлены записи в \"' + listTables['table_' + atrTable[0]].value + '\"'); 
					              else createElement('p', document.getElementById('infoDiv'), 'pInfoDiv', 'Обнавлены записи в \"' + listTables['table_' + atrTable[0]].value + '\"'); 		            	  
				              }		              
				              viewInfoTable(line);		
				              hiddenItemsHM();
			              }	
		            	}
		            }		            
		        }
		 };
		 var data = "table=" + atrTable[0];
		 data += "&typeEvent=" + typeEvent;

		 for(var i = 0; i < arrayValue.length; i++)
		   data +="&" + arrayValue[i][0] + "=" + arrayValue[i][1];

		 req.open('GET', '/CSFT/constructor?' + data, false);
         data = null;
		 req.send(null); 
		
	}
	/**
	 * Сохранение параметров на форме (добавление, изменение)
	 * @param formName
	 */
	function saveForm(formName){
		//formatSendData(formName);
		var tableName = formName.split('_')[1];
		var map = getMapForm(formName, true);
	    var req = getXmlHttp();
	    var errors;	    
		req.onreadystatechange = function() { 

		        if (req.readyState == 4) {
		           
		            if(req.status == 200) {
		            	//alert(req.responseText);
		            	errors = getMapResponse(req.responseText, 'error');
		            	if(!isError(errors)){
		            		
		            		var delElement = document.getElementById('messageInfo');
		            		if(delElement != null)
		            		  delElement.parentNode.removeChild(delElement);
		            		
		            		addRowInTable(tableName, req.responseText);
			            	remove(document.getElementById(formName));
			            	document.getElementById('formTable_' + tableName).style.display = '';
			            	hiddenItemsHM(); 		            	
		            	}
		            }
		        }
		 };		 
		 req.open('GET', '/CSFT/constructor?' + getUrlFromMap(map), false); 
		 req.send(null); 	
	}
	
	/**
	 * сохранение настроек системы
	 * @param id
	 */
	function saveTools(id){
		
		
		var element = document.getElementById(id);
	    var req = getXmlHttp();
		req.onreadystatechange = function() { 

		        if (req.readyState == 4) {
		           
		            if(req.status == 200) {
		              //alert(req.responseText);
		              var line = req.responseText.split('&');
		              messageInfo(line);
		            }
		        }
		 };
		 
		 var data = "typeEvent=save_properties";
		 data += "&system_property=" + id + "::" + element.value;

		 req.open('GET', '/CSFT/constructor?' + data, false); 
		 req.send(null); 		
	}	
	
	/**
	 * Подгрузка справочника на форму
	 * @param obj - объект в который записать значений
	 * @param idSelected - выбранный по умолчанию элумент
	 */
	function selected(obj, idSelected){
		var atr = obj.id.split('_');		
	    var req = getXmlHttp();
		req.onreadystatechange = function() { 

		        if (req.readyState == 4) {
		           
		            if(req.status == 200) {
		            	//alert(req.responseText);
		            	if(req.responseText.contains('!DOCTYPE html')){
		            		document.documentElement.innerHTML = req.responseText;
		            	}else{
			            	var options = '';
			                var line = req.responseText.split('&');		                
			                for(var i = 0, countLine = line.length; i < countLine; i++){	
			                  var atrLine = line[i].split('=');
			                   if(atrLine[1] == idSelected)
			                     options += "<option value='" + atrLine[0] + "' selected>" + atrLine[1] +"</option>";
			                   else
							     options += "<option value='" + atrLine[0] + "'>" + atrLine[1] +"</option>";		                
			                }
			                obj.innerHTML = options;	
		            	}
		            }
		        }
		 };
		 /*
		  * исключение для typeObject
		  */
		 if(atr[1] != 'typeObject')
			 atr[1] += 's';

		 var data = 'table=' + atr[1];
		 data += '&typeEvent=dictionary';
		 req.open('GET', '/CSFT/constructor?' + data, false); 
		 req.send(null); 
		
	}
	
	/**
	 * НОВЫЙ ФУНКЦИОНАЛ
	 *  Преобразовывает ответ в Map
	 * @param response - строка ответа от сервера
	 */
	function getMapResponse(value, param){
		var map = new Map();
		var attrMap;
		var lines = value.split('&');
		typeData: switch(true){
		  case  param == 'error' :{
			for ( var i = 0, count_i = lines.length; i < count_i; i++) {
				/*
				 * Заглушка
				 *  lines[i].contains('errors:')
				 */				
				if(lines[i].contains('errors:')){
					lines = lines[i].split(':')[1].split(';');
					if(lines[0] != ''){
						for(var j = 0, count_j = lines.length; j < count_j; j++ ){
							attrMap = lines[j].split('=');
							map[attrMap[0]] = attrMap[1];  
						}							
					}
				  break typeData;
				}
			}
			map['error'] = 'none';
			break typeData;
		  }
		  case param == 'first' : {
			lines = lines[0].split(';');
			for ( var i = 0, count = lines.length; i < count; i++) {
				attrMap = lines[i].split('=');
				map[attrMap[0]] = attrMap[1];
			}
			break;
		  }
		  case param == 'last' : {
				lines = lines[lines.length - 1].split(';');
				for ( var i = 0, count = lines.length; i < count; i++) {
					attrMap = lines[i].split('=');
					map[attrMap[0]] = attrMap[1];
				}
				break;
			  }
		  case param ==';=' : {			    
			    var records;
				for (var i = 0, count = lines.length; i < count; i++) {
					records = lines[i].split(param[0]);
					for(var i2 = 0, count2 = records.length; i2 < count2; i2++){
						if(records[i2] != undefined){
							attrMap = records[i2].split(param[1]);
							if (map.has(attrMap[0])) map.set(attrMap[0] + '_' + i, attrMap[1]);
							else map.set(attrMap[0], attrMap[1]);
							
						}
					}					
				}
				break;			  
		  }
		  default : {
			for ( var i = 0, count = lines.length; i < count; i++) {
				attrMap = lines[i].split('=');
				if(attrMap[0] != 'errors' && attrMap[0] != 'resultQueryType')
				  //map[attrMap[0]] = attrMap[1];
					map.set(attrMap[0], attrMap[1]);
			}
			break;
		  }
		}
		return map;
	} 
	

/**
 * =========================================================================
 * Реализация отдельных свойств системы
 * ========================================================================
 */
	
	/*
	 * Проверка наличия комиссий на всех терминалах
	 */
	function checkCommissionForTerminal(){
		var name_service = document.getElementById('id_services').value;
		if(name_service != ''){
			
			var delElement = document.getElementById('messageInfo');
			if(delElement != null)
			  delElement.parentNode.removeChild(delElement);
			 
		    var req = getXmlHttp();
			req.onreadystatechange = function() { 

			        if (req.readyState == 4) {
			            
			            if(req.status == 200) {

			            	var line = req.responseText.split('&');
			            	var countLine = line.length;
			            	var select = document.getElementById('terminalNoCommission');

			            	if(select != null){
			            		select.innerHTML = '';
			            	}else{
				            	select = document.createElement('select');
				            	select.setAttribute('id', 'terminalNoCommission');
				            	select.setAttribute('style', 'width:100%');
				            	document.getElementById('labelBlock_checkCommissionTerminal').parentNode.appendChild(select);	
			            	}
			            	
			            	select.setAttribute('size', 10);
			            	var option; 

			                if(countLine == 1 && line[0] == ''){
			                	option = document.createElement('option');
			                	option.setAttribute('value', 'Комиссия проставлена на всех терминала');			                	
			                	option.textContent = 'Комиссия проставлена на всех терминала';
				                select.appendChild(option);			                	
				                option.setAttribute('style', 'color:#0000ff');
			                }else{			
			                	var options = '';
				            	for(var i = 0; i < countLine; i++){

					                  var atrLine = line[i].split('=');
					                  option = document.createElement('option');
					                  option.setAttribute('value', atrLine[0]);
					                  option.innerText =  atrLine[1];
					                  select.appendChild(option);
					                  option.setAttribute('style', 'color:#ff0000');
									  //options += "<option value='" + atrLine[0] + "' style='color:#ff0000'>" + atrLine[1] +"</option>";
					                }
				            	//select.innerHTML = options;
			                }			                
			            }
			        }
			 };
			 var data = "table=terminals";
			 data += "&typeEvent=dictionary";
			 data += "&name_service=" + document.getElementById('id_services').value;
			 req.open('GET', '/CSFT/constructor?' + data, false); 
			 req.send(null);			
	  }else{
		  messageInfo("Не выбрана услуга", null);	
	  }
	}
	
	/**
	 * version 15.11.27
	 */
	function addBondForTerminal() {
		
		var delElement = document.getElementById('messageInfo');
		if(delElement != null)
		  delElement.parentNode.removeChild(delElement);
		
		var id_terminal = document.getElementById('addBondForTerminal').getAttribute('value');
		
		if(id_terminal != undefined){
			
		    var req = getXmlHttp();
		    var data = 'typeEvent=default';
		    	data += '&table=commissionservices';
				data += '&valueDefault=id_terminal';
				data += '&id_terminal=' + id_terminal;
			req.open('GET', '/CSFT/constructor?' + data, true);		
			req.onreadystatechange = function() { 

			        if (req.readyState == 4) {
			           
			            if(req.status == 200) {
			            	var count = req.responseText;
			            	//alert('count : ' + count);
			        		if(count != 0){
			        			messageInfo("Терминал №" + id_terminal + ' уже имеет привязку к услугам', null);
			        			process();
			        			return;
			        		}else{
			        			data = 'table=commissionServices';
			        			data += '&typeEvent=insert';
			        			data += '&id_terminal=' + id_terminal;
			        			data += '&valueDefault=id_terminal';
			        			req.open('GET', '/CSFT/constructor?' + data, true);
			        			req.onreadystatechange = function() { 

			        		        if (req.readyState == 4) {
			        		            
			        		            if(req.status == 200) {
			        		            	//alert(req.responseText);
			        		            	process();
			        		            	
			        		            	if(isError(getMapResponse(req.responseText, 'error')))
			        		            		return;			            
			        		            	
			        		            	var map = getMapResponse(req.responseText, ';=');		            	
			        		            	var log = document.getElementById('id_Log');
			        		            	log.innerText = 'Список привязанных услуг\r\n';
			        		            	var index;
			        		            	for(var i = 0, count = map.size.div(3); i < count; i++){
			        		            		index = i == 0 ? '' : '_' + i;
			        		            		log.innerText = log.textContent +  map.get('code_service' + index) + ' | ' +
			        		            										   map.get('name_service' + index) + ' | ' +
			        		            										   map.get('descriptionCommission' + index) + ' | ' + '\r\n';		            		
			        		            	}		            	 		            
			        		            }
			        		        }
			        		 };
			        		req.send();			    			
			        		}
			            }
			        }
			 };
			 process();
			 req.send();
		}else{
		  messageInfo("Не выбран терминал", null);
		}	
	}
	
	function process(){
		var visibility = document.getElementById('id_process').style.visibility;
		if(visibility == 'visible')
			document.getElementById('id_process').style.visibility = 'hidden';
		else
			document.getElementById('id_process').style.visibility = 'visible';
		
	}
	/**
	 * Выгрузка параметров услуг в Рапиде
	 */
	function unloadParamsOfServicesRapida() {
		
		//messageInfo("Даннй функционал находится в разработке", null);

	    var req = getXmlHttp();
	    var data = 'event=unload';
			data += '&supplier=rapida';
		req.open('GET', '/ServletTerminal/ActTerminal?' + data, true);
		req.onreadystatechange = function() { 

		        if (req.readyState == 4) {
		           
		            if(req.status == 200) {
		            	process();
		            	var lines = req.responseText.split('\r\n');		            	
		            	var data, buff = '';
		            	for(var i = 0, count = lines.length; i < count; i++){
		            		data = lines[i].split('_');
		            		if(data[1] != undefined)
		            		  buff += data[0] + ' : ' + data[1] + '\r\n'; 
		            	}  
		            	document.getElementById('id_unloadLog').innerText = buff;
		            }
		        }
		 }; 		 
		 req.send(null);
	}

	
	/**
	 * 25.11.2015
	 * РЕАЛИЗОВЫВАТЬСЯ НЕ ПЛАНИРУЕТСЯ
	 */
	function refreshParamsOfServicesRapida() {
		messageInfo("Даннй функционал находится в разработке", null);
		process();
	}
	
	
	/**
	 * version 15.11.30
	 * map - inputData
	 * ОПРЕДЕЛИТЬСЯ С ПАТТЕРНОМ РАЗБИЕНИЯ ОТВЕТА ;=
	 * @returns
	 */
	function getData(map){		
		
		var data = '';	    
	    var mapIter = map.entries();	    
	    var entrie = mapIter.next(); 	    
	    while(!entrie.done){
	    	if(data != '') data += '&';
	    	data += entrie.value[0] + '=' + entrie.value[1];
	    	entrie = mapIter.next();
	    }	    	   
	    if(data != ''){
      	  var req = getXmlHttp();
		  req.open('GET', '/CSFT/constructor?' + data, true);
		  req.onreadystatechange = function() { 

		        if (req.readyState == 4) {
		           
		            if(req.status == 200) {
		            	process();
		            	if(isError(getMapResponse(req.responseText, 'error')))
			        	  return;		
		            	
		            	return getMapResponse(req.responseText, ';=');		            	
		            }
		        }
		   }; 		 
		   process();
		   req.send();
	    }else {
	      messageInfo('Отсутствует тело запроса', null);
	      return;
	    }
	}
	