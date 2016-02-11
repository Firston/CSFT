package com.oep.elements_view.button;

import com.oep.html.AttributeHTMLElement;

/**
 * 
 * @author Anthony
 * @version 0.2
 * Версия 0.1 - 15.02.18 class ViewButton
 * Версия 0.2 - 15.03.11 class Button
 */
public class Button extends AbstractButton{
	
	private static Button instance;
	
	public static Button getInstance(){
		if(instance == null)
			instance = new Button();
		return instance;
	}

	@Override
	public void create(Object object) {
		if(object instanceof AttributeHTMLElement)
		  super.create(object);
	}
	
	@Override
	public  String getHTMLObject(Object object) {	
		create(object);
		return getContentButton();
	}

}
