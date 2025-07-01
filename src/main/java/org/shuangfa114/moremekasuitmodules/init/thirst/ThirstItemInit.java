package org.shuangfa114.moremekasuitmodules.init.thirst;

import mekanism.common.item.ItemModule;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;

public class ThirstItemInit {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MoreMekasuitModules.MODID);
    public static final ItemRegistryObject<ItemModule> AUTOMATIC_DRINKING_UNIT;
    static {
        AUTOMATIC_DRINKING_UNIT=ITEMS.registerModule(ThirstModulesInit.MODULE_AUTOMATIC_DRINKING_UNIT);
    }
}
