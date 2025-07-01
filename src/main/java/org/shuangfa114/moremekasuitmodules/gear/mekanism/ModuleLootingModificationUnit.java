package org.shuangfa114.moremekasuitmodules.gear.mekanism;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleContainer;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.gear.WithOffMode;
import org.shuangfa114.moremekasuitmodules.init.ModLang;
import org.shuangfa114.moremekasuitmodules.util.IModeEnum;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public record ModuleLootingModificationUnit(
        Multiplier multiplier) implements ICustomModule<ModuleLootingModificationUnit>, WithOffMode {
    public static final ResourceLocation LOOTING_MULTIPLIER = MoreMekasuitModules.rl("looting_modification");

    public ModuleLootingModificationUnit(IModule<ModuleLootingModificationUnit> module) {
        this(module.<Multiplier>getConfigOrThrow(LOOTING_MULTIPLIER).get());
    }

    public float getMultiplier() {
        return this.multiplier.getMultiplier();
    }

    public Component getTextComponent() {
        return this.multiplier.getTextComponent();
    }

    @Override
    public void addHUDStrings(IModule<ModuleLootingModificationUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player, Consumer<Component> hudStringAdder) {
        if (module.isEnabled()) {
            hudStringAdder.accept(ModLang.MODULE_LOOTING_MODIFICATION_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO, this.getTextComponent().getString()));
        }
    }

    @Override
    public boolean isOffMode() {
        return this.multiplier.getOffMode().equals(this.multiplier);
    }

    @NothingNullByDefault
    public enum Multiplier implements IModeEnum<Float> {
        OFF(1F),
        MEDIUM(2.5F),
        HIGH(5F);

        public static final Codec<Multiplier> CODEC = StringRepresentable.fromEnum(Multiplier::values);
        public static final IntFunction<Multiplier> BY_ID = ByIdMap.continuous(Multiplier::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, Multiplier> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Multiplier::ordinal);
        private final String serializedName;
        private final float multiplier;
        private final Component label;

        Multiplier(float multiplier) {
            this.serializedName = name().toLowerCase(Locale.ROOT);
            this.multiplier = multiplier;
            this.label = TextComponentUtil.getString((int) (multiplier * 100) + "%");
        }

        public Component getTextComponent() {
            return this.label;
        }

        @Override
        public Float getValue() {
            return multiplier;
        }

        public float getMultiplier() {
            return this.multiplier;
        }

        @Override
        public IModeEnum getOffMode() {
            return OFF;
        }

        @Override
        public String getSerializedName() {
            return serializedName;
        }
    }
}
