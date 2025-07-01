package org.shuangfa114.moremekasuitmodules.mixin.tacz;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.entity.shooter.LivingEntityAim;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.shuangfa114.moremekasuitmodules.config.ModConfig;
import org.shuangfa114.moremekasuitmodules.gear.tacz.ModuleQuickAimingUnit;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitmodules.util.ModuleUtil;
import org.shuangfa114.moremekasuitmodules.util.tacz.IShooterDataHolder;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Debug(export = true)
@Mixin(value = LivingEntityAim.class, remap = false)
public abstract class MixinLivingEntityAim {
    @Shadow
    @Final
    private LivingEntity shooter;

    @Shadow
    @Final
    private ShooterDataHolder data;

    @ModifyArg(method = "tickAimingProgress", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), index = 1)
    public float aimTimeWithMekasuit(float aimTime) {
        ItemStack itemStack = this.shooter.getItemBySlot(EquipmentSlot.CHEST);
        IModule<ModuleQuickAimingUnit> module = IModuleHelper.INSTANCE.getModule(itemStack, TaczModulesInit.MODULE_QUICK_AIMING_UNIT);
        if (module != null) {
            long usage = ModuleUtil.convertToFE((long) (ModConfig.base.energyQuickAiming.get() * (1 / module.getCustomInstance().getAimTime())));
            if (ModuleUtil.isValid(module, this.shooter, itemStack, usage)) {
                aimTime *= module.getCustomInstance().getAimTime();
            }
        }
        return aimTime;
    }

    @Definition(id = "aimingProgress", field = "Lcom/tacz/guns/entity/shooter/ShooterDataHolder;aimingProgress:F")
    @Expression("?.aimingProgress = ?.aimingProgress+?")
    @Inject(method = "tickAimingProgress", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    public void onAiming(CallbackInfo ci) {
        IShooterDataHolder iShooterDataHolder = ((IShooterDataHolder) this.data);
        if (!iShooterDataHolder.getLastAim()) {
            ItemStack itemStack = this.shooter.getItemBySlot(EquipmentSlot.CHEST);
            IModule<ModuleQuickAimingUnit> module = IModuleHelper.INSTANCE.getModule(itemStack, TaczModulesInit.MODULE_QUICK_AIMING_UNIT);
            if (module != null) {
                long usage = ModuleUtil.convertToFE((long) (ModConfig.base.energyQuickAiming.get() * (1 / module.getCustomInstance().getAimTime())));
                if (ModuleUtil.isValid(module, this.shooter, itemStack, usage)) {
                    module.useEnergy(this.shooter, itemStack, usage);
                }
            }
            iShooterDataHolder.setLastAim(true);
        }
    }

    @Definition(id = "aimingProgress", field = "Lcom/tacz/guns/entity/shooter/ShooterDataHolder;aimingProgress:F")
    @Expression("?.aimingProgress = ?.aimingProgress-?")
    @Inject(method = "tickAimingProgress", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    public void onNotAiming(CallbackInfo ci) {
        IShooterDataHolder iShooterDataHolder = ((IShooterDataHolder) this.data);
        if (iShooterDataHolder.getLastAim()) {
            ItemStack itemStack = this.shooter.getItemBySlot(EquipmentSlot.CHEST);
            IModule<ModuleQuickAimingUnit> module = IModuleHelper.INSTANCE.getModule(itemStack, TaczModulesInit.MODULE_QUICK_AIMING_UNIT);
            if (module != null) {
                long usage = ModuleUtil.convertToFE((long) (ModConfig.base.energyQuickAiming.get() * (1 / module.getCustomInstance().getAimTime())));
                if (ModuleUtil.isValid(module, this.shooter, itemStack, usage)) {
                    module.useEnergy(this.shooter, itemStack, usage);
                }
            }
            iShooterDataHolder.setLastAim(false);
        }
    }

    @ModifyExpressionValue(method = "lambda$tickSprint$1", at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/pojo/data/gun/GunData;getSprintTime()F"))
    public float sprintTime(float original) {
        IModule<?> unit = ModuleUtil.getUnit(this.shooter, TaczModulesInit.MODULE_QUICK_SPRINTSHOOT_UNIT, EquipmentSlot.CHEST);
        if (unit != null && unit.isEnabled()) {
            original *= 0.25F;
        }
        return original;
    }
}
