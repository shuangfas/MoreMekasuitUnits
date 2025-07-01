package org.shuangfa114.moremekasuitmodules.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import org.shuangfa114.moremekasuitmodules.config.ModConfig;
import org.shuangfa114.moremekasuitmodules.gear.mekanism.ModuleLootingModificationUnit;
import org.shuangfa114.moremekasuitmodules.init.mekanism.MekanismModulesInit;
import org.shuangfa114.moremekasuitmodules.util.ModuleUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantedCountIncreaseFunction.class)
public abstract class MixinEnchantedCountIncreaseFunction {
    @ModifyExpressionValue(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/LivingEntity;)I"))
    public int test(int original, @Local LivingEntity livingEntity) {
        ItemStack itemStack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        IModule<ModuleLootingModificationUnit> module = IModuleHelper.INSTANCE.getModule(itemStack, MekanismModulesInit.MODULE_LOOTING_MODIFICATION_UNIT);
        if (module == null) {
            itemStack = livingEntity.getItemInHand(InteractionHand.OFF_HAND);
            module = IModuleHelper.INSTANCE.getModule(itemStack, MekanismModulesInit.MODULE_LOOTING_MODIFICATION_UNIT);
        }
        long usage = ModConfig.base.energyLootingModification.get();
        if (ModuleUtil.isValidWithNull(module, livingEntity, itemStack, usage)) {
            original = (int) ((original <= 0 ? 1 : original) * module.getCustomInstance().getMultiplier());
            module.useEnergy(livingEntity, itemStack, usage);
        }
        return original;
    }
}
