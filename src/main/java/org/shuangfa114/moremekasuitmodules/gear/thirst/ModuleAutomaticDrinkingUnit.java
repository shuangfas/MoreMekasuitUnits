package org.shuangfa114.moremekasuitmodules.gear.thirst;

import dev.ghen.thirst.content.registry.ThirstComponent;
import dev.ghen.thirst.content.thirst.PlayerThirst;
import dev.ghen.thirst.foundation.common.capability.ModAttachment;
import mekanism.api.gear.*;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StorageUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.config.ModConfig;
import org.shuangfa114.moremekasuitmodules.util.ModuleUtil;

import java.util.function.Consumer;

public class ModuleAutomaticDrinkingUnit implements ICustomModule<ModuleAutomaticDrinkingUnit> {
    public static ResourceLocation icon = MoreMekasuitModules.rl(MekanismUtils.ResourceType.GUI_HUD.getPrefix() + "automatic_drinking_unit.png");

    public ModuleAutomaticDrinkingUnit() {
    }

    @Override
    public void tickServer(IModule<ModuleAutomaticDrinkingUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player) {
        long usage = ModuleUtil.convertToFE(ModConfig.base.energyAutomaticDrinking.get());
        if (MekanismUtils.isPlayingMode(player)) {
            PlayerThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
            int mbPerDrinking = ModConfig.base.drinkingMBPerDrinking.get();
            IFluidHandlerItem handler = Capabilities.FLUID.getCapability(stack);
            if (handler != null) {
                FluidStack fluidStack = new FluidStack(Fluids.WATER, 1);
                fluidStack.set(ThirstComponent.PURITY, 3);
                int needed = Math.min(20 - thirst.getThirst(), StorageUtils.getContainedFluid(handler, fluidStack).getAmount() / mbPerDrinking);
                int toDrink = Math.toIntExact(Math.min(module.getContainerEnergy(stack) / (usage), needed));
                if (toDrink >= 1) {
                    module.useEnergy(player, stack, ModConfig.base.energyAutomaticDrinking.get());
                    FluidUtil.getFluidHandler(stack).ifPresent((handler1) -> {
                        FluidStack fluidStack1 = new FluidStack(Fluids.WATER, mbPerDrinking);
                        fluidStack1.set(ThirstComponent.PURITY, 3);
                        handler1.drain(fluidStack1, IFluidHandler.FluidAction.EXECUTE);
                    });
                    thirst.drink(ModConfig.base.thirstPerDrinking.get(), ModConfig.base.quenchedPerDrinking.get());
                }
            }

        }
    }

    @Override
    public void addHUDElements(IModule<ModuleAutomaticDrinkingUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player, Consumer<IHUDElement> hudElementAdder) {
        if (module.isEnabled()) {
            int max = ModConfig.base.cleanWaterMaxStorage.getAsInt();
            FluidStack fluidStack = new FluidStack(Fluids.WATER, 1);
            fluidStack.set(ThirstComponent.PURITY, 3);
            IFluidHandlerItem handler = Capabilities.FLUID.getCapability(stack);
            double ratio = 0;
            if (handler != null) {
                handler.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE);
                FluidStack fluidStack1 = new FluidStack(Fluids.WATER, 1);
                fluidStack1.set(ThirstComponent.PURITY, 3);
                FluidStack stored = StorageUtils.getContainedFluid(handler, fluidStack1);
                ratio = StorageUtils.getRatio(stored.getAmount(), max);
            }
            hudElementAdder.accept(IModuleHelper.INSTANCE.hudElementPercent(icon, ratio));
        }
    }
}
