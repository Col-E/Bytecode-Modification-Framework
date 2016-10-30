package io.github.bmf.util.mapping;

import java.util.ArrayList;
import java.util.List;

import io.github.bmf.util.Box;

public class ClassMapping {
    public final Box<String> name;
    public final List<MemberMapping> members = new ArrayList<MemberMapping>();

    public ClassMapping(String name) {
        this.name = new Box<String>(name);
    }

    public MemberMapping getMemberData(String name, String desc) {
        for (MemberMapping data : members) {
            if (data.name.value.equals(name) && data.desc.toString().equals(desc)) { return data; }
        }
        return null;
    }

}
