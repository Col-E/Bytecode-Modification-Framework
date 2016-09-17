package io.github.bmf.attribute.annotation;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

public class AttributeAnnotations extends Attribute {
	public byte[] data;

	public AttributeAnnotations(int name, boolean invisible, byte[] data) {
		super(name,
				invisible ? AttributeType.RUNTIME_INVISIBLE_ANNOTATIONS : AttributeType.RUNTIME_VISIBLE_ANNOTATIONS);
		this.data = data;
	}

	@Override
	public int getLength() {
		// TODO Change attrib length method later
		return data.length;
	}
}
