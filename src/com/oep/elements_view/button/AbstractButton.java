package com.oep.elements_view.button;

import com.oep.html.AbstractElementHTML;
import com.oep.html.AttributeHTMLElement;
import com.oep.html.HTMLObject;

public abstract class AbstractButton extends AbstractElementHTML implements HTMLObject{

	
	private String contentButton;

	/**
	 * Надпись на кнопке получают возовом метода getContent класса AttributeHTMLElement
	 */
	@Override
	public void create(Object object) {
		
		AttributeHTMLElement attr =(AttributeHTMLElement) object;
		contentButton = "";

		attr.setTag("div");
		if(attr.getClassName().equals(""))
		  attr.setClassName("c_button");
		contentButton += createHTMLElement(attr);		
	}

	public String getContentButton() {
		return contentButton;
	}
	    
}
