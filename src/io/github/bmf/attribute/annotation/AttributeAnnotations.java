package io.github.bmf.attribute.annotation;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;
import io.github.bmf.util.MeasurableUtils;

import java.util.List;

public class AttributeAnnotations extends Attribute {
	public List<Annotation> annotations;

	public AttributeAnnotations(int name, boolean invisible, List<Annotation> annotations) {
		super(name,
				invisible ? AttributeType.RUNTIME_INVISIBLE_ANNOTATIONS : AttributeType.RUNTIME_VISIBLE_ANNOTATIONS);
		this.annotations = annotations;
	}

	@Override
	public int getLength() {
		// u2: num_annotations
		// ??: annotations
		return BASE_LEN + 2 + MeasurableUtils.getLength(annotations);
	}
}
