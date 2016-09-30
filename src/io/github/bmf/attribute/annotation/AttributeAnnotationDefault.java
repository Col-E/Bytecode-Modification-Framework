package io.github.bmf.attribute.annotation;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;
import io.github.bmf.attribute.annotation.element.ElementValue;

public class AttributeAnnotationDefault extends Attribute {
    public ElementValue value;

    public AttributeAnnotationDefault(int name, ElementValue value) {
        super(name, AttributeType.ANNOTATION_DEFAULT);
        this.value = value;
    }

    @Override
    public int getLength() {
        // element_value: value
        return value.getLength();
    }
}
