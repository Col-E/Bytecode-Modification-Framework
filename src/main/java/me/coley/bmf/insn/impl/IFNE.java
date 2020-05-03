package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IFNE extends AbstractJump {
    public IFNE(int jumpIndex) {
        super(OpType.ARRAY, Instruction.IFNE, jumpIndex);
    }
}
