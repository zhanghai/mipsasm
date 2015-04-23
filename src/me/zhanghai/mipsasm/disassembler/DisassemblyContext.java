/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.disassembler;

import me.zhanghai.mipsasm.Constants;
import me.zhanghai.mipsasm.assembler.Assemblable;
import me.zhanghai.mipsasm.assembler.SpaceDirective;
import me.zhanghai.mipsasm.assembler.StorageDirective;
import me.zhanghai.mipsasm.util.BitArray;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class DisassemblyContext {

    private Integer labelIndex = 0;

    private NavigableMap<Integer, String> addressLabelMap = new TreeMap<>();

    // WONTFIX: Maximum address can be checked when addLabel() is called, however this adds the overhead of knowing the
    // length of the input stream during the first pass.
    private Integer address = 0;

    private NavigableMap<Integer, Assemblable> addressAssemblableMap = new TreeMap<>();

    private String makeLabelName() {
        String name = "label_" + labelIndex;
        ++labelIndex;
        return name;
    }

    public String addLabel(Integer address) {
        String name = addressLabelMap.get(address);
        if (name != null) {
            return name;
        }
        name = makeLabelName();
        addressLabelMap.put(address, name);
        return name;
    }

    public NavigableMap<Integer, String> getAddressLabelMap() {
        return addressLabelMap;
    }

    public String getLabel(Integer address) {
        return addressLabelMap.get(address);
    }

    public NavigableMap<Integer, Assemblable> getAddressAssemblableMap() {
        return addressAssemblableMap;
    }

    public Integer getAddress() {
        return address;
    }

    public void appendAssemblable(Assemblable assemblable, int length) {
        addressAssemblableMap.put(address, assemblable);
        address += length;
    }

    private boolean isByteStorageDirective(Assemblable assemblable) {
        return assemblable != null && assemblable instanceof StorageDirective
                && ((StorageDirective) assemblable).getValue().length() == Constants.BYTE_LENGTH;
    }

    private void packByteStorageDirective() {

        for (Integer address1 = addressAssemblableMap.firstKey(); address1 != null;
             address1 = addressAssemblableMap.higherKey(address1)) {

            if (address1 % Constants.BYTES_PER_WORD != 0) {
                continue;
            }
            Assemblable assemblable1 = addressAssemblableMap.get(address1);
            if (!isByteStorageDirective(assemblable1)) {
                continue;
            }
            Integer address2 = address1 + 1;
            if (addressLabelMap.containsKey(address2)) {
                continue;
            }
            Assemblable assemblable2 = addressAssemblableMap.get(address2);
            if (!isByteStorageDirective(assemblable2)) {
                continue;
            }
            Integer address3 = address2 + 1;
            if (addressLabelMap.containsKey(address3)) {
                continue;
            }
            Assemblable assemblable3 = addressAssemblableMap.get(address3);
            if (!isByteStorageDirective(assemblable3)) {
                continue;
            }
            Integer address4 = address3 + 1;
            if (addressLabelMap.containsKey(address4)) {
                continue;
            }
            Assemblable assemblable4 = addressAssemblableMap.get(address4);
            if (!isByteStorageDirective(assemblable4)) {
                continue;
            }

            StorageDirective storageDirective = StorageDirective.of(BitArray.of(
                    ((StorageDirective) assemblable1).getValue(),
                    ((StorageDirective) assemblable2).getValue(),
                    ((StorageDirective) assemblable3).getValue(),
                    ((StorageDirective) assemblable4).getValue()));
            addressAssemblableMap.put(address1, storageDirective);
            addressAssemblableMap.remove(address2);
            addressAssemblableMap.remove(address3);
            addressAssemblableMap.remove(address4);
        }
    }

    private boolean isZeroWordStorageDirective(Assemblable assemblable) {
        if (assemblable != null && assemblable instanceof StorageDirective) {
            BitArray value = ((StorageDirective) assemblable).getValue();
            if (value.length() == Constants.WORD_LENGTH && value.isZero()) {
                return true;
            }
        }
        return false;
    }

    private void packZeroWordStorageDirective() {

        List<Integer> addressList = new ArrayList<>();

        for (Integer address = addressAssemblableMap.firstKey(); address != null;
             address = addressAssemblableMap.higherKey(address)) {

            if ((addressList.isEmpty() || !addressLabelMap.containsKey(address))
                    && isZeroWordStorageDirective(addressAssemblableMap.get(address))) {
                addressList.add(address);
                continue;
            }

            if (addressList.size() > 1) {
                for (Integer listAddress : addressList) {
                    addressAssemblableMap.remove(listAddress);
                }
                addressAssemblableMap.put(addressList.get(0),
                        SpaceDirective.of(Constants.BYTES_PER_WORD * addressList.size()));
            }
            addressList.clear();

            if (isZeroWordStorageDirective(addressAssemblableMap.get(address))) {
                addressList.add(address);
            }
        }
        if (addressList.size() > 1) {
            for (Integer listAddress : addressList) {
                addressAssemblableMap.remove(listAddress);
            }
            addressAssemblableMap.put(addressList.get(0),
                    SpaceDirective.of(Constants.BYTES_PER_WORD * addressList.size()));
        }
    }

    public void packStorageDirective() {
        packByteStorageDirective();
        packZeroWordStorageDirective();
    }
}
