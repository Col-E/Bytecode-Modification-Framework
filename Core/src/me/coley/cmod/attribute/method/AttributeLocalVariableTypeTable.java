package me.coley.cmod.attribute.method;

import java.util.List;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeLocalVariableTypeTable extends Attribute {
	public List<LocalVariableType> variableTypes;

	public AttributeLocalVariableTypeTable(int name, List<LocalVariableType> variableTypes) {
		super(name, AttributeType.LOCAL_VARIABLE_TYPE_TABLE);
		this.variableTypes = variableTypes;
	}

	@Override
	public int getLength() {
		// TODO Fill out length method
		return 0;
	}

}
