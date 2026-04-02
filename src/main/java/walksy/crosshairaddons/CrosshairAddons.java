package walksy.crosshairaddons;

import main.walksy.lib.core.config.local.options.type.PixelGridAnimation;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import walksy.crosshairaddons.config.Config;
import walksy.crosshairaddons.manager.AddonStateManager;
import walksy.crosshairaddons.config.State;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;


public class CrosshairAddons implements ModInitializer {

    private static AddonStateManager stateManager;
    private static Map<Supplier<PixelGridAnimation>, Supplier<Boolean>> addons;

    @Override
    public void onInitialize() {
        stateManager = new AddonStateManager();
        addons = new HashMap<>();
    }

    public static @Nullable EntityType<?> getEntityTypeFromString(@Nullable String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        String normalized = name.trim()
                .toLowerCase(Locale.ROOT)
                .replace(" ", "_");

        try {
            Identifier id = normalized.contains(":")
                    ? Identifier.parse(normalized)
                    : Identifier.fromNamespaceAndPath("minecraft", normalized);

            Optional<EntityType<?>> direct = BuiltInRegistries.ENTITY_TYPE.getOptional(id);
            if (direct.isPresent()) {
                return direct.get();
            }

            for (Map.Entry<ResourceKey<EntityType<?>>, EntityType<?>> entry : BuiltInRegistries.ENTITY_TYPE.entrySet()) {
                String path = entry.getKey().identifier().getPath();
                if (path.contains(normalized)) {
                    return entry.getValue();
                }
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }
    public static void setupAddons() {
        addons.clear();
        addons.putAll(Map.of(
            () -> Config.elytraAddon, () -> stateManager.get(State.ELYTRA),
            () -> Config.hitmarkerAddon, () -> stateManager.get(State.HITMARKER),
            () -> Config.shieldBreakAddon, () -> stateManager.get(State.SHIELD_BREAK),
            () -> Config.shieldIndicatorAddon, () -> stateManager.get(State.SHIELD_INDICATOR),
            () -> Config.entityIndicatorAddon, () -> stateManager.get(State.INDICATOR)
        ));
    }

    public static Map<Supplier<PixelGridAnimation>, Supplier<Boolean>> getAddons() {
        return addons;
    }

    public static AddonStateManager getStateManager() {
        return stateManager;
    }
}
