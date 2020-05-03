package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class GOTO extends AbstractJump {
    public GOTO(int jumpIndex) {
        super(OpType.FLOW_CONTROL, jumpIndex > Short.MAX_VALUE ? Instruction.GOTO_W : Instruction.GOTO, jumpIndex);
    }
}
