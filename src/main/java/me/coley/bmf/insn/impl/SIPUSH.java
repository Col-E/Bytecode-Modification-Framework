package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;
import me.coley.bmf.insn.SingleValueInstruction;

public class SIPUSH extends SingleValueInstruction<Integer> {
    public SIPUSH(int value) {
        super(OpType.STACK, Instruction.SIPUSH, 3, value);
    }
}
