package net.vinrobot.mcemote.client.font;

import net.minecraft.client.font.Glyph;

import java.time.Duration;
import java.util.stream.Stream;

public class Frames {
	private final Frame[] frames;
	private final Duration loopTime;

	public Frames(Frame[] frames) {
		this.frames = frames;
		this.loopTime = Stream.of(frames)
			.map(Frame::duration)
			.reduce(Duration::plus)
			.orElse(Duration.ofDays(1));
	}

	private static Duration modulo(Duration a, Duration b) {
		// TODO: Improve using Duration::getSeconds and Duration::getNano
		return a.minus(b.multipliedBy(a.toMillis() / b.toMillis()));
	}

	public Frame getFrameAt(Duration at) {
		final Duration time = modulo(at, loopTime);

		Duration current = Duration.ZERO;
		for (Frame frame : frames) {
			current = current.plus(frame.duration());
			if (current.compareTo(time) > 0) {
				return frame;
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
