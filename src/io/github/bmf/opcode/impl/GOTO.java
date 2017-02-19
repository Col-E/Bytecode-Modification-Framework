package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class GOTO extends AbstractJump {
    public GOTO(int jumpIndex) {
        super(OpcodeType.FLOW_CONTROL, jumpIndex > Short.MAX_VALUE ? Opcode.GOTO_W : Opcode.GOTO, jumpIndex);
    }
}
