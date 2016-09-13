package me.coley.cmod.attribute.clazz;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeEnclosingMethod extends Attribute {
	public int classIndex;
	public int methodIndex;

	public AttributeEnclosingMethod(int name, int classIndex, int methodIndex) {
		super(name, AttributeType.SIGNATURE);
		this.classIndex = classIndex;
		this.methodIndex = methodIndex;
	}

	@Override
	public int getLength() {
		// u2: class_index
		// u2: method_index
		return BASE_LEN + 4;
	}
}
