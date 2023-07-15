package net.vinrobot.mcemote.client.helpers;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public final class FutureHelper {
	public static <T> Optional<T> asOptional(Future<T> future) {
		if (!future.isDone()) {
			return Optional.empty();
		}

		try {
			return Optional.of(future.get());
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e.getCause());
		}
	}
}
