package org.shuangfa114.moremekasuitmodules.gear.mekanism;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleContainer;
import mekanism.api.text.EnumColor;
import mekanism.common.item.gear.ItemFlamethrower;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.init.ModLang;
import org.shuangfa114.moremekasuitmodules.util.IModeEnum;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public record ModuleFlameThrowerUnit(FlameMode flameMode) implements ICustomModule<ModuleFlameThrowerUnit> {

    public static final ResourceLocation FLAME_MODE = MoreMekasuitModules.rl("flame_mode");

    public ModuleFlameThrowerUnit(IModule<ModuleFlameThrowerUnit> module) {
        this(module.<FlameMode>getConfigOrThrow(FLAME_MODE).get());
    }


    public ItemFlamethrower.FlamethrowerMode getFlameMode() {
        return this.flameMode.getValue();
    }

    public Component getTextComponent() {
        return this.flameMode.getTextComponent();
    }

    @Override
    public InteractionResult onItemUse(IModule<ModuleFlameThrowerUnit> module, UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && ChemicalUtil.hasChemicalOfType(player.getItemBySlot(EquipmentSlot.CHEST), MekanismChemicals.HYDROGEN.get())) {
            player.startUsingItem(context.getHand());
            return InteractionResult.CONSUME;
        }
        return ICustomModule.super.onItemUse(module, context);
    }

    @Override
    public void addHUDStrings(IModule<ModuleFlameThrowerUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player, Consumer<Component> hudStringAdder) {
        if (module.isEnabled()) {
            hudStringAdder.accept(ModLang.MODULE_FLAME_THROWER.translateColored(EnumColor.DARK_GRAY).append(": ").append(getTextComponent()));
        }
    }

    @NothingNullByDefault
    public enum FlameMode implements IModeEnum<ItemFlamethrower.FlamethrowerMode> {
        COMBAT(ItemFlamethrower.FlamethrowerMode.COMBAT),
        HEAT(ItemFlamethrower.FlamethrowerMode.HEAT),
        INFERNO(ItemFlamethrower.FlamethrowerMode.INFERNO);

        public static final Codec<FlameMode> CODEC = StringRepresentable.fromEnum(FlameMode::values);
        public static final IntFunction<FlameMode> BY_ID = ByIdMap.continuous(FlameMode::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, FlameMode> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, FlameMode::ordinal);
        private final String serializedName;
        private final ItemFlamethrower.FlamethrowerMode flameMode;
        private final Component label;

        FlameMode(ItemFlamethrower.FlamethrowerMode mode) {
            this.serializedName = name().toLowerCase(Locale.ROOT);
            this.flameMode = mode;
            this.label = mode.getTextComponent();
        }

        @Override
        public @Nullable IModeEnum<ItemFlamethrower.FlamethrowerMode> getOffMode() {
            return null;
        }

        public Component getTextComponent() {
            return this.label;
        }

        @Override
        public ItemFlamethrower.FlamethrowerMode getValue() {
            return this.flameMode;
        }


        @Override
        public String getSerializedName() {
            return serializedName;
        }
    }
}
