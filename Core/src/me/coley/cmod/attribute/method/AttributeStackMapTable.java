package me.coley.cmod.attribute.method;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeStackMapTable extends Attribute {

	public AttributeStackMapTable(int name) {
		super(name, AttributeType.STACK_MAP_TABLE);
	}

}
