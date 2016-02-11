package com.oep.elements_view.block;

import java.util.List;

import com.oep.elements_interface.Blocks;
import com.oep.html.AbstractElementHTML;
import com.oep.html.AttributeHTMLElement;

public abstract class AbstractBlocks extends AbstractElementHTML implements Blocks{
	
	public static final String HEADER_KEY = "header_key";
	
	public static final String HEADER_LABEL = "header_label";
	
	public static final String DATA_VALUE = "data_value";
	
	private int width;
	
	public static String contentBlocks;
	
	private String sessionId; 
	
	@Override
	public synchronized void buildBlocks(int width, List<InfoBuildBlock> infoBuildBlocks,
			String sessionId) {

		this.sessionId = sessionId;
		this.width = width;
		AttributeHTMLElement attr = new AttributeHTMLElement("div");
		contentBlocks = "";
		String c_blocks = "";		
		int numberLine = 0;
		
		for(InfoBuildBlock infoBuildBlock : infoBuildBlocks){

		  int indexBlock = 0;		  
		  String block = "";
		  for(double percent : infoBuildBlock.getPercentBlockWidth()){
			  Object header_key = infoBuildBlock.getHeaders().get(HEADER_KEY + "_" + indexBlock);
			  
			  attr.setId("block_" + indexBlock + "_" + (header_key != null ? header_key.toString() : ""));
			  attr.setClassName("div_block");
			  attr.setStyle("width:" + width * percent + "px;height:" + (infoBuildBlock.getHeightBlock()) + "px;");// 
			  attr.setContent(createBlock(numberLine, indexBlock, infoBuildBlock, attr));
			  
			  block += createHTMLElement(attr);
			  indexBlock++;
		  }
		  
		  attr.setId("lineBlock_" + (++numberLine));
		  attr.setClassName(null);
		  attr.setStyle("height:" + infoBuildBlock.getHeightBlock() + "px;");
		  attr.setContent(block);
		  
		  c_blocks += createHTMLElement(attr);		  
		}		

		attr.setId(null);
		attr.setClassName("c_blocks");
		attr.setStyle(null);
		attr.setContent(c_blocks);
		
		contentBlocks = createHTMLElement(attr);
	}
	
	/**
	 * 
	 * @param numberLine - номер линии. начинается с 1
	 * @param indexBlock - индекс блока в линии. начинается с 0
	 * @param infoBuildBlock - содержит информацию о :
	 * 							  высоте блока,
	 * 							  заголовке,
	 * 							  объектах внутри блока
	 * @param attr
	 * @return
	 */
	private String createBlock( int numberLine, int indexBlock, InfoBuildBlock infoBuildBlock, AttributeHTMLElement attr){
		
		if(attr.getClassName().contains("div_block")){

			attr = new AttributeHTMLElement("div", 
											"block_" + numberLine + "_" + indexBlock,
											"c_block", 
											"width:" + (width * infoBuildBlock.getPercentBlockWidth().get(indexBlock) - 5) + "px;" +
													   "height:" + (infoBuildBlock.getHeightBlock() - 5) + "px;" +
													   "overflow-y: auto;",
											null);
			attr.setContent(createBlock(numberLine, indexBlock, infoBuildBlock, attr));
			
			return createHTMLElement(attr);
		}if(attr.getClassName().contains("c_block")){

			String result = "";
			attr = new AttributeHTMLElement("span", 
											"labelBlock_" + infoBuildBlock.getHeaders().get(HEADER_KEY + "_" + indexBlock), 
											"c_label_nameBlock", 
											null, 
											infoBuildBlock.getHeaders().get(HEADER_LABEL + "_" + indexBlock));
			result += createHTMLElement(attr);		
			result += ViewBlocks.getInstance().createContent(infoBuildBlock.getHeaders().get(HEADER_KEY + "_" + indexBlock));
			
			return result;
		}
		return null;
	}
}
