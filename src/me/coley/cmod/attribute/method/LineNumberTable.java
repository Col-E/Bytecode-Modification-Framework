package me.coley.cmod.attribute.method;

import java.util.Map;

import com.google.common.collect.Maps;

import me.coley.cmod.util.IMeasurable;

public class LineNumberTable  implements IMeasurable {
	public Map<Integer, Integer> indexToLine = Maps.newHashMap();

	public void add(int startPC, int lineNumber) {
		indexToLine.put(startPC, lineNumber);
	}

	@Override
	public int getLength() {
		// u2: num_lines
		// [u2: start_pc, u2: line_number] 
		return 2 + (4 * indexToLine.size());
	}
}
