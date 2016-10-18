package io.github.bmf.attribute.annotation.element;

import io.github.bmf.util.Measurable;

public class ElementValuePair implements Measurable {
    /**
     * Index in constant pool that indicated the pair's name.
     */
    public int name;
    /**
     * The value of the pair.
     */
    public ElementValue value;

    public ElementValuePair(int name, ElementValue value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public int getLength() {
        // u2: element_name_index
        // element_value: value
        return 2 + value.getLength();
    }
}
