package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IFEQ extends AbstractJump {
    public IFEQ(int jumpIndex) {
        super(OpType.ARRAY, Instruction.IFEQ, jumpIndex);
    }
}
