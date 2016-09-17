package io.github.bmf.attribute.annotation;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

public class AttributeAnnotationDefault extends Attribute {
	public byte[] data;

	public AttributeAnnotationDefault(int name, byte[] data) {
		super(name, AttributeType.ANNOTATION_DEFAULT);
		this.data = data;
	}

	@Override
	public int getLength() {
		// TODO Change attrib length method later
		return data.length;
	}
}
