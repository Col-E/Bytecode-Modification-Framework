package io.github.bmf.attribute.annotation.element;

import com.google.common.collect.Maps;

import java.util.Map;



public enum ElementValueType {
	//@formatter:off
	CONST_VALUE_INDEX(new char[]{'B', 'C', 'D', 'F', 'I', 'J', 'S', 'Z', 'S'}),
	ENUM_CONST_VALUE(new char[]{'e'}),
	CLASS_INFO_INDEX(new char[]{'c'}),
	ANNOTATION_VALUE(new char[]{'@'}),
	ARRAY_VALUE(new char[]{'['});
	//@formatter:on
	
	private static Map<Character, ElementValueType> typeMap;
	private final char[] keys;
	
	ElementValueType(char[] keys){
		this.keys = keys;
		register(this);
	}
	/**
	 * Retrieves an ElementValueType by a given name.
	 * 
	 * @param tag
	 * @return
	 */
	public static ElementValueType fromType(char tag){
		return typeMap.get(tag);
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
		for (char type : keys){
			typeMap.put(type, this);
		}
	}
}
