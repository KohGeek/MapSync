package gjum.minecraft.mapsync.common.utilities;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class RateLimitedExceptions {
	private static final Map<String, Long> lastTimeSeenError = new HashMap<>();

	public static void printErrorRateLimited(
			final @NotNull Throwable thrown
	) {
		try {
			final long now = System.currentTimeMillis();
			final String key = thrown.getMessage();
			if (lastTimeSeenError.getOrDefault(key, 0L) > now - 10000L) return;
			lastTimeSeenError.put(key, now);
			thrown.printStackTrace();
		}
		catch (final Throwable e2) {
			e2.printStackTrace();
		}
	}
}
