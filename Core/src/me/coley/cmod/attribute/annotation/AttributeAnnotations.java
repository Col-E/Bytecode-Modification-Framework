package me.coley.cmod.attribute.annotation;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeAnnotations extends Attribute {
	public byte[] data;

	public AttributeAnnotations(int name, boolean invisible, byte[] data) {
		super(name,
				invisible ? AttributeType.RUNTIME_INVISIBLE_ANNOTATIONS : AttributeType.RUNTIME_VISIBLE_ANNOTATIONS);
		this.data = data;
	}

	@Override
	public int getLength() {
		// TODO Fill out length method
		return 0;
	}
}
