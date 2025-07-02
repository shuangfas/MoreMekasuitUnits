package org.shuangfa114.moremekasuitmodules.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.IMekanismConfig;
import mekanism.common.config.value.CachedIntValue;
import mekanism.common.config.value.CachedLongValue;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModConfig {

    public static final Config base = new Config();
    private static final Map<IConfigSpec, IMekanismConfig> KNOWN_CONFIGS = new HashMap<>();

    public static void registerConfigs(ModContainer modContainer) {
        ModConfigHelper.registerConfig(KNOWN_CONFIGS, modContainer, base);
    }

    public static void onConfigLoad(ModConfigEvent configEvent) {
        ModConfigHelper.onConfigLoad(configEvent, MoreMekasuitModules.MOD_NAME, KNOWN_CONFIGS);
    }

    public static Collection<IMekanismConfig> getConfigs() {
        return Collections.unmodifiableCollection(KNOWN_CONFIGS.values());
    }

    public static class Config extends BaseMekanismConfig {
        //mekanism
        public final CachedLongValue energyFlameThrower;
        public final CachedLongValue energyElytraAccelerationEachTick;
        public final CachedLongValue energyLootingModification;
        //tacz
        public final CachedLongValue energyQuickAiming;
        public final CachedLongValue energyQuickReloading;
        public final CachedLongValue energyRecoilOffset;
        public final CachedLongValue energyBulletproof;
        //thirst
        public final CachedIntValue drinkingMBPerDrinking;
        public final CachedIntValue thirstPerDrinking;
        public final CachedIntValue quenchedPerDrinking;
        public final CachedLongValue energyAutomaticDrinking;
        public final CachedIntValue cleanWaterTransferRate;
        public final CachedIntValue cleanWaterMaxStorage;
        private final ModConfigSpec configSpec;

        Config() {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
            //mekanism
            builder.comment("Mekanism Settings").push("mekanism");
            energyFlameThrower = CachedLongValue.definePositive(this, builder,
                    ModConfigTranslations.COMMON_ENERGY_FLAME_THROWER,
                    "energyFlameThrower",
                    250L);
            energyElytraAccelerationEachTick = CachedLongValue.definePositive(this, builder,
                    ModConfigTranslations.COMMON_ENERGY_ELYTRA_ACCELERATION_EACH_TICK,
                    "energyElytraAccelerationEachTick",
                    2500L);
            energyLootingModification = CachedLongValue.definePositive(this, builder,
                    ModConfigTranslations.COMMON_ENERGY_LOOTING_MODIFICATION,
                    "energyLootingModification",
                    2500L);
            builder.pop();
            //tacz
            builder.comment("Tacz Settings").push("tacz");
            energyQuickAiming = CachedLongValue.definePositive(this, builder,
                    ModConfigTranslations.COMMON_ENERGY_QUICK_AIMING,
                    "energyQuickAiming",
                    1000L);
            energyQuickReloading = CachedLongValue.definePositive(this, builder,
                    ModConfigTranslations.COMMON_ENERGY_QUICK_RELOADING,
                    "energyQuickReloading",
                    1000L);
            energyRecoilOffset = CachedLongValue.definePositive(this, builder,
                    ModConfigTranslations.COMMON_ENERGY_RECOIL_OFFSET,
                    "energyRecoilOffset",
                    1000L);
            energyBulletproof = CachedLongValue.definePositive(this, builder,
                    ModConfigTranslations.COMMON_ENERGY_BULLETPROOF,
                    "energyBulletproof",
                    1000L);
            builder.pop();
            //thirst
            builder.comment("Thirst Settings").push("thirst");
            drinkingMBPerDrinking = CachedIntValue.wrap(this, ModConfigTranslations.COMMON_DRINKING_MB_PER_DRINKING.applyToBuilder(builder)
                    .defineInRange("mbPerDrinking", 75, 1, Integer.MAX_VALUE));
            energyAutomaticDrinking = CachedLongValue.definePositive(this, builder,
                    ModConfigTranslations.COMMON_ENERGY_AUTOMATIC_DRINKING,
                    "energyAutomaticDrinking",
                    2500L);
            thirstPerDrinking = CachedIntValue.wrap(this, ModConfigTranslations.COMMON_THIRST_PER_DRINKING.applyToBuilder(builder)
                    .defineInRange("thirstPerAutomaticDrinking", 1, 1, Integer.MAX_VALUE));
            quenchedPerDrinking = CachedIntValue.wrap(this, ModConfigTranslations.COMMON_QUENCHED_PER_DRINKING.applyToBuilder(builder)
                    .defineInRange("quenchedPerAutomaticDrinking", 2, 1, Integer.MAX_VALUE));
            cleanWaterTransferRate = CachedIntValue.wrap(this, ModConfigTranslations.COMMON_CLEAN_WATER_TRANSFER_RATE.applyToBuilder(builder)
                    .defineInRange("cleanWaterTransferRate", 500, 1, Integer.MAX_VALUE));
            cleanWaterMaxStorage = CachedIntValue.wrap(this, ModConfigTranslations.COMMON_CLEAN_WATER_MAX_STORAGE.applyToBuilder(builder)
                    .defineInRange("cleanWaterMaxStorage", 50000, 1, Integer.MAX_VALUE));

            configSpec = builder.build();
        }

        @Override
        public String getFileName() {
            return MoreMekasuitModules.MODID;
        }

        @Override
        public String getTranslation() {
            return "Common Config";
        }

        @Override
        public ModConfigSpec getConfigSpec() {
            return configSpec;
        }

        @Override
        public net.neoforged.fml.config.ModConfig.Type getConfigType() {
            return net.neoforged.fml.config.ModConfig.Type.COMMON;
        }
    }
}
