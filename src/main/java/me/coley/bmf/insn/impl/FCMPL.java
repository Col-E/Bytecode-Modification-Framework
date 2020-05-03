package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FCMPL extends Instruction {
    public FCMPL() {
        super(OpType.STACK, Instruction.FCMPL, 1);
    }
}
