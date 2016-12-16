package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IF_ICMPLT extends AbstractJump {
    public IF_ICMPLT(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IF_ICMPLT, jumpIndex);
    }
}
