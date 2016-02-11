package com.oep.process;

/**
 * ПРОТОТИП ЕЩЕ НЕ ПРИДУМАЛ ДЛЯ ЧЕГО =)
 * @author Anthony
 *
 */
public interface Foreground extends Process{

	public abstract void run (String sessionId, Object...objects);
}
