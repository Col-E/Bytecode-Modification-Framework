package io.github.bmf.consts.mapping;

import io.github.bmf.consts.ConstUTF8;
import io.github.bmf.signature.Signature;

public class ConstSignature extends ConstUTF8 {
    public Signature sig;

    public ConstSignature(Signature sig) {
        this.sig = sig;
    }

    @Override
    public String getValue() {
        return sig.toSignature();
    }

    @Override
    public void setValue(String value) {
        throw new RuntimeException("TODO: What should be done here?");
    }
}
