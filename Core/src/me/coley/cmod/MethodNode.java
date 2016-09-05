package me.coley.cmod;

import java.util.List;

import me.coley.cmod.attribute.Attribute;

/**
 * A method.
 * 
 * @author Matt
 */
public class MethodNode extends MemberNode {

	public MethodNode(ClassNode owner) {
		super(owner);
	}

	@Override
	public List<Attribute> getAttributes() {
		return null;
	}
}
