package com.oep.elements_view.button;

import com.oep.html.AttributeHTMLElement;

/**
 * @version 0.1
 * Версия - 15.03.11 class ViewButton
 * @author Anthony
 *
 */
public abstract class ViewButton {

	
	/**
	 * Проверка коммиссий на терминалах
	 * @version 15.03.11
	 * @return
	 */
	public static String button_checkCommissionTerminal(){

		AttributeHTMLElement attr = new AttributeHTMLElement();
		/*
		 * Вызов функции реализованной на JavaScript
		 */
		attr.addAttr("onClick", "checkCommissionForTerminal(), removeMessage()");
		attr.setContent("Проверить наличие комиссий на всех терминалах по услуге");
		return Button.getInstance().getHTMLObject(attr);
	}
	
	/**
	 * Выгрузка услуг из Рапиды
	 * @version 15.03.11
	 * @return
	 */
	public static String button_unLoadRapida(){

		AttributeHTMLElement attr = new AttributeHTMLElement();
		/*
		 * Вызов функции реализованной на JavaScript
		 */
		attr.addAttr("onClick", "unloadParamsOfServicesRapida()");
		attr.addAttr("onmousedown", "process(), removeMessage()");
		attr.setContent("Выгрузить параметры для оплаты по новым услугам RAPIDA");
		attr.setClassName("c_button_unload");
		return Button.getInstance().getHTMLObject(attr);
	}
	
	/**
	 * Выгрузка услуг из Рапиды
	 * @version 15.03.11
	 * @return
	 */
	public static String button_refreshUnLoadRapida(){

		AttributeHTMLElement attr = new AttributeHTMLElement();
		/*
		 * Вызов функции реализованной на JavaScript
		 */
		attr.addAttr("onClick", "process(), refreshParamsOfServicesRapida(), removeMessage()");
		attr.setContent("Обновить параметры оплаты услуг RAPIDA");
		attr.setClassName("c_button_unload");
		return Button.getInstance().getHTMLObject(attr);
	}
	
}
