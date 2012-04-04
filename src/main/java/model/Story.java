package model;

public class Story {
	public final String label;
	public final String state;

	public Story(String stateId, String label) {
		this.state = stateId;
		this.label = label;
	}
}
