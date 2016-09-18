package io.github.bmf.attribute.annotation.element;

import io.github.bmf.attribute.annotation.Annotation;

public class ElementValueAnnotationValue extends ElementValue {
	/**
	 * Nested annotation structure.
	 */
	public Annotation annotation;

	public ElementValueAnnotationValue(Annotation annotation) {
		super(ElementValueType.ANNOTATION_VALUE);
		this.annotation = annotation;
	}

	@Override
	public int getLength() {
		return BASE_LEN + annotation.getLength();
	}
}
