package net.vinrobot.mcemote.api.bttv;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BetterTTVService {
	public static final String BASE_API = "https://api.betterttv.net/3/cached";
	public static final URI GLOBAL_EMOTE_SET_URI = URI.create(BASE_API + "/emotes/global");

	private final HttpClient httpClient;
	private final Gson gson = new Gson();

	public BetterTTVService() {
		this(HttpClient.newHttpClient());
	}

	public BetterTTVService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public Emote[] fetchGlobalEmoteSet() throws IOException, InterruptedException {
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(GLOBAL_EMOTE_SET_URI)
			.build();

		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

		return gson.fromJson(httpResponse.body(), Emote[].class);
	}

	public UserResponse fetchUserEmoteSet(Provider provider, String userId) throws IOException, InterruptedException {
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(BASE_API + "/users/" + provider.path + "/" + userId))
			.build();

		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

		return gson.fromJson(httpResponse.body(), UserResponse.class);
	}
}
