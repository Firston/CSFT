package com.oep.elements_interface;

import java.util.Map;

import com.oep.process.ProcessForeground;

public interface Properties {

	public abstract boolean save(Map<String, Object> map, ProcessForeground process);
}
