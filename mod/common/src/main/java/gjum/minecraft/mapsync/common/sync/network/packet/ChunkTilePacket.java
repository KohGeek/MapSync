package gjum.minecraft.mapsync.common.sync.network.packet;

import gjum.minecraft.mapsync.common.sync.data.ChunkTile;
import gjum.minecraft.mapsync.common.sync.network.Packet;
import io.netty.buffer.ByteBuf;
import jakarta.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;

/**
 * This packet is sent in two situations:
 *
 * 1. Clients are relaying chunk data to each other in real time.
 *
 * 2. You have requested synchronisation via
 * {@link ServerboundCatchupRequestPacket}.
 */
public class ChunkTilePacket implements Packet {
	public static final int PACKET_ID = 4;

	public final ChunkTile chunkTile;

	public ChunkTilePacket(@Nonnull ChunkTile chunkTile) {
		this.chunkTile = chunkTile;
	}

	public static Packet read(ByteBuf buf) {
		return new ChunkTilePacket(
				ChunkTile.fromBuf(buf));
	}

	@Override
	public void write(@NotNull ByteBuf buf) {
		chunkTile.write(buf);
	}
}
