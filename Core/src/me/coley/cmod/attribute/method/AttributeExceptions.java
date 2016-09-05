package me.coley.cmod.attribute.method;

import java.util.List;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeExceptions extends  Attribute{
	public List<Integer> exceptionIndicies;

	public AttributeExceptions(int name, List<Integer> exceptionIndicies) {
		super(name, AttributeType.EXCEPTIONS);
		this.exceptionIndicies = exceptionIndicies;
	}

	@Override
	public int getLength() {
		// TODO Fill out length method
		return 0;
	}
}
