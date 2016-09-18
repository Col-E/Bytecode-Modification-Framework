package io.github.bmf;

import com.google.common.collect.Lists;
import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.field.AttributeConstantValue;

import java.util.List;

/**
 * A field.
 *
 * @author Matt
 */
public class FieldNode extends MemberNode {
    /**
     * Attribute: Finalized value of the field <i>(Will be null if field is not
     * final, or the value is not constant)</i>
     */
    public AttributeConstantValue value;

    public FieldNode(ClassNode owner) {
        super(owner);
    }

    @Override
    public void addAttribute(Attribute attribute) {
        switch (attribute.type) {
            case CONSTANT_VALUE:
                value = (AttributeConstantValue) attribute;
                break;
            default:
                super.addAttribute(attribute);
        }
    }

    @Override
    public List<Attribute> getAttributes() {
        List<Attribute> attributes = Lists.newArrayList();
        if (value != null)
            attributes.add(value);
        if (signature != null)
            attributes.add(signature);
        if (runtimeInvisibleAnnotations != null)
            attributes.add(runtimeInvisibleAnnotations);
        if (runtimeVisibleAnnotations != null)
            attributes.add(runtimeVisibleAnnotations);
        if (runtimeVisibleParamAnnotations != null)
            attributes.add(runtimeVisibleParamAnnotations);
        if (runtimeInvisibleParamAnnotations != null)
            attributes.add(runtimeInvisibleParamAnnotations);
        if (deprecated != null)
            attributes.add(deprecated);
        if (synthetic != null)
            attributes.add(synthetic);
        return attributes;
    }
}
