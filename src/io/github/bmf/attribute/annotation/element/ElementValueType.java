package io.github.bmf.attribute.annotation.element;

import java.util.Map;

import com.google.common.collect.Maps;



public enum ElementValueType {
	//@formatter:off
	ConstValueIndex(new String[]{"B", "C", "D", "F", "I", "J", "S", "Z", "S"}), 
	EnumConstValue(new String[]{"e"}),
	ClassInfoIndex(new String[]{"c"}), 
	AnnotationValue(new String[]{"@"}), 
	ArrayValue(new String[]{"["});
	//@formatter:on
	
	private static Map<String, ElementValueType> typeMap;
	private final String[] keys;
	
	ElementValueType(String[] keys){
		this.keys = keys;
		register(this);
	}
	/**
	 * Retrieves an ElementValueType by a given name.
	 * 
	 * @param type
	 * @return
	 */
	public static ElementValueType fromType(String type){
		return typeMap.get(type);
	}
	/**
	 * Registers the ElementValueType's associated types to an instance.
	 * 
	 * @param valueType
	 */
	private void register(ElementValueType valueType) {
		if (typeMap == null){
			typeMap = Maps.newHashMap();
		}
		for (String type : keys){
			typeMap.put(type, this);
		}
	}
}
