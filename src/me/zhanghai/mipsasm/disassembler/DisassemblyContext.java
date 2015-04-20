/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.assembler.Assemblable;
import me.zhanghai.mipsasm.assembler.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DisassemblyContext {

    private SortedMap<Integer, String> addressLabelMap = new TreeMap<>();

    private List<Assemblable> assemblableList = new ArrayList<>();

    private int labelIndex = 0;

    private String makeLabelName() {
        String name = "label_" + labelIndex;
        ++labelIndex;
        return name;
    }

    public String addLabel(int address) {
        String name = makeLabelName();
        addressLabelMap.put(address, name);
        return name;
    }

    public List<Assemblable> getAssemblableList() {
        return assemblableList;
    }

    public void appendAssemblable(Assemblable assemblable) {
        assemblableList.add(assemblable);
    }
}
