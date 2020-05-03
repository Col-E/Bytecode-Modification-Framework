package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class AASTORE extends Instruction {
    public AASTORE() {
        super(OpType.ARRAY, Instruction.AASTORE, 1);
    }
}
