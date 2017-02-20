package objects3d;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;

public class Paddle {
	private float TLX;
	private float TLY;
	private float HEIGHT = 0.2f;
	private float WIDTH = 0.05f;
	private int[] VBO;
	private int[] EBO;
	public Paddle(float x, float y) {
		TLX = x;
		TLY = y;
		VBO = new int[1];
		EBO = new int[1];
	}
	public float getHeight(){
		return HEIGHT;
	}
	public float getWidth(){
		return WIDTH;
	}
	public float getTX(){
		return TLX;
	}
	public float getTY(){
		return TLY;
	}
	
	public void drawSelf(GL3 gl, int program){
		float[] paddle = {
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
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, paddle.length*4, FloatBuffer.wrap(paddle), GL3.GL_STATIC_DRAW);
		
		gl.glGenBuffers(1, EBO,0);
		gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, EBO[0]);
		gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, indices.length*4, IntBuffer.wrap(indices), GL3.GL_STATIC_DRAW);
		
		gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glUseProgram(program);
		
		gl.glDrawElements(GL3.GL_TRIANGLES, 6, GL.GL_UNSIGNED_INT, 0);
		
	}

	public void moveUp() {
		TLY+=0.02f;
	}

	public void moveDown() {
		TLY-=0.02f;		
	}
	public void update(Ball ball) {
		if(ball.getTY() > this.TLY-(this.HEIGHT/2)){
			this.moveUp();
		}
		else if(ball.getTY() < this.TLY-(this.HEIGHT/2) ){
			this.moveDown();
		}
	}
}
