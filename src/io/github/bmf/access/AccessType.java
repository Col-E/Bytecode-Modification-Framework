package io.github.bmf.access;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Enumeration for handling access flag types.
 *
 * @author Matt
 */
public enum AccessType {
    //@formatter:off
    ABSTRACT(0x0400),
    ANNOTATION(0x2000),
    BDIDGE(0x0040),
    ENUM(0x4000),
    FINAL(0x0010),
    INTERFACE(0x0200),
    NATIVE(0x0100),
    PRIVATE(0x0002),
    PROTECTED(0x0004),
    PUBLIC(0x0001),
    STATIC(0x0008),
    STRICT(0x0800),
    SUPER(0x0020),
    SYNCHRONIZED(0x0020),
    SYNTHETIC(0x1000),
    TRANSIENT(0x0080),
    VARARGS(0x0080),
    VOLATILE(0x0040);
    //@formatter:on

   /**
    * Map of values to types.
    */
    private static Map<Integer, AccessType> typeMap;
    /**
     * The tag of the Constant type.
     */
    private final int tag;

    AccessType(int tag) {
        this.tag = tag;
        register(this);
    }

    /**
     * Retrieves a ConstantType instance from a given tag.
     *
     * @param tag
     * @return
     */
    public static AccessType fromTag(int tag) {
        return typeMap.get(tag);
    }

    /**
     * Checks if given access flags match a single type.
     *
     * @param combinedAccess
     * @param access
     * @return
     */
    public static boolean matches(int combinedAccess, AccessType access) {
        return (combinedAccess & access.tag) != 0;
    }

    /**
     * Registers the ConstantType's tag to an instance.
     *
     * @param type
     */
    private void register(AccessType type) {
        if (typeMap == null) {
            typeMap = Maps.newHashMap();
        }
        typeMap.put(tag, type);
    }

    public int getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return name().replace("ACC_", " ").toLowerCase();
    }
}
