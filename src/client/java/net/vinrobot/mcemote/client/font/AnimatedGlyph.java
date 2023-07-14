package net.vinrobot.mcemote.client.font;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;
import net.minecraft.client.font.Glyph;

public class AnimatedGlyph {
	private final Frame[] frames;
	private final Duration loopTime;

	public AnimatedGlyph(Frame[] frames) {
		this.frames = frames;
		this.loopTime = Stream.of(frames)
			.map(Frame::duration)
			.reduce(Duration::plus)
			.orElse(Duration.ofDays(1));
	}

	private static Duration modulo(Instant a, Duration b) {
		// TODO: Improve using Duration::getSeconds and Duration::getNano
		return Duration.ofMillis(a.toEpochMilli() % b.toMillis());
	}

	public Glyph getGlyphAt(Instant at) {
		final Duration time = modulo(at, loopTime);

		Duration current = Duration.ZERO;
		for (Frame frame : frames) {
			current = current.plus(frame.duration());
			if (current.compareTo(time) > 0) {
				return frame.image();
			}
		}

		throw new IndexOutOfBoundsException();
	}

	public record Frame(Glyph image, Duration duration) {
		public Frame {
			if (duration.isNegative()) {
				throw new IllegalArgumentException("Duration must be positive");
			}
		}
	}
}
