package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IF_ACMPEQ extends AbstractJump {
    public IF_ACMPEQ(int jumpIndex) {
        super(OpType.ARRAY, Instruction.IF_ACMPEQ, jumpIndex);
    }
}
