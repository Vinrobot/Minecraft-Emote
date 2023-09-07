package net.vinrobot.mcemote.client.imageio.plugins.gif;

public enum DisposalMethod {
	RESTORE_TO_BACKGROUND("restoreToBackgroundColor"),
	RESTORE_TO_PREVIOUS("restoreToPrevious"),
	DO_NOT_DISPOSE("doNotDispose"),
	NONE("none"),
	UNSPECIFIED(null);

	private final String identifier;

	DisposalMethod(final String identifier) {
		this.identifier = identifier;
	}

	public static DisposalMethod getByIdentifier(String identifier) {
		for (DisposalMethod method : values()) {
			if (method.identifier.equals(identifier)) {
				return method;
			}
		}
		return UNSPECIFIED;
	}
}
