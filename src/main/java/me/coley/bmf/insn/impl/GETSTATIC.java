package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractFieldInstruction;
import me.coley.bmf.insn.Instruction;

public class GETSTATIC extends AbstractFieldInstruction {
    public GETSTATIC( int fieldIndex) {
        super(Instruction.GETSTATIC, fieldIndex);
    }
}
