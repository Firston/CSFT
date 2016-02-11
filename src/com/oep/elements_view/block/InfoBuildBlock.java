package com.oep.elements_view.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.oep.html.AttributeHTMLElement;
import com.oep.utils.Logger;

public class InfoBuildBlock {

	/*
	 * Процентное соотношение ширины блоков в линии (формат 0.00) 
	 */
	private List<Double> percentBlockWidth;
	
	/*
	 * Высота блоков в одной линии
	 */
	private int heightBlock;
	
	/*
	 * содержит  знаечния для заголовков блоков
	 * header_key_*
	 * header_label_*
	 */
	private Map<String, String> headers;
	/*
	 * Данные для отображения
	 */
	private Map<String, List<AttributeHTMLElement>> data;
	
	
	public InfoBuildBlock(String percentBlockWidth, int heightBlock){

		if(percentBlockWidth != null){
			this.percentBlockWidth = new ArrayList<Double>();
			List<String> listWidth = Arrays.asList(percentBlockWidth.split(","));
			headers = new TreeMap<String, String>();
			data = new TreeMap<String, List<AttributeHTMLElement>>();
			for(int i = 0; i < listWidth.size(); i++){
				this.percentBlockWidth.add(Double.valueOf(listWidth.get(i)));
				headers.put(AbstractBlocks.HEADER_KEY + "_" + i, null);
				headers.put(AbstractBlocks.HEADER_LABEL + "_" + i, null);
				data.put(AbstractBlocks.DATA_VALUE + "_" + i, null);
			}
		}
		this.heightBlock  = heightBlock;		
	}
	
	public void setPercentBlockWidth(List<Double> percentBlockWidth) {
		this.percentBlockWidth = percentBlockWidth;
	}

	public List<Double> getPercentBlockWidth() {
		return percentBlockWidth;
	}

	public void setHeightBlock(int heightBlock) {
		this.heightBlock = heightBlock;
	}

	public int getHeightBlock() {
		return heightBlock;
	}

	public Map<String, List<AttributeHTMLElement>> getData() {
		if(data == null)
			data = new TreeMap<String, List<AttributeHTMLElement>>();
		return data;
	}
	
	public Map<String, String> getHeaders() {
		if(headers == null)
			headers = new TreeMap<String, String>();
		return headers;
	}
	private boolean putIntoHeader(String key, Object value){
		
		if(headers.containsKey(key)){
			headers.put(key, value.toString());
			return true;
		}else{
			Logger.addLog("Error: Запись значения в  headers не возможна! Отсутствует ключ - " + key);
			return false; 
		}
	}
	
	private boolean addIntoData(String key, AttributeHTMLElement attr){
		if(data.containsKey(key)){
			if(data.get(key) == null) 
			  data.get(key).addAll(new ArrayList<AttributeHTMLElement>());
			data.get(key).add(attr);
			return true;
		}else{
			Logger.addLog("Error: Запись значения в data не возможна! Отсутствует ключ - " + key);
			return false;
		}
	}
	
	public boolean addHEADER_PARAM(int index, Object key, Object label){
		
		return (addHEADER_KEY(index, key)&& addHEADER_LABEL(index, label));	
	}
	
	private boolean addHEADER_KEY(int index, Object value){		
		return putIntoHeader(AbstractBlocks.HEADER_KEY + "_" + index, value);
		
	}
	
	private boolean addHEADER_LABEL(int index, Object value){		
		return putIntoHeader(AbstractBlocks.HEADER_LABEL + "_" + index, value);		
	}
	
	public boolean addDATAL(int index, AttributeHTMLElement attr){		
		return addIntoData(AbstractBlocks.DATA_VALUE + "_" + index, attr);		
	}
	
}
