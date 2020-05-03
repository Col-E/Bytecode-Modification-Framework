package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IFLE extends AbstractJump {
    public IFLE(int jumpIndex) {
        super(OpType.ARRAY, Instruction.IFLE, jumpIndex);
    }
}
