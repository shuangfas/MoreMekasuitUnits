package org.shuangfa114.moremekasuitmodules.config;

import mekanism.common.Mekanism;
import mekanism.common.config.IConfigTranslation;
import net.minecraft.Util;

public enum ModConfigTranslations implements IConfigTranslation {
    COMMON_ENERGY_FLAME_THROWER("common.energy_flame_thrower","Energy Flame Thrower","Energy (FE) of MekaSuit when throwing flame"),
    COMMON_ENERGY_ELYTRA_ACCELERATION_EACH_TICK("common.energy_elytra_acceleration_each_tick", "Energy Elytra Acceleration Each Tick","Energy (FE) of MekaSuit per tick per tier when elytra acceleration"),
    COMMON_ENERGY_LOOTING_MODIFICATION("common.energy_looting_modification", "Energy Looting Modification","Energy (FE) of MekaSuit per tier each looting modification"),
    COMMON_ENERGY_QUICK_AIMING("common.energy_quick_aiming", "Energy Quick Aiming","Energy (FE) of MekaSuit per tier when quick aiming"),
    COMMON_ENERGY_QUICK_RELOADING("common.energy_quick_reloading", "Energy Quick Reloading","Energy (FE) of MekaSuit per tier when quick reloading"),
    COMMON_ENERGY_RECOIL_OFFSET("common.energy_recoil_offset", "Energy Recoil Offset","Energy (FE) of MekaSuit per tier when shooting recoil offsetting"),
    COMMON_DRINKING_MB_PER_DRINKING("common.drinking_mb_per_drinking", "Drinking MB Per Drinking","Mb of pure water needed per drinking"),
    COMMON_ENERGY_AUTOMATIC_DRINKING("common.energy_automatic_drinking", "Energy Automatic Drinking","Energy (FE) of MekaSuit when automatic drinking"),
    COMMON_THIRST_PER_DRINKING("common.thirst_per_drinking", "Thirst Per Drinking","Thirst provided by each automatic drinking"),
    COMMON_QUENCHED_PER_DRINKING("common.quenched_per_drinking", "Quenched Per Drinking","Thirst provided by each automatic drinking"),
    COMMON_CLEAN_WATER_TRANSFER_RATE("common.clean_water_transfer_rate", "Clean Water Transfer Rate","Thirst provided by each automatic drinking"),
    COMMON_CLEAN_WATER_MAX_STORAGE("common.clean_water_max_storage", "Clean Water Max Storage","Thirst provided by each automatic drinking");

    private final String key;
    private final String title;
    private final String tooltip;

    ModConfigTranslations(String path, String title, String tooltip) {
        this.key = Util.makeDescriptionId("configuration", Mekanism.rl(path));
        this.title = title;
        this.tooltip = tooltip;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String tooltip() {
        return tooltip;
    }

    @Override
    public String getTranslationKey() {
        return key;
    }
}
