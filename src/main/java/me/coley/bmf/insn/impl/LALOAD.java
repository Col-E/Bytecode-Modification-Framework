package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LALOAD extends Instruction {
    public LALOAD() {
        super(OpType.ARRAY, Instruction.LALOAD, 1);
    }
}
