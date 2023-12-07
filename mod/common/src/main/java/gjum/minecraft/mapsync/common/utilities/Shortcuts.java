package gjum.minecraft.mapsync.common.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public final class Shortcuts {
    public static @NotNull MessageDigest sha1() {
        try {
            return MessageDigest.getInstance("SHA-1");
        }
        catch (final NoSuchAlgorithmException impossible) {
            throw throwImpossible(impossible);
        }
    }

    @ApiStatus.Internal
    public static @NotNull RuntimeException throwImpossible(
            final @NotNull Throwable thrown
    ) {
        return new IllegalStateException("Something impossible happened!", thrown);
    }
}
