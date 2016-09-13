package me.coley.cmod.attribute.clazz;

import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.util.IMeasurable;

public class BootstrapMethod implements IMeasurable {
	public int methodReference;
	public List<Integer> arguments = Lists.newArrayList();

	public BootstrapMethod(int methodReference) {
		this.methodReference = methodReference;
	}

	public void addArgument(int i) {
		arguments.add(i);
	}

	@Override
	public int getLength() {
		// u2: method_ref
		// u2: num_args
		// u2[]: args
		return 4 + 2 * arguments.size();
	}
}
