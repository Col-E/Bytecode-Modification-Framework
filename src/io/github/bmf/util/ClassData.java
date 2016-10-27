package io.github.bmf.util;

import java.util.ArrayList;

public class ClassData {

    public final Box<String> name;
    public final ArrayList<MemberData> members = new ArrayList<>();

    public ClassData(String name) {
        this.name = new Box<>(name);
    }

    public MemberData getMemberData(String name, String desc) {
        for (MemberData data : members) {
            if (data.name.value.equals(name) && data.desc.toString().equals(desc)) {
                return data;
            }
        }
        return null;
    }

}
