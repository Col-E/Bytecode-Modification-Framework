package io.github.bmf.attribute.clazz;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;
import io.github.bmf.util.MeasurableUtils;

import java.util.List;

public class AttributeInnerClasses extends Attribute {
    public List<InnerClass> classes;

    public AttributeInnerClasses(int name, List<InnerClass> classes) {
        super(name, AttributeType.INNER_CLASSES);
        this.classes = classes;
    }

    @Override
    public int getLength() {
        // u2: num_classes
        // ?: classes
        return BASE_LEN + 2 + MeasurableUtils.getLength(classes);
    }

}
