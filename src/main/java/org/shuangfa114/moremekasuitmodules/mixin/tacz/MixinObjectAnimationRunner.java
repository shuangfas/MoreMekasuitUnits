package org.shuangfa114.moremekasuitmodules.mixin.tacz;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.api.client.animation.ObjectAnimation;
import com.tacz.guns.api.client.animation.ObjectAnimationRunner;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.shuangfa114.moremekasuitmodules.gear.tacz.ModuleQuickReloadingUnit;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitmodules.util.ModuleUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nonnull;

@Mixin(value = ObjectAnimationRunner.class, remap = false)
public abstract class MixinObjectAnimationRunner {
    @Shadow
    @Final
    @Nonnull
    private ObjectAnimation animation;

    @Definition(id = "lastUpdateNs", field = "Lcom/tacz/guns/api/client/animation/ObjectAnimationRunner;lastUpdateNs:J")
    @Expression("? - this.lastUpdateNs")
    @ModifyExpressionValue(method = "update", at = @At("MIXINEXTRAS:EXPRESSION"))
    public long reloadingTime(long original) {
        if (this.animation.name.contains("reload")) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer == null) {
                return original;
            }
            ItemStack itemStack = localPlayer.getItemBySlot(EquipmentSlot.CHEST);
            IModule<ModuleQuickReloadingUnit> module = IModuleHelper.INSTANCE.getModule(itemStack, TaczModulesInit.MODULE_QUICK_RELOADING_UNIT);
            if (ModuleUtil.isValidWithNull(module, localPlayer, itemStack, 0)) {
                original = (long) (original * (1 / module.getCustomInstance().getReloadingTime()));
            }
        }
        return original;
    }
}
