package me.coley.cmod;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.IAttributeOwner;

/**
 * A member. Implementation is either a field or method.
 * 
 * @author Matt
 *
 */
public abstract class MemberNode implements IAttributeOwner {
	/**
	 * The class the member belongs to.
	 */
	public final ClassNode owner;
	/**
	 * Access. May be multiple combined.
	 */
	public int access;
	/**
	 * Index in the constant pool pointing to the member's name.
	 */
	public int name;
	/**
	 * Index in the constant pool pointing to the member's desc.
	 */
	public int desc;

	/**
	 * Creates the node and sets its owner.
	 * 
	 * @param owner
	 */
	public MemberNode(ClassNode owner) {
		this.owner = owner;
	}

	@Override
	public void addAttribute(Attribute attribute) {
		switch (attribute.type) {
		case SYNTHETIC:
			break;
		case SIGNATURE:
			break;
		case DEPRECATED:
			break;
		case RUNTIME_INVISIBLE_ANNOTATIONS:
			break;
		case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
			break;
		case RUNTIME_VISIBLE_ANNOTATIONS:
			break;
		case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
			break;
		default:
			break;
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName().replace("Node", "") + ": " + access + " , " + name + " , " + desc;
	}
}
