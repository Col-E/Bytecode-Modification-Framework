package io.github.bmf.attribute.method;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

import java.util.List;

public class AttributeExceptions extends Attribute {
    public List<Integer> exceptionIndicies;

    public AttributeExceptions(int name, List<Integer> exceptionIndicies) {
        super(name, AttributeType.EXCEPTIONS);
        this.exceptionIndicies = exceptionIndicies;
    }

    @Override
    public int getLength() {
        // u2: num_exceptions
        // u2[]: exceptions
        return BASE_LEN + 2 + 2 * exceptionIndicies.size();
    }
}
