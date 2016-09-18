package io.github.bmf.attribute.annotation;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;
import io.github.bmf.util.MeasurableUtils;

import java.util.List;

public class AttributeParameterAnnotations extends Attribute {
    /**
     * Each entry represents the annotation on a parameter. The indices in the
     * list match up to indices of parameters in the method.
     */
    public List<ParameterAnnotations> parametersAnnotations;

    public AttributeParameterAnnotations(int name, boolean invisible, List<ParameterAnnotations> parameterAnnotations) {
        super(name, invisible ? AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS
                : AttributeType.RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS);
        this.parametersAnnotations = parameterAnnotations;
    }

    @Override
    public int getLength() {
        // u1: num_parameters
        // ??: parameter_annotations
        return BASE_LEN + 1 + MeasurableUtils.getLength(parametersAnnotations);
    }
}
