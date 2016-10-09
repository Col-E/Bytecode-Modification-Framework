package io.github.bmf.attribute.method;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;
import io.github.bmf.util.MeasurableUtils;

import java.util.List;

public class AttributeLocalVariableTypeTable extends Attribute {
    public List<LocalVariableType> localTypes;

    public AttributeLocalVariableTypeTable(int name, List<LocalVariableType> localTypes) {
        super(name, AttributeType.LOCAL_VARIABLE_TYPE_TABLE);
        this.localTypes = localTypes;
    }

    @Override
    public int getLength() {
        // u2: num_variables
        // ??: variable_table
        return 2 + MeasurableUtils.getLength(localTypes);
    }
}
