package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IFGE extends AbstractJump {
    public IFGE(int jumpIndex) {
        super(OpType.ARRAY, Instruction.IFGE, jumpIndex);
    }
}
