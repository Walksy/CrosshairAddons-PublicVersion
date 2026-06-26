package walksy.crosshairaddons.config;

import main.walksy.lib.api.WalksyLibConfig;
import main.walksy.lib.core.config.impl.ModConfig;
import main.walksy.lib.core.config.local.Category;
import main.walksy.lib.core.config.local.Option;
import main.walksy.lib.core.config.local.OptionDescription;
import main.walksy.lib.core.config.local.options.BooleanOption;
import main.walksy.lib.core.config.local.options.NumericalOption;
import main.walksy.lib.core.config.local.options.PixelGridAnimationOption;
import main.walksy.lib.core.config.local.options.StringListOption;
import main.walksy.lib.core.config.local.options.groups.OptionGroup;
import main.walksy.lib.core.config.local.options.type.PixelGrid;
import main.walksy.lib.core.config.local.options.type.PixelGridAnimation;
import main.walksy.lib.core.gui.impl.HudEditorScreen;
import main.walksy.lib.core.utils.PathUtils;
import net.minecraft.client.Minecraft;
import walksy.crosshairaddons.CrosshairAddons;

import java.util.List;

public class Config implements WalksyLibConfig {

    public static boolean modEnabled = true;
    public static int attackIndicatorGap = -2;

    public static boolean environmentBlend = true;
    public static boolean showInThirdPerson = false;

    public static boolean entityIndicatorAddonEnabled = true;
    public static boolean entityIndicatorShowAll = false;
    public static List<String> entityIndicatorEntityList = List.of("player");
    public static PixelGridAnimation entityIndicatorAddon = new PixelGridAnimation(PixelGrid.create()
        .set(4, 4).set(5, 4)
        .set(9, 4).set(10, 4)
        .set(4, 5)
        .set(10, 5)
        .set(4, 10).set(5, 10)
        .set(9, 10).set(10, 10)
        .set(4, 9)
        .set(10, 9)
        .build()).offset(0, 0);

    public static boolean elytraAddonEnabled = true;
    public static PixelGridAnimation elytraAddon = new PixelGridAnimation(PixelGrid.create()
        .set(1, 4).set(1, 5).set(1, 6).set(1, 7)
        .set(2, 3).set(2, 8).set(2, 9)
        .set(3, 3).set(3, 10).set(3, 11)
        .set(4, 2).set(4, 11)
        .set(5, 2).set(5, 10)
        .set(6, 2).set(6, 3).set(6, 4).set(6, 5).set(6, 6).set(6, 7).set(6, 8).set(6, 9)
        .set(7, 2).set(7, 3)
        .set(8, 2).set(8, 3).set(8, 4).set(8, 5).set(8, 6).set(8, 7).set(8, 8).set(8, 9)
        .set(9, 2).set(9, 10)
        .set(10, 2).set(10, 11)
        .set(11, 3).set(11, 10).set(11, 11)
        .set(12, 3).set(12, 8).set(12, 9)
        .set(13, 4).set(13, 5).set(13, 6).set(13, 7)
        .build()).offset(0, 11);

    public static boolean hitmarkerAddonEnabled = true;
    public static boolean hitmarkerToggleOnAttack = false;
    public static boolean hitmarkerStopOnAnimationEnd = true;
    public static int hitmarkerDuration = 15;
    public static PixelGridAnimation hitmarkerAddon = new PixelGridAnimation(PixelGrid.create()
        .set(3, 3).set(11, 3)
        .set(4, 4).set(10, 4)
        .set(5, 5).set(9, 5)
        .set(5, 9).set(9, 9)
        .set(4, 10).set(10, 10)
        .set(3, 11).set(11, 11)
        .build()).offset(0, 0);

