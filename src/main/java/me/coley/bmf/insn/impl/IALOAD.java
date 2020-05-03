package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IALOAD extends Instruction {
    public IALOAD() {
        super(OpType.ARRAY, Instruction.IALOAD, 1);
    }
}
