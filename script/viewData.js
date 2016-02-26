var listTables = {
		table_contentDictionaries : {value : '\'Содержимое справочника\'', name : 'contentDictionaries', code : '0'},	
	 	table_typeObject : {value : '\'Элементы формы\'', name : 'table_typeObject', code : '1'},
		table_dictionaries : {value : '\'Справочники\'', name : 'table_dictionaries', code : '2'},
		table_subServices : { value : '\'Подуслуги\'', name : 'table_subServices', code : '3'},
		table_roles : {value : '\'Роли\'', name : 'table_roles', code : '4'},
		table_commissionServices : {value : '\'Коммиссии по услугам\'', name : 'table_commissionServices', code : '5'},
		table_suppliers : {value : '\'Поставщики\'', name : 'table_suppliers', code : '6'},
		table_users : {value : '\'Пользователи\'', name : 'table_users', code : '7'},
		table_scripts : {value : '\'Скрипты\'', name : 'table_scripts', code : '8'},
		table_scriptsObject : {value : '\'Скрипты элемента\'', name : 'table_scriptsObject', code : '9'},
		table_services : {value : '\'Сервисы\'', name : 'table_services', code : '10'},
		table_terminals : {value : '\'Терминалы\'', name : 'table_terminals', code : '11'},
		table_commissions : {value : '\'Коммиссии\'', name : 'table_commissions', code : '12'},
		table_contentSubServices : {value : '\'Элементы подуслуги\'', name : 'table_contentSubServices', code : '13'},
		table_profileAccess : {value : '\'Права доступа\'', name : 'table_profileAccess', code : '14'},
		table_listTypes : {value : '\'Типы элементов\'', name : 'table_listTypes', code : '15'},
		table_contentGroupObjects : {value : '\'Группы объектов\'', name : 'table_contentGroupObjects', code : '16'},
		table_listGroupObjects : {value : '\'Группы сортировки\'', name : 'table_listGroupObjects', code : '17'},
		table_listClassService : {value : '\'Модули\'', name : 'table_listClassService', code : '18'},
		table_classServices : {value : '\'Связь модулей и услуг\'', name : 'table_classServices', code : '19'}
		
	};

	/*
	 * ОПРЕДЕЛИТЬ НАДОБНОСТЬ
	 */
	var elementId;
	/*
	 * Переменная использутся только для корректировки состояния  textarea
	 */
	
	/**
	 * 15.12.02 - modifited
	 * Отображение изменений в таблице
	 * @param tableName
	 * @param result
	 */
	function addRowInTable(tableName, result){
		var table_body = document.getElementById('table_body_' + tableName);
		var indexTR = table_body.getElementsByTagName('tr').length;
		var newTR = table_body.insertRow(indexTR);
		var resultAttr = result.split('&');
        var line = resultAttr[0].split(';');
        var newTD = document.createElement('td');
        newTR.appendChild(newTD);
        for(var i = 0, countLine = line.length; i < countLine; i++){	
          var atrLine = line[i].split('=');
                    
          if(resultAttr[1] != undefined && resultAttr[1].split('=')[1] == 'insert' ){
        	  /*Вставка строки при добавлении записи в таблицу*/
        	 if(i == 0){
        		 var newInput = document.createElement('input');
        		 newInput.setAttribute('id', tableName + "_" + atrLine[1]);
        		 newInput.setAttribute('name', 'ch');
        		 newInput.setAttribute('type', 'radio');
        		 newInput.setAttribute('style', 'float: left;');
        		 newInput.setAttribute('onchange', 'change(this.id, \'table\')');
        		 newTD.appendChild(newInput);
        		 newTD.setAttribute('id', 'ch_td_' + atrLine[1]);
        		 newTR.setAttribute('id', 'tr' + atrLine[1]);
        	 }
            newTD = document.createElement('td');
            newTD.style.background = '#98FB98';
            result ='';
          }else if(resultAttr[1] != undefined && resultAttr[1].split('=')[1] == 'update'){
        	/*Обновление строки при изменении записи в таблице*/
            newTD = document.createElement('td');
          	newTD.style.background = '#ffff00';  
          	result ='';
          }else{
        	  /*Формирование строки из выборки*/
	       	 if(i == 0){
	    		 var newInput = document.createElement('input');
	    		 newInput.setAttribute('id', tableName + "_" + atrLine[1]);
	    		 newInput.setAttribute('name', 'ch');
	    		 newInput.setAttribute('type', 'radio');
	    		 newInput.setAttribute('style', 'float: left;');
	    		 newInput.setAttribute('onchange', 'change(this.id, \'table\')');
	    		 newTD.appendChild(newInput);
	    		 newTD.setAttribute('id', 'ch_td_' + atrLine[1]);
	    		 newTR.setAttribute('id', 'tr' + atrLine[1]);

	    		 if(resultAttr.length % 2 == 0) newTR.setAttribute('class', 'c_table_tr2');
	    		 else newTR.setAttribute('class', 'c_table_tr');
	    	 }
	        newTD = document.createElement('td');
	   		if(resultAttr.length % 2 == 0) newTD.setAttribute('class', 'c_table_td2');
			else newTD.setAttribute('class', 'c_table_td');
	        
	        result = resultAttr.length == 1 ? '' : result.replace(resultAttr[0] + '&', '');
          }
          newTD.style.width = '120px';
          if(atrLine[0].contains(':')){
        	  var len0 = atrLine[0].length;
        	  var typeColumn = atrLine[0].substring(len0 - 3, len0);
        	  switch(true){
        	  	case typeColumn == '(c)' : {
        	  		var ch = '<input type="checkbox" ' + (atrLine[1] == 1 ? 'checked="checked"' : '""' ) + 'disabled="" value="' + atrLine[1] + '">';
        	  		newTD.innerHTML = '<span>' + ch + '</span>';
        	  		break;
        	  	}
        	  	case typeColumn == '(w)' : {
        	  		if(atrLine[1] != null && atrLine[1] != 'null'){
        	  			var len1 = atrLine[1].length;
        	  			if(len1 > 10 && !atrLine[1].contains(' ')) 
        	  				newTD.innerHTML = '<span>' + atrLine[1].replaceAll('.', '.\n') + '</span>';
        	  			else newTD.innerHTML = '<span>' + atrLine[1] + '</span>';	        	  			
        	  		}else newTD.innerHTML = '<span></span>';
        	  		break;
        	  	}
        	  	default : {
        	  		if(atrLine[1] != null && atrLine[1] != 'null'){
        	  			var len1 = atrLine[1].length;
        	  			if(len1 > 10 && !atrLine[1].contains(' ')) 
        	  				newTD.innerHTML = '<span>' + atrLine[1].replaceAll('.', '.\n') + '</span>';
        	  			else newTD.innerHTML = '<span>' + atrLine[1] + '</span>';	        	  			
        	  		}else newTD.innerHTML = '<span></span>';
        	  		//newTD.innerHTML = '<span>' + (atrLine[1] == null || atrLine[1] == 'null' ? '' : atrLine[1]) + '</span>';
        	  		break;
        	  	}
        	  }
        	  newTR.appendChild(newTD);
          }                                  
        }
        if(result != '')
        	addRowInTable(tableName, result);
	}
	
	/**
	 * Создание элемента
	 * @param nameTag
	 * @param parentElement
	 * @param idElement
	 * @param textElement
	 * @returns {___newChild21}
	 */
	function createElement(nameTag, parentElement, idElement, textElement){
		var newChild;
		switch(true) {
		  case nameTag == 'select' : {
			newChild = document.createElement(nameTag);
			newChild.className = 'c_select';
			newChild.id = idElement;
			newChild.name = idElement;
			parentElement.appendChild(newChild);
			break;
		  }
		  case nameTag == 'p' : {
			newChild = document.createElement(nameTag);
			newChild.id = idElement;
			newChild.name = idElement;
			newChild.innerText = textElement;
			parentElement.appendChild(newChild);
			break;
		  }
		  case nameTag == 'img' : {
			newChild = document.createElement(nameTag);
			newChild.id = idElement;
			newChild.src = textElement;
			parentElement.appendChild(newChild);
			if(idElement =='trSave'){
				  newChild.setAttribute('onclick', 'save(), removeMessage()');
				  newChild.setAttribute('onmouseover', 'mouseOver(this.id, null)');
				  newChild.setAttribute('onmouseout', 'mouseOut(this.id)');			  
			}
			break;
		  }
		  case nameTag == 'table' : {
			  newChild = document.createElement(nameTag);
			  newChild.id = idElement;
			  parentElement.appendChild(newChild);
			  break;
		  }
		  case nameTag == 'div' : {
			  newChild = document.createElement(nameTag);
			  newChild.id = idElement;
			  parentElement.appendChild(newChild);
			  break;			  
		  }
		  case nameTag == 'textarea' : {
			  newChild = document.createElement(nameTag);
			  newChild.id = idElement;
			  newChild.textContent = textElement;
			  newChild.setAttribute('ondblclick', 'mouseDoubleClick(this)');
			  newChild.setAttribute('readonly','readonly');
			  
			  parentElement.appendChild(newChild);
			  break;			  
		  }
		  case nameTag == 'input' : {			  
			  newChild = document.createElement(nameTag);
			  if(idElement != null )
			    newChild.id = idElement;
			  var attr = textElement.split(':');
			  if(attr[0] != null)
			    newChild.setAttribute('type', attr[0]);
			  if(attr[1] != null)
				newChild.setAttribute('value', attr[1]);
			  parentElement.appendChild(newChild);
			  break;
		  }
		}
		return newChild; 
	}
	
	/**
	 * Скрытие элементов горизонтального меню
	 */	
	function hiddenItemsHM(){
		var hm = document.getElementById('id_HM');
		var itemsHM = hm.childNodes;
		for(var i = 1, count = itemsHM.length; i < count; i++ ){
		  if(itemsHM[i].style.display == 'none')
			itemsHM[i].style.display = '';
		  else itemsHM[i].style.display = 'none';
		}
	}

	
	/**
	 * Отображение информационного сообщения
	 * @param message
	 * @param element - НЕ ДОЛЖЕН ПЕРЕДАВАТЬСЯ
	 */
	function messageInfo(message, element){
		element = document.getElementById('c_context');
		var delElement = document.getElementById('messageInfo');
		if(delElement != null)
		  delElement.parentNode.removeChild(delElement);
		
		var newDiv = document.createElement('div');
		newDiv.id = 'messageInfo';
		newDiv.className = 'c_messageInfo';
		var newChild = document.createElement('p');			
		newChild.style.position = 'inherit';
		newChild.innerHTML = '<font color="red" ><h2>' + message + '</h2></font>';
		newDiv.appendChild(newChild);
		//element.insertBefore(newDiv, element.childNodes[0]);
		element.insertBefore(newDiv, element.childNodes[0]);
		
	}		
	/**
	 * @param errors - Map информационных сообщений полученных от сервера
	 */
	function viewBlockInfo(errors){
		var messages = '';
		for(var key in errors){
		  if(key != undefined && key != '')
			  messages += errors[key];
		}
		messageInfo(messages);
	}
		
	function onLoad(){
		
		var div = document.getElementById('c_table_body');
		var table =  (div != null) ? div.childNodes[0] : null;
		if(table != null){
			if(table.offsetHeight > div.offsetHeight){
				var styleDiv = div.getAttribute('style');
				styleDiv += 'overflow-y: scroll;';
				div.setAttribute('style', styleDiv);
				
				var styleTable = table.getAttribute('style');
				styleTable += 'padding-left: 1.6%;';
				table.setAttribute('style', styleTable);		
					
			}
		}	
	}
	
	
	/**
	 * Отображение дерава
	 * @param event
	 */
	function tree_toggle(event) {
		    event = event || window.event;
		    var clickedElem = event.target || event.srcElement;
		    if (!hasClass(clickedElem, 'Expand')) {
		        return;// клик не там
		    }
		    // Node, на который кликнули
		    var node = clickedElem.parentNode;
		    if (hasClass(node, 'ExpandLeaf')) {
		        return; // клик на листе
		    }
		    // определить новый класс для узла
		    var newClass = hasClass(node, 'ExpandOpen') ? 'ExpandClosed' : 'ExpandOpen';
		    // заменить текущий класс на newClass
		    // регексп находит отдельно стоящий open|close и меняет на newClass
		    var re =  /(^|\s)(ExpandOpen|ExpandClosed)(\s|$)/;
		    node.className = node.className.replace(re, '$1'+newClass+'$3');
		}
	
	function viewInfoTable(listTR){
		
		/*
		 * insert or update 
		 */
		var isHeader = false;
		var event = listTR[listTR.length-1].split('=')[1];
		
		var table = document.getElementById('infoTable');
		if(table == null){
		  table = createElement('table', document.getElementById('infoDiv'), 'infoTable', null);		  
		}else isHeader = true;
		
		document.getElementById('infoDiv').appendChild(table);
		var newTR; var newTD; var listTD;		
		
		for(var i = 0; i < listTR.length - 1; i++){

		  listTD = listTR[i].split(';');
		  
		 /*
		  * Отрисовка шапки таблицы		  * 
		  */
		  if(i == 0 && isHeader == false){
		    newTR = document.createElement('tr'); 
		    table.appendChild(newTR);
		    for(var j = 0, countTD = listTD.length;  j < countTD; j++ ){
		    	newTD = document.createElement('td');
		    	newTD.setAttribute('style', 'background: #b5cbe6');
		          newTD.innerText = listTD[j].split('=')[0];
		          newTR.appendChild(newTD);
		    }
		  }
		  newTR = document.createElement('tr'); 
		  table.appendChild(newTR);		 
		  
		 /*
		  * Отрисовка тела таблицы
		  */
		  for(var j = 0, countTD = listTD.length;  j < countTD; j++ ){
	          newTD = document.createElement('td'); 
	          event == 'insert' ? newTD.setAttribute('style', 'background: #98FB98')
	        		  			: newTD.setAttribute('style', 'background: #ffff00');
	          newTD.innerText = listTD[j].split('=')[1];
	          newTR.appendChild(newTD);  
		  }		  
		}		
	}	
	
