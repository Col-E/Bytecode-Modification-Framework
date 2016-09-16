package me.coley.cmod.attribute;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeSynthetic extends Attribute {

	public AttributeSynthetic(int name) {
		super(name, AttributeType.SYNTHETIC);
	}

	@Override
	public int getLength() {
		return BASE_LEN;
	}
}
