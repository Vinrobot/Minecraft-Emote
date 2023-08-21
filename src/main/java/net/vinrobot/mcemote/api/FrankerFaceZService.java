package net.vinrobot.mcemote.api;

import com.google.gson.Gson;
import net.vinrobot.mcemote.api.ffz.GlobalEmoteSets;
import net.vinrobot.mcemote.api.ffz.Platform;
import net.vinrobot.mcemote.api.ffz.RoomResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FrankerFaceZService {
	public static final String BASE_API = "https://api.frankerfacez.com/v1";
	public static final URI GLOBAL_EMOTE_SET_URI = URI.create(BASE_API + "/set/global");

	private final HttpClient httpClient;
	private final Gson gson = new Gson();

	public FrankerFaceZService() {
		this(HttpClient.newHttpClient());
	}

	public FrankerFaceZService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public GlobalEmoteSets fetchGlobalEmoteSet() throws IOException, InterruptedException {
		final HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(GLOBAL_EMOTE_SET_URI)
			.build();

		final HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

		return gson.fromJson(httpResponse.body(), GlobalEmoteSets.class);
	}

	public RoomResponse fetchRoom(Platform platform, String roomId) throws IOException, InterruptedException {
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(BASE_API + "/room/" + platform.path + "/" + roomId))
			.build();

		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

		return gson.fromJson(httpResponse.body(), RoomResponse.class);
	}
}
