package io.github.bmf.attribute.method;

public class MethodException {
	/**
	 * Opcode indices in the method.
	 */
	public int start, end, handler;
	/** Index in the constant pool of the type of exeption handled. */
	public int type;
}
