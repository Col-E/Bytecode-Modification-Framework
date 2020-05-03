package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractFieldInstruction;
import me.coley.bmf.insn.Instruction;

public class PUTFIELD extends AbstractFieldInstruction {
    public PUTFIELD( int fieldIndex) {
        super(Instruction.PUTFIELD, fieldIndex);
    }
}
