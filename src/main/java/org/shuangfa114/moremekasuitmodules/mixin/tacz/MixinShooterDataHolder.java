package org.shuangfa114.moremekasuitmodules.mixin.tacz;


import com.tacz.guns.entity.shooter.ShooterDataHolder;
import org.shuangfa114.moremekasuitmodules.util.tacz.IShooterDataHolder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ShooterDataHolder.class,remap = false)
public abstract class MixinShooterDataHolder implements IShooterDataHolder {
    public boolean lastAim;

    @Override
    public boolean getLastAim() {
        return lastAim;
    }

    @Override
    public void setLastAim(boolean a) {
        lastAim = a;
    }

}
