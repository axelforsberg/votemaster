package se.splish.votemaster.model;

public class Result {
	int vid;
	int cid;
	int votes;
	
	public Result(int vid, int cid, int votes){
		this.vid = vid;
		this.cid = cid;
		this.votes = votes;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	
	

}
