package org.shuangfa114.moremekasuitmodules.init.mekanism;

import mekanism.api.gear.ModuleData;
import mekanism.api.gear.config.ModuleEnumConfig;
import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.gear.mekanism.ModuleElytraAccelerationUnit;
import org.shuangfa114.moremekasuitmodules.gear.mekanism.ModuleFlameThrowerUnit;
import org.shuangfa114.moremekasuitmodules.gear.mekanism.ModuleLootingModificationUnit;

public class MekanismModulesInit {
    public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(MoreMekasuitModules.MODID);
    public static final ModuleRegistryObject<ModuleFlameThrowerUnit> MODULE_FLAME_THROWER_UNIT;
    public static final ModuleRegistryObject<ModuleElytraAccelerationUnit> MODULE_ELYTRA_ACCELERATION_UNIT;
    public static final ModuleRegistryObject<ModuleLootingModificationUnit> MODULE_LOOTING_MODIFICATION_UNIT;

    static {
        MODULE_FLAME_THROWER_UNIT = MODULES.register("flame_thrower_unit", ModuleFlameThrowerUnit::new, () -> MekanismItemInit.FLAME_THROWER_UNIT,
                (m) -> m.exclusive(ModuleData.ExclusiveFlag.INTERACT_ANY).rendersHUD().addConfig(
                        ModuleEnumConfig.create(ModuleFlameThrowerUnit.FLAME_MODE, ModuleFlameThrowerUnit.FlameMode.COMBAT),
                        ModuleEnumConfig.codec(ModuleFlameThrowerUnit.FlameMode.CODEC),
                        ModuleEnumConfig.streamCodec(ModuleFlameThrowerUnit.FlameMode.STREAM_CODEC)
                )
        );
        MODULE_ELYTRA_ACCELERATION_UNIT = MODULES.register("elytra_acceleration_unit", ModuleElytraAccelerationUnit::new, () -> MekanismItemInit.ELYTRA_ACCELERATION_UNIT,
                (m) -> m.maxStackSize(3).exclusive(ModuleData.ExclusiveFlag.OVERRIDE_JUMP).rendersHUD().addInstalledCountConfig(
                        install -> ModuleEnumConfig.createBounded(ModuleElytraAccelerationUnit.ACCELERATION, ModuleElytraAccelerationUnit.Acceleration.MEDIUM, install + 1),
                        install -> ModuleEnumConfig.codec(ModuleElytraAccelerationUnit.Acceleration.CODEC, ModuleElytraAccelerationUnit.Acceleration.class, install + 1),
                        install -> ModuleEnumConfig.streamCodec(ModuleElytraAccelerationUnit.Acceleration.STREAM_CODEC, ModuleElytraAccelerationUnit.Acceleration.class, install + 1)
                )
        );
        MODULE_LOOTING_MODIFICATION_UNIT = MODULES.register("looting_modification_unit", ModuleLootingModificationUnit::new, () -> MekanismItemInit.LOOTING_MODIFICATION_UNIT,
                (m) -> m.maxStackSize(2).rendersHUD().addInstalledCountConfig(
                        install -> ModuleEnumConfig.createBounded(ModuleLootingModificationUnit.LOOTING_MULTIPLIER, ModuleLootingModificationUnit.Multiplier.MEDIUM, install + 1),
                        install -> ModuleEnumConfig.codec(ModuleLootingModificationUnit.Multiplier.CODEC, ModuleLootingModificationUnit.Multiplier.class, install + 1),
                        install -> ModuleEnumConfig.streamCodec(ModuleLootingModificationUnit.Multiplier.STREAM_CODEC, ModuleLootingModificationUnit.Multiplier.class, install + 1)
                )
        );
    }
}
