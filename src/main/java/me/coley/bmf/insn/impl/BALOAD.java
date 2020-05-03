package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class BALOAD extends Instruction {
    public BALOAD() {
        super(OpType.ARRAY, Instruction.BALOAD, 1);
    }
}
