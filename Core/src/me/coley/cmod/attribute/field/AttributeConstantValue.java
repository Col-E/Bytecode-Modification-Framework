package me.coley.cmod.attribute.field;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeConstantValue extends Attribute {
	public int constantIndex;

	public AttributeConstantValue(int name, int constantIndex) {
		super(name, AttributeType.CONSTANT_VALUE);
		this.constantIndex = constantIndex;
	}

	@Override
	public int getLength() {
		// TODO Fill out length method
		return 0;
	}
}
