package org.shuangfa114.moremekasuitunits.module.gear.tacz;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.IModuleConfigItem;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.gear.config.ModuleEnumData;
import mekanism.api.radial.mode.NestedRadialMode;
import mekanism.api.text.EnumColor;
import mekanism.api.text.IHasTextComponent;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.MekanismLang;
import mekanism.common.content.gear.mekatool.ModuleAttackAmplificationUnit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.shuangfa114.moremekasuitunits.init.ModLang;

import java.util.function.Consumer;

public class ModuleQuickAimingUnit implements ICustomModule<ModuleQuickAimingUnit> {
    private IModuleConfigItem<AimTime> aimTime;
    public ModuleQuickAimingUnit() {
    }

    @Override
    public void init(IModule<ModuleQuickAimingUnit> module, ModuleConfigItemCreator configItemCreator) {
        this.aimTime = configItemCreator.createConfigItem("aim_time", ModLang.MODULE_QUICK_AIMING, new ModuleEnumData(AimTime.LOW, module.getInstalledCount()+1));
    }

    public float getAimTime(){
        return this.aimTime.get().getAimTime();
    }

    public Component getTextComponent(){return this.aimTime.get().getTextComponent();}

    @Override
    public void addHUDStrings(IModule<ModuleQuickAimingUnit> module, Player player, Consumer<Component> hudStringAdder) {
        if(module.isEnabled()){
            hudStringAdder.accept(ModLang.MODULE_QUICK_AIMING_HUD.translateColored(EnumColor.DARK_GRAY, EnumColor.INDIGO,getTextComponent().getString()));
        }
    }

    @NothingNullByDefault
    public enum AimTime implements IHasTextComponent {
        OFF(1F),
        LOW(0.8F),
        MEDIUM(0.6F),
        HIGH(0.4F),
        EXTREME(0.2F);

        private final float aimTime;
        private final Component label;

        AimTime(float aimTime) {
            this.aimTime = aimTime;
            this.label = TextComponentUtil.getString((int)(aimTime*100)+"%");
        }

        public Component getTextComponent() {
            return this.label;
        }

        public float getAimTime() {
            return this.aimTime;
        }
    }
}
