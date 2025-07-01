package org.shuangfa114.moremekasuitmodules.mixin.mekanism;

import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.gear.mekasuit.ModuleJetpackUnit;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismModules;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;
import java.util.function.ToLongFunction;

@Mixin(value = ItemMekaSuitArmor.class,remap = false)
public abstract class MixinItemMekaSuitArmor {
    @ModifyArg(method = "<init>",at = @At(value = "INVOKE", target = "Lmekanism/common/capabilities/chemical/item/ChemicalTankSpec;createFillOnly(Ljava/util/function/LongSupplier;Ljava/util/function/ToLongFunction;Ljava/util/function/Predicate;Ljava/util/function/Predicate;)Lmekanism/common/capabilities/chemical/item/ChemicalTankSpec;"),index = 1)
    public ToLongFunction<ItemStack> test(ToLongFunction<ItemStack> stackBasedCapacity){
        return (stack)->{
            IModule<ModuleJetpackUnit> module = IModuleHelper.INSTANCE.getModule(stack, MekanismModules.JETPACK_UNIT);
            return module != null ? MekanismConfig.gear.mekaSuitJetpackMaxStorage.get() * module.getInstalledCount() : MekanismConfig.gear.mekaSuitJetpackMaxStorage.get();
        };
    }
    @ModifyArg(method = "<init>",at = @At(value = "INVOKE", target = "Lmekanism/common/capabilities/chemical/item/ChemicalTankSpec;createFillOnly(Ljava/util/function/LongSupplier;Ljava/util/function/ToLongFunction;Ljava/util/function/Predicate;Ljava/util/function/Predicate;)Lmekanism/common/capabilities/chemical/item/ChemicalTankSpec;"),index = 3)
    public Predicate<@NotNull ItemStack> returnTrue(Predicate<@NotNull ItemStack> supportsStack){
        return (itemStack)->true;
    }
}
