package io.github.bmf.attribute;

import io.github.bmf.util.IMeasurable;

/**
 * Generic Attribute.
 *
 * @author Matt
 */
public abstract class Attribute implements IMeasurable {
	/**
	 * TODO: So when I made this, I thought it would include nameIndex and
	 * length... but now that doesn't seem to make sense. Should this just be
	 * removed?
	 */
	protected final static int BASE_LEN = 6;
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
