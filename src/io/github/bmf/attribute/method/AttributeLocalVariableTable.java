package io.github.bmf.attribute.method;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

/**
 * An attribute belonging to the
 * "{@link io.github.bmf.attribute.method.AttributeCode Code}" attribute of a method.
 * Contains information about local variables.
 * 
 * @author Matt
 *
 */
public class AttributeLocalVariableTable extends Attribute {
	/**
	 * The {@link io.github.bmf.attribute.method.LocalVariableTable table}.
	 */
	public LocalVariableTable variableTable;

	public AttributeLocalVariableTable(int name, LocalVariableTable variableTable) {
		super(name, AttributeType.LOCAL_VARIABLE_TABLE);
		this.variableTable = variableTable;
	}

	@Override
	public int getLength() {
		return BASE_LEN + variableTable.getLength();
	}

}
