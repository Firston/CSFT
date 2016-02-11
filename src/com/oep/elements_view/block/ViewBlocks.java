package com.oep.elements_view.block;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.oep.db.sql.ResultSQL;
import com.oep.dictionary.ListTables;
import com.oep.dictionary.ListTypeBlock;
import com.oep.dictionary.ItemMenu;
import com.oep.elements_view.button.ViewButton;
import com.oep.elements_view.form.ViewForms;
import com.oep.elements_view.other.ViewCheckBox;
import com.oep.elements_view.other.ViewSelect;
import com.oep.elements_view.table.ViewTable;
import com.oep.process.ProcessForeground;
import com.oep.process.data.BufferProcess;

public class ViewBlocks extends AbstractBlocks{

	private List<InfoBuildBlock> infoBuildBlocks = null;
	
	private ProcessForeground process;
	
	private static ViewBlocks instance;
	
	public static ViewBlocks getInstance(){
		if(instance == null)
		  instance = new ViewBlocks();
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see com.oep.elements.Blocks#create(java.lang.String)
	 */
	public void create(String sessionId) { 		
		process = BufferProcess.getProcess(sessionId);
		switch (ItemMenu.getValue(process.map.get("table").toString())) {
			case DICTIONARIES: {
				select_dictionaries(sessionId);
				break;	
			}
			case COMMISSIONSERVICES : {
				select_commissions_for_service_and_terminal(sessionId);
				break;	
			}
			case TERMINALS : {
				select_terminals_and_default_bond_services(sessionId);
				break;
			}
			case TYPEOBJECT : {
				select_typeObject_with_scripts(sessionId);
				break;	
			}
			case PROFILEACCESS : {
				select_profileAccess(sessionId);
				break;
			}
			case UNLOAD  : {
				unLoad(sessionId);
				break;				
			}
			case CONTENTGROUPOBJECTS : {
				select_contentGroupObjects(sessionId);
				break;
			}
			case SAVE_PROPERTIES : {
				save_properties(sessionId);
				break;
			}
			case NONE : {				
				break;	
			}
		}
	}


	public String getBlocks(String sessionId){
		create(sessionId);
		return AbstractBlocks.contentBlocks;
	}	
	
	public void getBlocks(String sessionId, PrintWriter pw){
		create(sessionId);
		if(AbstractBlocks.contentBlocks != null)
		  pw.write(AbstractBlocks.contentBlocks);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.oep.elements.Blocks#createContent(java.lang.String)
	 * 
	 * ВАЖНО! Вызов метода create(String sessionId) должен предшевстновать
	 */
	public String createContent(String header_key) {
		
		/*
		 * attrKey[0] - тип блока
		 * attrKey[1] - содержимое объекта
		 * 
		 */
		String[] attrKey = header_key.split("_");
		switch (ListTypeBlock.getValue(attrKey[0])) {
			case LIST: {
				
				process.map.put("table", attrKey[1]);
				process.map.put("typeEvent", "dictionary");
				ResultSet resultSet = ResultSQL.getResultSet(process.map);
				
				switch (ListTables.getValue(attrKey[1])){
					case LISTGROUPOBJECTS : 
						return ViewSelect.getInstance().getSelect(resultSet, false);
					case SERVICES :
					case COMMISSIONS :
						return ViewSelect.getInstance().getSelect(resultSet, true);
					default : 
						return ViewSelect.getInstance().getSelect(resultSet, true);				
				}			
			}
			case TABLE: {
				process.map.put("table", attrKey[1]);
				process.map.put("typeEvent", "select");
				return ViewTable.getInstance().getTable(ResultSQL.getResultSet(process.map));
				
			}
			case COMPLEX : {
				
				/*
				 * Создание блока отображающего настройки логирования системы					
				 */
				if(header_key.equals("paramLog"))
					return ViewForms.getInstance().formLog();
				
				/*
				 * Блок мультифильтра  в разеле коммиисии по сервисам
				 */
				if(header_key.equals("toolsCommissionServices"))
					return ViewCheckBox.getInstance().getCheckBox("isMutliFilter", 
																  "Поддержка нескольких фильтров", 
																  false);
				/*
				 * параматры сессий
				 */
				if(header_key.equals("paramSession"))
					return ViewForms.getInstance().formSession();
				/*
				 * информация о пользователях
				 */
				if(header_key.equals("infoUsers"))
					return ViewForms.getInstance().formInfoUsers();
				/*
				 * Системный конфигурационный файл
				 */					
				if(header_key.equals("paramSystemConfig"))
					return ViewForms.getInstance().formSystemConfig();
				/*
				 * Кнопка  проверки коммиссий на терминалах
				 */
				if(header_key.equals("checkCommissionTerminal"))
					return ViewButton.button_checkCommissionTerminal();
				/*
				 * Выгрузка услуг из Рапиды
				 */				
				if(header_key.equals("unLoadRapida"))
					//return ViewButton.button_unLoadRapida();
					return ViewForms.formUnloadFromRapida();				
				/*
				 * Раздел дополнительных функций терминала
				 */
				if(header_key.equals("toolsTerminals"))
					return ViewForms.toolsTerminals();
				
				if(header_key.equals("log"))
					return ViewForms.log();
			}
			case NONE : {
				return "";
			}
			default : {
				return "";				
			}
		}
	}
	
	private void save_properties(String sessionId){

		InfoBuildBlock infoBuildBlock = null;
		infoBuildBlocks = new ArrayList<InfoBuildBlock>();
		
		infoBuildBlock = new InfoBuildBlock("1.0", 100);
		infoBuildBlock.addHEADER_PARAM(0, "paramSystemConfig", "Системный файл конфигурациции");
		infoBuildBlocks.add(infoBuildBlock);
				
		infoBuildBlock = new InfoBuildBlock("0.46, 0.54", 150);
		infoBuildBlock.addHEADER_PARAM(0, "paramLog", "Логирование");
		infoBuildBlock.addHEADER_PARAM(1, "paramSession", "Сессии");
		infoBuildBlocks.add(infoBuildBlock);

		infoBuildBlock = new InfoBuildBlock("0.5, 0.25, 0.25", 150);
		infoBuildBlock.addHEADER_PARAM(0, "", "");
		infoBuildBlock.addHEADER_PARAM(1, "", "");
		infoBuildBlock.addHEADER_PARAM(2, "", "");
		infoBuildBlocks.add(infoBuildBlock);
		
		buildBlocks(916, infoBuildBlocks, sessionId);
	}
	private void select_dictionaries(String sessionId){

		InfoBuildBlock infoBuildBlock = null;
		infoBuildBlocks = new ArrayList<InfoBuildBlock>();		
		
		infoBuildBlock = new InfoBuildBlock("0.4, 0.6", 500);
		infoBuildBlock.addHEADER_PARAM(0, "table_dictionaries", "Справочники");
		infoBuildBlock.addHEADER_PARAM(1, "table_contentDictionaries", "Содержимое справочника");
		infoBuildBlocks.add(infoBuildBlock);
		
		buildBlocks(916, infoBuildBlocks, sessionId);
	}	
	
	private void select_commissions_for_service_and_terminal(String sessionId){
		
		InfoBuildBlock infoBuildBlock = null;
		infoBuildBlocks = new ArrayList<InfoBuildBlock>();
		
		infoBuildBlock = new InfoBuildBlock("0.5, 0.25, 0.25", 75);
		infoBuildBlock.addHEADER_PARAM(0, "toolsCommissionServices", "Мульти фильтр");
		infoBuildBlock.addHEADER_PARAM(1, "list_services", "Услуги");
		infoBuildBlock.addHEADER_PARAM(2, "list_terminals", "Терминалы");
		infoBuildBlocks.add(infoBuildBlock);
		
		infoBuildBlock = new InfoBuildBlock("0.5, 0.5", 450);		
		infoBuildBlock.addHEADER_PARAM(0, "table_commissionServices", "Коммиссия по сервисам");
		infoBuildBlock.addHEADER_PARAM(1, "checkCommissionTerminal", "Дополнительная информация");
		infoBuildBlocks.add(infoBuildBlock);
		
		buildBlocks(916, infoBuildBlocks, sessionId);		
	}
	
	private void select_typeObject_with_scripts(String sessionId) {
		
		InfoBuildBlock infoBuildBlock = null;
		infoBuildBlocks = new ArrayList<InfoBuildBlock>();
		
		infoBuildBlock = new InfoBuildBlock("1.0", 400);		
		infoBuildBlock.addHEADER_PARAM(0, "table_typeObject", "Элементы форм");
		infoBuildBlocks.add(infoBuildBlock);
				
		infoBuildBlock = new InfoBuildBlock("0.6, 0.4", 120);		
		infoBuildBlock.addHEADER_PARAM(0, "table_scriptsObject", "Скрипт элемента");
		infoBuildBlock.addHEADER_PARAM(1, "", "");
		infoBuildBlocks.add(infoBuildBlock);
		
		buildBlocks(916, infoBuildBlocks, sessionId);				
	}
	
	private void select_profileAccess(String sessionId){
		
		InfoBuildBlock infoBuildBlock = null;
		infoBuildBlocks = new ArrayList<InfoBuildBlock>();
		
		 infoBuildBlock = new InfoBuildBlock("0.8, 0.2", 390);		
		infoBuildBlock.addHEADER_PARAM(0, "table_profileAccess", "Права доступа");
		infoBuildBlock.addHEADER_PARAM(1, "", "");
		infoBuildBlocks.add(infoBuildBlock);
				
		infoBuildBlock = new InfoBuildBlock("0.6, 0.4", 150);		
		infoBuildBlock.addHEADER_PARAM(0, "infoUsers", "Информация о пользователях");
		infoBuildBlock.addHEADER_PARAM(1, "", "");
		infoBuildBlocks.add(infoBuildBlock);
		
		buildBlocks(916, infoBuildBlocks, sessionId);
	}
	
	private void select_contentGroupObjects(String sessionId){

		InfoBuildBlock infoBuildBlock = null;
		infoBuildBlocks = new ArrayList<InfoBuildBlock>();
		
		infoBuildBlock = new InfoBuildBlock("1.0", 75);
		infoBuildBlock.addHEADER_PARAM(0, "list_listGroupObjects", "Группы объектов");
		infoBuildBlocks.add(infoBuildBlock);
		
		infoBuildBlock = new InfoBuildBlock("1.0", 400);		
		infoBuildBlock.addHEADER_PARAM(0, "table_contentGroupObjects", "Содержимое группы");
		infoBuildBlocks.add(infoBuildBlock);
		
		buildBlocks(916, infoBuildBlocks, sessionId);
	}
	
	private void unLoad(String sessionId){

		infoBuildBlocks = new ArrayList<InfoBuildBlock>();
		
		InfoBuildBlock infoBuildBlock = new InfoBuildBlock("1.0", 390);		
		infoBuildBlock.addHEADER_PARAM(0, "unLoadRapida", "Выгрузка услуг. Rapida");
		infoBuildBlocks.add(infoBuildBlock);
		
		buildBlocks(916, infoBuildBlocks, sessionId);
	}
	
	private void select_terminals_and_default_bond_services(String sessionId) {
		
		InfoBuildBlock infoBuildBlock = null;
		infoBuildBlocks = new ArrayList<InfoBuildBlock>();		
		
		infoBuildBlock = new InfoBuildBlock("0.45, 0.55", 390);
		infoBuildBlock.addHEADER_PARAM(0, "table_terminals", "Терминалы");
		infoBuildBlock.addHEADER_PARAM(1, "toolsTerminals", "Дополнительные функции");
		infoBuildBlocks.add(infoBuildBlock);

		infoBuildBlock = new InfoBuildBlock("1.0", 150);	
		infoBuildBlock.addHEADER_PARAM(0, "log", "Лог процесса");
		infoBuildBlocks.add(infoBuildBlock);
		
		buildBlocks(916, infoBuildBlocks, sessionId);		
		
	}
}
