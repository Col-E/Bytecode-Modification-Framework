package me.coley.cmod;

import java.util.List;

import me.coley.cmod.attribute.Attribute;

/**
 * A field.
 * @author Matt
 */
public class FieldNode extends MemberNode  {
	public Object value;

	public FieldNode(ClassNode owner) {
		super(owner);
	}

	@Override
	public List<Attribute> getAttributes() {
		return null;
	}
}