    public static boolean shieldBreakAddonEnabled = false;
    public static boolean shieldIndicatorEnabled = false;
    public static boolean shieldBreakStopOnAnimationEnd = true;
    public static int shieldBreakDuration = 15;
    public static PixelGridAnimation shieldBreakAddon = new PixelGridAnimation(
        PixelGrid.create()
            .set(4, 5).set(5, 5).set(6, 5).set(7, 5).set(8, 5).set(9, 5).set(10, 5)
            .set(4, 6).set(10, 6)
            .set(4, 7).set(10, 7)
            .set(4, 8).set(10, 8)
            .set(4, 9).set(10, 9)
            .set(4, 10).set(10, 10)
            .set(5, 11).set(9, 11)
            .set(6, 12).set(7, 12).set(8, 12)
            .build(),

        PixelGrid.create()
            .set(3, 5).set(4, 5).set(5, 5).set(9, 5).set(10, 5).set(11, 5)
            .set(3, 6).set(11, 6)
            .set(3, 7).set(11, 7)
            .set(3, 8).set(11, 8)
            .set(3, 9).set(11, 9)
            .set(3, 10).set(11, 10)
            .set(4, 11).set(10, 11)
            .set(5, 12).set(9, 12).set(6, 12).set(8, 12)
            .build(),

        PixelGrid.create()
            .set(2, 5).set(4, 5).set(10, 5).set(11, 5)
            .set(2, 6).set(12, 6)
            .set(2, 7).set(12, 7)
            .set(12, 8)
            .set(2, 9).set(12, 9)
            .set(2, 10).set(12, 10)
            .set(3, 11)
            .set(10, 12)
            .build(),

        PixelGrid.create()
            .set(1, 5).set(3, 5).set(11, 5)
            .set(1, 7).set(13, 7)
            .set(1, 9).set(13, 9)
            .set(13, 10)
            .set(2, 11)
            .set(10, 12)
            .build(),

        PixelGrid.create()
            .set(2, 5)
            .set(0, 7).set(14, 7)
            .set(14, 9)
            .set(1, 11)
            .set(12, 12)
            .build()
    ).offset(0, 8.5).animationSpeed(17);


    public static PixelGridAnimation shieldIndicatorAddon = new PixelGridAnimation(PixelGrid.create()
        .set(4, 5).set(5, 5).set(6, 5).set(7, 5).set(8, 5).set(9, 5).set(10, 5)
        .set(4, 6).set(10, 6)
        .set(4, 7).set(10, 7)
        .set(4, 8).set(10, 8)
        .set(4, 9).set(10, 9)
        .set(4, 10).set(10, 10)
        .set(5, 11).set(9, 11)
        .set(6, 12).set(7, 12).set(8, 12)
        .build()).offset(0, 8.5);


    /**
     * General Category
     */

