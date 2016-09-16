package me.coley.cmod.attribute.method;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeStackMapTable extends Attribute {
	public byte[] data;
	public AttributeStackMapTable(int name, byte[] data) {
		super(name, AttributeType.STACK_MAP_TABLE);
		this.data = data;
	}
	@Override
	public int getLength() {
		// TODO Change attrib length method later
		return BASE_LEN + data.length;
	}
}
