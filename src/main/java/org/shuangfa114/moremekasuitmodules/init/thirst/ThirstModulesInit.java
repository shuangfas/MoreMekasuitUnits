package org.shuangfa114.moremekasuitmodules.init.thirst;

import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.gear.thirst.ModuleAutomaticDrinkingUnit;

public class ThirstModulesInit {
    public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(MoreMekasuitModules.MODID);
    public static final ModuleRegistryObject<ModuleAutomaticDrinkingUnit> MODULE_AUTOMATIC_DRINKING_UNIT;

    static {
        MODULE_AUTOMATIC_DRINKING_UNIT = MODULES.registerInstanced("automatic_drinking_unit",ModuleAutomaticDrinkingUnit::new,()->ThirstItemInit.AUTOMATIC_DRINKING_UNIT,(m)-> m.rendersHUD());
    }
}
