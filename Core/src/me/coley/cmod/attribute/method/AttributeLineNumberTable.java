package me.coley.cmod.attribute.method;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

/**
 * An attribute belonging to the
 * "{@link me.coley.cmod.attribute.method.AttributeCode Code}" attribute of a method.
 * The table correlates opcode indices with java line numbers <i>(For
 * debugging).</i>
 */
public class AttributeLineNumberTable extends Attribute {
	/**
	 * The {@link me.coley.cmod.attribute.method.LineNumberTable table}.
	 */
	public LineNumberTable lines;

	public AttributeLineNumberTable(int name, LineNumberTable lines) {
		super(name, AttributeType.LINE_NUMBER_TABLE);
		this.lines = lines;
	}

	@Override
	public int getLength() {
		// TODO Fill out length method
		return 0;
	}

}
