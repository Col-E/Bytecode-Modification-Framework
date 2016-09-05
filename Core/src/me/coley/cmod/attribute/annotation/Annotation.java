package me.coley.cmod.attribute.annotation;

import java.util.List;

import com.google.common.collect.Lists;

public class Annotation {
	public int type;
	public List<ElementPair> pairs = Lists.newArrayList();

	public Annotation(int type) {
		this.type = type;
	}

	public void addPair(ElementPair pair) {
		pairs.add(pair);
	}
}
