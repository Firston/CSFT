package com.oep.elements_view.other;

import com.oep.html.AbstractElementHTML;
import com.oep.html.AttributeHTMLElement;

public class ViewCheckBox {

	private static String contentCheckBox;
	private static ViewCheckBox instance;	
	private AttributeHTMLElement attributeHTMLElement = null;
	
	public static ViewCheckBox getInstance(){
		if(instance == null)
			instance = new ViewCheckBox();
		return instance;
	}
	
	private void create(String id, String description, boolean defaultValue){
		
		contentCheckBox = "";
		contentCheckBox += AbstractElementHTML.createHTMLElemen("p",
																  null, 
																  null, 
																  null, 
																  description);
		attributeHTMLElement = new AttributeHTMLElement("input");
		attributeHTMLElement.setId(id);
		attributeHTMLElement.addAttr("type", "checkbox");
		attributeHTMLElement.addAttr("value", String.valueOf(defaultValue));
		attributeHTMLElement.addAttr("onchange", "changeCheckBox(this)");
		contentCheckBox += AbstractElementHTML.createHTMLElement(attributeHTMLElement);
		attributeHTMLElement.clearAttr();
		
		attributeHTMLElement.setTag("div");
		attributeHTMLElement.setId(null);
		attributeHTMLElement.setStyle("display: -webkit-box; margin: 1% 1% auto;");
		attributeHTMLElement.setContent(contentCheckBox);
		contentCheckBox = AbstractElementHTML.createHTMLElement(attributeHTMLElement);	
		
	}
	public synchronized String getCheckBox(String id, String description, boolean defaultValue){
		create(id, description, defaultValue);
		return contentCheckBox;
	}
}
