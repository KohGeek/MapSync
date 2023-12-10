package gjum.minecraft.mapsync.common.sync.network;

import static gjum.minecraft.mapsync.common.MapSyncMod.getMod;

import gjum.minecraft.mapsync.common.sync.data.CatchupChunk;
import gjum.minecraft.mapsync.common.sync.network.packet.ChunkTilePacket;
import gjum.minecraft.mapsync.common.sync.network.packet.ClientboundChunkTimestampsResponsePacket;
import gjum.minecraft.mapsync.common.sync.network.packet.ClientboundEncryptionRequestPacket;
import gjum.minecraft.mapsync.common.sync.network.packet.ClientboundRegionTimestampsPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.IOException;
import java.net.ConnectException;

/**
 * tightly coupled to {@link SyncConnection}
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
	private final SyncConnection client;

	public ClientHandler(SyncConnection client) {
		this.client = client;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object packet) {
		try {
			if (!client.isEncrypted()) {
				if (packet instanceof ClientboundEncryptionRequestPacket pktEncryptionRequest) {
					client.setUpEncryption(ctx, pktEncryptionRequest);
				} else throw new Error("Expected encryption request, got " + packet);
			} else if (packet instanceof ChunkTilePacket pktChunkTile) {
				getMod().handleSharedChunk(pktChunkTile.chunkTile);
			} else if (packet instanceof ClientboundRegionTimestampsPacket pktRegionTimestamps) {
				getMod().handleRegionTimestamps(pktRegionTimestamps, client);
			} else if (packet instanceof ClientboundChunkTimestampsResponsePacket pktCatchup) {
				for (CatchupChunk chunk : pktCatchup.chunks) {
					chunk.syncConnection = this.client;
				}
				getMod().handleCatchupData((ClientboundChunkTimestampsResponsePacket) packet);
			} else throw new Error("Expected packet, got " + packet);
		} catch (Throwable err) {
			err.printStackTrace();
			ctx.close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable err) throws Exception {
		if (err instanceof IOException && "Connection reset by peer".equals(err.getMessage())) return;
		if (err instanceof ConnectException && err.getMessage().startsWith("Connection refused: ")) return;

		SyncConnection.logger.info("[map-sync] Network Error: " + err);
		err.printStackTrace();
		ctx.close();
		super.exceptionCaught(ctx, err);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		client.handleDisconnect(new RuntimeException("Channel inactive"));
		super.channelInactive(ctx);
	}
}
