package me.coley.cmod.attribute.clazz;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeSourceFile extends Attribute {
	public int sourceFile;

	public AttributeSourceFile(int name, int sourceFile) {
		super(name, AttributeType.SIGNATURE);
		this.sourceFile = sourceFile;
	}
}
