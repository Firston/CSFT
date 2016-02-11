package com.oep.elements_interface;

import java.io.PrintWriter;

public interface View<T> {

	/**
	 * Отображение переданного объекта t в выходном потоке pw
	 * @param pw
	 * @param t
	 * 
	 * Не реализован. Не используется. 
	 * Расширение и модификация функционала. 
	 */
	public void view(PrintWriter pw, T t);
}
