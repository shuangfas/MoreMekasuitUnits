package org.shuangfa114.moremekasuitmodules.gear.tacz;

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

public record ModuleRecoilOffsetUnit(
        RecoilOffset recoil) implements ICustomModule<ModuleRecoilOffsetUnit>, WithOffMode {

    public static final ResourceLocation RECOIL_OFFSET = MoreMekasuitModules.rl("recoil_offset");

    public ModuleRecoilOffsetUnit(IModule<ModuleRecoilOffsetUnit> module) {
        this(module.<RecoilOffset>getConfigOrThrow(RECOIL_OFFSET).get());
    }

    public float getRecoil() {
        return recoil.getValue();
    }

    public Component getTextComponent() {
        return recoil.getTextComponent();
    }

    @Override
    public void addHUDStrings(IModule<ModuleRecoilOffsetUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player, Consumer<Component> hudStringAdder) {
        if (module.isEnabled()) {
            hudStringAdder.accept(ModLang.MODULE_RECOIL_OFFSET_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO, getTextComponent().getString()));
        }
    }

    @Override
    public boolean isOffMode() {
        return recoil.getOffMode().equals(this.recoil);
    }

    @NothingNullByDefault
    public enum RecoilOffset implements IModeEnum<Float> {
        OFF(1F),
        LOW(0.8F),
        MEDIUM(0.6F),
        HIGH(0.4F),
        EXTREME(0.2F);

        public static final Codec<RecoilOffset> CODEC = StringRepresentable.fromEnum(RecoilOffset::values);
        public static final IntFunction<RecoilOffset> BY_ID = ByIdMap.continuous(RecoilOffset::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, RecoilOffset> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, RecoilOffset::ordinal);
        private final String serializedName;
        private final float recoil;
        private final Component label;

        RecoilOffset(float recoil) {
            this.serializedName = name().toLowerCase(Locale.ROOT);
            this.recoil = recoil;
            this.label = TextComponentUtil.getString((int) (recoil * 100) + "%");
        }

        @Override
        public Component getTextComponent() {
            return this.label;
        }

        @Override
        public Float getValue() {
            return recoil;
        }

        @Override
        public IModeEnum<Float> getOffMode() {
            return OFF;
        }

        @Override
        public String getSerializedName() {
            return serializedName;
        }
    }
}
