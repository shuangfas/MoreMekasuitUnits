package org.shuangfa114.moremekasuitmodules.mixin.mekanism;

import mekanism.api.Action;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.content.gear.IBlastingItem;
import mekanism.common.content.gear.IRadialModuleContainerItem;
import mekanism.common.entity.EntityFlame;
import mekanism.common.item.ItemEnergized;
import mekanism.common.item.gear.ItemMekaTool;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.util.ChemicalUtil;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StorageUtils;
import net.minecraft.SharedConstants;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.shuangfa114.moremekasuitmodules.gear.mekanism.ModuleFlameThrowerUnit;
import org.shuangfa114.moremekasuitmodules.init.mekanism.MekanismModulesInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemMekaTool.class)
public abstract class MixinItemMekaTool extends ItemEnergized implements IRadialModuleContainerItem, IBlastingItem {

    public MixinItemMekaTool(Properties properties) {
        super(properties);
    }

    @Inject(method = "use",at = @At("HEAD"), cancellable = true)
    private void test(Level world, Player player, @NotNull InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
        ItemStack stack = player.getItemInHand(hand);
        if (ChemicalUtil.hasChemicalOfType(player.getItemBySlot(EquipmentSlot.CHEST),MekanismChemicals.HYDROGEN.get())) {
            player.awardStat(Stats.ITEM_USED.get(this));
            player.startUsingItem(hand);
            if(IModuleHelper.INSTANCE.getIfEnabled(stack,MekanismModulesInit.MODULE_FLAME_THROWER_UNIT)!=null){
                cir.setReturnValue(InteractionResultHolder.consume(stack));
            }
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration >= 0 && livingEntity instanceof Player player) {
            //If the flamethrower has gas, add the entity if we are on the server and use gas if we aren't creative
            IModule<ModuleFlameThrowerUnit> module = IModuleHelper.INSTANCE.getModule(stack, MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
            if (module != null) {
                if (ChemicalUtil.hasChemicalOfType(player.getItemBySlot(EquipmentSlot.CHEST), MekanismChemicals.HYDROGEN.get())) {
                    if (!level.isClientSide) {
                        EntityFlame flame = EntityFlame.create(level, livingEntity, livingEntity.getUsedItemHand(), module.getCustomInstance().getFlameMode());
                        if (flame != null) {
                            if (flame.isAlive()) {
                                //If the flame is alive (and didn't just instantly hit a block while trying to spawn add it to the world)
                                level.addFreshEntity(flame);
                            }
                            if (MekanismUtils.isPlayingMode(player)) {
                                IChemicalHandler handler = Capabilities.CHEMICAL.getCapability(stack);
                                if (handler != null) {
                                    ChemicalStack chemicalStack = StorageUtils.getContainedChemical(handler, MekanismChemicals.HYDROGEN);
                                    chemicalStack.setAmount(1);
                                    handler.extractChemical(chemicalStack, Action.EXECUTE);
                                }
                            }
                        }
                    }
                } else {
                    //If the flamethrower runs out of gas, make it act as if the entity stopped using the item
                    // Have this happen on both the server and the client
                    livingEntity.releaseUsingItem();
                }
            } else {
                livingEntity.releaseUsingItem();
            }
        } else {
            livingEntity.releaseUsingItem();
        }
    }
    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 60 * SharedConstants.TICKS_PER_MINUTE;
    }
}
