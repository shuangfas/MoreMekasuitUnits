package org.shuangfa114.moremekasuitmodules.event;

import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import org.shuangfa114.moremekasuitmodules.config.ModConfig;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitmodules.util.ModuleUtil;

import java.util.Arrays;
import java.util.stream.Stream;

public class EntityHurtByGun {
    @SubscribeEvent
    public void preHurt(EntityHurtByGunEvent.Pre event){
        Entity entity = event.getHurtEntity();
        if(entity instanceof LivingEntity livingEntity){
            float multiple = 0;
            Stream<EquipmentSlot> stream = Arrays.stream(EquipmentSlot.values()).filter((e)->e.getType()==EquipmentSlot.Type.HUMANOID_ARMOR);
            long usage = ModuleUtil.convertToFE(ModConfig.base.energyBulletproof.get());
            for(EquipmentSlot equipmentSlot: stream.toList()){
                ItemStack itemStack = livingEntity.getItemBySlot(equipmentSlot);
                IModule<?> module = IModuleHelper.INSTANCE.getIfEnabled(itemStack,TaczModulesInit.MODULE_BULLETPROOF_UNIT);
                if(ModuleUtil.isValidWithNull(module,livingEntity,itemStack,usage)){
                    module.useEnergy(livingEntity,itemStack,usage);
                    multiple+=2;
                }
            }
            event.setBaseAmount(event.getBaseAmount()*(1-multiple/10));
        }
    }
}
