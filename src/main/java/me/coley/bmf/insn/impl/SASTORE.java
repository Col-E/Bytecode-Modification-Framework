package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class SASTORE extends Instruction {
    public SASTORE() {
        super(OpType.ARRAY, Instruction.SASTORE, 1);
    }
}
