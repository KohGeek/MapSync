package gjum.minecraft.mapsync.common.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public final class Shortcuts {
    public static final Minecraft MINECRAFT = Minecraft.getInstance();

    public static @NotNull MessageDigest sha1() {
        try {
            return MessageDigest.getInstance("SHA-1");
        } catch (final NoSuchAlgorithmException impossible) {
            throw throwImpossible(impossible);
        }
    }

    @ApiStatus.Internal
    public static @NotNull RuntimeException throwImpossible(
            final @NotNull Throwable thrown) {
        return new IllegalStateException("Something impossible happened!", thrown);
    }

    public static @NotNull Registry<Biome> getBiomeRegistry() {
        return Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME);
    }
}