/**
 * =========================================================================
 * Реализация фильстрации в таблицах
 * ========================================================================
 */
	
	/**
	 * version 15.11.23
	 * ПОСЛЕДНЯЯ ВЕРСИЯ ФИЛЬТРАЦИИ ДАННЫХ С ОБРАЩЕНИЕМ К БД
	 * 
	 * ДОБАВИТЬ ОБРАБОТКУ
	 */	
	function execute_filter(tableName, extraOptions){
		
		/*
		 * Добавить обработку получения данных вне таблицы и дозаписывать условие выполнения ффильта
		 */
		
		/*
		 * Используется для фильтрации по данным взятым ТОЛЬКО из таблицы по которой производится поиск
		 */
		viewRecordsOfTable(tableName, extraOptions);
	}
	
	
	/**
	 *  Состояние использования фильтра. По умолчанию false
	 *  Переходит в состояие false при скрытии текущей страницы таблицы.
	 *  
	 */
	var isUsedFilter = false;
	/**
	 * Хранение нескольких параметров фильтрации
	 */
	var mutliFilterMap;
	
	/**
	 * Очищает mutliFilterMa, если 
	 * поддержка нескольких фильтров не включена
	 */
	function clearMutliFilterMap(){
	  if(!isMultiFilter() || mutliFilterMap == undefined)
	    mutliFilterMap = new Object(); 
	}
	/**
	 * Проверяет включена ли поддержка нескольких фильтров
	 */
	 function isMultiFilter(){
		 var isMutliFilter = document.getElementById('isMutliFilter');
		 return isMutliFilter == undefined ? false : isMutliFilter.value =='true' ? true : false;
	 }
	/**
	 * @param tableName - талица, в которой производится поиск
	 * @param columnName - колонка по которой производится поиск
	 * @param value -  значение фильтра
	 * Новая реализация фильтров таблицы. ИСПОЛЬЗОВАТЬ!
	 * СТАРОЕ НАЗВАНИЕ hidenRowNEW
	 */
	function showRecordsByColumn(tableName, columnName, value){

	  isUsedFilter = true;
	  setArrShowIDs(tableName, null);
	  clearMutliFilterMap();
	  if(value != '' && value != null){
		var headerTable = document.getElementById('table_header_' + tableName);		
		var headerTDs = headerTable.getElementsByTagName('td');
		
		var body_table = headerTable.parentNode.childNodes[1];
		var isNext = true;		
		headerTDs: for(var i = 0 , count = headerTDs.length; i < count; i++){
			if(headerTDs[i]['id'] == columnName){
			    mutliFilterMap[i] = value;
				var body_table = document.getElementById('table_body_' + tableName);
				var bodyTRs = body_table.getElementsByTagName('tr');								
				bodyTRs: for(var j = 0, countBodyTR = bodyTRs.length; j < countBodyTR; j++){
					if(bodyTRs[j].childNodes[i].textContent == value){
						if(bodyTRs[j].style.display != 'none'){
		 				  bodyTRs[j].style.display = 'table-row';
		 				 addArrShowIDs(tableName, bodyTRs[j].id);
						}else{
						  if(isMultiFilter()){
						    var b = true;
						    multiFilterM: for(var key in mutliFilterMap){
							  if(!(bodyTRs[j].childNodes[key].textContent == mutliFilterMap[key])){
							    b = false;
							  	break multiFilterM;
							  }
							}
							if(b){
							 bodyTRs[j].style.display = 'table-row';
							 addArrShowIDs(tableName, bodyTRs[j].id);
							} 							
						  }else{
							bodyTRs[j].style.display = 'table-row';
							addArrShowIDs(tableName, bodyTRs[j].id);
						  }
						}						
					  isNext = false;
					}else{
					  bodyTRs[j].style.display = "none";
					}
				}
				if(!isNext)
				  break headerTDs;
			}			
		}
	  }
	}
	
	/**
	 * @param obj
	 * @param indx
	 * @param state
	 * Используется  для реализации функций : 
	 * 			1. фильстры
	 * 			2. Видимость столбцов
	 * НЕ ИСПОЛЬЗОВАТЬ в новом функционале
	 * РЕАНИМИРОВАНО В НОВОЙ ЛОГИКЕ РАБОТЫ ФИЛЬТРОВ ОТ 15.11.24
	 */
	function hidenRow_OLD(obj, indx) {		

		
		var trs = document.getElementsByTagName('tr');
		var countTR = trs.length;
		var countTD = trs[0].getElementsByTagName("td").length;
		
		if(obj.value != ""){
		  for(var i = 2; i < countTR; i++){
		    var tds = trs[i].getElementsByTagName("td");
		    if (!~tds[+indx].innerHTML.indexOf(obj.value)){
			  trs[i].style.display = "none";
			}else{
			  trs[i].style.display = "table-row";
			  tds[indx].style.background = "#00FF00";
			}
		  }
		}else{
		  for(var i2 = 2; i2 < countTR; i2++){
		    var tds = trs[i2].getElementsByTagName("td");
    	    trs[i2].style.display = "table-row";
		    for(var j = 1; j < countTD; j++){
		      if(i2%2 != 0){
		    	tds[j].style.background = "";
		    	tds[j].className = "c_table_td2";
		      }else{
		    	tds[j].style.background = "";
			   	tds[j].className = "c_table_td";
		      }
			}			 
		  }			  
		}				
	}
		
	/**
	 * version 15.11.24
	 * @param tableName
	 * @param obj
	 * @param indx
	 */
	function hidenRow(obj, indx, tableName) {		

		var table = document.getElementById('table_body_' + tableName);
		var trs = table.getElementsByTagName('tr');
		var countTR = trs.length;
		var countTD = trs[0].getElementsByTagName('td').length;
		
		if(obj.value != ''){
		  for(var i = 0; i < countTR; i++){
		    var tds = trs[i].getElementsByTagName('td');
		    if (!~tds[+indx].innerHTML.indexOf(obj.value)){
			  trs[i].style.display = 'none';
			}else{
			  trs[i].style.display = 'table-row';
			  tds[indx].style.background = '#00FF00';
			}
		  }
		}else{
		  for(var i = 0; i < countTR; i++){
		    var tds = trs[i].getElementsByTagName('td');
    	    trs[i].style.display = 'table-row';
		    for(var j = 1; j < countTD; j++){
		      if(i%2 != 0){
		    	tds[j].style.background = '';
		    	tds[j].className = 'c_table_td2';
		      }else{
		    	tds[j].style.background = '';
			   	tds[j].className = 'c_table_td';
		      }
			}			 
		  }			  
		}				
	}

	/**
	 * 	 * Скрытие текущего содержимого таблицы
	 * @param tableName
	 * @param isfilter
	 * @param isNext - true - переход осуществляется на следующую страницу
	 * @returns {Boolean}
	 */

	function currentHiddenRow(tableName, isfilter, isNext){
		
		if(Boolean(isfilter)){
		  isUsedFilter = false;
		  var ids = arrShowIDs[tableName].split('|');
		  var idsLen = ids.length;
		  for(var i = 0; i < idsLen; i++)
		    if(document.getElementById(ids[i]))
		      document.getElementById(ids[i]).style.display = 'none';
		  /*
		   * Возвращаемся на текущую страницу
		   */
		  current(tableName);
		  return false;
		}else{
			
			if(arrShowIDs == null){
				arrShowIDs = new Object();
				
			  var trs = document.getElementById('table_body_' + tableName).getElementsByTagName('tr');
			  var trsLen = trs.length;
			    if(trsLen != 10){
				  messageInfo('Вы находитель на последней странице');
				  removeMessage();
				  return false;	
				}/*(else{
				  for(var i = 0; i < trsLen; i++)
				  	document.getElementById(trs[i]).style.display = 'none';
				}*/
				
			}else{
			  if(arrShowIDs[tableName] != undefined){
				/*
				 * Проверка наличия следующей страницы записей
				 * ВАЖНО! Не обработана ситуация, когда  
				 * не обработана ситуация когда обще количество 
				 * записей  кратно 10
				 */
				var ids = arrShowIDs[tableName].split('|');
				var idsLen = ids.length;
				if(Boolean(isNext)){
				    if(idsLen != 10){
					  messageInfo('Вы находитель на последней странице');
					  removeMessage();
					  return false;	
					}else{
					  for(var i = 0; i < idsLen; i++)
					  	document.getElementById(ids[i]).style.display = 'none';
					}					
				}else{
				  for(var i = 0; i < idsLen; i++)
					 document.getElementById(ids[i]).style.display = 'none';
				}
			  }else{
			    messageInfo('Вы находитель на последней странице');
			    removeMessage();
			    return false;
			  }
			}
		}
		return true;		
	}
	
