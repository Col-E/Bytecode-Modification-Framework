package io.github.bmf.attribute.clazz;

import io.github.bmf.util.Measurable;

public class InnerClass implements Measurable {
    public int innerClassIndex;
    public int outerClassIndex;
    public int innerName;
    public int innerAccess;

    public InnerClass(int innerIndex, int outerIndex, int cInnerName, int innerAccess) {
        this.innerClassIndex = innerIndex;
        this.outerClassIndex = outerIndex;
        this.innerName = cInnerName;
        this.innerAccess = innerAccess;
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
