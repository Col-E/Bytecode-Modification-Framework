package me.coley.cmod;

import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.annotation.AttributeAnnotationDefault;
import me.coley.cmod.attribute.method.AttributeCode;

/**
 * A method.
 * 
 * @author Matt
 */
public class MethodNode extends MemberNode {
	/**
	 * Attribute: Method code properties.
	 */
	public AttributeCode code;
	/**
	 * Attribute: Annotations
	 */
	public AttributeAnnotationDefault annotationDefault;

	public MethodNode(ClassNode owner) {
		super(owner);
	}

	@Override
	public void addAttribute(Attribute attribute) {
		switch (attribute.type) {
		case CODE:
			code = (AttributeCode) attribute;
			break;
		case ANNOTATION_DEFAULT:
			annotationDefault = (AttributeAnnotationDefault) attribute;
			break;
		default:
			super.addAttribute(attribute);
		}
	}

	@Override
	public List<Attribute> getAttributes() {
		List<Attribute> attributes = Lists.newArrayList();
		if (code != null)
			attributes.add(code);
		if (annotationDefault != null)
			attributes.add(annotationDefault);
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
