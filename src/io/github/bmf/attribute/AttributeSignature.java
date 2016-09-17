package io.github.bmf.attribute;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

public class AttributeSignature extends Attribute {
	public int signature;
	public AttributeSignature(int name, int signature) {
		super(name, AttributeType.SIGNATURE);
		this.signature = signature;
	}
	@Override
	public int getLength() {
		// u2: signature_index
		return BASE_LEN + 2;
	}
}
