package io.github.bmf.attribute;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

public class AttributeSynthetic extends Attribute {

	public AttributeSynthetic(int name) {
		super(name, AttributeType.SYNTHETIC);
	}

	@Override
	public int getLength() {
		return BASE_LEN;
	}
}
