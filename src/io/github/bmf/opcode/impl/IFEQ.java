package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IFEQ extends AbstractJump {
    public IFEQ(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IFEQ, jumpIndex);
    }
}