    private final Option<Boolean> modEnabledOption = BooleanOption.createBuilder("Mod Enabled", () -> modEnabled, modEnabled, newValue -> modEnabled = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Should Crosshair Addons be enabled"))
        .build();

    private final Option<Integer> attackIndicatorGapOption = NumericalOption.createBuilder("Vanilla Attack Indicator Gap", () -> attackIndicatorGap, attackIndicatorGap, newValue -> attackIndicatorGap = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Gap between vanilla attack indicator and any overlapping addons"))
        .availability(() -> modEnabled, "Requires 'Mod Enabled' to be enabled")
        .values(-5, 10, 1)
        .build();

    private final Category generalCategory = Category.createBuilder("General")
        .group(OptionGroup.createBuilder("Global Options")
            .addOption(modEnabledOption)
            .addOption(attackIndicatorGapOption)
            .build())
        .build();

    /**
     * Addons Category
     */

    private final Option<Boolean> environmentBlendOption = BooleanOption.createBuilder("Environment Blend", () -> environmentBlend, environmentBlend, newValue -> environmentBlend = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Blend crosshair with environmental lighting"))
        .availability(() -> modEnabled, "Requires 'Mod Enabled' to be enabled")
        .build();

    private final Option<Boolean> showInThirdPersonOption = BooleanOption.createBuilder("Show In Third Person", () -> showInThirdPerson, showInThirdPerson, newValue -> showInThirdPerson = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Should the addons show in third person"))
        .availability(() -> modEnabled, "Requires 'Mod Enabled' to be enabled")
        .build();

    private final Option<Boolean> entityIndicatorAddonEnabledOption = BooleanOption.createBuilder("Entity Indicator Addon Enabled", () -> entityIndicatorAddonEnabled, entityIndicatorAddonEnabled, newValue -> entityIndicatorAddonEnabled = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Enables the Entity Indicator addon"))
        .availability(() -> modEnabled, "Requires 'Mod Enabled' to be enabled")
        .build();

    private final Option<Boolean> entityIndicatorShowAllEntitiesOption = BooleanOption.createBuilder("Entity Indicator Show All Entities", () -> entityIndicatorShowAll, entityIndicatorShowAll, newValue -> entityIndicatorShowAll = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Should the entity indicator work for all entities"))
        .availability(() -> modEnabled, "Requires 'Mod Enabled' to be enabled")
        .build();

    private final Option<List<String>> entityIndicatorEntityListOption = StringListOption.createBuilder("Entity Indicator Entity List", () -> entityIndicatorEntityList, entityIndicatorEntityList, newValue -> entityIndicatorEntityList = newValue)
        .description(OptionDescription.ofOrderedString(() -> "List of entities that trigger the indicator"))
        .availability(() -> modEnabled && entityIndicatorAddonEnabled && !entityIndicatorShowAll, "Requires 'Mod Enabled & Entity Indicator Addon Enabled' to be enabled and 'Entity Indicator Show All Entities' to be disabled")
        .build();

    private final Option<PixelGridAnimation> entityIndicatorAddonOption = PixelGridAnimationOption.createBuilder("Entity Indicator Addon Icon", () -> entityIndicatorAddon, entityIndicatorAddon, newValue -> entityIndicatorAddon = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Icon animation for the Entity Indicator"))
        .availability(() -> modEnabled && entityIndicatorAddonEnabled, "Requires 'Mod Enabled & Entity Indicator Addon Enabled' to be enabled")
        .build();

    private final Option<Boolean> elytraAddonEnabledOption = BooleanOption.createBuilder("Elytra Addon Enabled", () -> elytraAddonEnabled, elytraAddonEnabled, newValue -> elytraAddonEnabled = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Enables the Elytra status addon"))
        .availability(() -> modEnabled, "Requires 'Mod Enabled' to be enabled")
        .build();

    private final Option<PixelGridAnimation> elytraAddonOption = PixelGridAnimationOption.createBuilder("Elytra Addon Icon", () -> elytraAddon, elytraAddon, newValue -> elytraAddon = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Icon animation for Elytra status"))
        .availability(() -> modEnabled && elytraAddonEnabled, "Requires 'Mod Enabled & Elytra Addon Enabled' to be enabled")
        .build();

    private final Option<Boolean> hitmarkerAddonEnabledOption = BooleanOption.createBuilder("Hitmarker Addon Enabled", () -> hitmarkerAddonEnabled, hitmarkerAddonEnabled, newValue -> hitmarkerAddonEnabled = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Enables the Hitmarker addon"))
        .availability(() -> modEnabled, "Requires 'Mod Enabled' to be enabled")
        .build();

    private final Option<Boolean> hitmarkerAddonToggleOnAttackOption = BooleanOption.createBuilder("Toggle Hitmarker On Attack", () -> hitmarkerToggleOnAttack, hitmarkerToggleOnAttack, newValue -> hitmarkerToggleOnAttack = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Automatically plays hitmarker when attacking"))
        .availability(() -> modEnabled && hitmarkerAddonEnabled, "Requires 'Mod Enabled & Hitmarker Addon Enabled' to be enabled")
        .build();

    private final Option<Boolean> hitmarkerAddonStopOnAnimationEndOption = BooleanOption.createBuilder("Hitmarker Stop On Animation End", () -> hitmarkerStopOnAnimationEnd, hitmarkerStopOnAnimationEnd, newValue -> hitmarkerStopOnAnimationEnd = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Stops hitmarker when its animation finishes"))
        .availability(() -> modEnabled && hitmarkerAddonEnabled, "Requires 'Mod Enabled & Hitmarker Addon Enabled' to be enabled")
        .build();

    private final Option<Integer> hitmarkerAddonDurationOption = NumericalOption.<Integer>createBuilder("Hitmarker Duration", () -> hitmarkerDuration, hitmarkerDuration, newValue -> hitmarkerDuration = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Duration of hitmarker display"))
        .availability(() -> modEnabled && hitmarkerAddonEnabled && !hitmarkerStopOnAnimationEnd, "Requires 'Mod Enabled & Hitmarker Addon Enabled' to be enabled and 'Hitmarker Stop On Animation End' to be disabled")
        .values(0, 50, 1)
        .build();

    private final Option<PixelGridAnimation> hitmarkerAddonOption = PixelGridAnimationOption.createBuilder("Hitmarker Addon Icon", () -> hitmarkerAddon, hitmarkerAddon, newValue -> hitmarkerAddon = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Icon animation for the Hitmarker"))
        .availability(() -> modEnabled && hitmarkerAddonEnabled, "Requires 'Mod Enabled & Hitmarker Addon Enabled' to be enabled")
        .build();

    private final Option<Boolean> shieldBreakAddonEnabledOption = BooleanOption.createBuilder("Shield Break Addon Enabled", () -> shieldBreakAddonEnabled, shieldBreakAddonEnabled, newValue -> shieldBreakAddonEnabled = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Enables the Shield Break addon"))
        .availability(() -> modEnabled, "Requires 'Mod Enabled' to be enabled")
        .build();

    private final Option<Boolean> shieldBreakStopOnAnimationEndOption = BooleanOption.createBuilder("Shield Break Stop On Animation End", () -> shieldBreakStopOnAnimationEnd, shieldBreakStopOnAnimationEnd, newValue -> shieldBreakStopOnAnimationEnd = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Stops shield break icon after animation ends"))
        .availability(() -> modEnabled && shieldBreakAddonEnabled, "Requires 'Mod Enabled & Shield Break Addon Enabled' to be enabled")
        .build();

    private final Option<Integer> shieldBreakAddonDurationOption = NumericalOption.<Integer>createBuilder("Shield Break Duration", () -> shieldBreakDuration, shieldBreakDuration, newValue -> shieldBreakDuration = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Duration of shield break icon display"))
        .values(0, 50, 1)
        .availability(() -> modEnabled && shieldBreakAddonEnabled && !shieldBreakStopOnAnimationEnd, "Requires 'Mod Enabled & Shield Break Addon Enabled' to be enabled & 'Shield Break Stop On Animation End' to be disabled")
        .build();

    private final Option<PixelGridAnimation> shieldBreakAddonOption = PixelGridAnimationOption.createBuilder("Shield Break Addon Icon", () -> shieldBreakAddon, shieldBreakAddon, newValue -> shieldBreakAddon = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Icon animation for shield break"))
        .availability(() -> modEnabled && shieldBreakAddonEnabled, "Requires 'Mod Enabled & Shield Break Addon Enabled' to be enabled")
        .build();

    private final Option<Boolean> shieldIndicatorAddonEnabledOption = BooleanOption.createBuilder("Shield Indicator Addon Enabled", () -> shieldIndicatorEnabled, shieldIndicatorEnabled, newValue -> shieldIndicatorEnabled = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Enables the Shield Indicator addon"))
        .availability(() -> modEnabled, "Requires 'Mod Enabled' to be enabled")
        .build();

    private final Option<PixelGridAnimation> shieldIndicatorAddonOption = PixelGridAnimationOption.createBuilder("Shield Indicator Addon Icon", () -> shieldIndicatorAddon, shieldIndicatorAddon, newValue -> shieldIndicatorAddon = newValue)
        .description(OptionDescription.ofOrderedString(() -> "Icon animation for shield indicator"))
        .availability(() -> modEnabled && shieldIndicatorEnabled, "Requires 'Mod Enabled & Shield Indicator Addon Enabled' to be enabled")
        .build();


    private final Category addonsCategory = Category.createBuilder("Addons")
        .group(OptionGroup.createBuilder("General Options")
            .addOption(environmentBlendOption)
            .addOption(showInThirdPersonOption)
            .build())
        .group(OptionGroup.createBuilder("Entity Indicator Addon Options")
            .addOption(entityIndicatorAddonEnabledOption)
            .addOption(entityIndicatorShowAllEntitiesOption)
            .addOption(entityIndicatorEntityListOption)
            .addOption(entityIndicatorAddonOption)
            .build())
        .group(OptionGroup.createBuilder("Elytra Addon Options")
            .addOption(elytraAddonEnabledOption)
            .addOption(elytraAddonOption)
            .build())
        .group(OptionGroup.createBuilder("Hitmarker Addon Options")
            .addOption(hitmarkerAddonEnabledOption)
            .addOption(hitmarkerAddonToggleOnAttackOption)
            .addOption(hitmarkerAddonStopOnAnimationEndOption)
            .addOption(hitmarkerAddonDurationOption)
            .addOption(hitmarkerAddonOption)
            .build())
        .group(OptionGroup.createBuilder("Shield Hovering Indicator Addon Options")
            .addOption(shieldIndicatorAddonEnabledOption)
            .addOption(shieldIndicatorAddonOption)
            .build())
        .group(OptionGroup.createBuilder("Shield Break Indicator Addon Options")
            .addOption(shieldBreakAddonEnabledOption)
            .addOption(shieldBreakStopOnAnimationEndOption)
            .addOption(shieldBreakAddonDurationOption)
            .addOption(shieldBreakAddonOption)
            .build())
        .build();

    public static boolean isEditing() {
        return Minecraft.getInstance().gui.screen() instanceof HudEditorScreen;
    }

    @Override
    public ModConfig define() {
        CrosshairAddons.setupAddons();
        return ModConfig.createBuilder()
                .path(PathUtils.ofConfigDir("crosshairaddons"))
                .onSave(CrosshairAddons.getStateManager()::updateCachedEntityTypes)
                .category(generalCategory)
                .category(addonsCategory)
                .build();
    }
}
