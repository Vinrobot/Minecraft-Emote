package net.vinrobot.mcemote.config.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.Reader;

public class TypedGson<S> {
	private static final Gson DEFAULT_GSON = new GsonBuilder().setPrettyPrinting().create();

	private final Gson gson;

	public TypedGson() {
		this(DEFAULT_GSON);
	}

	public TypedGson(final Gson gson) {
		this.gson = gson;
	}

	public void toJson(S serializable, Appendable writer) throws JsonIOException {
		this.gson.toJson(serializable, writer);
	}

	public <T extends S> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
		return this.gson.fromJson(json, classOfT);
	}
}
