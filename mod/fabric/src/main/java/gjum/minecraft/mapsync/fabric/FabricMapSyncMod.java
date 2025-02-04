package gjum.minecraft.mapsync.fabric;

import gjum.minecraft.mapsync.common.MapSyncMod;
import gjum.minecraft.mapsync.common.utilities.MagicValues;
import java.io.File;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.NotNull;

public class FabricMapSyncMod extends MapSyncMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		init();
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			try {
				handleTick();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public String getVersion() {
		return MagicValues.VERSION + "+fabric";
	}

	@Override
	public boolean isDevMode() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	@Override
	public void registerKeyBinding(KeyMapping mapping) {
		KeyBindingHelper.registerKeyBinding(mapping);
	}

	@Override
	public @NotNull File getConfigDir() {
		return FabricLoader.getInstance().getConfigDir().toFile();
	}
}
