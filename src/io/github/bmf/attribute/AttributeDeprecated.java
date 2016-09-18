package io.github.bmf.attribute;

public class AttributeDeprecated extends Attribute {

    public AttributeDeprecated(int name) {
        super(name, AttributeType.DEPRECATED);
    }

    @Override
    public int getLength() {
        return BASE_LEN;
    }
}
