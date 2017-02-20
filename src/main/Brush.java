package main;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import objects3d.Ball;
import objects3d.Paddle;

public class Brush implements GLEventListener {

	private String[] vertSrc = {
		"#version 330 core\n",
		"layout (location = 0) in vec3 position;\n",
		"void main(){\n",
		"gl_Position = vec4(position,1.0f);\n",
		"}\n"
	};
	private String[] fragSrc = {
			"#version 330 core\n",
			"out vec4 color;\n",
			"void main(){\n",
			"color = vec4(1.0f,1.0f,1.0f,1.0f);\n",
			"}\n"
	};
	private int PROGRAM = -1;
	private Paddle userPaddle;
	private Ball currBall;
	private Paddle AI;
	
	public void setUPaddle(Paddle p){
		userPaddle = p;
	}
	@Override
	public void init(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		gl.glClearColor(0.0f, 0.1f, 0.1f, 1.0f);
		
		int vertShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
		gl.glShaderSource(vertShader, vertSrc.length, vertSrc, null);
		gl.glCompileShader(vertShader);
		
		IntBuffer success = IntBuffer.allocate(1);
		ByteBuffer log = ByteBuffer.allocate(512);
		
		gl.glGetShaderiv(vertShader, GL3.GL_COMPILE_STATUS, success);
		if(success.get() == 0){
			gl.glGetShaderInfoLog(vertShader, 512, null, log);
			System.out.println(new String(log.array(),Charset.forName("UTF-8")));
		}else{
			//System.out.println("Vert Compiled");
		}
		
		int fragShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
		gl.glShaderSource(fragShader, fragSrc.length, fragSrc, null);
		gl.glCompileShader(fragShader);
		
		success.rewind();log.rewind();
		
		gl.glGetShaderiv(fragShader, GL3.GL_COMPILE_STATUS, success);
		if(success.get() == 0){
			gl.glGetShaderInfoLog(fragShader, 512, null, log);
			System.out.println(new String(log.array(),Charset.forName("UTF-8")));
		}
		else{
			//System.out.println("Frag Compiled");
		}
		PROGRAM = gl.glCreateProgram();
		gl.glAttachShader(PROGRAM, vertShader);
		gl.glAttachShader(PROGRAM, fragShader);
		gl.glLinkProgram(PROGRAM);
		
		success.rewind();log.rewind();
		gl.glGetProgramiv(PROGRAM, GL3.GL_LINK_STATUS, success);
		if(success.get() == 0){
			gl.glGetProgramInfoLog(PROGRAM, 512, null, log);
			System.out.println(new String(log.array(),Charset.forName("UTF-8")));
		}
		else{
			//System.out.println("Program linked");
		}
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL3 gl = drawable.getGL().getGL3();
		gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
		userPaddle.drawSelf(gl, PROGRAM);
		currBall.drawSelf(gl, PROGRAM);
		AI.drawSelf(gl, PROGRAM);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL3 gl = drawable.getGL().getGL3();
		gl.glViewport(x, y, width, height);
		
		/*float[] triangle = {0.5f,0.0f,0.0f, 0.0f,0.5f,0.0f, -0.5f,0.0f,0.0f};
		int[] VBO = new int[1];
		
		gl.glGenBuffers(1, VBO,0);
		gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, VBO[0]);
		gl.glBufferData(GL3.GL_ARRAY_BUFFER, triangle.length*4, FloatBuffer.wrap(triangle), GL3.GL_STATIC_DRAW);
		gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		
		gl.glUseProgram(PROGRAM);
		gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 3);*/
	}
	public void setBall(Ball gameBall) {
		currBall = gameBall;		
	}
	public void setEnemy(Paddle aiPaddle) {
		this.AI = aiPaddle;
	}

}
