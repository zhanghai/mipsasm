/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.Address;
import me.zhanghai.mipsasm.util.IoUtils;

public class AddressParser {

    private AddressParser() {}

    public static Address parse(String addressString) {
        try {
            return Address.of(IoUtils.decodeInteger(addressString));
        } catch (NumberFormatException e) {
            throw new InvalidOperandException("Unable to parse address: " + addressString, e);
        } catch (IllegalArgumentException e) {
            throw new InvalidOperandException("Address length too long: " + addressString, e);
        }
    }
}
