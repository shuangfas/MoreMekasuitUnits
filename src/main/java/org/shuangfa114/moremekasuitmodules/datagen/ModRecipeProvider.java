package org.shuangfa114.moremekasuitmodules.datagen;

import com.tacz.guns.init.ModItems;
import mekanism.common.registries.MekanismItems;
import mekanism.common.tags.MekanismTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import org.jetbrains.annotations.NotNull;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.init.tacz.TaczItemInit;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        if(MoreMekasuitModules.isTaczLoaded){
            ItemStack attachment = new ItemStack(ModItems.ATTACHMENT.get());
            ModItems.ATTACHMENT.get().setAttachmentId(attachment, MoreMekasuitModules.taczRl("sight_552"));
            fromComponent(output,attachment,TaczItemInit.QUICK_AIMING_UNIT);
            ModItems.ATTACHMENT.get().setAttachmentId(attachment, MoreMekasuitModules.taczRl("light_extended_mag_3"));
            fromComponent(output,attachment,TaczItemInit.QUICK_RELOADING_UNIT);
            ModItems.ATTACHMENT.get().setAttachmentId(attachment, MoreMekasuitModules.taczRl("stock_carbon_bone_c5"));
            fromComponent(output,attachment,TaczItemInit.QUICK_SPRINTSHOOT_UNIT);
            ModItems.ATTACHMENT.get().setAttachmentId(attachment, MoreMekasuitModules.taczRl("oem_stock_heavy"));
            fromComponent(output,attachment,TaczItemInit.RECOIL_OFFSET_UNIT);
        }
    }
    private static void fromComponent(RecipeOutput output, ItemStack attachment, ItemLike result){
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("I#I")
                .pattern("IAI")
                .pattern("PPP")
                .define('I', MekanismTags.Items.ALLOYS_ELITE)
                .define('A', MekanismItems.MODULE_BASE)
                .define('P', MekanismTags.Items.PELLETS_POLONIUM)
                .define('#', DataComponentIngredient.of(true, attachment))
                .unlockedBy(getHasName(MekanismItems.MODULE_BASE), has(MekanismItems.MODULE_BASE))
                .save(output);
    }
}
