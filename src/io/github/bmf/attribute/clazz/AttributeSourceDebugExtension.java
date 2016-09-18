package io.github.bmf.attribute.clazz;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

import java.util.List;

public class AttributeSourceDebugExtension extends Attribute {
    /**
     * Holds extended debugging information which has no semantic effect on the
     * Java Virtual Machine. The information is represented using a modified
     * UTF-8 string with no terminating zero byte.
     */
    public List<Integer> data;

    public AttributeSourceDebugExtension(int name, List<Integer> data) {
        super(name, AttributeType.SOURCE_DEBUG_EXTENSION);
        this.data = data;
    }

    @Override
    public int getLength() {
        // u1[]: debug_extension[attribute_length]
        // TODO Verify that this is correct
        return BASE_LEN + data.size();
    }
}
