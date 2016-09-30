package io.github.bmf.attribute;

import io.github.bmf.util.IMeasurable;

/**
 * Generic Attribute.
 *
 * @author Matt
 */
public abstract class Attribute implements IMeasurable {
	/**
	 * An attribute's {@link io.github.bmf.attribute.AttributeType type}.
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
}
