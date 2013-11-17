package se.splish.votemaster.model;

public class Settings {
	private Boolean sound;
	private String waitTime;
	
	public Settings(Boolean sound, String string){
		this.sound = sound;
		this.waitTime = string;
	}
	
	public Boolean getSound() {
		return sound;
	}
	
	public String getWaitTime() {
		return waitTime;
	}
}
