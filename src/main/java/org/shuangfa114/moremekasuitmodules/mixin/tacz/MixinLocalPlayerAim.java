package org.shuangfa114.moremekasuitmodules.mixin.tacz;


import com.tacz.guns.client.gameplay.LocalPlayerAim;
import mekanism.api.gear.IModule;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import org.shuangfa114.moremekasuitmodules.gear.tacz.ModuleQuickAimingUnit;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitmodules.util.ModuleUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = LocalPlayerAim.class, remap = false)
public abstract class MixinLocalPlayerAim {
    @Shadow
    @Final
    private LocalPlayer player;

    @ModifyArg(method = "getAlphaProgress", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), index = 1)
    public float aimTimeWithMekasuit(float aimTime) {
        IModule<ModuleQuickAimingUnit> aimTimeUnit = ModuleUtil.getUnit(this.player, TaczModulesInit.MODULE_QUICK_AIMING_UNIT, EquipmentSlot.CHEST);
        if (aimTimeUnit != null && aimTimeUnit.isEnabled()) {
            aimTime *= aimTimeUnit.getCustomInstance().getAimTime();
        }
        return aimTime;
    }
}
