package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DALOAD extends Instruction {
    public DALOAD() {
        super(OpType.ARRAY, Instruction.DALOAD, 1);
    }
}
