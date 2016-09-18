package io.github.bmf.attribute.method;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;
import io.github.bmf.util.MeasurableUtils;

import java.util.List;

public class AttributeLocalVariableTypeTable extends Attribute {
    public List<LocalVariableType> variableTypes;

    public AttributeLocalVariableTypeTable(int name, List<LocalVariableType> variableTypes) {
        super(name, AttributeType.LOCAL_VARIABLE_TYPE_TABLE);
        this.variableTypes = variableTypes;
    }

    @Override
    public int getLength() {
        // u2: num_variables
        // ??: variable_table
        return BASE_LEN + 2 + MeasurableUtils.getLength(variableTypes);
    }

}