/**
 * =========================================================================
 * Функции работы с постраничным переходом в таблицах
 * ========================================================================
 */
	/**
	 * Объект хранящий идентификаторы записей по таблцам
	 */
	var arrTRsForPage;
	
	/**
	 * Хранение идентификаторов текущего отображения
	 */
	var arrShowIDs;
	
	/**
	 *  Хранение типов полей таблицы
	 */
	var arrTypeFieldTable;
	
	/**
	 * добавление сформированного массива ID или обнуление
	 * @param tableName
	 * @param ids
	 */
	function setArrShowIDs(tableName, ids){
		
		if(tableName == null)
			return;
		if(arrShowIDs == undefined)
			arrShowIDs = new Object();
		if(ids == null)	arrShowIDs[tableName] = '';
		else arrShowIDs[tableName] = ids;
	}
	/**
	 * Добавление нового ID к существующим
	 * ВАЖНО! Перед формирвоаием нового массива 
	 * необходимо зачищать вызовом setArrShowIDs([ИМЯ ТАБЛИЦЫ], null) 
	 * @param tableName
	 * @param ids
	 */
	function addArrShowIDs(tableName, id){
		
		if(arrShowIDs == undefined)
			arrShowIDs = new Object();
		if(tableName == null || id == null)
			return;
		if(tableName == '' || id == '')
			return;
		arrShowIDs[tableName] += ((arrShowIDs[tableName] != undefined) &&
								  (arrShowIDs[tableName] != '')) ? '|' + id : id;
	}

	/**
	 * version 15.11.20
	 * Отображение предыдущей страницы
	 * @param tableName
	 */
	function execute_pre_page(tableName){
	
		var numberPage = Number(document.getElementById('numberPage_' + tableName).getAttribute('value'));
		
		if(numberPage > 1)
		  viewTenRecordsOfTable(tableName, false);
		else{
		  messageInfo("Вы находитесь на первой странице");
		  removeMessage();
		}
	}
	
	/**
	 * !!!ВЫВОДИТСЯ ИЗ ИСПОЛЬЗОВАНИЯ!!!
	 * @param tableName
	 */
	function pre_OLD(tableName){			
		
		var numberPage = Number(document.getElementById('numberPage_' + tableName).getAttribute('value'));
		
		/*
		 * Проверка наличия предыдущих страниц
		 */		
		if(numberPage > 1){
			
			/*
			 * Проверяем наличие следующей страницы.
			 * Если есть данные для отображениия то скрывает текущии записи
			 */
			if(!Boolean(currentHiddenRow(tableName, isUsedFilter, false)))
				return;
			
			/*
			 * Отображеие ноера страницы, на которую осуществляется переход
			 */	
			document.getElementById('numberPage_' + tableName).setAttribute('value', numberPage - 1);
			
			/*
			 * Объекты для скрытия
			 */
			var hiddenIDsTRs = arrTRsForPage[tableName + '_' + (numberPage)];
			/*
			 * Объекты для отображения
			 */
			var showIDsTRs= arrTRsForPage[tableName + '_' + (numberPage - 1)].split('|');
			if(hiddenIDsTRs == undefined){
				var indexTR;
				var table_body = document.getElementById('table_body_' + tableName);
				var trs = table_body.getElementsByTagName('tr');
				hiddenIDsTRs = '';
				/*
				 * Отображение записей предыдущей страницы
				 */
				setArrShowIDs(tableName, null);
				for(var i = 0; i < 10; i++){
				  document.getElementById(showIDsTRs[i]).style.display = 'table-row';
					/*
					 * Сохранение ID текущего отображения
					 */
				  addArrShowIDs(tableName, showIDsTRs[i]);
				  
				  indexTR = i + (10 * (numberPage - 1));
				  /*
				   * Сохранение состояния текущей страницы таблицы
				   */
				  if(trs[indexTR] != undefined){
				    hiddenIDsTRs += (indexTR % 10) == 9 ? trs[indexTR].id : trs[indexTR].id + '|';  
				  }
				}				
				hiddenIDsTRs = (hiddenIDsTRs.length != 10) ? hiddenIDsTRs.substring(0, hiddenIDsTRs.length - 1) : hiddenIDsTRs;
				arrTRsForPage[tableName + '_' + numberPage] = hiddenIDsTRs.substring(0, hiddenIDsTRs.length);				
			}else{
				//hiddenIDsTRs = hiddenIDsTRs.split('|');	
				setArrShowIDs(tableName, null);
				for(var i = 0; i < 10; i++){
				  document.getElementById(showIDsTRs[i]).style.display = 'table-row';
				  addArrShowIDs(tableName, showIDsTRs[i]);
				}
			}
		}else{
			messageInfo("Вы находитесь на первой странице");
			removeMessage();
		}
	}
	/**
	 * version 15.11.20
	 * Отображение следующей страницы
	 * @param tableName
	 */
	function execute_next_page(tableName){
		viewTenRecordsOfTable(tableName, true);
	}
	/**
	 * !!!ВЫВОДИТЬ ИЗ ЭКСПЛУАТАЦИИ!!!
	 * @param tableName
	 */
	function next_OLD(tableName){

		/*
		 * Хранение состояний контента таблицы
		 *  добавить наименование таблицы
		 */
		//if(arrTRsForPage == null)
		//	arrTRsForPage = new Object();

		/*
		 * Проверяем наличие следующей страницы.
		 * Если есть данные для отображениия то скрывает текущии записи
		 */
		//if(!Boolean(currentHiddenRow(tableName, isUsedFilter, true)))
		//	return;

		//var table_body = document.getElementById('table_body_' + tableName);
		
		//var hiddenIDsTRs;
		/*
		 * Отображеие ноера страницы, на которую осуществляется переход
		 */	
		//var numberPage = Number(document.getElementById('numberPage_' + tableName).getAttribute('value'));
		//document.getElementById('numberPage_' + tableName).setAttribute('value', numberPage + 1);
				 
		//var showIDsTRs = arrTRsForPage[tableName + '_' + (numberPage + 1)];
		
		/*
		 * Проверка отрисовки следующей страницы ранее
		 */
	/*	if(showIDsTRs == undefined){
			hiddenIDsTRs = '';*/
			/*
			 * Получаем контент не отрисованных данных
			 */			
			/*var data = document.getElementById('buffData_' + tableName).textContent;
			var buffData = data.split('&');
			var trs = table_body.getElementsByTagName('tr');
			var trsLen = trs.length;
			var indexTR;
			setArrShowIDs(tableName, null);
			for(var i = 0; i < 10; i++){*/
				
				/*
				 * Скрытие текущего контента
				 */
			/*	if(numberPage == 1){
				  trs[i].style.display = 'none';
				  hiddenIDsTRs += (i % trsLen) == (trsLen - 1) ? trs[i].id : trs[i].id + '|';
				}else{
				  indexTR = i + (10 * (numberPage - 1));
				  if(trs[indexTR] != undefined){
				    trs[indexTR].style.display = 'none';
				    hiddenIDsTRs += (indexTR % trsLen) == (trsLen - 1) ? trs[indexTR].id : trs[indexTR].id + '|';  
				  }
				}
				if (buffData[i] != undefined) {*/

				  /*
				   * Добавляем ID в Список отображаемых идентификаторов
				   */
				/*  addArrShowIDs(tableName, nextBodyTable(table_body, buffData[i], i + 1).id);
				  data = data.replace(buffData[i] + '&', '');
				}
			}	*/		
			/*
			 * Сохранение предыдущих идентификаторов
			 */
			//arrTRsForPage[tableName + '_' + numberPage] = hiddenIDsTRs;
			
			/*
			 * пересохранение контента необработанных данных
			 */
		/*	document.getElementById('buffData_' + tableName).textContent = data;					
		}else{
		  //hiddenIDsTRs = arrTRsForPage[tableName + '_' + (numberPage)].split('|');
		  showIDsTRs = showIDsTRs.split('|');
		  setArrShowIDs(tableName, null);
		  for(var i = 0; i < 10; i++)
			if(showIDsTRs[i] != undefined){
			  document.getElementById(showIDsTRs[i]).style.display = 'table-row';
			  addArrShowIDs(tableName, showIDsTRs[i]);
			}
		}*/
	}	
	
	/**
	 * !!!ВЫВОДИТЬ ИЗ ЭКСПЛУАТАЦИИ!!!
	 * @param tableName
	 */
	function current(tableName){
		
		/*
		 * Номер страницы на которую будет осуществлен возврат состояния
		 */
		var numberPage = Number(document.getElementById('numberPage_' + tableName).getAttribute('value'));
		/*
		 * Содержимое для отображения
		 */
		var showIDsTRS = arrTRsForPage[tableName + '_' + numberPage];
		/*
		 * Если содержимое еще не было сохарнено, то 
		 * контент страницы осуществляется по индексиции TR
		 */
		if(showIDsTRS != undefined){
		  showIDsTRS = showIDsTRS.split('|');
		  setArrShowIDs(tableName, null);
		  for(var i = 0; i < showIDsTRS.length; i++)
			if(showIDsTRS[i] != undefined){
			  document.getElementById(showIDsTRS[i]).style.display = 'table-row';
			  addArrShowIDs(tableName, showIDsTRS[i]);
			}
		}else{
		  var trs = document.getElementById('table_body' + tableName).getElementsByTagName('tr');
		  var inexTR;
		  setArrShowIDs(tableName, null);
		  for(var i = 0; i < 10; i++){
		    inexTR = (numberPage - 1) * 10 + i;
		    if(trs[inexTR] != undefined){
			  trs[inexTR].style.display = 'table-row';
			  addArrShowIDs(tableName, trs[inexTR].id);	
		    }
		  }	
		}		
	}
	
	/**
	 * !!!ВЫВОДИТЬ ИЗ ЭКСПЛУАТАЦИИ!!!
	 * отрисовка новой страницы
	 * @param table_body
	 * @param line
	 * @param indexTD
	 */
	function nextBodyTable(table_body, line, indexTD){		
		
		var tableName = table_body.getAttribute('id').split('_')[2];
		
		/*
		 * Массив дополнительных элементов 
		 * при построении содержимого яцейки
		 */
		var other = new Object();
		var typeField;
		
		/*
		 * Определение типов столбцов таблицы
		 * данные хранятся в arrTypeFieldTable
		 */
		addTypeFieldOfTable(tableName);
		
		var data = line.split('|');
		var newTR = document.createElement('tr');
		newTR.setAttribute('id', 'tr' + data[0]);		
		if((indexTD % 2) == 0) newTR.setAttribute('class', 'c_table_tr');
		else newTR.setAttribute('class', 'c_table_tr2');		
		newTR.setAttribute('onmouseover', 'mouseOver(this.id, \'tr\')');
		newTR.setAttribute('onmouseout', 'mouseOut(this.id, \'tr\')');

		var newTD;
		for(var i = 0, countTD = data.length; i < countTD; i++ ){

			/*
			 * Первый столбец с чекбоксом
			 */
			if(i == 0){
				newTD = document.createElement('td');
				newTD.setAttribute('class', 'c_table_ch')
	       		 var newInput = document.createElement('input');
	    		 newInput.setAttribute('id', tableName + "_" + data[i]);
	    		 newInput.setAttribute('name', 'ch');
	    		 newInput.setAttribute('type', 'radio');
	    		 newInput.setAttribute('style', 'float: left;');
	    		 newInput.setAttribute('onchange', 'change(this.id, \'table\')');
	    		 newTD.appendChild(newInput);
	    		 newTD.setAttribute('id', 'ch_td_' + indexTD);
	    		 newTR.appendChild(newTD);
			} 			
			newTD = document.createElement('td');
			newTD.setAttribute('id', 'td_' + indexTD + '_' + i);				
			if((indexTD % 2) == 0) newTD.setAttribute('class', 'c_table_td');
			else newTD.setAttribute('class', 'c_table_td2');
			
			/*
			 * Определение типа поля
			 */			
			//alert(typeField);
			typeField = arrTypeFieldTable[tableName + (i + 1)];
			other['name'] = 'td' + indexTD + i;
			newTD.appendChild(contentCellOfTable(typeField, data[i], other));
			newTR.appendChild(newTD);			
		}
		table_body.childNodes[0].appendChild(newTR);
		return newTR;
	}
	
	/**
	 * !!!ВЫВОДИТЬ ИЗ ЭКСПЛУАТАЦИИ!!!
	 *  отрисовка всех данных в таблице
	 * @param table_body
	 */
	function allBodyTable(tableName){

		/*
		 * Инициализация объекта хранящего идентификаторы записей
		 */
		 if(arrTRsForPage == null)
			arrTRsForPage = new Object();
		 
		var table_body = document.getElementById('table_body_' + tableName);
		/*
		 * Получаем контент не отрисованных данных
		 */			
		 var data = document.getElementById('buffData_' + tableName).textContent;

		 /*
		  * Проверка наличия не обработанных данных
		  */
		 if(data != ''){
			 var buffData, buffLen = 0;

			 hiddenIDsTRs = '';
			 var numberPage = Number(document.getElementById('numberPage_' + tableName).getAttribute('value'));
			 var trs = table_body.getElementsByTagName('tr');
			 /*
			  * Сохранение видимого контента
			  */
			 var trsLen = trs.length;
			 for(var i = 0; i < trsLen; i++){
				 if(trs[i].style.display == 'table-row' || trs[i].style.display == '')
				   hiddenIDsTRs += (i % trsLen) == (trsLen - 1) ? trs[i].id : trs[i].id + '|';
			 }
				 
			 arrTRsForPage[tableName + '_' + numberPage] = hiddenIDsTRs;
			 numberPage++;
			 /*
			  * Сохранение не видимого контента
			  */
			 while(data != ''){
				 buffData = data.split('&',10);
				 buffLen = buffData.length;
				 hiddenIDsTRs = '';
				 for(var i = 0; i < buffLen; i++){
				   
				   tr = nextBodyTable(table_body, buffData[i], i + 1);
				   tr.style.display = 'none';
				   hiddenIDsTRs += (i % buffLen) == (buffLen - 1) ? tr.id : tr.id + '|';
				   
				   /*
				    * Если последний элемент блока, то проверяется наличие спецвставки |r
				    * В конце последнего ассива она не стоит
				    */
				   data = (i == (buffLen - 1)) 
				   		? ((data.contains(buffData[i] + '&')) ? (data.replace(buffData[i] + '&', '')) 
				   											   : (data.replace(buffData[i], ''))) 
				   		: (data.replace(buffData[i] + '&', '')); 		   				   
				 }
				arrTRsForPage[tableName + '_' + numberPage] = hiddenIDsTRs;
				document.getElementById('buffData_' + tableName).textContent = data;
				numberPage++;
			 }			 
		 }
	}
	
	
	/**
	 * Доступные типы полей
	 * Для расширения необходимо переопределять
	 * представления таблиц в БД
	 * i - автоинкрементное поле
	 * ц - поле доступное для ввода любой последовательности символов
	 * l-  справочник выбора
	 * c - флаг 
	 */
	var listAccessType = ['i', 'w', 'l', 'c', 'nonType'];
	
	/**
	 * Сохранение типов полей таблицы
	 * @param tableName
	 */
	function addTypeFieldOfTable(tableName){
		
		if(arrTypeFieldTable == undefined)
			arrTypeFieldTable = new Object();
		
		if(arrTypeFieldTable[tableName + 1] != undefined)
			return;
		
		/*
		 * массив TDпредставлен одинм рядом, 
		 * т.к. таблица содержит только шапку
		 */
		//var tds = document.getElementById('table_' + tableName).getElementsByTagName('tr')[0].getElementsByTagName('td');
		var tds = document.getElementById('table_header_' + tableName).getElementsByTagName('td');
		var buff, lenBuff, data ='';
		for(var i = 0, lenTd = tds.length; i < lenTd; i++){
			
			buff = tds[i].innerText;			
			if(buff != ''){
			  lenBuff = buff.length; 
			  buff = buff.substring(lenBuff - 3, lenBuff);

			  if(listAccessType.contains(buff[1])){
				/*
				 * Добавление типа
				 */
			     arrTypeFieldTable[tableName + i] = buff[1];
			  }else{
				/*
				 *  Поле без типа (информационное в дальнейшем реализовать скрытие)
				 */
				arrTypeFieldTable[tableName + i] = 'nonType';  
			  }
			}
		}
	}
	
	
	/**
	 * 
	 * @param typeField - тип поля
	 * @param data - данные для отображения
	 * @param other - опциональный парамтр, type - map
	 * @returns объект
	 */
	function contentCellOfTable(typeField, data, other){
		
		var span;
		switch(true){
			/*
			 * 'i' 
			 */
		  case listAccessType[0] == typeField:{
			 span = document.createElement('span');
			 span.innerText = data;
			 return span;
		  }
		  /*
		   * 'w' 
		   */
		  case listAccessType[1] == typeField:{
			  
			 span = document.createElement('span');
			 if((data.length > 25 && data.contains(" ")) || 
				(data.length > 10 && !data.contains(" "))){				
				 
				 var textarea = document.createElement('textarea');
				  textarea.setAttribute('name', other[name]);
				  textarea.setAttribute('readonly', 'readonly');
				  textarea.setAttribute('class', 'c_table_textarea');
				  textarea.setAttribute('ondblclick', 'mouseDoubleClick(this)');
				  textarea.innerText = data;
				  span.appendChild(textarea);				 
			 }else{
				 span = document.createElement('span');
				 span.innerText = data;			 
			 }			 
			 return span;
		  }
		  /*
		   * 'l'
		   */
		  case listAccessType[2] == typeField:{

			 span = document.createElement('span');
			 if((data.length > 25 && data.contains(" ")) || 
				(data.length > 10 && !data.contains(" "))){				
				 
				 var textarea = document.createElement('textarea');
				  textarea.setAttribute('name', other[name]);
				  textarea.setAttribute('readonly', 'readonly');
				  textarea.setAttribute('class', 'c_table_textarea');
				  textarea.setAttribute('ondblclick', 'mouseDoubleClick(this)');
				  textarea.innerText = data;
				  span.appendChild(textarea);
				 
			 }else{
				 span = document.createElement('span');
				 span.innerText = data;			 
			 }			 
			 return span;
		  }
		  /*
		   * 'c'
		   */
		  case listAccessType[3] == typeField:{
			  
			 span = document.createElement('span');
			 
			 var input = document.createElement('input');
			 input.setAttribute('type', 'checkbox');
			 input.setAttribute('type', 'checkbox');
			 
			 if(data == '1')
				input.setAttribute('checked', 'checked');
				
			 input.setAttribute('value', data);
			 input.setAttribute('disabled', 'disabled');
			 span.appendChild(input);
			 
			 return span;
		  }
		  case listAccessType[4] == typeField : {
			 span = document.createElement('span');
			 span.innerText = data;
			 return span;  
		  }
		  default :{
			  messageInfo('Передан не корректный тип поля - ' + typeField + '.\r\n Обратитесь к администратору', null);
			  break;
		  }
		}
	}
	
	/**
	 * version 15.11.20 - add
	 * version 15.12.02 - moditited
	 * Обращение к серверу для постраничного запроса данных таблицы
	 * @param tableName
	 * @param isNext
	 */	
	function viewTenRecordsOfTable(tableName, isNext){

		var numberPage = Number(document.getElementById('numberPage_' + tableName).getAttribute('value'));					
		var newNumberPage = isNext ? numberPage + 1 : numberPage - 1;
	    var req = getXmlHttp();
	    
		 var data = "typeEvent=select";
		 data += "&table="+ tableName;
		 data += "&system_numberPage=" + newNumberPage;
		 req.open('GET', '/CSFT/constructor?' + data, true);

		req.onreadystatechange = function() { 

	        if (req.readyState == 4) {
	           
	            if(req.status == 200) {
	            	process();
	            	removeTRs(tableName);	            		            		
	        		document.getElementById('numberPage_' + tableName).setAttribute('value', newNumberPage);  			        		
	        		addRowInTable(tableName, req.responseText);
	            }
	        }
		 };
		 process(); 
		 req.send(); 

	}

	/**
	 * version 15.11.23
	 * version 15.12.01 - moditited
	 *  Возвращает весь набор данных по заданным критериям
	 * @param tableName
	 */
	function viewRecordsOfTable(tableName, extraOptions){
		
		var table = document.getElementById('table_' + tableName);
		var inputs = table.getElementsByTagName('input');	

	    var req = getXmlHttp();
	    
		 var data = "typeEvent=filter";
		 data += "&table="+ tableName;
		 var filters = '';
		 
		 for(var i=0, ii=inputs.length;i<ii;i++ ){
		  if(inputs[i].value != '')
		    filters += "&" + inputs[i].id + ':' + inputs[i].placeholder + '=' + inputs[i].value;  		  
		 }
		 
		 /*
		  * Параметр обязательно должен начинаться с 'filter_'
		  * иначе не будет добавлен при формирвоании условия
		  */
		 if(extraOptions != undefined)
			 filters += "&" + extraOptions;
		 
		 if(filters == '') return;
		 data += filters;

		 req.open('GET', '/CSFT/constructor?' + data, true);
		 process();
	    
		req.onreadystatechange = function() { 

	        if (req.readyState == 4) {
	           
	            if(req.status == 200) {	
	            	process();		        		
	        		addRowInTable(tableName, req.responseText);
	            }
	        }
		 };
		 
		 req.send(); 

	}	
	
