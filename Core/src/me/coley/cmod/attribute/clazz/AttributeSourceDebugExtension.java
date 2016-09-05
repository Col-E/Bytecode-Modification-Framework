package me.coley.cmod.attribute.clazz;

import java.util.List;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeSourceDebugExtension extends Attribute {
	/**
	 * Holds extended debugging information which has no semantic effect on the
	 * Java Virtual Machine. The information is represented using a modified
	 * UTF-8 string with no terminating zero byte.
	 */
	public List<Integer> data;

	public AttributeSourceDebugExtension(int name, List<Integer> data) {
		super(name, AttributeType.SIGNATURE);
		this.data = data;
	}

	@Override
	public int getLength() {
		// TODO Fill out length method
		return 0;
	}
}
