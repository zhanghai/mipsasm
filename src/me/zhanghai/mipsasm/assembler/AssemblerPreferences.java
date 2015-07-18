/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import java.util.prefs.Preferences;

public class AssemblerPreferences {

    private enum Preference {

        USE_DELAY_SLOT(true);

        private Object defaultValue;

        Preference(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        public <T> T getDefaultValue() {
            //noinspection unchecked
            return (T) defaultValue;
        }
    }

    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(AssemblerPreferences.class);

    private AssemblerPreferences() {}

    public static boolean getUseDelaySlot() {
        return PREFERENCES.getBoolean(Preference.USE_DELAY_SLOT.name(),
                Preference.USE_DELAY_SLOT.<Boolean>getDefaultValue());
    }
}
