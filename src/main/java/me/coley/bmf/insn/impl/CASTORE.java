package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class CASTORE extends Instruction {
    public CASTORE() {
        super(OpType.ARRAY, Instruction.CASTORE, 1);
    }
}
