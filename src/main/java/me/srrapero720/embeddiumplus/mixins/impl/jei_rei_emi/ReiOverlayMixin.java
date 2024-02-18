package me.srrapero720.embeddiumplus.mixins.impl.jei_rei_emi;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.impl.client.REIRuntimeImpl;
import me.shedaniel.rei.impl.client.gui.ScreenOverlayImpl;
import me.shedaniel.rei.impl.client.gui.dragging.CurrentDraggingStack;
import me.shedaniel.rei.impl.client.gui.widget.entrylist.EntryListWidget;
import me.shedaniel.rei.impl.client.gui.widget.search.OverlaySearchField;
import me.srrapero720.embeddiumplus.EmbyConfig;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = ScreenOverlayImpl.class, priority = 500, remap = false)
@Pseudo
public class ReiOverlayMixin {
    @Unique private EntryListWidget embPlus$entryListWidget;

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    public boolean redirect$addOnList(List<Widget> instance, Object e, Operation<Boolean> original) {
        if (e instanceof EntryListWidget listWidget) {
            embPlus$entryListWidget = listWidget;
            return false;
        } else {
            return original.call(instance, e);
        }
    }

    @Inject(method = "renderWidgets", at = @At("TAIL"))
    public void inject$bottom(GuiGraphics graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (EmbyConfig.hideJREMICache && REIRuntimeImpl.getSearchField().getText().isEmpty()) return;
        embPlus$entryListWidget.render(graphics, mouseX, mouseY, delta);
    }
}