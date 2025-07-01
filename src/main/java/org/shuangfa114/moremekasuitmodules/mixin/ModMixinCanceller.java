package org.shuangfa114.moremekasuitmodules.mixin;

import com.bawnorton.mixinsquared.api.MixinCanceller;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;

import java.util.List;

public class ModMixinCanceller implements MixinCanceller {
    @Override
    public boolean shouldCancel(List<String> list, String s) {
        if (s.contains("org.shuangfa114.moremekasuitmodules.mixin.thirst")) {
            return !MoreMekasuitModules.isThirstLoaded;
        }
        return false;
    }
}
