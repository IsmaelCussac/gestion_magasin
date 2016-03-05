package fr.mgs.connection;

public enum DataSource {
	H2("gestion_magasin_h2"),
	LOCAL("gestion_magasin_local");
	
	private final String text;

	private DataSource(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
