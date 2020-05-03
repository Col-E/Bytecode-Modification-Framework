package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IF_ICMPGE extends AbstractJump {
    public IF_ICMPGE(int jumpIndex) {
        super(OpType.ARRAY, Instruction.IF_ICMPGE, jumpIndex);
    }
}
