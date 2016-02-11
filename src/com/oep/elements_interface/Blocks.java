package com.oep.elements_interface;

import java.util.List;

import com.oep.elements_view.block.InfoBuildBlock;

/**
 * 
 * @author Anthony
 *
 */
public interface Blocks {

	/**
	 * @param pw
     * @param width - ширина блока родителя
	 * @param infoBuildBlocks 
	 */
	public abstract void buildBlocks(int width, List<InfoBuildBlock> infoBuildBlocks, String sessionId);
	
	/**
	 * Создание контента блоков
	 */
	public abstract String createContent(String header_key);
	
	/**
	 * @param sessionId
	 */
	public abstract void create(String sessionId);
}
