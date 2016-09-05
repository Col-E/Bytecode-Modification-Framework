package me.coley.cmod.attribute.annotation;

import java.util.List;

import com.google.common.collect.Lists;

public class ParameterAnnotations {
	public List<Annotation> annotations = Lists.newArrayList();

	public void addAnnotation(Annotation annotation) {
		annotations.add(annotation);
	}
}
