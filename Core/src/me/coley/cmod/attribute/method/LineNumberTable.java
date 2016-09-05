package me.coley.cmod.attribute.method;

import java.util.Map;

import com.google.common.collect.Maps;

public class LineNumberTable {
	public Map<Integer, Integer> indexToLine = Maps.newHashMap();

	public void add(int startPC, int lineNumber) {
		indexToLine.put(startPC, lineNumber);
	}
}
