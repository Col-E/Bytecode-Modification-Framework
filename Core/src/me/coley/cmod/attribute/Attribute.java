package me.coley.cmod.attribute;

/**
 * Generic Attribute.
 * 
 * @author Matt
 *
 */
public abstract class Attribute {
	/**
	 * An attribute's {@link me.coley.cmod.attribute.AttributeType type}.
	 */
	public final AttributeType type;
	/**
	 * Index of the attribute's name in the constant pool.
	 */
	public int name;

	public Attribute(int name, AttributeType type) {
		this.type = type;
		this.name = name;
	}

	public abstract int getLength();
}
