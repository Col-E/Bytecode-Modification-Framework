package me.coley.cmod.consts;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Enumeration for handling constant pool entry types.
 * 
 * @author Matt
 */
public enum ConstantType {
	//@formatter:off
	UTF8(0x1), 
	INT(0x3),
	FLOAT(0x4),
	LONG(0x5), 
	DOUBLE(0x6), 
	CLASS(0x7), 
	STRING(0x8), 
	FIELD(0x9), 
	METHOD(0xA), 
	INTERFACE_METHOD(0xB),
	NAME_TYPE(0xC), 
	METHOD_HANDLE(0xF), 
	METHOD_TYPE(0x10), 
	INVOKEDYNAMIC(0x12);
	//@formatter:on

	/**
	 * The tag of the Constant type.
	 */
	private static Map<Integer, ConstantType> typeMap;
	private final int tag;

	ConstantType(int tag) {
		this.tag = tag;
		register(this);
	}

	/**
	 * Retrieves a ConstantType instance from a given tag.
	 * 
	 * @param tag
	 * @return
	 */
	public static ConstantType fromTag(int tag) {
		return typeMap.get(tag);
	}
	

	/**
	 * Checks if given tag matches a const type.
	 * 
	 * @param tag
	 * @param type
	 * @return
	 */
	public static boolean matches(int tag, ConstantType type) {
		return (tag & type.tag) != 0;
	}

	/**
	 * Registers the ConstantType's tag to an instance.
	 * 
	 * @param type
	 */
	private void register(ConstantType type) {
		if (typeMap == null) {
			typeMap = Maps.newHashMap();
		}
		typeMap.put(tag, this);
	}

	public int getTag() {
		return tag;
	}

	@Override
	public String toString() {
		return name().replace("_", " ").toLowerCase();
	}
}
