package org.shuangfa114.moremekasuitmodules;

import com.mojang.logging.LogUtils;
import mekanism.api.MekanismIMC;
import mekanism.api.gear.ModuleData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import org.shuangfa114.moremekasuitmodules.config.ModConfig;
import org.shuangfa114.moremekasuitmodules.datagen.ModRecipeProvider;
import org.shuangfa114.moremekasuitmodules.event.EntityHurtByGun;
import org.shuangfa114.moremekasuitmodules.init.ModTabs;
import org.shuangfa114.moremekasuitmodules.init.mekanism.MekanismItemInit;
import org.shuangfa114.moremekasuitmodules.init.mekanism.MekanismModulesInit;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczItemInit;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczModulesInit;
import org.shuangfa114.moremekasuitmodules.init.thirst.ThirstItemInit;
import org.shuangfa114.moremekasuitmodules.init.thirst.ThirstModulesInit;
import org.slf4j.Logger;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MoreMekasuitModules.MODID)
public class MoreMekasuitModules {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "moremekasuitmodules";
    public static final String MOD_NAME = "MekanismMoreModules";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Map<ModuleData<?>, Set<ModuleData<?>>> conflictingModules = new IdentityHashMap<>();

    public static boolean isTaczLoaded;
    public static boolean isThirstLoaded;
    public MoreMekasuitModules() {

        ModContainer modContainer = ModLoadingContext.get().getActiveContainer();
        NeoForge.EVENT_BUS.register(this);
        IEventBus modEventBus = modContainer.getEventBus();
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        isTaczLoaded = ModList.get().isLoaded("tacz");
        isThirstLoaded = ModList.get().isLoaded("thirst");
        MekanismItemInit.ITEMS.register(modEventBus);
        MekanismModulesInit.MODULES.register(modEventBus);
        ModConfig.registerConfigs(modContainer);
        //Tacz
        if(isTaczLoaded){
            TaczItemInit.ITEMS.register(modEventBus);
            TaczModulesInit.MODULES.register(modEventBus);
            NeoForge.EVENT_BUS.register(new EntityHurtByGun());
        }
        //Thirst
        if(isThirstLoaded) {
            ThirstItemInit.ITEMS.register(modEventBus);
            ThirstModulesInit.MODULES.register(modEventBus);
        }
        modEventBus.addListener(ModConfig::onConfigLoad);
        modEventBus.addListener(this::imcQueue);
    }
    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MoreMekasuitModules.MODID, path);
    }
    public static ResourceLocation taczRl(String path) {
        return ResourceLocation.fromNamespaceAndPath("tacz", path);
    }
    private void imcQueue(InterModEnqueueEvent event)
    {
        MekanismIMC.addMekaToolModules(MekanismModulesInit.MODULE_FLAME_THROWER_UNIT);
        MekanismIMC.addMekaSuitBodyarmorModules(MekanismModulesInit.MODULE_ELYTRA_ACCELERATION_UNIT);
        MekanismIMC.addMekaToolModules(MekanismModulesInit.MODULE_LOOTING_MODIFICATION_UNIT);
        if(isTaczLoaded){
            MekanismIMC.addMekaSuitBodyarmorModules(TaczModulesInit.MODULE_QUICK_AIMING_UNIT);
            MekanismIMC.addMekaSuitBodyarmorModules(TaczModulesInit.MODULE_RECOIL_OFFSET_UNIT);
            MekanismIMC.addMekaSuitBodyarmorModules(TaczModulesInit.MODULE_QUICK_RELOADING_UNIT);
            MekanismIMC.addMekaSuitBodyarmorModules(TaczModulesInit.MODULE_QUICK_SPRINTSHOOT_UNIT);
            MekanismIMC.addMekaSuitModules(TaczModulesInit.MODULE_BULLETPROOF_UNIT);
        }
        if (isThirstLoaded) {
            MekanismIMC.addMekaSuitHelmetModules(ThirstModulesInit.MODULE_AUTOMATIC_DRINKING_UNIT);
        }
    }
    //NEVER DELETE IT!!!!!
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
