package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FASTORE extends Instruction {
    public FASTORE() {
        super(OpType.ARRAY, Instruction.FASTORE, 1);
    }
}
