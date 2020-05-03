package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class RETURN extends Instruction {
    public RETURN() {
        super(OpType.RETURN, Instruction.RETURN, 1);
    }
}
