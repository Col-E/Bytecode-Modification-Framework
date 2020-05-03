package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DASTORE extends Instruction {
    public DASTORE() {
        super(OpType.ARRAY, Instruction.DASTORE, 1);
    }
}
