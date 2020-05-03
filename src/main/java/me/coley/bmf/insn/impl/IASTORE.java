package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IASTORE extends Instruction {
    public IASTORE() {
        super(OpType.ARRAY, Instruction.IASTORE, 1);
    }
}
