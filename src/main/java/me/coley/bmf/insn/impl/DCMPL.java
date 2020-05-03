package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DCMPL extends Instruction {
    public DCMPL() {
        super(OpType.STACK, Instruction.DCMPL, 1);
    }
}
