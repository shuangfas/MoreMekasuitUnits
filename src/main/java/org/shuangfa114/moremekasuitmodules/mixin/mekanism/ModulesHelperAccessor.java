package org.shuangfa114.moremekasuitmodules.mixin.mekanism;

import mekanism.api.gear.ModuleData;
import mekanism.common.content.gear.ModuleHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Set;

@Mixin(value = ModuleHelper.class,remap = false)
public interface ModulesHelperAccessor {
    @Accessor(value = "conflictingModules")
    Map<ModuleData<?>, Set<ModuleData<?>>> getConflictingModules();
}
