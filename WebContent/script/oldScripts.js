/*
 * ПЕРЕНЕСТИ В АРХИВ
 */	
	/*
	 * Редактирование яцеек таблицы. Не используется 
	 */
	
	function edit(obj){	

    	var trs = document.getElementsByTagName("tr");
    	var countTR = trs.length; 
		for(var i = 1; i < countTR; i++ ){
			var tds = trs[i].getElementsByTagName("td");
    		if(trs[i].getAttribute('id') == obj){
      		  var countTD = tds.length;
      		  for(var j = 0; j < countTD; j++){
    			var spans = tds[j].getElementsByTagName("span");
				if (spans[0] == null){
				  tds[j].innerHTML = '<img id = "trSave" src="/CSFT/image/buttonSave1.png" onClick="save()" onmouseover="mouseOver(this.id, null)" onmouseout="mouseOut(this.id)"/>';				  
				  tds[j].style.background =  "#FF4500";
				}else{
				  var ing = spans[0].innerHTML;
				  var str = "<input size='10px' value='" + ing + "'></input>";						  
    			  tds[j].innerHTML = str;
				  tds[j].style.background =  "#FF4500";
				}
    		  }
    		}else
				trs[i].style.display = "none";
		}
	}
	
		
	/*
	 * Отрисовка формы добавления новой записи 
	 * версия 0.1
	 */
	function add(typeElement, tableId){

	  switch(true){
	  
	    case typeElement == 'table' : {
	    	
			var tableName = tableId.split('_')[1];
			var idParent;
			var addIdParent;
			switch(true){			
				case tableName == 'contentDictionaries' : {
					idParent = document.getElementById('add_' + tableName).getAttribute('value').split('_')[2];					
					addIdParent = '<input type=\"hidden\" id=\"idParent\" name=\"id\" value=\"' + idParent + '\">';  
					break;
				}
				default : {
					addIdParent = '';
					break;
				}
			}
			var table = document.getElementById(tableId);
			var col;
			var parentNode = table.parentNode.parentNode;

			var newDiv = document.createElement('div');
			newDiv.className = 'c_form';
			parentNode.appendChild(newDiv);

			var newChild = document.createElement('p');
			newChild.innerHTML = '<span style="font-weight:bold; font-size:16px">Добавление новой записи в ' +  listTables[table.id].value + '</span>';//  table.id
			newDiv.appendChild(newChild);
			
			var newForm = document.createElement('form');
			newForm.id='formAdd_' + tableName;
			newForm.name='formaAdd_' + tableName; 
			newForm.method='post';
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
						newChild.className = 'c_input';
						newChild.id = col.id;
						newChild.name = col.id;
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
						  newChild.className = 'c_select';
						  newChild.id = col.id;
						  newChild.name = col.id;
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
			newChild.innerHTML = '<img id = "trSave" src="/CSFT/image/knopka.png" onClick="formaAdd_' + tableName + '.submit()"' + 
																					  'onmouseover="mouseOver(this.id, null)"' +
																					  'onmouseout="mouseOut(this.id)"/>' +
								 '<input type=\"hidden\" id=\"typeEventFormAdd\" name=\"typeEvent\" value=\"insert\">' + 
								 '<input type=\"hidden\" id=\"tableFormAdd\" name=\"table\" value=\"' + tableName + '\">' + 
								 addIdParent;
			newForm.appendChild(newChild);		
			//remove(document.getElementById(tableId).parentNode);
			alert("tableId : " + tableId);
			document.getElementById(tableId).parentNode.style.display = 'none'
			
			for ( var j = 1, count = table.rows[0].cells.length; j < count; j++ ) {
				var id = table.rows[0].cells[j].id;
				var atrId = id.split('_');
				if(atrId[0] == 'id' && atrId[1] != null)
				  selected(id);
			}
			hiddenItemsHM();
			break;
	    }
	    case typeElement == 'tree' : {
	    	
	    	var id = document.getElementById('idInTree').value;
	    	if(id != ''){
	    	  document.getElementById('typeEventInTree').value = 'add';
	    	  document.forms["FormaTree"].submit();
	    	}else{
	    	  messageInfo('Не выбрано услуги для добавления процедуры!', document.getElementById('FormaTree'));
	    	}	    	
	    	break;
	    }
	  }
	}
	
	
	/*ИЗ ФОРМЫ ДОБАВЛЕНИЯ ЗАПИСИ
	 * for ( var j = 1, count = table.rows[0].cells.length; j < count; j++ ) {
	var id = table.rows[0].cells[j].id;
	var atrId = id.split('_');
	if(atrId[0] == 'id' && atrId[1] != null){
		var v = document.getElementById(id).innerText;
		if(v.contains('(') && v.contains(')'))							
			selected(id);	
	}
}*/
