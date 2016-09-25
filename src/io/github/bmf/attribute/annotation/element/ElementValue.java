package io.github.bmf.attribute.annotation.element;

import io.github.bmf.util.IMeasurable;

/**
 * A structure used by annotations that represent different values depending on
 * its type.
 *
 * @author Matt
 */
public abstract class ElementValue implements IMeasurable {
    /**
     * <pre>
     * u1: tag
     * </pre>
     */
    protected final static int BASE_LEN = 1;
    public ElementValueType type;

    public ElementValue(ElementValueType type) {
        this.type = type;
    }
}
