package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractMethodOpcode;
import io.github.bmf.opcode.Opcode;

public class INVOKEINTERFACE extends AbstractMethodOpcode {
    public int argWords;

    public INVOKEINTERFACE(int methodIndex, int argWords, int zero) {
        super(Opcode.INVOKEINTERFACE, 7, methodIndex);
        this.argWords = argWords;
    }
}
