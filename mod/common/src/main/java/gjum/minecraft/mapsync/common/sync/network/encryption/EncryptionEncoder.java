package gjum.minecraft.mapsync.common.sync.network.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.security.GeneralSecurityException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;

public class EncryptionEncoder extends MessageToByteEncoder<ByteBuf> {
	private final EncryptionTranslator encryptionCodec;

	public EncryptionEncoder(Key key) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(key.getEncoded()));
			encryptionCodec = new EncryptionTranslator(cipher);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws ShortBufferException {
		encryptionCodec.encipher(in, out);
	}
}
