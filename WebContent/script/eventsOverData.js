
	/**
	 * Изменение состояния объекта
	 * @param id
	 * @param blockActive
	 */
	function change(id, blockActive){

		var arg;
		switch(true){
 		  // test
		  case blockActive == 'filter' : { 
			  var table = id.split('_')[1];
			  switch (true) {
			  	case table == 'services': {
				  showRecordsByColumn('commissionServices', 'id_service', document.getElementById(id)['value']);
				  break;
			  }
			  case table == 'terminals' : {
				  showRecordsByColumn('commissionServices','id_terminal', document.getElementById(id)['value']);
				  break;			  		
			  }
			  case table == 'listGroupObjects' : {
				  var id_listGroupObject = document.getElementById(id)['value'];
			      removeTRs('contentGroupObjects');
			      execute_filter('contentGroupObjects', 'filter_id_listGroupObject:Код группы=' + id_listGroupObject);
			      
			      /*
			       * ОПЕРДЕЛИТЬ НАДОБНОСТЬ
			       */
				  var div = createElement('div', document.getElementById('labelBlock_table_' + contentGroupObjects), 
						  		'labelTable', null);
				  createElement('input', div, null, 'hidden:' + contentGroupObjects + '_' + id_listGroupObject);
				  break;
			  }
			  default:
				break;
			}			 
			break;  
		  }		
		  case blockActive == 'table' : {			  
			  arg = id.split('_');
			  var del = document.getElementById('del_' + arg[0]);
			  if(del != null)del.setAttribute('value', 'del_' + arg[0] + '_' + arg[1]);
			  var edit = document.getElementById('edit_' + arg[0]);
			  if(edit != null)edit.setAttribute('value', 'edit_' + arg[0] +'_' + arg[1]);
			  switch(true){
			    case arg[0] == 'dictionaries' : {			    	
			    	document.getElementById('add_contentDictionaries').setAttribute('value', 'add_contentDictionaries_' + arg[1]);
			    	//document.getElementById('show_contentDictionaries').setAttribute('value', 'show_contentDictionaries_' + arg[1]);
			    	removeTRs('contentDictionaries');
			    	execute_filter('contentDictionaries', 'filter_id_dictionarie:Справочник=' + arg[1]);			    	
			    	break;
			    }
			    case arg[0] == 'typeObject' : {
			    	document.getElementById('show_scripts').setAttribute('value', 'show_scripts_' + arg[1]);
			    	removeTRs('scriptsObject');
			    	execute_filter('scriptsObject', 'filter_id_typeObject:Код объекта=' + arg[1]);
			    	//showRecordsByColumn('scriptsObject', 'id_typeObject', arg[1]);
			    	break;
			    }
			    case arg[0] == 'services' : {
			    	document.getElementById('show_commissions').setAttribute('value', 'show_commissions_' + arg[1]);
			    	break;
			    }
			    case arg[0] == 'contentSubServices' : {
			    	document.getElementById('show_listPriorities').setAttribute('value', 'show_listPriorities_' + arg[1]);
			    	break;
			    }
			    case arg[0] == 'terminals' : {
			    	document.getElementById('addBondForTerminal').setAttribute('value', arg[1]);
			    	break;
			    }
			    default :{
			    	break;
			    }	
			  }
			  break;			  
		  }
		  case blockActive == 'tree' : {
			  arg = id.split('_');
			  if(arg[0] == 'services'){
				document.getElementById('tableInTree').value = 'subServices';
				document.getElementById('idInTree').value = arg[1];
				document.getElementById('getFormInTree').value = 'subServices';
			  }
			  if(arg[0] == 'subServices'){
				document.getElementById('del_subServices').setAttribute('value', 'del_subServices_' + arg[1]);
				document.getElementById('edit_subServices').setAttribute('value', 'edit_subServices_' + arg[1]);				  
			  }
			  break;		
		  }
		  case blockActive == 'tools' : {
			  var indexColumn = 1;
			  var atr = id.id.split(':');
			  var el = document.getElementById(atr[0]);
			  var childs = el.parentNode.childNodes;
			  
			  var table = document.getElementById('table_body');			  
			  var tds = table.getElementsByTagName("td");
			  var atrTd;
			  
			  for(indexColumn; indexColumn <= childs.length - 1; indexColumn++)
				if(childs[indexColumn].id == atr[0]) break;
			  
			  if(id.value == 'true'){
				id.setAttribute('value', 'false');
				el.style.display = 'none';
				
				for(var i = 0; i < tds.length; i++){
				  atrTd = tds[i].id.split('_');
				  if (atrTd[atrTd.length - 1] == indexColumn && !tds[i].id.contains('ch'))
					tds[i].style.display = 'none';	
				}				
			  }else{
				id.setAttribute('value', 'true');
				el.style.display = '';
				
				for(var i = 0; i < tds.length; i++){
				  atrTd = tds[i].id.split('_');
				  if (atrTd[atrTd.length - 1] == indexColumn && !tds[i].id.contains('ch'))
					tds[i].style.display = '';	
				}
			  }
			  break;

		  }
		  case blockActive == 'properties' : {
			  var atr = id.id;
			  if(id.value == 'true'){
				  id.setAttribute('value', 'false');
				  document.getElementById('system_isLog').setAttribute('value', false);
			  } else{
				  id.setAttribute('value', 'true');
				  document.getElementById('system_isLog').setAttribute('value', true);
			  }
			  break;
		  }		  
		  case blockActive == 'form' : {
			  break;
		  }
		}	
	}
	function changeCheckBox(obj){
		if(obj.value != null){
		  if(obj.value == 'true'){
			obj.checked = '';
			obj.value = 'false';
		  }else{
			 obj.checked = 'checked';
		    obj.value = 'true';			  
		  }
		}else{
		  obj.checked = 'checked';
		  obj.value = 'true';			
		}
	}

	/**
	 * Снятие курсора
	 * @param elementId
	 * @param blockActive
	 */
	function mouseOver(elementId, blockActive){
		  switch (true) {
			case blockActive == "verticalMenu" : {
			  var id = elementId.getElementsByTagName('input')[0].getAttribute('id');
			  var table = id.contains('menu') ?  id.split('_')[1] : undefined;
			  document.getElementById("table").value = id;
			  switch(true){
			    case id == 'services' : {
				  if(!document.getElementById("h_entryPerson").textContent.contains('Администратор'))
					document.getElementById('elementContentVerticalMenu').setAttribute('value', 'tree');
				  else document.getElementById('elementContentVerticalMenu').value = 'table';
				  break;
			    }
			    case  id == "systemTools" :{
			      document.getElementById('elementContentVerticalMenu').value = 'blocks'; //form  
			      document.getElementById('typeEvent').value = 'save_properties';
			      /*
			       * КОСЯК
			       */
			      document.getElementById('table').value = 'save_properties';
			      break;
			    }
			    /*
			     * естовый функционал
			     */
			    case id == 'unLoad' : {
			    	document.getElementById('elementContentVerticalMenu').value = 'blocks'; //form
			    	document.getElementById('typeEvent').value = 'unLoad';
				      /*
				       * КОСЯК
				       */
			    	document.getElementById('table').value = 'unLoad';
			    	break;
			    }
			    case id == 'profileAccess' :
			    case id == "dictionaries" : 
			    case id == "typeObject" : 
			    case id == 'commissionServices': 
			    case id =='contentGroupObjects':
	/*15.11.27*/case id == 'terminals' :
	/*15.12.11*/case id == 'statistic' : {
			      document.getElementById('elementContentVerticalMenu').value = 'blocks';
			      break;
			    }
			    default : {
			      document.getElementById('elementContentVerticalMenu').value = 'table';
			      document.getElementById('typeEvent').value = 'select';
			      break;
			    }
			  }
			  var imagePath = document.getElementById(id).src;			  
			  imagePath = table != undefined ? imagePath.replace(table, table + '_dark') 
					  						 : imagePath.replace(id, id + '_dark');
			  document.getElementById(id).src = imagePath; 
			  break;
			}
			case blockActive == "horizontalMenu" : {
				var itemHM = document.getElementById(elementId);
				var className = itemHM.getAttribute('class') + " c_over";
				itemHM.setAttribute('class', className);			
			  break;
			}
			case blockActive == "tree" : {
			  document.getElementById("id").value = elementId;
			  break;
			}
			case  blockActive =='exit' : {			
				document.getElementById("exit").src='/CSFT/image/exit_white2.png';
				break;
			}
			case blockActive == 'logo' : {
				document.getElementById(elementId).src='/CSFT/image/logo2.png';
				break;
			}
			case blockActive == 'tr' : {
				
				var tr = document.getElementById(elementId);
				var tds = tr.getElementsByTagName('td');
				for(var j = 1, countTD = tds.length; j < countTD; j++ )
				  tds[j].setAttribute('class',tds[j].getAttribute('class') + " c_over");
				break;
			}
			default:{		
				if(elementId == "trSave"){
					var s = document.getElementById("trSave");
					if(s != null) s.setAttribute('src', '/CSFT/image/knopka_navedenie.png');
				}else document.getElementById(elementId).style.background = '#768FFF';
				break;
			}			
		  }
		}
	
	
	/**
	 * Снятие курсора
	 * @param elementId
	 * @param blockActive
	 */
	
	function mouseOut(elementId, blockActive){
		
	  switch(true){
		case blockActive == "horizontalMenu":{
		    var itemHM = document.getElementById(elementId);
			var className = itemHM.getAttribute('class').split(' ')[0];
			itemHM.setAttribute('class', className);
		  break;
		}
		case blockActive == 'verticalMenu' : {
			var id = elementId.getElementsByTagName('input')[0].id;
			var table =  id.contains('menu') ? id.split('_')[1] : undefined;
			var imagePath = document.getElementById(id).src;
			  imagePath = table != undefined ? imagePath.replace(table + '_dark', table) 
						 					 : imagePath.replace(id + '_dark', id);
			document.getElementById(id).src = imagePath; 
			break;
		}
		case blockActive == 'tr' : {
			var tr = document.getElementById(elementId);
			var tds = tr.getElementsByTagName('td');
			for(var j = 1, countTD = tds.length; j < countTD; j++ )
			  tds[j].setAttribute('class',tds[j].getAttribute('class').split(' ')[0]);			
			break;
		}
		default:{
		  if(elementId == "trSave"){
			  var s = document.getElementById("trSave");
			  if(s != null)s.setAttribute('src', '/CSFT/image/knopka.png');
		  } else document.getElementById(elementId).style.background = '#FDF5E6';
		  break;
		}
	  }
	}
	
	/**
	 * Клик мыши
	 * @param id
	 * @param blockActive
	 */
	function mouseClick(id, blockActive){

		  switch(true){
			case blockActive == 'horizontalMenu':{
			  document.getElementById(id).style.background = '#FFFF00';			  
			  break;
			}
			case blockActive == 'table': {
				break;
			}		
			case blockActive == 'tree' : {
			  var arg = id.split('_');
			  if(arg[0] == 'services'){
				document.getElementById('tableInTree').value = 'subServices';
				document.getElementById('idInTree').value = arg[1];
				document.getElementById('getFormInTree').value = 'subServices';

				document.getElementById('elementContentHorizontalMenu').value = blockActive;
			  }
			  if(arg[0] == 'subServices'){
				  document.getElementById('del_subServices').id = 'del_subServices_' + arg[1];
				  document.getElementById('edit_subServices').id = 'edit_subServices_' + arg[1];

			  }
			  break;
			}
			case blockActive == 'filter' : {
				var parent =document.getElementById('checkF'); 
				var child = document.getElementById('openF').parentNode;
				if(child.name == 'open'){
					parent.removeChild(child);
					parent.innerHTML = '<a name=\"close\" href=\"javascript:;\" \">' +
									   '<input id=\"openF\" type=\"image\" src=\"/CSFT/image/arrow1.png\" onclick=\"mouseClick(openF,\'filter\');\">' +
									   '</input></a>';
				}else if(child.name == 'close'){
					parent.removeChild(child);
					parent.innerHTML = '<a name=\"open\" href=\"javascript:;\"\">' +
					   '<input id=\"openF\" type=\"image\" src=\"/CSFT/image/arrow2.png\" onclick=\"slidedown(\'mydiv\') mouseClick(openF,\'filter\');\">' +
					   '</input></a>';
				}
				break;
			}
			case blockActive == 'textarea' : {		
				
				var labelTable = document.getElementById('labelTable');				
				var td = document.getElementById(id);								
				td.childNodes[0].childNodes[0].style.background = '#ffff00';

				
				var newDiv = document.getElementById('c_form_value_td');
				if(newDiv != null){
					remove(newDiv);
					document.getElementById(preId).childNodes[0].childNodes[0].style.background = '#ffffff';					
				}
				preId = id;
				newDiv = document.createElement('div');
				newDiv.setAttribute('id', 'c_form_value_td');
				
				var newPre = document.createElement('pre');
				newPre.textContent = td.textContent ;				
				newDiv.appendChild(newPre);
				
				var newInput =  document.createElement('input');
				newInput.setAttribute('type', 'button');
				newInput.setAttribute('value', 'Закрыть');
				newInput.setAttribute('onclick', 'mouseClick(\'' + preId + '\', \'button\')');
				newDiv.appendChild(newInput); 

				labelTable.appendChild(newDiv);
				break;
			}
			case blockActive == 'button' : {
				document.getElementById(id).childNodes[0].childNodes[0].style.background = '#ffffff';
				remove(document.getElementById('c_form_value_td'));
				break;
			}
			case blockActive = 'verticalMenu' : {
				
				document.getElementById(elementId).parentNode.color = '#737477';
				break;
			}
			default:{
			  break;
			}
		  }
	}
	
	/**
	 * Двойной клик мыши
	 * @param obj
	 */
	function mouseDoubleClick(obj){
		
		var oldObj = document.getElementById('c_table_textarea_large');
		if(oldObj != null)
		  remove(oldObj);
		else
		  createElement('textarea', document.getElementById('c_context'), 'c_table_textarea_large', obj.textContent);		
	}
	
	/*
	 * НЕ АКТУАЛЬНО, НО ИСПОЛЬЗУЕТСЯ
	 */
	function remove(elem) {
		  var parentElement = elem.parentNode;
		  parentElement.removeChild(elem);
	}
	
	
	function changeCheckBox(obj){
		if(obj.value == 'true') obj.value = false;
		  else obj.value = true;
			
	}
	
	function isError(errors){
    	if(errors['error'] == undefined){
    		viewBlockInfo(errors);
    		return true;
    	}else if (errors['error'] =='none')return false;
    		  else return true;
	}
	
	
	function removeMessage(){
	        setTimeout(function(){
	    		var elem = document.getElementById('messageInfo');
	    		if(elem != null) remove(elem);
	        }, 5000);
	}

	/**
	 * version 15.12.02 
	 * @param tableName - Наименование таблицы из которой удаляем строки
	 * @param arrIndex - массив удаляемых строк
	 */
	function removeTRs(tableName, listIndex){
		
		var table = document.getElementById('table_body_' + tableName);
		if(listIndex == undefined){
		  var len = table.getElementsByTagName('tr').length;
		  for(var i = 0; i < len; i++)
		    table.deleteRow(0);		  
		}else{
		  var len = listIndex.length - 1;
		  for(var i = len; 0 <= i; i--)
			  table.deleteRow(listIndex[i]);
		}
		
	}

	