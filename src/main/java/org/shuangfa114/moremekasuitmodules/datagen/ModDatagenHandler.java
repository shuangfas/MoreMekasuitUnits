package org.shuangfa114.moremekasuitmodules.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;

import java.util.concurrent.CompletableFuture;
@EventBusSubscriber(modid = MoreMekasuitModules.MODID)
public class ModDatagenHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(
                event.includeServer(),
                new ModRecipeProvider(output,lookupProvider)
        );
    }
}
