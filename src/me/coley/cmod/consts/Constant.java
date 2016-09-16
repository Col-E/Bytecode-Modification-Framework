package me.coley.cmod.consts;

/**
 * A generic constant class. An entry in a class's constant pool.
 * 
 * @author Matt
 *
 * @param <T>
 */
public abstract class Constant<T> {
	/**
	 * Constant type.
	 */
	public final ConstantType type;
	/**
	 * Constant's value. 
	 */
	public T value;

	public ConstantType getType() {
		return type;
	}

	public Constant(ConstantType type) {
		this(type, null);
	}

	public Constant(ConstantType type, T value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return type.toString() + " - " + value;
	}
}
