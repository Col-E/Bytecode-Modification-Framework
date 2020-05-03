package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class SALOAD extends Instruction {
    public SALOAD() {
        super(OpType.ARRAY, Instruction.SALOAD, 1);
    }
}
