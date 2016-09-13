package me.coley.cmod.attribute.clazz;

import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;
import me.coley.cmod.util.MeasurableUtils;

public class AttributeBootstrapMethods extends Attribute {
	public List<BootstrapMethod> methods = Lists.newArrayList();

	public AttributeBootstrapMethods(int name, List<BootstrapMethod> methods) {
		super(name, AttributeType.BOOTSTRAP_METHODS);
		this.methods = methods;
	}

	@Override
	public int getLength() {
		return BASE_LEN + MeasurableUtils.getLength(methods);
	}
}
