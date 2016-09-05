package me.coley.cmod.attribute.annotation;

import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeParameterAnnotations extends Attribute {
	public List<ParameterAnnotations> annotations = Lists.newArrayList();

	public AttributeParameterAnnotations(int name, boolean invisible, List<ParameterAnnotations> annotations) {
		super(name, invisible ? AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS
				: AttributeType.RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS);
		this.annotations = annotations;
	}
}
