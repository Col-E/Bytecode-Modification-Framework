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
		// TODO Fill out length method
		return 0;
	}
}
