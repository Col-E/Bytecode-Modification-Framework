package me.coley.cmod.attribute.method;

import me.coley.cmod.util.IMeasurable;

public class LocalVariableType implements IMeasurable {
	public int start;
	public int len;
	public int name;
	public int signature;
	public int index;

	public LocalVariableType(int start, int len, int name, int signature, int index) {
		this.start = start;
		this.len = len;
		this.name = name;
		this.signature = signature;
		this.index = index;
	}

	@Override
	public int getLength() {
		// u2: start_pc
		// u2: length
		// u2: name_index
		// u2: signature_index
		// u2: index
		return 10;
	}
}
