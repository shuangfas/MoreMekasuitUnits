package org.shuangfa114.moremekasuitmodules.util;

import mekanism.api.text.IHasTextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public interface IModeEnum<T> extends IHasTextComponent, StringRepresentable {
    IModeEnum<T> getOffMode();
    Component getTextComponent();
    T getValue();

}
