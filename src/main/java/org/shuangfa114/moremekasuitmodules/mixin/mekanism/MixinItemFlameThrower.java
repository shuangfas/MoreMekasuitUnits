package org.shuangfa114.moremekasuitmodules.mixin.mekanism;

import mekanism.api.gear.IModuleHelper;
import mekanism.common.item.gear.ItemFlamethrower;
import mekanism.common.item.gear.ItemMekaTool;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.shuangfa114.moremekasuitmodules.init.mekanism.MekanismModulesInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFlamethrower.class)
public abstract class MixinItemFlameThrower {
    @Inject(method = "isIdleFlamethrower",at = @At("HEAD"), cancellable = true)
    private static void test(Player player, InteractionHand hand, CallbackInfoReturnable<Boolean> cir){
        ItemStack stack = player.getItemInHand(hand);
        if(stack.getItem() instanceof ItemMekaTool && IModuleHelper.INSTANCE.getModule(stack, MekanismModulesInit.MODULE_FLAME_THROWER_UNIT) !=null && ChemicalUtil.hasChemicalOfType(player.getItemBySlot(EquipmentSlot.CHEST), MekanismChemicals.HYDROGEN.get())){
            cir.setReturnValue(true);
        }
    }
}
