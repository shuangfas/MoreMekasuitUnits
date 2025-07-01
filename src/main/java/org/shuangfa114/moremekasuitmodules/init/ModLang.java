package org.shuangfa114.moremekasuitmodules.init;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;

public enum ModLang implements ILangEntry {
    MODULE_QUICK_AIMING_HUD("quick_aiming_hud"),
    MODULE_RECOIL_OFFSET_HUD("recoil_offset_hud"),
    MODULE_QUICK_RELOADING_HUD("quick_reloading_hud"),
    MODULE_FLAME_THROWER("flame_thrower"),
    MODULE_ELYTRA_ACCELERATION_HUD("elytra_acceleration_hud"),
    MODULE_LOOTING_MODIFICATION_HUD("looting_modification_hud");

    private final String key;

    ModLang(String path){
        this.key = Util.makeDescriptionId("module", MoreMekasuitModules.rl(path));
    }
    @Override
    public @NotNull String getTranslationKey() {
        return this.key;
    }
}
