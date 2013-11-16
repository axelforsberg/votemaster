package se.splish.votemaster.model;

public class Vote {
	private int vid;
	private String name;
	private String description;
	private int nbrOfVotes;

	public Vote(String name, String description, int nbrOfVotes) {
		this.name = name;
		this.description = description;
		this.nbrOfVotes = nbrOfVotes;
	}

	public Vote(int vid, String name, String description, int nbrOfVotes) {
		this(name, description, nbrOfVotes);
		this.vid = vid;
	}

	public int getId() {
		return vid;
	}

	public void setId(int vid) {
		this.vid = vid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNbrOfVotes() {
		return nbrOfVotes;
	}

	public void setNbrOfVotes(int nbrOfVotes) {
		this.nbrOfVotes = nbrOfVotes;
	}

}
