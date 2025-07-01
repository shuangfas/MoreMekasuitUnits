package org.shuangfa114.moremekasuitmodules.init;

import mekanism.common.registries.MekanismItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.init.mekanism.MekanismItemInit;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczItemInit;
import org.shuangfa114.moremekasuitmodules.init.thirst.ThirstItemInit;

import java.util.function.Supplier;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreMekasuitModules.MODID);
    public static final Supplier<CreativeModeTab> UNIT_TAB;

    static {
        UNIT_TAB = CREATIVE_MODE_TABS.register("unit_tab", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.moremekasuitmodules"))
                .icon(() -> {
                    if (MoreMekasuitModules.isTaczLoaded) {
                        return TaczItemInit.QUICK_AIMING_UNIT.get().getDefaultInstance();
                    }
                    return MekanismItems.MODULE_BASE.get().getDefaultInstance();
                })
                .displayItems((parameters, output) -> {
                    {
                        output.accept(MekanismItemInit.FLAME_THROWER_UNIT.get());
                        output.accept(MekanismItemInit.ELYTRA_ACCELERATION_UNIT.get());
                        output.accept(MekanismItemInit.LOOTING_MODIFICATION_UNIT.get());
                        if (MoreMekasuitModules.isThirstLoaded) {
                            output.accept(ThirstItemInit.AUTOMATIC_DRINKING_UNIT.get());
                        }
                        if (MoreMekasuitModules.isTaczLoaded) {
                            output.accept(TaczItemInit.QUICK_AIMING_UNIT.get());
                            output.accept(TaczItemInit.QUICK_RELOADING_UNIT.get());
                            output.accept(TaczItemInit.QUICK_SPRINTSHOOT_UNIT.get());
                            output.accept(TaczItemInit.RECOIL_OFFSET_UNIT.get());
                        }

                    }
                }).build());
    }
}
