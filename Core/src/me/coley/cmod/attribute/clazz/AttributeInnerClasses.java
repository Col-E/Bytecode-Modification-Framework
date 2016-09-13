package me.coley.cmod.attribute.clazz;

import java.util.List;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;
import me.coley.cmod.util.MeasurableUtils;

public class AttributeInnerClasses extends Attribute {
	public List<InnerClass> classes;

	public AttributeInnerClasses(int name, List<InnerClass> classes) {
		super(name, AttributeType.INNER_CLASSES);
		this.classes = classes;
	}

	@Override
	public int getLength() {
		// u2: num_classes
		// ?: classes
		return BASE_LEN + 2 + MeasurableUtils.getLength(classes);
	}

}
