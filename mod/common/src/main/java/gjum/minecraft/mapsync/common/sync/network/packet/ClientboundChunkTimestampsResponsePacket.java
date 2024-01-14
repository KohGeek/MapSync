package gjum.minecraft.mapsync.common.sync.network.packet;

import gjum.minecraft.mapsync.common.sync.data.CatchupChunk;
import gjum.minecraft.mapsync.common.sync.network.Packet;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.Nonnull;
import net.minecraft.core.registries.Registries;

/**
 * You'll receive this in response to a sent
 * {@link ServerboundChunkTimestampsRequestPacket},
 * containing an elaboration of chunk timestamps of all the regions you listed.
 * You should respond with a {@link ServerboundCatchupRequestPacket}.
 */
public class ClientboundChunkTimestampsResponsePacket implements Packet {
	public static final int PACKET_ID = 5;

	/**
	 * sorted by newest to oldest
	 */
	public final @Nonnull List<CatchupChunk> chunks;

	public ClientboundChunkTimestampsResponsePacket(@Nonnull List<CatchupChunk> chunks) {
		this.chunks = chunks;
	}

	public static Packet read(ByteBuf buf) {
		var dimension = Packet.readResourceKey(buf, Registries.DIMENSION);

		int length = buf.readInt();
		List<CatchupChunk> chunks = new ArrayList<>(length);
		for (int i = 0; i < length; i++) {
			int chunk_x = buf.readInt();
			int chunk_z = buf.readInt();
			long timestamp = buf.readLong();
			CatchupChunk chunk = new CatchupChunk(
					dimension, chunk_x, chunk_z, timestamp);
			chunks.add(chunk);
		}
		return new ClientboundChunkTimestampsResponsePacket(chunks);
	}
}
