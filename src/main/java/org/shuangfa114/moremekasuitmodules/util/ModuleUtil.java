package org.shuangfa114.moremekasuitmodules.util;

import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.gear.ModuleData;
import mekanism.common.util.UnitDisplayUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.shuangfa114.moremekasuitmodules.gear.WithOffMode;

public class ModuleUtil {
    public static <T extends ICustomModule<T>>@Nullable IModule<T> getUnit(LivingEntity livingEntity, DeferredHolder<ModuleData<?>, ModuleData<T>> unit, EquipmentSlot equipmentSlot) {
        if (livingEntity instanceof Player player) {
            ItemStack itemStack = getEquipment(player,equipmentSlot);
            return IModuleHelper.INSTANCE.getModule(itemStack, unit);
        }
        return null;
    }
    public static <T extends ICustomModule<T>> @Nullable IModule<T> getMekaToolUnit(LivingEntity livingEntity, DeferredHolder<ModuleData<?>, ModuleData<T>> unit){
        if (livingEntity instanceof Player player) {
            ItemStack itemStack = player.getInventory().getSelected();
            return IModuleHelper.INSTANCE.getModule(itemStack,unit);
        }
        return null;
    }
    public static ItemStack getEquipment(Player player, EquipmentSlot equipmentSlot){
        return player.getItemBySlot(equipmentSlot);
    }
    public static boolean isValidWithNull(IModule<?> module, LivingEntity livingEntity,ItemStack itemStack, long value){
        return module!=null&&isValid(module,livingEntity,itemStack,value);
    }
    public static boolean isValid(@NotNull IModule<?> module, LivingEntity livingEntity,ItemStack itemStack, long value){
        boolean t = true;
        if(module.getCustomInstance() instanceof WithOffMode withOffMode){
            t=!withOffMode.isOffMode();
        }
        return module.isEnabled()&&t&&module.canUseEnergy(livingEntity,itemStack, convertToFE(value));
    }
    public static long convertToFE(long value){
        return UnitDisplayUtils.EnergyUnit.FORGE_ENERGY.convertFrom(value);
    }
}
