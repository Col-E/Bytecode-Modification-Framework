package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IFNULL extends AbstractJump {
    public IFNULL(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IFNULL, jumpIndex);
    }
}
