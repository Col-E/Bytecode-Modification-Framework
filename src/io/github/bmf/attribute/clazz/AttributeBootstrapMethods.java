package io.github.bmf.attribute.clazz;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;
import io.github.bmf.util.MeasurableUtils;

import java.util.List;

public class AttributeBootstrapMethods extends Attribute {
    public List<BootstrapMethod> methods;

    public AttributeBootstrapMethods(int name, List<BootstrapMethod> methods) {
        super(name, AttributeType.BOOTSTRAP_METHODS);
        this.methods = methods;
    }

    @Override
    public int getLength() {
        // u2: num_methods
        // ??:
        return 2 + MeasurableUtils.getLength(methods);
    }
}
