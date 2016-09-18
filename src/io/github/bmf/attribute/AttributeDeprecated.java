package io.github.bmf.attribute;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

public class AttributeDeprecated extends Attribute {

	public AttributeDeprecated(int name) {
		super(name, AttributeType.DEPRECATED);
	}

	@Override
	public int getLength() {
		return BASE_LEN;
	}
}
