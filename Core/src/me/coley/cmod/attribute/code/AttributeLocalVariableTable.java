package me.coley.cmod.attribute.code;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

/**
 * An attribute belonging to the
 * "{@link me.coley.cmod.attribute.AttributeCode Code}" attribute of a method.
 * Contains information about local variables.
 * 
 * @author Matt
 *
 */
public class AttributeLocalVariableTable extends Attribute {
	/**
	 * The {@link me.coley.cmod.attribute.code.LocalVariableTable table}.
	 */
	public LocalVariableTable variableTable;

	public AttributeLocalVariableTable(int name, LocalVariableTable variableTable) {
		super(name, AttributeType.LOCAL_VARIABLE_TABLE);
		this.variableTable = variableTable;
	}

}
