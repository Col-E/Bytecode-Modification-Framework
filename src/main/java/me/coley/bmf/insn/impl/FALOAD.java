package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FALOAD extends Instruction {
    public FALOAD() {
        super(OpType.ARRAY, Instruction.FALOAD, 1);
    }
}
