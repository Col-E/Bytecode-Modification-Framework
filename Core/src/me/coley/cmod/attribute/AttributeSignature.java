package me.coley.cmod.attribute;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeSignature extends Attribute {
	public int signature;
	public AttributeSignature(int name, int signature) {
		super(name, AttributeType.SIGNATURE);
		this.signature = signature;
	}
	@Override
	public int getLength() {
		// TODO Fill out length method
		return 0;
	}
}
