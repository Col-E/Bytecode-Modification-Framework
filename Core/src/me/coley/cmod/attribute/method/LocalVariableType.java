package me.coley.cmod.attribute.method;

public class LocalVariableType {
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

}
