package gjum.minecraft.mapsync.forge;

import gjum.minecraft.mapsync.common.MapSyncMod;
import gjum.minecraft.mapsync.common.sync.gui.ModGui;
import gjum.minecraft.mapsync.common.utilities.MagicValues;
import java.io.File;
import net.minecraft.client.KeyMapping;
// import net.minecraftforge.client.ClientRegistry;
// import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;

@Mod("mapsync")
public class ForgeMapSyncMod extends MapSyncMod {
	public ForgeMapSyncMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getVersion() {
		return MagicValues.VERSION + "+forge";
	}

	@Override
	public boolean isDevMode() {
		return !FMLLoader.isProduction();
	}

	public void clientSetup(FMLClientSetupEvent event) {
		init();

		// // Register config hook for the mod list
		// ModLoadingContext.get().registerExtensionPoint(
		// 		ConfigGuiHandler.ConfigGuiFactory.class,
		// 		() -> new ConfigGuiHandler.ConfigGuiFactory(
		// 				(minecraft, previousScreen) -> new ModGui(previousScreen)
		// 		)
		// );
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		try {
			if (event.phase == TickEvent.Phase.START) {
				handleTick();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void registerKeyBinding(KeyMapping mapping) {
		// ClientRegistry.registerKeyBinding(mapping);
	}

	@Override
	public @NotNull File getConfigDir() {
		return FMLPaths.CONFIGDIR.get().toFile();
	}
}
