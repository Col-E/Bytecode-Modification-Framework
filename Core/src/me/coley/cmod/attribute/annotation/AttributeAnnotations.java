package me.coley.cmod.attribute.annotation;

import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;

public class AttributeAnnotations extends Attribute {
	public List<Annotation> annotations = Lists.newArrayList();

	public AttributeAnnotations(int name, boolean invisible, List<Annotation> annotations) {
		super(name,
				invisible ? AttributeType.RUNTIME_INVISIBLE_ANNOTATIONS : AttributeType.RUNTIME_VISIBLE_ANNOTATIONS);
		this.annotations = annotations;
	}
}
