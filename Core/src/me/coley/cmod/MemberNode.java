package me.coley.cmod;

import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.attribute.Attribute;

/**
 * A member. Implementation is either a field or method.
 * 
 * @author Matt
 *
 */
public abstract class MemberNode {
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
	 * A list of attributes the member has.<br>
	 * TODO: Do what was done to Attribute code here. Give field / method
	 * specific fields for attributes, then add general attributes here.
	 */
	public List<Attribute> attributes = Lists.newArrayList();

	/**
	 * Creates the node and sets its owner.
	 * 
	 * @param owner
	 */
	public MemberNode(ClassNode owner) {
		this.owner = owner;
	}

	/**
	 * Adds an attribute to the node.
	 * 
	 * @param attribute
	 */
	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName().replace("Node", "") + ": " + access + " , " + name + " , " + desc + " , "
				+ "Attributes[" + attributes.size() + "]";
	}
}
