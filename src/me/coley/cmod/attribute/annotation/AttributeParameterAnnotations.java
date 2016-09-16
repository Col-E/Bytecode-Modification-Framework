package me.coley.cmod.attribute.annotation;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeParameterAnnotations extends Attribute {
	public byte[] data;

	public AttributeParameterAnnotations(int name, boolean invisible, byte[] data) {
		super(name, invisible ? AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS
				: AttributeType.RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS);
		this.data = data;
	}

	@Override
	public int getLength() {
		// TODO Change attrib length method later
		return BASE_LEN + data.length;
	}
}
