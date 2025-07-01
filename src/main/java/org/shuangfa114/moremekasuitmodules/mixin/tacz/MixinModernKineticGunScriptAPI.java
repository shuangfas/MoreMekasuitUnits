package org.shuangfa114.moremekasuitmodules.mixin.tacz;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.shuangfa114.moremekasuitmodules.gear.tacz.ModuleQuickReloadingUnit;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitmodules.util.ModuleUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ModernKineticGunScriptAPI.class, remap = false)
public abstract class MixinModernKineticGunScriptAPI {
    @Shadow
    private LivingEntity shooter;

    @ModifyReturnValue(method = "getReloadTime", at = @At("RETURN"))
    public long reloadTime(long original) {
        ItemStack itemStack = this.shooter.getItemBySlot(EquipmentSlot.CHEST);
        IModule<ModuleQuickReloadingUnit> module = IModuleHelper.INSTANCE.getModule(itemStack, TaczModulesInit.MODULE_QUICK_RELOADING_UNIT);
        if (ModuleUtil.isValidWithNull(module, this.shooter, itemStack, 0)) {
            original = (long) (1 / module.getCustomInstance().getReloadingTime() * original);
        }
        return original;
    }
}
