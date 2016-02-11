package com.oep.process;

import com.oep.dictionary.ListBackgoundProcess;

/**
 * ПРОТОТИП ЕЩЕ НЕ ПРИДУМАЛ ДЛЯ ЧЕГО =)
 * @author Anthony
 *
 */
public interface Background extends Process{
	
	public abstract void run(ListBackgoundProcess event, Object...objects);

}
