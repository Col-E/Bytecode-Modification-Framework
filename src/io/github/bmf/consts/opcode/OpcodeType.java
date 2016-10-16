package io.github.bmf.consts.opcode;

import java.util.Map;

import com.google.common.collect.Maps;

public enum OpcodeType {
    //@formatter:off
    WIDE(Opcode.WIDE);
    //@formatter:on

    /**
     * Map of values to types.
     */
    private static Map<Integer, OpcodeType> typeMap;
    private final int value;

    OpcodeType(int value) {
        this.value = value;
        register(this);
    }

    /**
     * Retrieves an OpcodeType instance from a value.
     * 
     * @param value
     * @return
     */
    public static OpcodeType fromValue(int value) {
        return typeMap.get(value);
    }

    /**
     * Registers the OpcodeType's value to an instance.
     *
     * @param type
     */
    private void register(OpcodeType type) {
        if (typeMap == null) {
            typeMap = Maps.newHashMap();
        }
        typeMap.put(type.value, type);
    }

    public int getValue() {
        return value;
    }

}
