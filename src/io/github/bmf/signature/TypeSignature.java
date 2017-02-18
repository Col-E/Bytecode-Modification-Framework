package io.github.bmf.signature;

public class TypeSignature extends Signature {

    public TypeSignature(SigArg type){
        this.type = type;
    }
    
    @Override
    public String toSignature() {
        return type.toArg();
    }

}
