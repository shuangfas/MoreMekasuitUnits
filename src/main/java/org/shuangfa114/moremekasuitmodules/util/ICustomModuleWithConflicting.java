package org.shuangfa114.moremekasuitmodules.util;

import mekanism.api.gear.ModuleData;
import org.shuangfa114.moremekasuitmodules.MoreMekasuitModules;


import java.util.HashSet;
import java.util.Set;

public interface ICustomModuleWithConflicting {
    default void addConflicting(ModuleData<?> moduleData, Set<ModuleData<?>> moduleDataSet) {
        MoreMekasuitModules.conflictingModules.putIfAbsent(moduleData, new HashSet<>());
        for (ModuleData<?> data : moduleDataSet){
            MoreMekasuitModules.conflictingModules.get(moduleData).add(data);
            MoreMekasuitModules.conflictingModules.putIfAbsent(data, new HashSet<>());
            MoreMekasuitModules.conflictingModules.get(data).add(moduleData);
        }
    }
}
