package net.vinrobot.seventv.api;

import com.google.gson.Gson;
import net.vinrobot.seventv.api.seventv.EmoteSet;
import net.vinrobot.seventv.api.seventv.Platform;
import net.vinrobot.seventv.api.seventv.UserResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SevenTVService {
	public static final String BASE_API = "https://7tv.io/v3";
	public static final String GLOBAL_EMOTESET_ID = "global";

	private final HttpClient httpClient;
	private final Gson gson = new Gson();

	public SevenTVService() {
		this(HttpClient.newHttpClient());
	}

	public SevenTVService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public UserResponse fetchUser(Platform platform, String userId) throws IOException, InterruptedException {
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(BASE_API + "/users/" + platform.name + "/" + userId))
			.build();

		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

		return gson.fromJson(httpResponse.body(), UserResponse.class);
	}

	public EmoteSet fetchGlobalEmoteSet() throws IOException, InterruptedException {
		return this.fetchEmoteSet(GLOBAL_EMOTESET_ID);
	}

	public EmoteSet fetchEmoteSet(String emoteSetId) throws IOException, InterruptedException {
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(BASE_API + "/emote-sets/" + emoteSetId))
			.build();

		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

		return gson.fromJson(httpResponse.body(), EmoteSet.class);
	}
}
