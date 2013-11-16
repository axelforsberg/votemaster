package se.splish.votemaster.model;

public class Candidate {
	int cid;
	String name;
	
	public Candidate(int cid, String name){
		this.cid = cid;
		this.name = name;
	}
	
	public Candidate(String name){
		this.name = name;
	}
	
	public int getId(){
		return cid;
	}
	
	public String getName(){
		return name;
	}
	
	public void setId(int cid){
		this.cid = cid;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
