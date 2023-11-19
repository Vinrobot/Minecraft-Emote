package net.vinrobot.mcemote.http;

import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.cache.HttpCacheEntrySerializer;
import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.client.cache.HttpCacheUpdateCallback;
import org.apache.http.impl.client.cache.DefaultHttpCacheEntrySerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class FileHttpCacheStorage implements HttpCacheStorage {
	private final HttpCacheEntrySerializer serializer = new DefaultHttpCacheEntrySerializer();
	private final Path cacheDir;

	public FileHttpCacheStorage(final Path cacheDir) {
		this.cacheDir = Objects.requireNonNull(cacheDir);
	}

	@Override
	public void putEntry(final String key, final HttpCacheEntry entry) throws IOException {
		final Path file = this.computePath(key);
		synchronized (this) {
			this.writeCacheEntry(entry, file);
		}
	}

	@Override
	public HttpCacheEntry getEntry(final String key) throws IOException {
		final Path file = this.computePath(key);
		try {
			synchronized (this) {
				return this.readCacheEntry(file);
			}
		} catch (final NoSuchFileException e) {
			return null;
		}
	}

	@Override
	public void removeEntry(final String key) throws IOException {
		final Path file = this.computePath(key);
		synchronized (this) {
			Files.deleteIfExists(file);
		}
	}

	@Override
	public void updateEntry(final String key, final HttpCacheUpdateCallback callback) throws IOException {
		final Path file = this.computePath(key);
		synchronized (this) {
			final HttpCacheEntry entry = Files.exists(file) ? this.readCacheEntry(file) : null;
			final HttpCacheEntry updated = callback.update(entry);
			if (updated == null) {
				Files.deleteIfExists(file);
			} else {
				this.writeCacheEntry(updated, file);
			}
		}
	}

	private void writeCacheEntry(final HttpCacheEntry entry, final Path path) throws IOException {
		final Path parentPath = path.getParent();
		if (!Files.exists(parentPath)) {
			Files.createDirectories(parentPath);
		}
		try (final OutputStream os = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
			this.serializer.writeTo(entry, os);
		}
	}

	private HttpCacheEntry readCacheEntry(final Path path) throws IOException {
		try (final InputStream is = Files.newInputStream(path, StandardOpenOption.READ)) {
			return this.serializer.readFrom(is);
		}
	}

	private Path computePath(final String key) {
		return this.cacheDir.resolve(sha256Hex(key));
	}
}
