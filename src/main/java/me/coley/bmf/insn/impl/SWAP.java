package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class SWAP extends Instruction {
    public SWAP() {
        super(OpType.STACK, Instruction.SWAP, 1);
    }
}
