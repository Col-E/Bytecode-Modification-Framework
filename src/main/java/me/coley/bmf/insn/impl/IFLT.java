package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IFLT extends AbstractJump {
    public IFLT(int jumpIndex) {
        super(OpType.ARRAY, Instruction.IFLT, jumpIndex);
    }
}
