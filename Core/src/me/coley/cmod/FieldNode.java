package me.coley.cmod;

/**
 * A field.
 * @author Matt
 */
public class FieldNode extends MemberNode {
	public Object value;

	public FieldNode(ClassNode owner) {
		super(owner);
	}
}
