package io.github.bmf.attribute.clazz;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

public class AttributeSourceFile extends Attribute {
	public int sourceFile;

	public AttributeSourceFile(int name, int sourceFile) {
		super(name, AttributeType.SOURCE_FILE);
		this.sourceFile = sourceFile;
	}

	@Override
	public int getLength() {
		// u2: source_index
		return BASE_LEN + 2;
	}
}
