package gjum.minecraft.mapsync.common.config;

import com.google.gson.annotations.Expose;
import java.io.File;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;

public class ModConfig extends JsonConfig {
	@Expose
	private boolean showDebugLog = false;

	public boolean isShowDebugLog() {
		return showDebugLog;
	}

	public void setShowDebugLog(boolean value) {
		showDebugLog = value;
		saveLater();
	}

	@Expose
	private int catchupWatermark = 100;

	public int getCatchupWatermark() {
		return catchupWatermark;
	}

	public void setCatchupWatermark(int value) {
		catchupWatermark = value;
		saveLater();
	}

	public static ModConfig load() {
		final String mcRoot = Minecraft.getInstance().gameDirectory.getAbsolutePath();
		var dir = Path.of(mcRoot, "MapSync").toFile();
		dir.mkdirs();
		return ModConfig.load(new File(dir, "mod-config.json"), ModConfig.class);
	}
}
