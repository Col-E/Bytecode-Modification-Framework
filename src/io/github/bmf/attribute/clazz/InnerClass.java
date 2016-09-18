package io.github.bmf.attribute.clazz;

import io.github.bmf.util.IMeasurable;

public class InnerClass implements IMeasurable {
    public int innerClassIndex;
    public int outerClassIndex;
    public int innerName;
    public int innerAccess;

    public InnerClass(int ici, int oci, int in, int ia) {
        this.innerAccess = ici;
        this.outerClassIndex = oci;
        this.innerName = in;
        this.innerAccess = ia;
    }

    @Override
    public int getLength() {
        // u2: inner_class_index
        // u2: outer_class_index
        // u2: inner_name
        // u2: inner_access
        return 8;
    }
}
