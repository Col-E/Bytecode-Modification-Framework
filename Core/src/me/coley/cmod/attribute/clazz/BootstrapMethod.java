package me.coley.cmod.attribute.clazz;

import java.util.List;

import com.google.common.collect.Lists;

public class BootstrapMethod {
	public int methodReference;
	public List<Integer> arguments = Lists.newArrayList();

	public BootstrapMethod(int methodReference) {
		this.methodReference = methodReference;
	}

	public void addArgument(int i) {
		arguments.add(i);
	}
}
