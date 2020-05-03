package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class CALOAD extends Instruction {
    public CALOAD() {
        super(OpType.ARRAY, Instruction.CALOAD, 1);
    }
}
