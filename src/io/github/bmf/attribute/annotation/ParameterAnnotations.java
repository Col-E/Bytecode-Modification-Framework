package io.github.bmf.attribute.annotation;

import java.util.List;

import io.github.bmf.util.IMeasurable;
import io.github.bmf.util.MeasurableUtils;

public class ParameterAnnotations implements IMeasurable{
	/**
	 * List of annotations belonging to the parameter.
	 */
	public List<Annotation> annotations;

	public ParameterAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	@Override
	public int getLength() {
		// u2: num_annotations
		// ??: annotations
		return 2 + MeasurableUtils.getLength(annotations);
	}
}
