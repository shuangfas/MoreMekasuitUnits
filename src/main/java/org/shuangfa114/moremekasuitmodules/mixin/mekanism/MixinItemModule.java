//package org.shuangfa114.moremekasuitmodules.mixin.mekanism;
//import com.llamalad7.mixinextras.sugar.Local;
//import mekanism.api.gear.IModuleHelper;
//import mekanism.common.item.ItemModule;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.level.Level;
//import org.jetbrains.annotations.NotNull;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import java.util.List;
//
//@Mixin(value = ItemModule.class,remap = false)
//public abstract class MixinItemModule {
//    @Inject(method = "appendHoverText",at = @At(value = "INVOKE", target = "Lmekanism/api/gear/IModuleHelper;getSupported(Lmekanism/api/providers/IModuleDataProvider;)Ljava/util/Set;"))
//    public void addConflicting(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag, CallbackInfo ci, @Local IModuleHelper moduleHelper){
//        //((ModulesHelperAccessor)moduleHelper).getConflictingModules().putAll(MoreMekasuitUnits.conflictingModules);
//    }
//}
