package se.splish.votemaster.model;

public class Vote {
	private int vid;
	private String name;
	private String description;
	private int nbrOfVotes;
	private int totalVotes;

	public Vote(String name, String description, int nbrOfVotes, int totalVotes) {
		this.name = name;
		this.description = description;
		this.nbrOfVotes = nbrOfVotes;
		this.totalVotes = totalVotes;
	}

	public Vote(int vid, String name, String description, int nbrOfVotes, int totalVotes) {
		this(name, description, nbrOfVotes, totalVotes);
		this.vid = vid;
	}
	
	public int getTotalVotes(){
		return totalVotes;
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
