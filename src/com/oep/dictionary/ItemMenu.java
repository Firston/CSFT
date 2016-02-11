package com.oep.dictionary;

public enum ItemMenu {

	NONE(0){
		public String description(){
			return "";
		}
		public String image(){
			return "";
		}
	}, ROLES(1){
		public String description(){
			return "Роли";
		}
		public String image(){
			return "roles.png";
		}
	}, COMMISSIONSERVICES(2){
		public String description(){
			return "Коммиссия по услугам";
		}
		public String image(){
			return "commissionServices.png";
		}
	}, SUPPLIERS(3){
		public String description(){
			return "Постащики";
		}
		public String image(){
			return "suppliers.png";
		}
	}, COMMISSIONS (4){
		public String description(){
			return "Коммиссии";
		}
		public String image(){
			return "commissions.png";
		}
	}, USERS(5){
		public String description(){
			return "Пользователи";
		}
		public String image(){
			return "users.png";
		}
	}, SERVICES(6){
		public String description(){
				return "Услуги";
		}			
		public String image(){
			return "services.png";
		}
	}, SUBSERVICES(7){
		public String description(){
			return "Подуслуги";
		}
		public String image(){
			return "subServices.png";
		}
	}, DICTIONARIES(8){
		public String description(){
			return "Справочники";
		}
		public String image(){
			return "dictionaries.png";
		}
	}, TYPEOBJECT(9){
		public String description(){
			return "Элементы форм";
		}
		public String image(){
			return "typeObject.png";
		}
	}, CONTENTSUBSERVICES(10){
		public String description(){
			return "Элементы подуслуги";
		}
		public String image(){
			return "contentSubServices.png";
		}
	}, LISTTYPES(11){
		public String description(){
			return "Типы элементов";
		}
		public String image(){
			return "listTypes.png";
		}
	}, CONTENTDICTIONARIES(12){
		public String description(){
			return "Содержимое справочника";
		}
		public String image(){
			return "contentDictionaries.png";
		}
	}, SCRIPTS(13){
		public String description(){
			return "Скрипты";
		}
		public String image(){
			return "scripts.png";
		}
	}, SCRIPTSOBJECT(14){
		public String description(){
		    return "Скрипты элемента формы";
		}		
		public String image(){
			return "scriptsObject.png";
		}
	}, TERMINALS(15){
		public String description(){
		    return "Терминалы";
		}		
		public String image(){
			return "terminals.png";
		}
	}, PROFILEACCESS(16){
		public String description(){
		    return "Права доступа";
		}
		public String image(){
			return "profileAccess.png";
		}
	}, LISTPRIORITIES(17){
		public String description(){
		    return "Приоритет элементов";
		}
		public String image(){
			return "";
		}		
	}, SYSTEMTOOLS(18){ 
		public String description(){
		    return "Настройки системы";
		}
		public String image(){
			return "tools.png";
		}		
	}, MOREINFO(19){

		public String description(){
		    return "Дополнительная информация";
		}
		public String image(){
			return "moreInfo.png";
		}		
	}, UNLOAD(20){

		public String description(){
		    return "Вышрузка";
		}
		public String image(){
			return "unLoad.png";
		}
	}, CONTENTGROUPOBJECTS(21){
		public String description(){
		    return "Элементы группы";
		}
		public String image(){
			return "contentGroupObjects.png";
		}
	}, LISTGROUPOBJECTS(22){
		public String description(){
		    return "Группы сортировки";
		}
		public String image(){
			return "listGroupObjects.png";
		}
	}, COMMISSIONSERVICESDEFAULT(23){
		public String description(){
		    return "";
		}
		public String image(){
			return "";
		}
	}, STATISTIC(24){

		@Override
		public String description() {
			return "Статистика";
		}

		@Override
		public String image() {
			return "statistic.png";
		}
		
	}, DEFAULT(25){

		@Override
		public String description() {
			return "";
		}

		@Override
		public String image() {
			return "default.png";
		}
	}, ACCESS(26) {

		@Override
		public String description() {
			return "Конфиденциальность";
		}

		@Override
		public String image() {
			return "access.png";
		}
	}, TOOLS(27){

		@Override
		public String description() {
			return "Настройки";
		}

		@Override
		public String image() {
			return "tools.png";
		}
	}, SAVE_PROPERTIES(28){
		
		/*Объединить с SYSTEMTOOLS*/
		@Override
		public String description(){
		    return "Настройки системы";
		}
		@Override
		public String image(){
			return "tools.png";	
		}
	};
		
	 private int value;
	 
	 /*
	  * Возвращает русскоязычное описание
	  */	 
	 public abstract String description();
	 
	 /*
	  * Файл иконки
	  */
	 public abstract String image();

	 private  ItemMenu(int value){
		 this.setValue(value);
	 }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static ItemMenu getValue(String value){
		if(value == null || value.equals(""))
		  return NONE;
		for(ItemMenu menu : ItemMenu.values())
		  if(menu.toString().equalsIgnoreCase(value))
			return menu;
		return DEFAULT;
	}
	
	public static String getDescription(String key){
		
		if(key == null || key.equals(""))
		  return NONE.description();
		
		for(ItemMenu menu : ItemMenu.values()){
		  if(menu.toString().equalsIgnoreCase(key))
			return menu.description();
		}
		
		return DEFAULT.description();
	}
	
	public static String getImagePath(String key){
		
		if(key == null || key.equals(""))
			  return NONE.image() ;
		
		for(ItemMenu menu : ItemMenu.values()){
			  if(menu.toString().equalsIgnoreCase(key))
				return menu.image();
		}
		return DEFAULT.image();
	}
	
	
}
