package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractJump;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IFGT extends AbstractJump {
    public IFGT(int jumpIndex) {
        super(OpType.ARRAY, Instruction.IFGT, jumpIndex);
    }
}
