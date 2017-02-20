package main;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.awt.GLCanvas;

import objects3d.Ball;
import objects3d.Paddle;

public class Main {

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("G Pong");
		mainFrame.setSize(600,450);
		
		GLCanvas glContext = new GLCanvas();
		Brush $3dBrush = new Brush();
		
		ScoreKeep ref = new ScoreKeep();
		
		Ball gameBall = new Ball(-0.05f,0.05f);
		Paddle userPaddle = new Paddle(-1.0f,0.1f);
		Paddle aiPaddle = new Paddle(0.95f,0.1f);
		$3dBrush.setEnemy(aiPaddle);
		$3dBrush.setUPaddle(userPaddle);
		$3dBrush.setBall(gameBall);
		KeyControl keyboardControl = new KeyControl(userPaddle);
		
		glContext.addGLEventListener($3dBrush);
		glContext.addKeyListener(keyboardControl);
		
		mainFrame.getContentPane().add(glContext);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		glContext.requestFocus();

		int i = 1000;
		while(!ref.goodGame()){
			boolean[] keys = keyboardControl.keys;
			if(keys[KeyEvent.VK_W]){
				userPaddle.moveUp();
			}
			else if(keys[KeyEvent.VK_S]){
				userPaddle.moveDown();
			}
			int score = 0;
			if((score = gameBall.scored()) != 0){
				ref.Score(score);
				gameBall.reset();
				System.out.println(ref.toString());
			}
			else{
				gameBall.updateMotion(userPaddle,aiPaddle);
			}
			glContext.display();
		}
	}

}
