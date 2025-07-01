package org.shuangfa114.moremekasuitmodules.init.tacz;

import mekanism.api.gear.config.ModuleEnumConfig;
import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.gear.tacz.ModuleQuickAimingUnit;
import org.shuangfa114.moremekasuitmodules.gear.tacz.ModuleQuickReloadingUnit;
import org.shuangfa114.moremekasuitmodules.gear.tacz.ModuleRecoilOffsetUnit;

public class TaczModulesInit {
    public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(MoreMekasuitModules.MODID);
    public static final ModuleRegistryObject<ModuleQuickAimingUnit> MODULE_QUICK_AIMING_UNIT;
    public static final ModuleRegistryObject<ModuleRecoilOffsetUnit> MODULE_RECOIL_OFFSET_UNIT;
    public static final ModuleRegistryObject<ModuleQuickReloadingUnit> MODULE_QUICK_RELOADING_UNIT;
    public static final ModuleRegistryObject<?> MODULE_QUICK_SPRINTSHOOT_UNIT;

    static {
        MODULE_QUICK_AIMING_UNIT = MODULES.register("quick_aiming_unit", ModuleQuickAimingUnit::new, () -> TaczItemInit.QUICK_AIMING_UNIT,
                (m) -> m.maxStackSize(4).rendersHUD().addInstalledCountConfig(
                        install -> ModuleEnumConfig.createBounded(ModuleQuickAimingUnit.AIMING_TIME, ModuleQuickAimingUnit.AimTime.LOW, install + 1),
                        install -> ModuleEnumConfig.codec(ModuleQuickAimingUnit.AimTime.CODEC, ModuleQuickAimingUnit.AimTime.class, install + 1),
                        install -> ModuleEnumConfig.streamCodec(ModuleQuickAimingUnit.AimTime.STREAM_CODEC, ModuleQuickAimingUnit.AimTime.class, install + 1)
                )
        );
        MODULE_RECOIL_OFFSET_UNIT = MODULES.register("recoil_offset_unit", ModuleRecoilOffsetUnit::new, () -> TaczItemInit.RECOIL_OFFSET_UNIT,
                (m) -> m.maxStackSize(4).rendersHUD().addInstalledCountConfig(
                        install -> ModuleEnumConfig.createBounded(ModuleRecoilOffsetUnit.RECOIL_OFFSET, ModuleRecoilOffsetUnit.RecoilOffset.LOW, install + 1),
                        install -> ModuleEnumConfig.codec(ModuleRecoilOffsetUnit.RecoilOffset.CODEC, ModuleRecoilOffsetUnit.RecoilOffset.class, install + 1),
                        install -> ModuleEnumConfig.streamCodec(ModuleRecoilOffsetUnit.RecoilOffset.STREAM_CODEC, ModuleRecoilOffsetUnit.RecoilOffset.class, install + 1)
                )
        );
        MODULE_QUICK_SPRINTSHOOT_UNIT = MODULES.registerMarker("quick_sprintshoot_unit", () -> TaczItemInit.QUICK_SPRINTSHOOT_UNIT, (m) -> m);
        MODULE_QUICK_RELOADING_UNIT = MODULES.register("quick_reloading_unit", ModuleQuickReloadingUnit::new, () -> TaczItemInit.QUICK_RELOADING_UNIT,
                (m) -> m.maxStackSize(4).rendersHUD().addInstalledCountConfig(
                        install -> ModuleEnumConfig.createBounded(ModuleQuickReloadingUnit.RELOADING_TIME, ModuleQuickReloadingUnit.ReloadingTime.LOW, install + 1),
                        install -> ModuleEnumConfig.codec(ModuleQuickReloadingUnit.ReloadingTime.CODEC, ModuleQuickReloadingUnit.ReloadingTime.class, install + 1),
                        install -> ModuleEnumConfig.streamCodec(ModuleQuickReloadingUnit.ReloadingTime.STREAM_CODEC, ModuleQuickReloadingUnit.ReloadingTime.class, install + 1)
                )
        );
    }

    public TaczModulesInit() {
    }
}
