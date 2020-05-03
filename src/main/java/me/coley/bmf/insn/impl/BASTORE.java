package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class BASTORE extends Instruction {
    public BASTORE() {
        super(OpType.ARRAY, Instruction.BASTORE, 1);
    }
}
