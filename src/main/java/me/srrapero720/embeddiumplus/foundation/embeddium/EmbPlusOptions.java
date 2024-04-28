package me.srrapero720.embeddiumplus.foundation.embeddium;

import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import me.srrapero720.embeddiumplus.EmbyConfig;
import me.srrapero720.embeddiumplus.EmbyConfig.FullScreenMode;
import me.srrapero720.embeddiumplus.EmbyTools;
import me.srrapero720.embeddiumplus.foundation.embeddium.storage.EmbPlusOptionsStorage;
import net.minecraft.network.chat.Component;

import java.util.List;

public class EmbPlusOptions {
    public static final OptionStorage<?> STORAGE = new EmbPlusOptionsStorage();

    public static Option<FullScreenMode> getFullscreenOption(MinecraftOptionsStorage options) {
        return OptionImpl.createBuilder(FullScreenMode.class, options)
                .setName(Component.translatable("embeddium.plus.options.screen.title"))
                .setTooltip(Component.translatable("embeddium.plus.options.screen.desc"))
                .setControl((opt) -> new CyclingControl<>(opt, FullScreenMode.class, new Component[] {
                        Component.translatable("embeddium.plus.options.screen.windowed"),
                        Component.translatable("embeddium.plus.options.screen.borderless"),
                        Component.translatable("options.fullscreen")
                }))
                .setBinding(EmbyConfig::setFullScreenMode, (opts) -> EmbyConfig.fullScreen.get()).build();
    }

    public static void setPerformanceOptions(List<OptionGroup> groups, SodiumOptionsStorage sodiumOpts) {
        var builder = OptionGroup.createBuilder();
        var fontShadow = OptionImpl.createBuilder(boolean.class, sodiumOpts)
                .setName(Component.translatable("embeddium.plus.options.fontshadow.title"))
                .setTooltip(Component.translatable("embeddium.plus.options.fontshadow.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> {
                            EmbyConfig.fontShadows.set(value);
                            EmbyConfig.fontShadowsCache = value;
                        },
                        (options) -> EmbyConfig.fontShadowsCache)
                .setImpact(OptionImpact.VARIES)
                .build();

        var leavesCulling = OptionImpl.createBuilder(EmbyConfig.LeavesCullingMode.class, sodiumOpts)
                .setName(Component.translatable("embeddium.plus.options.leaves_culling.title"))
                .setTooltip(Component.translatable("embeddium.plus.options.leaves_culling.desc"))
                .setControl(opt -> new CyclingControl<>(opt, EmbyConfig.LeavesCullingMode.class, new Component[] {
                        Component.translatable("embeddium.plus.options.leaves_culling.all"),
                        Component.translatable("embeddium.plus.options.leaves_culling.off")
                }))
                .setBinding((opt, v) -> EmbyConfig.leavesCulling.set(v),
                        (options) -> EmbyConfig.leavesCulling.get())
                .setImpact(OptionImpact.HIGH)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build();

        var fastChest = OptionImpl.createBuilder(boolean.class, sodiumOpts)
                .setName(Component.translatable("embeddium.plus.options.fastchest.title"))
                .setTooltip(Component.translatable("embeddium.plus.options.fastchest.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> {
                            EmbyConfig.fastChests.set(value);
                            EmbyConfig.fastChestsCache = value;
                        },
                        (options) -> EmbyConfig.fastChestsCache)
                .setImpact(OptionImpact.HIGH)
//                .setEnabled(FastModels.canUseOnChests())
                .setEnabled(false)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build();

        var fastBeds = OptionImpl.createBuilder(boolean.class, sodiumOpts)
                .setName(Component.translatable("embeddium.plus.options.fastbeds.title"))
                .setTooltip(Component.translatable("embeddium.plus.options.fastbeds.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> {
                            EmbyConfig.fastBeds.set(value);
                            EmbyConfig.fastBedsCache = value;
                        },
                        (options) -> EmbyConfig.fastBedsCache)
                .setImpact(OptionImpact.LOW)
//                .setEnabled(EmbyTools.isFlywheelOff())
                .setEnabled(false)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build();

        var hideJEI = OptionImpl.createBuilder(boolean.class, sodiumOpts)
                .setName(Component.translatable("embeddium.plus.options.jei.title"))
                .setTooltip(Component.translatable("embeddium.plus.options.jei.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> {
                            EmbyConfig.hideJREMI.set(value);
                            EmbyConfig.hideJREMICache = value;
                        },
                        (options) -> EmbyConfig.hideJREMICache)
                .setImpact(OptionImpact.LOW)
                .setEnabled(EmbyTools.isModInstalled("jei") || EmbyTools.isModInstalled("roughlyenoughitems") || EmbyTools.isModInstalled("emi"))
                .build();

        builder.add(leavesCulling);
        builder.add(fontShadow);
        builder.add(fastChest);
        builder.add(fastBeds);
        builder.add(hideJEI);

        groups.add(builder.build());
    }
}
