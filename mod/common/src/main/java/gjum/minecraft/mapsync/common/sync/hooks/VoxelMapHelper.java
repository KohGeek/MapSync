package gjum.minecraft.mapsync.common.sync.hooks;

import gjum.minecraft.mapsync.common.sync.data.ChunkTile;

public class VoxelMapHelper {
	public static boolean isVoxelMapNotAvailable;

	static {
		try {
			Class.forName("com.mamiyaotaru.voxelmap.interfaces.AbstractVoxelMap");
			isVoxelMapNotAvailable = false;
		} catch (NoClassDefFoundError | ClassNotFoundException ignored) {
			isVoxelMapNotAvailable = true;
		}
	}

	public static boolean isMapping() {
		if (isVoxelMapNotAvailable) return false;
		return VoxelMapHelperReal.isMapping();
	}

	public static boolean updateWithChunkTile(ChunkTile chunkTile) {
		if (isVoxelMapNotAvailable) return false;
		return VoxelMapHelperReal.updateWithChunkTile(chunkTile);
	}
}