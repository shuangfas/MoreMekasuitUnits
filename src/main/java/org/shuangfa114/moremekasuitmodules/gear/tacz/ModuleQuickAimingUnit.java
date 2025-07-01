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

public record ModuleQuickAimingUnit(AimTime aimTime) implements ICustomModule<ModuleQuickAimingUnit>, WithOffMode {
    public static final ResourceLocation AIMING_TIME = MoreMekasuitModules.rl("quick_aiming");

    public ModuleQuickAimingUnit(IModule<ModuleQuickAimingUnit> module) {
        this(module.<AimTime>getConfigOrThrow(AIMING_TIME).get());
    }

    public float getAimTime() {
        return aimTime.getValue();
    }

    public Component getTextComponent() {
        return aimTime.getTextComponent();
    }

    @Override
    public void addHUDStrings(IModule<ModuleQuickAimingUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player, Consumer<Component> hudStringAdder) {
        if (module.isEnabled()) {
            hudStringAdder.accept(ModLang.MODULE_QUICK_AIMING_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO, getTextComponent().getString()));
        }
    }

    @Override
    public boolean isOffMode() {
        return this.aimTime.getOffMode().equals(this.aimTime);
    }

    @NothingNullByDefault
    public enum AimTime implements IModeEnum<Float> {
        OFF(1F),
        LOW(0.8F),
        MEDIUM(0.6F),
        HIGH(0.4F),
        EXTREME(0.2F);

        public static final Codec<AimTime> CODEC = StringRepresentable.fromEnum(AimTime::values);
        public static final IntFunction<AimTime> BY_ID = ByIdMap.continuous(AimTime::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, AimTime> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, AimTime::ordinal);
        private final String serializedName;
        private final float aimTime;
        private final Component label;

        AimTime(float aimTime) {
            this.serializedName = name().toLowerCase(Locale.ROOT);
            this.aimTime = aimTime;
            this.label = TextComponentUtil.getString((int) (aimTime * 100) + "%");
        }

        public Component getTextComponent() {
            return this.label;
        }

        @Override
        public Float getValue() {
            return aimTime;
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
