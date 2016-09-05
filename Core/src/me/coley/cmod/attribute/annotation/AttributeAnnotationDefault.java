package me.coley.cmod.attribute.annotation;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeAnnotationDefault extends Attribute {
	public byte[] data;

	public AttributeAnnotationDefault(int name, byte[] data) {
		super(name, AttributeType.ANNOTATION_DEFAULT);
		this.data = data;
	}

	@Override
	public int getLength() {
		// TODO Fill out length method
		return 0;
	}
}
