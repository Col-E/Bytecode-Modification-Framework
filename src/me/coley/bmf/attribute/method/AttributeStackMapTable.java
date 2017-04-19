package me.coley.bmf.attribute.method;

import me.coley.bmf.attribute.Attribute;
import me.coley.bmf.attribute.AttributeType;

public class AttributeStackMapTable extends Attribute {
    public byte[] data;

    public AttributeStackMapTable(int name, byte[] data) {
        super(name, AttributeType.STACK_MAP_TABLE);
        this.data = data;
    }

    @Override
    public int getLength() {
        // TODO Change attrib length method later
        return data.length;
    }
}
