package gjum.minecraft.mapsync.common.sync.network.packet;

import gjum.minecraft.mapsync.common.sync.network.Packet;
import gjum.minecraft.mapsync.common.utilities.Arguments;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * This is sent to the server in response to a {@link ClientboundEncryptionRequestPacket},
 * after which, if the connection persists, you are considered authenticated
 * with the server. You should then receive a {@link ClientboundRegionTimestampsPacket}.
 *
 * @param authenticatedWithMojang Whether the client authenticated with Mojang.
 * @param sharedSecret encrypted with server's public key
 * @param verifyToken  encrypted with server's public key
 */
public record ServerboundEncryptionResponsePacket(
		boolean authenticatedWithMojang,
		byte @NotNull [] sharedSecret,
		byte @NotNull [] verifyToken
) implements Packet {
	public static final int PACKET_ID = 3;

	public ServerboundEncryptionResponsePacket {
		Arguments.checkNotNull("sharedSecret", sharedSecret);
		Arguments.checkNotNull("verifyToken", verifyToken);
	}

	@Override
	public void write(
			final @NotNull ByteBuf out
	) {
		out.writeBoolean(authenticatedWithMojang());
		Packet.writeIntLengthByteArray(out, sharedSecret());
		Packet.writeIntLengthByteArray(out, verifyToken());
	}
}
