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

public record ModuleQuickReloadingUnit(
        ReloadingTime reloadingTime) implements ICustomModule<ModuleQuickReloadingUnit>, WithOffMode {

    public static final ResourceLocation RELOADING_TIME = MoreMekasuitModules.rl("quick_reloading");

    public ModuleQuickReloadingUnit(IModule<ModuleQuickReloadingUnit> module) {
        this(module.<ReloadingTime>getConfigOrThrow(RELOADING_TIME).get());
    }

    public float getReloadingTime() {
        return reloadingTime.getValue();
    }

    public Component getTextComponent() {
        return reloadingTime.getTextComponent();
    }

    @Override
    public void addHUDStrings(IModule<ModuleQuickReloadingUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player, Consumer<Component> hudStringAdder) {
        if (module.isEnabled()) {
            hudStringAdder.accept(ModLang.MODULE_QUICK_RELOADING_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO, getTextComponent().getString()));
        }
    }

    @Override
    public boolean isOffMode() {
        return reloadingTime.getOffMode().equals(this.reloadingTime);
    }

    @NothingNullByDefault
    public enum ReloadingTime implements IModeEnum<Float> {
        OFF(1F),
        LOW(0.8F),
        MEDIUM(0.6F),
        HIGH(0.4F),
        EXTREME(0.2F);

        public static final Codec<ReloadingTime> CODEC = StringRepresentable.fromEnum(ReloadingTime::values);
        public static final IntFunction<ReloadingTime> BY_ID = ByIdMap.continuous(ReloadingTime::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, ReloadingTime> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, ReloadingTime::ordinal);
        private final String serializedName;
        private final float reloadingTime;
        private final Component label;

        ReloadingTime(float ReloadingTime) {
            this.serializedName = name().toLowerCase(Locale.ROOT);
            this.reloadingTime = ReloadingTime;
            this.label = TextComponentUtil.getString((int) (ReloadingTime * 100) + "%");
        }

        public Component getTextComponent() {
            return this.label;
        }

        @Override
        public Float getValue() {
            return reloadingTime;
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
