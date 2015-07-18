/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import java.util.prefs.Preferences;

public class AssemblerPreferences {

    private enum Preference {

        DELAY_SLOT_ENABLED(true);

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

    public static boolean getDelaySlotEnabled() {
        return PREFERENCES.getBoolean(Preference.DELAY_SLOT_ENABLED.name(),
                Preference.DELAY_SLOT_ENABLED.<Boolean>getDefaultValue());
    }

    public static void setDelaySlotEnabled(boolean delaySlotEnabled) {
        PREFERENCES.putBoolean(Preference.DELAY_SLOT_ENABLED.name(), delaySlotEnabled);
    }
}
