package org.shuangfa114.moremekasuitmodules.gear.mekanism;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleContainer;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.Mekanism;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;
import org.shuangfa114.moremekasuitmodules.config.ModConfig;
import org.shuangfa114.moremekasuitmodules.gear.WithOffMode;
import org.shuangfa114.moremekasuitmodules.init.ModLang;
import org.shuangfa114.moremekasuitmodules.util.IModeEnum;
import org.shuangfa114.moremekasuitmodules.util.ModuleUtil;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public record ModuleElytraAccelerationUnit(
        Acceleration acceleration) implements ICustomModule<ModuleElytraAccelerationUnit>, WithOffMode {
    public static final ResourceLocation ACCELERATION = MoreMekasuitModules.rl("elytra_acceleration");

    public ModuleElytraAccelerationUnit(IModule<ModuleElytraAccelerationUnit> module) {
        this(module.<Acceleration>getConfigOrThrow(ACCELERATION).get());
    }

    @Override
    public void tickServer(IModule<ModuleElytraAccelerationUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player) {
        long usage = getEnergyUsage();
        if (Mekanism.keyMap.has(player.getUUID(), 0) && player.isFallFlying() && ModuleUtil.isValid(module, player, stack, usage)) {
            module.useEnergy(player, stack, usage);
        }
    }

    @Override
    public void tickClient(IModule<ModuleElytraAccelerationUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player) {
        if (player instanceof LocalPlayer) {
            if (Minecraft.getInstance().options.keyJump.isDown()) {
                if (player.isFallFlying()) {
                    long usage = getEnergyUsage();
                    if (ModuleUtil.isValid(module, player, stack, usage) && getAcceleration() != 1F) {
                        Vec3 lookAngle = player.getLookAngle();
                        player.setDeltaMovement(lookAngle.scale(getAcceleration()));
                    }
                }
            }
        }
    }

    private long getEnergyUsage() {
        return ModuleUtil.convertToFE((long) (ModConfig.base.energyElytraAccelerationEachTick.get() * getAcceleration()));
    }

    public float getAcceleration() {
        return this.acceleration.getValue();
    }

    public Component getTextComponent() {
        return this.acceleration.getTextComponent();
    }

    @Override
    public void addHUDStrings(IModule<ModuleElytraAccelerationUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player, Consumer<Component> hudStringAdder) {
        if (module.isEnabled()) {
            hudStringAdder.accept(ModLang.MODULE_ELYTRA_ACCELERATION_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO, this.getTextComponent().getString()));
        }
    }

    @Override
    public boolean isOffMode() {
        return this.acceleration.getOffMode().equals(this.acceleration);
    }


    @NothingNullByDefault
    public enum Acceleration implements IModeEnum<Float> {
        OFF(1F),
        MEDIUM(2.5F),
        HIGH(5F),
        EXTREME(7.5F);

        public static final Codec<Acceleration> CODEC = StringRepresentable.fromEnum(Acceleration::values);
        public static final IntFunction<Acceleration> BY_ID = ByIdMap.continuous(Acceleration::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, Acceleration> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Acceleration::ordinal);
        private final String serializedName;
        private final float acceleration;
        private final Component label;


        Acceleration(float acceleration) {
            this.serializedName = name().toLowerCase(Locale.ROOT);
            this.acceleration = acceleration;
            this.label = TextComponentUtil.getString((int) (acceleration * 100) + "%");
        }

        public Component getTextComponent() {
            return this.label;
        }

        @Override
        public Float getValue() {
            return acceleration;
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
