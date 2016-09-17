package io.github.bmf.util;

import java.util.Collection;

public class MeasurableUtils {
	public static int getLength(Collection<? extends  IMeasurable> items){
		int i = 0;
		for (IMeasurable m : items){
			i += m.getLength();
		}
		return i;
	}
}
