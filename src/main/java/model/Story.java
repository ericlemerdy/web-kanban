package model;

public class Story {
	public final int id;
	public final String label;
	public final String state;

	public Story(int id, String stateId, String label) {
		this.id = id;
		this.state = stateId;
		this.label = label;
	}
}
