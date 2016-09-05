package me.coley.cmod;

import java.util.List;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;
import me.coley.cmod.attribute.clazz.InnerClass;

public class AttributeInnerClasses extends Attribute {
	public List<InnerClass> classes;

	public AttributeInnerClasses(int name, List<InnerClass> classes) {
		super(name, AttributeType.INNER_CLASSES);
		this.classes = classes;
	}

}
