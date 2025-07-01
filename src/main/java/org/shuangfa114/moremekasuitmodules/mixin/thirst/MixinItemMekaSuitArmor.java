package org.shuangfa114.moremekasuitmodules.mixin.thirst;

import dev.ghen.thirst.content.registry.ThirstComponent;
import mekanism.common.attachments.IAttachmentAware;
import mekanism.common.capabilities.ICapabilityAware;
import mekanism.common.capabilities.fluid.item.FluidTankSpec;
import mekanism.common.content.gear.IModuleContainerItem;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.item.gear.ItemSpecialArmor;
import mekanism.common.item.interfaces.IJetpackItem;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.config.ModConfig;
import org.shuangfa114.moremekasuitmodules.init.thirst.ThirstModulesInit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ItemMekaSuitArmor.class,remap = false)
public abstract class MixinItemMekaSuitArmor extends ItemSpecialArmor implements IModuleContainerItem, IJetpackItem, CreativeTabDeferredRegister.ICustomCreativeTabContents, IAttachmentAware, ICapabilityAware {


    @Shadow
    @Final
    private List<FluidTankSpec> fluidTankSpecs;

    protected MixinItemMekaSuitArmor(Holder<ArmorMaterial> material, Type armorType, Properties properties) {
        super(material, armorType, properties);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void addCleanWaterFluidTank(Type armorType, Properties properties, CallbackInfo ci) {
        if (MoreMekasuitModules.isThirstLoaded&&armorType == Type.HELMET) {
            FluidStack fluidStack1 = new FluidStack(Fluids.WATER, 1);
            fluidStack1.set(ThirstComponent.PURITY,3);
            fluidTankSpecs.add(FluidTankSpec.createFillOnly(ModConfig.base.cleanWaterTransferRate, ModConfig.base.cleanWaterMaxStorage,
                    fluidStack -> FluidStack.isSameFluidSameComponents(fluidStack,fluidStack1),
                    itemStack -> this.hasModule(itemStack, ThirstModulesInit.MODULE_AUTOMATIC_DRINKING_UNIT)));
        }
    }
}