/**
 * =========================================================================
 * Реализация ФОРМ
 * ======================================================================== 
*/
	
	
	/**
	 * Отрисовка формы добавления.
	 * Версия 0.2 (до 27.03.15)
	 */
	function createFormAdd(typeElement, tableId){
		  var tableName = tableId.split('_')[1];
		  typeElement:switch(true){
		  
		  case typeElement == 'table' : {
			    //hiddenItemsHM();
		    	var listObjectsTagSelect = new Array();
		    	var inddexTagSelect = 0;
				var idParent;
				var addIdParent;
				switch(true){			
					case tableName == 'contentDictionaries' : {
						var t = document.getElementById('add_' + tableName).getAttribute('value');
						if(t != null){
							delElement = document.getElementById('messageInfo');
							if(delElement != null) 
							  remove(delElement);
							idParent = t.split('_')[2];
							addIdParent = '<input type=\"hidden\" id=\"idParent\" name=\"id\" value=\"' + idParent + '\">';	
						}else{
							messageInfo('Не выбран справочник для добавления записи ', document.getElementById('formTable_' + tableName));
							break typeElement;							
						}
						break;
					}
					default : {
						addIdParent = '';
						break;
					}
				}
				var table = document.getElementById(tableId);
				var col;
				var parentNode = table.parentNode.parentNode; // УБРАТЬ

				var newDiv = document.createElement('div');
				newDiv.className = 'c_form';
				parentNode.appendChild(newDiv);

				var newChild = document.createElement('p');
				newChild.innerHTML = '<span style="font-weight:bold; font-size:16px">Добавление новой записи в ' +  listTables[table.id].value + '</span>';//  table.id
				newDiv.appendChild(newChild);
				
				var newForm = document.createElement('form');
				newForm.id='formAdd_' + tableName;
				newForm.name='formaAdd_' + tableName; 
				newForm.method='GET';
				newForm.action='/CSFT/constructor';
				newForm.setAttribute('accept-charset', 'UTF-8');
				newDiv.appendChild(newForm);
				
				cells:for ( var j = 1, count = table.rows[0].cells.length; j < count; j++ ) {
					
					newDiv = document.createElement('div');
					newDiv.className = 'c_form_blok';	
					
					col = table.rows[0].cells[j];
					var atrId = col.id.split('_');
					var isLabel = true;
					var bufValue = col.innerHTML;
					if(bufValue != null){
					  switch(true){
						case bufValue.contains('(i)') : {
							isLabel = false;
							break;
						}
						case bufValue.contains('(w)') : {
							newChild = document.createElement('input');
							newChild.id = col.id;
							newChild.name = col.id;
							newChild.className = 'c_input';
							newChild.type = 'text';		
							break;						
						}
						case bufValue.contains('(c)') : {
							newChild = document.createElement('input');
							newChild.id = col.id;
							newChild.name = col.id;
							newChild.type = 'checkbox'; 
							break;
						}
						case bufValue.contains('(l)') : {
						    if(atrId[1] != null){
							  var newChild = document.createElement('select');
							  newChild.id = col.id;
							  newChild.name = col.id;
							  newChild.className = 'c_select';
							  listObjectsTagSelect[inddexTagSelect] = newChild;
							  inddexTagSelect++;
							  // ПРИВЕСТИ К ОБЩЕМУ ВИДУ
							  if(col.id == 'id_service')
							    newChild.setAttribute('change', 'subSelected(this.id,\"id_subServices\")');
							}else{
							  isLabel = false;
							}
						    break;
						}
						default : {
							isLabel = false;
							break;
						}
					}	
					}
					if(newChild != null) newDiv.appendChild(newChild);			
					if(isLabel){
					  newChild = document.createElement('p');
					  newChild.innerHTML = col.firstChild.nodeValue;
					  newDiv.insertBefore(newChild, newDiv.firstChild);
					  newChild = null;
					}				
					newForm.appendChild(newDiv);			    
				}		
				newChild = document.createElement('div');
				newChild.innerHTML = '<img id = "trSave" src="/CSFT/image/knopka.png" onClick="saveForm(\'' + newForm.id + '\'), removeMessage()"' + 
																						  'onmouseover="mouseOver(this.id, null)"' +
																						  'onmouseout="mouseOut(this.id)"/>' +
									 '<input type=\"hidden\" id=\"typeEventFormAdd\" name=\"typeEvent\" value=\"insert\">' + 
									 '<input type=\"hidden\" id=\"tableFormAdd\" name=\"table\" value=\"' + tableName + '\">' + 
									 addIdParent;
				newForm.appendChild(newChild);		
				document.getElementById(tableId).parentNode.style.display = 'none';				
				for(var j = 0, count = listObjectsTagSelect.length; j < count; j++)
					selected(listObjectsTagSelect[j]);
				hiddenItemsHM();
				break;
		    }
		    case typeElement == 'tree' : {
		    	var id = document.getElementById('idInTree').value;
		    	if(id != ''){
		    	  document.getElementById('typeEventInTree').value = 'add';
		    	  document.forms['formTree_' + tableName].submit();
		    	}else{
		    	  messageInfo('Не выбрано услуги для добавления процедуры!', document.getElementById('formTree_' + tableName));
		    	}	    	
		    	break;
		    }
		  }
	}
	
	/**
	 * Отрисовка формы редактирвоания
	 * версия 0.2 (до 15.03.27)
	 * @param typeElement
	 * @param tableId
	 */
	
	function createFormEdit(typeElement, tableId){
			
	  var listObjectsTagSelect = new Array();
	  var inddexTagSelect = 0;
	  var tableName = tableId.split('_')[1];
	  var idRecord = document.getElementById('edit_' + tableName).getAttribute('value');
	  
	  switch(true){
	    
	    case typeElement == 'table' : {
			if(idRecord != null){
				var table = document.getElementById(tableId);
				var atrIdRecord = idRecord.split('_');
				delElement = document.getElementById('messageInfo');
				if(delElement != null) 
				  remove(delElement);
				var tr = document.getElementById('tr'+ atrIdRecord[2]);		
				var col;
				var parentNode = table.parentNode.parentNode;
		
				var newDiv = document.createElement('div');
				newDiv.className = 'c_form';
				parentNode.appendChild(newDiv);
		
				var newChild = document.createElement('p');
				newChild.innerHTML = '<span style="font-weight:bold; font-size:16px">Редактирование записи № ' + atrIdRecord[2] + ' в ' + listTables[table.id].value + + '</span>'; //  tableId  
				newDiv.appendChild(newChild);
				
				var newForm = document.createElement('form');
				newForm.id='formEdit_' + tableName;
				newForm.name='formEdit_' + tableName; 
				newForm.method='GET';
				newForm.action='/CSFT/constructor';
				newForm.setAttribute('accept-charset', 'UTF-8');				
				newDiv.appendChild(newForm);
				cells:for ( var j = 1, count = table.rows[0].cells.length; j < count; j++ ) {
					
					newDiv = document.createElement('div');
					newDiv.className = 'c_form_blok';	
					
					col = table.rows[0].cells[j];
					value = tr.cells[j].textContent;
					var bufValue = tr.cells[j].innerHTML;
					var atrId = col.id.split('_');
					var isLabel = true;
					if(atrId[0] != 'id' ){
						
						switch(true){
						  case  bufValue.contains("textarea") :{
								newChild = document.createElement('textarea');
								newChild.className = 'c_textarea';
								newChild.id = col.id;
								newChild.name = col.id;
								newChild.value = value;
								break;
						  }
						  case bufValue.contains("checkbox") : {
							  
							    value = tr.cells[j].childNodes[0].childNodes[0].value;
								newChild = document.createElement('input');
								newChild.id = col.id;
								newChild.name = col.id;
								newChild.type = 'checkbox'; 
								if(value == 1){
								  newChild.setAttribute("checked", "checked");
								  newChild.value = 'true';
								}else{
									newChild.value = 'false';
								}
								newChild.setAttribute("onchange", "changeCheckBox(this)");
								break;
							  break;
						  }
						  default : {
								newChild = document.createElement('input');
								newChild.className = 'c_input';
								newChild.id = col.id;								
								newChild.name = col.id;
								newChild.type = 'text';
								newChild.value = value; 
								break;
						  }
						}
					}else{
					  if(atrId[1] != null){
						var newChild = document.createElement('select');
						newChild.className = 'c_select';
						newChild.id = col.id;
						newChild.name = col.id;  
						listObjectsTagSelect[inddexTagSelect] = newChild;
						inddexTagSelect++;
					  }else{
						isLabel = false;
					  }
					}					
					newDiv.appendChild(newChild);			
					if(isLabel){
					  newChild = document.createElement('p');
					  newChild.innerHTML = col.firstChild.nodeValue;
					  newDiv.insertBefore(newChild, newDiv.firstChild);	
					}					
					newForm.appendChild(newDiv);				    
				}
				newChild = document.createElement('div');
				newChild.innerHTML = '<img id = "trSave" src="/CSFT/image/knopka.png" onClick="saveForm(\'' + newForm.id + '\'), removeMessage()"' +
																						  'onmouseover="mouseOver(this.id, null)"'+
																						  'onmouseout="mouseOut(this.id)"/>' +
									 '<input type=\"hidden\" id=\"typeEventFormEdit\" name=\"typeEvent\" value=\"update\">' +
									 '<input type=\"hidden\" id=\"tableFormEdit\" name=\"table\" value=\"' + tableName + '\">' + 
									 '<input type=\"hidden\" id=\"idFormEdit\" name=\"id\" value=\"' + atrIdRecord[2] + '\">';
				newForm.appendChild(newChild);		
				document.getElementById(tableId).parentNode.style.display = 'none';
				
				for ( var j = 1, count = table.rows[0].cells.length; j < count; j++ ) {
					idSelected = tr.cells[j].innerText;
					listObjectsTagSelect: for(var j2 = 0, count2 = listObjectsTagSelect.length; j2 < count2; j2++){
					  if(listObjectsTagSelect[j2].name == table.rows[0].cells[j].id){
					    selected(listObjectsTagSelect[j2], idSelected);
					    break listObjectsTagSelect;
					  }
					}
				}
				
				hiddenItemsHM();
			}else{ 
				messageInfo('Не выбрано записи для редактирования!', document.getElementById('formTable_' + tableName));
			}	
			break;
	    }
	    case typeElement == 'tree' : {
	    		    	
			if(idRecord != null){
				var atrIdRecord = idRecord.split('_');
				
			  delElement = document.getElementById('messageInfo');
			  if(delElement != null) 
			    remove(delElement);
		
				var parentNode = document.getElementById('formTree_' + tableName).parentNode;
				
				var newDiv = document.createElement('div');
				newDiv.className = 'c_form';
				parentNode.appendChild(newDiv);
		
				var newChild = document.createElement('p');
				var newChild2;
				newChild.innerHTML = 'Редактирование записи № ' + atrIdRecord[2] + ' в ' + listTables['table_' + atrIdRecord[1]].value; //  'table_' + atrIdRecord[1]
				newDiv.appendChild(newChild);
				
				var newForm = document.createElement('form');
				newForm.id='formEdit_' + tableName;
				newForm.name='formEdit_' + tableName; 
				newForm.method='post';
				newForm.action='/CSFT/constructor';
				newForm.setAttribute('accept-charset', 'UTF-8');
				newDiv.appendChild(newForm);
				
				newDiv = document.createElement('div');
				newDiv.className = 'c_form_blok';	
								
				var value;	
				/*
				 * name_subService
				 */
				value = document.getElementById(atrIdRecord[1] + '_' + atrIdRecord[2]).parentNode.textContent;
				newChild = document.createElement('textarea');
				newChild.className = 'c_textarea';
				newChild.id = 'name_subService';
				newChild.name = 'name_subService';
				newChild.type = 'text';
				newChild.value = value;
			
				newDiv.appendChild(newChild);			
				
				newChild2 = document.createElement('p');
				newChild2.innerHTML = 'Наименование';
				newDiv.insertBefore(newChild2, newChild);	
								
				newForm.appendChild(newDiv);				
				
				/*
				 * external_code_service
				 */
				newChild = document.createElement('input');
				newChild.className = 'c_input';
				newChild.id = 'external_code_service';
				newChild.name = 'external_code_service';
				newChild.type = 'text';
				newChild.setAttribute('placeholder', 'Введите новое значение.');
			
				newDiv.appendChild(newChild);			
				
				newChild2 = document.createElement('p');
				newChild2.innerHTML = 'Код услуги в системе поставщика';
				newDiv.insertBefore(newChild2, newChild);	
								
				newForm.appendChild(newDiv);			
				
				newChild = document.createElement('div');
				newChild.innerHTML = '<img id = "trSave" src="/CSFT/image/knopka.png" onClick="formEdit_' + tableName + '.submit()"' +
																						  'onmouseover="mouseOver(this.id, null)"'+
																						  'onmouseout="mouseOut(this.id)"/>' +
									 '<input type=\"hidden\" id=\"typeEventFormEdit\" name=\"typeEvent\" value=\"update\">' + 
									 '<input type=\"hidden\" id=\"tableFormEdit\" name=\"table\" value=\"' + atrIdRecord[1] + '\">' +
									 '<input type=\"hidden\" id=\"idFormEdit\" name=\"id\" value=\"' + atrIdRecord[2] + '\">';
				newForm.appendChild(newChild);
				
				remove(document.getElementById('formTree_' +  tableName));
			  
	    	}else{
	    	  messageInfo('Не выбрано процедуры для редактирования!', document.getElementById('formTree_' + tableName));
	    	}
	    	break;
	    }
	  }
	}
	
	
	/**
	 * Отображение объектов для просмотра или выбора
	 * версия 0.1 (до 15.04.01)
	 * @param record
	 * @param typeElement
	 * OLD NAME - show
	 */
	function createFormShow(record, typeElement, parentTable) {
		var idRecord;
		var idSelect;
		if(typeElement != null){
		  switch(true) {
			case typeElement == 'table' : {
			  idRecord = record.getAttribute('value');   
			  break;
			}
			case typeElement == 'tree' : {
			  idRecord = record.getAttribute('name');
			  break;
			}			
		  }		  	
		}else{
			idRecord = record;
		}
		
		// проерка актуально только в случае typeElement = 'table'
		// параметр name генерится заранее и не может быть null
		if(idRecord != null){
			var atr = idRecord.split('_');
				var delMessageInfo = document.getElementById('messageInfo');
				if(delMessageInfo != null) delMessageInfo.parentNode.removeChild(delMessageInfo);
				var delBlockAddElement = document.getElementById('id_blockAddElement');
				if(delBlockAddElement != null) delBlockAddElement.parentNode.removeChild(delBlockAddElement);
				
				switch(true) {
				
				  case typeElement =='table' :{
					if(atr[2] != null){
						document.getElementById("idTable").value = atr[2];
						document.getElementById("typeEventTable").value = 'show';
						document.getElementById("getFormTable").value = '';
						document.getElementById('tableTable').value=atr[1];
						document.getElementById('elementContentTable').value = 'table';				
						document.forms["formTable_" + parentTable].submit();			
					}else{
						messageInfo('Не выбрано записи!', document.getElementsByTagName('table')[0]);
					}
					break;  
				  }
				  case typeElement == 'tree' : {
						if(atr[2] != null){
							document.getElementById("idInTree").value = atr[2];
							document.getElementById("typeEventInTree").value = 'show';
							document.getElementById("getFormInTree").value = '';
							document.getElementById('tableInTree').value='contentSubServices';
							document.getElementById('elementContentInTree').value = 'table';				
							document.forms["formTree_" + parentTable].submit();
						}else{
							messageInfo('Не выбрано записи!', document.getElementsByTagName('table')[0]);
						}
					  break;
				  }
				  default : {
					var isSend = true, valueFroInput;
					switch(true){
					  case atr[1] == 'typeObject' : {
						  obj = document.getElementById('id_listGroupObjects');
						  if(obj != undefined){
							  if(obj['value'] == ''){
								  isSend = false;
								  messageInfo('Не выбрана группа!');
							  }
						  }
						  idSelect = 'id_typeObject';
						  break;
					  }
					  case atr[1] == 'scripts' : {
						  var value = document.getElementById(idRecord).getAttribute('value');
						  if(value != null){
							 idSelect = 'id_script';
							 //elementId = value.split('_')[2];
							 valueFroInput = 'scriptsObject_' + value.split('_')[2];
						  }else{
							isSend = false;
							messageInfo('Не выбрано записи!', document.getElementById('formTable_' + parentTable));							  
						  }
						  break;
					  } 
					  case atr[1] == 'listPriorities' : {
						  var value = document.getElementById(record).getAttribute('value');
						  if(value != null){
							idSelect = 'id_listPrioritie';
							//elementId = value.split('_')[2];
							valueFroInput = 'contentSubServices_' + value.split('_')[2];
						  }else{
							isSend = false;
							messageInfo('Не выбрано записи!', document.getElementById('formTable_' + parentTable));							  
						  }
						  break;
					  }
					  case atr[1] == 'commissions' : {
						var value = document.getElementById(record).getAttribute('value');						
						if(value != null){
						  idSelect = 'id_commission';
							elementId = value.split('_')[2];
						}else{
						  isSend = false;
						  messageInfo('Не выбрано записи!', document.getElementById('formTable_' + parentTable));
	 					} 
						break;
					  }
					  case atr[1] == 'listGroupObjects' : {
						  idSelect = 'id_listGroupObject';
						  break;
					  }
					  case atr[1] == 'listClassService' : {
							var value = document.getElementById(record).getAttribute('value');						
							if(value != null){
							  idSelect = 'id_classService';
								elementId = value.split('_')[2];
							}else{
							  isSend = false;
							  messageInfo('Не выбрано записи!', document.getElementById('formTable_' + parentTable));
		 					} 
							break;
					  }
					}
					if(isSend){
							var parentNode = document.getElementById('formTable_' + parentTable);
							var newDiv = document.createElement('div');
							newDiv.id = 'id_blockAddElement';
							newDiv.className = 'c_form_blok';
							parentNode.parentNode.insertBefore(newDiv, parentNode);
							createElement('p', newDiv, null, 'Доступные для добавления элементы');
							if(valueFroInput != undefined) createElement('input', newDiv, null, 'hidden:' + valueFroInput);
							createElement('select', newDiv, idSelect);
							createElement('img', newDiv, 'trSave', '/CSFT/image/knopka.png');
											
							var tagSelected = document.getElementById(idSelect);
				    	    var req = getXmlHttp();
				    		req.onreadystatechange = function() { 

				    		        if (req.readyState == 4) {
				    		           
				    		            if(req.status == 200) {
				    		            	var options;
				    		            	//alert(req.responseText);
				    		                var line = req.responseText.split('&');		                
				    		                for(var i = 0, countLine = line.length; i < countLine; i++){	
				    		                  var atrLine = line[i].split('=');
				    						  options += "<option value='" + atrLine[0] + "'>" + atrLine[1] +"</option>";
				    		                }
				    		                tagSelected.innerHTML = options;
				    		            }
				    					hiddenItemsHM();
				    		        }
				    		 };
			    		 var data = "table=" + atr[1];
			    		 data += "&typeEvent=dictionary";
			    		 req.open('GET', '/CSFT/constructor?' + data, true); 
			    		 req.send(null); 
					}
		    		break;
				  }
				}		
		}else{
			messageInfo('Не выбрано записи!', document.getElementById('formTable_' + parentTable));
		}
	}
	
	/**
	 * Отрисовка формы с частичным доступом к полям
	 * версия 0.1 (15.03.26)
	 * Реализовано ТОЛЬКО для таблиц
	 */
	function createFormShowOrEdit(tableId, editFields){
					
		/*
		 * Проверка наличия выведенных сообщений ранее
		 */
		var delElement = document.getElementById('messageInfo');
		if(delElement != null) 
		  remove(delElement);
		
		
		/*
		 * Список полей доступных для редактироавния
		 */
		var nameFields;
		if(editFields != null)
			nameFields = editFields.split(',');

		var tableName = tableId.split('_')[1];
		/*
		 * ИД редактируемой записи
		 */
		var idRecord = document.getElementById('edit_' + tableName).getAttribute('value').split('_')[2];

		if(idRecord != null){
			
			/*
			 * ДЛЯ подгрузки справочников
			 */
			var listObjectsTagSelect = new Array();
			var inddexTagSelect = 0;

			/*
			 * Получение значений редактируемой строки
			 */
			var tdsValue = document.getElementById('tr' + idRecord).getElementsByTagName('td');
			var  countField = tdsValue.length;
			/*
			 * Получение дентификаторов полей и описаний
			 */
			var tdsCodeAndLabel = document.getElementById(tableId).getElementsByTagName('tr')[0].childNodes;			
			
			/*
			 * Объект в котором отрисовывается форма
			 */
			var parentNode = document.getElementById('c_context');
			
			
			/*
			 * Обертка формы
			 */
			var newDiv = document.createElement('div');
			newDiv.className = 'c_form';
			parentNode.appendChild(newDiv);
			
			/*
			 * Заголовок формы
			 */
			var newChild = document.createElement('p');
			newChild.innerHTML = '<span style="font-weight:bold; font-size:16px">Редактирование записи № ' + idRecord + ' в ' + listTables[tableId].value + + '</span>'; //  tableId  
			newDiv.appendChild(newChild);
			
			/*
			 * Форма
			 */
			var newForm = document.createElement('form');
			newForm.id='formEdit_' + tableName;
			newForm.name='formEdit_' + tableName; 
			newForm.method='GET';
			newForm.action='/CSFT/constructor';
			newForm.setAttribute('accept-charset', 'UTF-8');				
			newDiv.appendChild(newForm);
			
			var buffCodeAndLabel;
			for(var i = 1; i < countField; i++){
				
				/*
				 * Объект содержащий информацию об одном поле
				 */
				newDiv = document.createElement('div');
				newDiv.className = 'c_form_blok';
				
				/*
				 * Наличие заголовка у поля
				 */
				var isLabel = true;
				
				buffCodeAndLabel = tdsCodeAndLabel[i];
				var attrCode = buffCodeAndLabel.id.split('_') ;

				if(attrCode[0] != 'id'){
					
					switch(true){
					  case  tdsValue[i].innerHTML.contains('textarea') : {						  
							newChild = document.createElement('textarea');
							newChild.className = 'c_textarea';
							newChild.id = buffCodeAndLabel.id;
							newChild.name = buffCodeAndLabel.id;
							newChild.value = tdsValue[i].textContent;
							
							if(!isEditElement(nameFields, buffCodeAndLabel.id))
							  newChild.setAttribute('readonly', 'readonly');
						  break;
					  }
					  case tdsValue[i].innerHTML.contains('checkbox') : {				
						  /*
						   * НЕ ОТТЕСТИРОВАН
						   */
							newChild = document.createElement('input');
							newChild.id = buffCodeAndLabel.id;
							newChild.name = buffCodeAndLabel.id;
							newChild.type = 'checkbox';
														
							if(tdsValue[i].getElementsByTagName('input').value == 1){
								  newChild.setAttribute("checked", "checked");
								  newChild.value = 'true';
								}else{
									newChild.value = 'false';
								}
							
							if(!isEditElement(nameFields, buffCodeAndLabel.id))
							  newChild.setAttribute('disabled', 'disabled');
							else newChild.setAttribute("onchange", "changeCheckBox(this)");
							break;
					  }
					  default : {
							newChild = document.createElement('input');
							newChild.className = 'c_input';
							newChild.id = buffCodeAndLabel.id;								
							newChild.name = buffCodeAndLabel.id;
							newChild.type = 'text';
							newChild.value = tdsValue[i].textContent;

							if(!isEditElement(nameFields, buffCodeAndLabel.id))
								newChild.setAttribute('disabled', 'disabled');
							break;
					  }					 
					}					
				}else{
					if(attrCode[1] != null){
						var newChild = document.createElement('select');
						newChild.className = 'c_select';
						newChild.id = buffCodeAndLabel.id;
						newChild.name = buffCodeAndLabel.id;  

						if(!isEditElement(nameFields, buffCodeAndLabel.id))
							  newChild.setAttribute('disabled', 'disabled');
						
						listObjectsTagSelect[inddexTagSelect] = newChild;
						inddexTagSelect++;
					}else{
						isLabel = false;
					}
				}
				newDiv.appendChild(newChild);
				/*
				 * Добавления заголовка поля
				 */
				if(isLabel){
				  newChild = document.createElement('p');
				  newChild.innerHTML = buffCodeAndLabel.textContent;
				  newDiv.insertBefore(newChild, newDiv.firstChild);	
				}					
				newForm.appendChild(newDiv);		
			}			    
			
			/*
			 * Проставления системных параметров
			 */
			newChild = document.createElement('div');
			newChild.innerHTML = '<img id = "trSave" src="/CSFT/image/knopka.png" onClick="saveForm(\'' + newForm.id + '\'), removeMessage()"' +
																					  'onmouseover="mouseOver(this.id, null)"'+
																					  'onmouseout="mouseOut(this.id)"/>' +
								 '<input type=\"hidden\" id=\"typeEventFormEdit\" name=\"typeEvent\" value=\"update\">' +
								 '<input type=\"hidden\" id=\"tableFormEdit\" name=\"table\" value=\"' + tableName + '\">' + 
								 '<input type=\"hidden\" id=\"idFormEdit\" name=\"id\" value=\"' + idRecord + '\">';
			newForm.appendChild(newChild);		
			/*
			 * Скрытие таблицы
			 */
			document.getElementById(tableId).parentNode.style.display = 'none';
			
			/*
			 * Подгрузка справочников
			 */
			for ( var j = 1; j < countField; j++ ) {
				idSelected = tdsValue[j].innerText;				
				listObjectsTagSelect: for(var key in listObjectsTagSelect){
					if(listObjectsTagSelect[key].name == tdsCodeAndLabel[j].id){
						selected(listObjectsTagSelect[key], idSelected);
						break listObjectsTagSelect; 
					}
				}
			}
			hiddenItemsHM();			
		}else{
			messageInfo('Не выбрано записи для редактирования!', null);
		}	
		
	}
	
	function isEditElement(editElements, element){
		
		if(editElements[0] == 'all')
			return true;
		
		for(var elem in editElements){
			if(editElements[elem] == element)
				return true;
		}
		return false;
	}
	
/**
 * =========================================================================
 * Дополнительные функции JavaScript
 * ========================================================================
 */
	
	if ( !String.prototype.contains ) {
	    String.prototype.contains = function() {
	        return String.prototype.indexOf.apply( this, arguments ) !== -1;
	    };
	}
	
	if(!Array.prototype.contains){		
		Array.prototype.contains = function(obj) {
		    var i = this.length;
		    while (i--) {		    	
		        if (this[i] === obj) {
		            return true;
		        }
		    }
		    return false;
		};		
	}
	
	Number.prototype.div = function(by){	
	    return (this - this % by) / by;
	}

	function hasClass(elem, className) {
	    return new RegExp("(^|\\s)"+className+"(\\s|$)").test(elem.className);
	}

	String.prototype.replaceAll = function(search, replacement) {
	    var target = this;
	    return target.split(search).join(replacement);
	};	
