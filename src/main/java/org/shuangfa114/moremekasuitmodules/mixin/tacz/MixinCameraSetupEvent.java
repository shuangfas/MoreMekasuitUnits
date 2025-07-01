package org.shuangfa114.moremekasuitmodules.mixin.tacz;


import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.tacz.guns.client.event.CameraSetupEvent;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import org.shuangfa114.moremekasuitmodules.config.ModConfig;
import org.shuangfa114.moremekasuitmodules.gear.tacz.ModuleRecoilOffsetUnit;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitmodules.util.ModuleUtil;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Debug(export = true)
@Mixin(value = CameraSetupEvent.class, remap = false)
public abstract class MixinCameraSetupEvent {
    @ModifyArg(method = "initialCameraRecoil", at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/pojo/data/gun/GunRecoil;genPitchSplineFunction(F)Lorg/apache/commons/math3/analysis/polynomials/PolynomialSplineFunction;"))
    private static float modify(float modifier, @Local LocalPlayer shooter) {
        return test(shooter,modifier);
    }
    @ModifyArg(method = "initialCameraRecoil", at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/pojo/data/gun/GunRecoil;genYawSplineFunction(F)Lorg/apache/commons/math3/analysis/polynomials/PolynomialSplineFunction;"))
    private static float modify1(float modifier, @Local LocalPlayer shooter) {
        return test(shooter,modifier);
    }
    private static float test(LocalPlayer shooter,float modifier){
        ItemStack itemStack = shooter.getItemBySlot(EquipmentSlot.CHEST);
        IModule<ModuleRecoilOffsetUnit> module = IModuleHelper.INSTANCE.getModule(itemStack, TaczModulesInit.MODULE_RECOIL_OFFSET_UNIT);
        if (module != null) {
            long usage = (long) (ModConfig.base.energyRecoilOffset.get() * (1 / module.getCustomInstance().getRecoil()));
            if (ModuleUtil.isValid(module, shooter, shooter.getItemBySlot(EquipmentSlot.CHEST), usage)) {
                modifier *= module.getCustomInstance().getRecoil();
                module.useEnergy(shooter, itemStack, usage);
            }
        }
        return modifier;
    }
}
