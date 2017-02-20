package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objects3d.Paddle;

public class KeyControl implements KeyListener {

	private Paddle userPaddle;
	public boolean[] keys = new boolean[1024];
	public KeyControl(Paddle userPaddle) {
		this.userPaddle = userPaddle;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

}
