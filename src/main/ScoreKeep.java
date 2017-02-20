package main;

public class ScoreKeep {
	int userScore = 0;
	int enemyScore = 0;
	
	public void Score(int type){
		if(type>0){
			userScore++;
		}
		else if(type<0){
			enemyScore++;
		}
	}
	@Override
	public String toString(){
		return "U:"+userScore+" E:"+enemyScore;
	}
	public boolean goodGame() {
		return userScore>4 || enemyScore>4;
	}
}
