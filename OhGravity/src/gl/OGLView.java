package gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import main.OSettings;
import threading.OProcessorManager;
import data.OObject;

public class OGLView implements GLEventListener {
	private GLU glu = new GLU();
	private float rAngleY = 0;
	private float z = -10;
	private float x = 0;
	private float y = -2;
	private List<OObject> objects2draw = new LinkedList<>();
	private OProcessorManager processorManager;
	private float rAngleXZ;
	private OSettings settings;
	private static final float clearColor1 = 0.0f;
	private static final float clearColor2 = 0.0f;
	private static final float clearColor3 = 0.1f;
	private static final float clearColor4 = 1.0f;
	public static FloatBuffer ambientBuffer;
	private static float[] global_ambient = { 0.5f, 0.5f, 0.5f, 1.0f };

	public OGLView(OProcessorManager processorManager) {
		this.processorManager = processorManager;
		this.settings = processorManager.getSettings();
	}

	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		try {
			this.objects2draw = processorManager.calculateMesh();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		settings.frameRendered();
		display(gl);
		settings.showStats();
	}

	public void rotateY(float angleY) {
		this.rAngleY += angleY;
	}

	public void rotateXZ(float angle) {
		this.rAngleXZ += angle;
	}

	private void drawLines(GL2 gl) {
		double boundMax = settings.getUniverseSize();
		double boundMin = -settings.getUniverseSize();

		// x axis
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(1.0f, 0.0f, 0.0f); /* Set green */
		gl.glVertex3d(boundMin, boundMin, boundMin);
		gl.glVertex3d(boundMax, boundMin, boundMin);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(1.0f, 0.0f, 0.0f); /* Set green */
		gl.glVertex3d(boundMin, boundMax, boundMin);
		gl.glVertex3d(boundMax, boundMax, boundMin);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(1.0f, 0.0f, 0.0f); /* Set green */
		gl.glVertex3d(boundMin, boundMin, boundMax);
		gl.glVertex3d(boundMax, boundMin, boundMax);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(1.0f, 0.0f, 0.0f); /* Set green */
		gl.glVertex3d(boundMin, boundMax, boundMax);
		gl.glVertex3d(boundMax, boundMax, boundMax);
		gl.glEnd();
		gl.glFlush();

		// y axis
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 0.0f, 1.0f); /* Set green */
		gl.glVertex3d(boundMin, boundMin, boundMin);
		gl.glVertex3d(boundMin, boundMax, boundMin);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 0.0f, 1.0f); /* Set green */
		gl.glVertex3d(boundMax, boundMin, boundMin);
		gl.glVertex3d(boundMax, boundMax, boundMin);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 0.0f, 1.0f); /* Set green */
		gl.glVertex3d(boundMin, boundMin, boundMax);
		gl.glVertex3d(boundMin, boundMax, boundMax);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 0.0f, 1.0f); /* Set green */
		gl.glVertex3d(boundMax, boundMin, boundMax);
		gl.glVertex3d(boundMax, boundMax, boundMax);
		gl.glEnd();
		gl.glFlush();

		// z axis
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 1.0f, 0.0f); /* Set green */
		gl.glVertex3d(boundMin, boundMin, boundMin);
		gl.glVertex3d(boundMin, boundMin, boundMax);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 1.0f, 0.0f); /* Set green */
		gl.glVertex3d(boundMin, boundMax, boundMin);
		gl.glVertex3d(boundMin, boundMax, boundMax);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 1.0f, 0.0f); /* Set green */
		gl.glVertex3d(boundMax, boundMin, boundMin);
		gl.glVertex3d(boundMax, boundMin, boundMax);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor3f(0.0f, 1.0f, 0.0f); /* Set green */
		gl.glVertex3d(boundMax, boundMax, boundMin);
		gl.glVertex3d(boundMax, boundMax, boundMax);
		gl.glEnd();

	}

	public float getrAngleXZ() {
		return rAngleXZ;
	}

	public float getrAngleY() {
		return rAngleY;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public float getY() {
		return y;
	}

	public void display(GL2 gl) {

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();
		gl.glRotatef(rAngleXZ, 1, 0, 0);
		gl.glRotatef(rAngleY, 0, 1, 0);
		gl.glTranslatef(x, y, this.z);

		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, ambientBuffer);

		drawLines(gl);

		for (OObject mesh : objects2draw) {
			mesh.draw(gl);
		}
		gl.glFlush();

	}

	public void displayChanged() {
		System.out.println("displayChanged called");
	}

	public void init(GLAutoDrawable gLDrawable) {
		System.out.println("init() called");
		GL2 gl = gLDrawable.getGL().getGL2();

		gl.glClearColor(clearColor1, clearColor2, clearColor3, clearColor4);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		int x = 600;
		int y = 600;

		gl.glViewport(0, 0, x, y);
		// Select the projection matrix
		gl.glMatrixMode(GL2.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		glu.gluPerspective(45.0f, (float) x / (float) y, 0.01f, 40000.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();

		// buffer stuff
		ByteBuffer bbGlobalAmbient;
		bbGlobalAmbient = ByteBuffer.allocateDirect(global_ambient.length * 4).order((ByteOrder.nativeOrder()));
		ambientBuffer = bbGlobalAmbient.asFloatBuffer();
		ambientBuffer.put(global_ambient);
		ambientBuffer.position(0);

	}

	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		System.out.println("OGLView.reshape(x." + width + " y." + height + ")");
		GL2 gl = gLDrawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, (float) width / (float) height, 0.01f, 4000.0f);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	public void dispose(GLAutoDrawable arg0) {
		System.out.println("dispose() called");
	}

	/**
	 * positive values zoom in
	 * 
	 * @param amount
	 */
	public void zoom(int amount) {
		this.z = this.z + amount;
	}

	public void translate(float dx, float dy, float dz) {
		this.x += dx;
		this.y += dy;
		this.z += dz;
	}
	//
	// public long getFPS() {
	// return fps;
	// }

}
