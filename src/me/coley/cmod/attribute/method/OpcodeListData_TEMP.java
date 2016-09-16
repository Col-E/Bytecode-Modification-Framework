package me.coley.cmod.attribute.method;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * A temporary garbage class containing the raw data of what should be a list of
 * method opcodes. <br>
 * Delete this and replace it with something better. Or refactor it to be not
 * shit.
 * 
 * @author Matt
 *
 */
public class OpcodeListData_TEMP {
	public byte[] data;
	public List<MethodException> exceptions = Lists.newArrayList();

	public void addException(MethodException mexeption) {
		exceptions.add(mexeption);
	}
}
