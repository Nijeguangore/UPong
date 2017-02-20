package objects3d;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;

public class Ball {
	private float iniX;
	private float iniY;
	private float TLX;
	private float TLY;
	private float WIDTH = 0.05f;
	private float HEIGHT = 0.05f;
	private int[] VBO,EBO;
	private float velocityX = -0.01f;
	private float velocityY = 0.01f;
	
	public Ball(float x,float y) {
		iniX = TLX = x;
		iniY = TLY = y;
		VBO = new int[1];
		EBO = new int[1];
	}
	public float getTY(){
		return TLY;
	}
	
	public void drawSelf(GL3 gl,int program){
		
		float[] ball = {
				TLX,TLY,0.0f,
				TLX+WIDTH,TLY,0.0f,
				TLX+WIDTH,TLY-HEIGHT,0.0f,
				TLX,TLY-HEIGHT,0.0f
		};
		int[] indices = {
				0,3,1,/**/2,1,3
		};
		
		gl.glGenBuffers(1, VBO,0);
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, VBO[0]);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, ball.length*4, FloatBuffer.wrap(ball), GL3.GL_STATIC_DRAW);
		
		gl.glGenBuffers(1, EBO,0);
		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, EBO[0]);
		gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, indices.length*4, IntBuffer.wrap(indices), GL3.GL_STATIC_DRAW);
		
		gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glUseProgram(program);
		
		gl.glDrawElements(GL3.GL_TRIANGLES, 6, GL.GL_UNSIGNED_INT, 0);
	}
	public int scored() {
		if(TLX+WIDTH <-1.0f){
			return -1;
		}
		else if(TLX >1.0f){
			return 1;
		}
		return 0;
	}
	public void reset() {
		TLX = iniX;
		TLY = iniY;
		if(velocityX > 0)
			velocityX = -0.01f;
		else
			velocityX = 0.01f;
	}
	public void updateMotion(Paddle userPaddle, Paddle aiPaddle) {
		if(TLY > 1.0f){
			velocityY = -velocityY;
		}
		else if(TLY-HEIGHT < -1.0f){
			velocityY = -velocityY;
		}
		if(this.TLX < userPaddle.getTX()+userPaddle.getWidth()){
			if(this.TLY < userPaddle.getTY() && this.TLY > userPaddle.getTY()-userPaddle.getHeight()){
				velocityX = -velocityX;
			}
		}
		
		if(this.TLX+this.WIDTH > aiPaddle.getTX()){
			if(this.TLY < aiPaddle.getTY() && this.TLY > aiPaddle.getTY()-aiPaddle.getHeight()){
				velocityX = -velocityX*1.11f;
			}
		}
		
		TLX += velocityX;
		TLY += velocityY;
		aiPaddle.update(this);
	}
}
