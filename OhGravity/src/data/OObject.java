package data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import main.OSettings;
import math.OMath;
import mesh.OMesh;
import tools.OTools;

public class OObject implements OMesh {

	private double heat = 1;
	private double x;
	private double y;
	private double z;
	private double vx = 0;
	private double vy = 0;
	private double vz = 0;
	private double dvx = 0;
	private double dvy = 0;
	private double dvz = 0;
	private double collisionRadiusSquared;
	private double mass;
	private String name;
	private boolean merged = false;
	private boolean explode = false;
	private int id;
	private static volatile int ID = 0;

	private static synchronized int getID() {
		return OObject.ID++;
	}

	public static void resetIDs() {
		OObject.ID = 0;
	}

	public synchronized void setToBeMergedWith(OObject toBeMergedWith) {
		synchronized (toBeMergedWith) {
			this.toBeMergedWith = toBeMergedWith;
			this.merged = true;
		}

	}

	private List<OObject> thingsToMerge = null;
	private OObject toBeMergedWith = null;
	private OSettings settings;

	public synchronized void mergeWith(OObject toMergeWith) {
		if (this.toBeMergedWith == null) {
			if (this.thingsToMerge == null)
				this.thingsToMerge = new ArrayList<OObject>();
			if (this.thingsToMerge.indexOf(toMergeWith) < 0) {
				toMergeWith.setToBeMergedWith(this);
				this.thingsToMerge.add(toMergeWith);
			}
		} else {
			this.toBeMergedWith.mergeWith(toMergeWith);
		}
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param scopeRadius
	 *            radius in which other oobjects are influenced
	 */
	public OObject(double x, double y, double z, double mass, OSettings settings) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = getID();
		this.mass = mass;
		this.settings = settings;
		calculateProperties();
	}

	public int getId() {
		return id;
	}

	private void calculateProperties() {
		this.collisionRadiusSquared = Math.log(1 + mass * .1f);

		float hh = (float) (Math.sqrt(heat) / 7.0f);
		if (heat <= 1) {
			hh = 0;
		}
		float mm = (float) (Math.sqrt(mass) / 5.0f);

		r = 0.5f + hh;
		b = 0.5f + mm;
		g = 1.0f - hh - mm;

		colors = new float[] { r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g,
				b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b,
				r, g, b, r, g, b, r, g, b };
		ByteBuffer bbColors;
		bbColors = ByteBuffer.allocateDirect(colors.length * 4).order((ByteOrder.nativeOrder()));
		colorBuffer = bbColors.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}

	public boolean isMerged() {
		return merged;
	}

	public boolean doesExplode() {
		return explode;
	}

	public double getCollisionRadiusSquared() {
		return collisionRadiusSquared;
	}

	public void addVelocityVector(double[] xyz) {
		this.dvx += xyz[0];
		this.dvy += xyz[1];
		this.dvz += xyz[2];
	}

	public void addVelocityVector(double x, double y, double z) {
		this.dvx += x;
		this.dvy += y;
		this.dvz += z;
	}

	public double getFx() {
		return vx;
	}

	public double getFy() {
		return vy;
	}

	public double getFz() {
		return vz;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getMass() {
		return mass;
	}

	public double[] getVelocityVector() {
		return new double[] { vx, vy, vz };
	}

	public double[] getCoordinates() {
		return new double[] { x, y, z };
	}

	public String getDescription(int level) {
		String spaces = OTools.getSpaces(level);
		StringBuilder builder = new StringBuilder();
		builder.append(spaces);
		if (name == null) {
			builder.append("[OObject]\n");
		} else {
			builder.append("[OObject]: " + name + "\n");
		}
		builder.append(spaces);
		builder.append("x." + x);
		builder.append(" fx." + vx + "\n");
		builder.append(spaces);
		builder.append("y." + y);
		builder.append(" fy." + vy + "\n");
		builder.append(spaces);
		builder.append("z." + z);
		builder.append(" fz." + vz + "\n");
		builder.append(spaces);
		builder.append("m." + mass + "\n");
		return builder.toString();
	}

	@Override
	public String toString() {
		return getDescription(0);
	}

	public double getHeat() {
		return heat;
	}

	public void calculatePhysics() {
		double[] velocityVector = new double[] { 0, 0, 0 };
		double[] positionVector = new double[] { 0, 0, 0 };
		double sumVectorLength = 0;
		double massOfItAll = 0;
		double sumHeat = 0;
		if (this.thingsToMerge != null) {
			this.settings.merge(this.thingsToMerge.size());
			this.thingsToMerge.add(this);

			for (OObject object : this.thingsToMerge) {
				massOfItAll += object.getMass();
			}
			for (int i = 0; i < this.thingsToMerge.size(); i++) {
				OObject toMergeWith = this.thingsToMerge.get(i);
				double massRatio = toMergeWith.getMass() / massOfItAll;
				double[] toMergeVeloctiyVector = toMergeWith.getVelocityVector();
				double[] toMergePositionVector = toMergeWith.getPositionVector();
				sumVectorLength += OMath.getVectorLength(toMergeVeloctiyVector);
				sumHeat += toMergeWith.getHeat() * massRatio;
				for (int ii = 0; ii < velocityVector.length; ii++) {
					velocityVector[ii] += toMergeVeloctiyVector[ii] * massRatio;
					positionVector[ii] += toMergePositionVector[ii] * massRatio;
				}

			}
			double velocityVectorLength = OMath.getVectorLength(velocityVector);
			double dif = sumVectorLength - velocityVectorLength;
			dif = (dif < 1) ? 1 : dif;
			this.heat += sumHeat + Math.log(dif);
			this.setVelocityVector(velocityVector);
			this.setPositionVector(positionVector);
			this.mass = massOfItAll;
			calculateProperties();
			if (heat > 1000)
				this.explode = true;
		}
	}

	private double[] getPositionVector() {
		return new double[] { x, y, z };
	}

	private void setPositionVector(double[] xyz) {
		x = xyz[0];
		y = xyz[1];
		z = xyz[2];
	}

	private void setVelocityVector(double[] velocityVector) {
		vx = velocityVector[0];
		vy = velocityVector[1];
		vz = velocityVector[2];
	}

	public void applyVectors() {
		vx += dvx;
		vy += dvy;
		vz += dvz;
		x += vx;
		y += vy;
		z += vz;
		dvx = 0;
		dvy = 0;
		dvz = 0;
	}

	public void apply() {
		if (this.isMerged()) {

		} else {
			if (this.thingsToMerge == null) {
				this.applyVectors();
				if (heat > 1) {
					heat = 0.98 * heat; // cool it down a bit
					calculateProperties();
				}
			} else {
				this.applyVectors();
				this.calculatePhysics();
				this.thingsToMerge = null;
			}
		}
	}

	public boolean isInBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		if (minX > x || minY > y || minZ > z)
			return false;
		if (maxX < x || maxY < y || maxZ < z) {
			return false;
		}
		return true;
	}

	public String getName() {
		return (name == null) ? "null" : name;
	}

	public static FloatBuffer vertexBuffer;
	public static FloatBuffer normalBuffer;
	private FloatBuffer colorBuffer;
	public static ByteBuffer indexBuffer;

	private float sizeMulti;
	private static final float[] vertices = { 1, 1, 1, -1, 1, 1, -1, -1, 1, 1, -1, 1, // v0,v1,v2,v3
			// (front)
			1, 1, 1, 1, -1, 1, 1, -1, -1, 1, 1, -1, // v0,v3,v4,v5 (right)
			1, 1, 1, 1, 1, -1, -1, 1, -1, -1, 1, 1, // v0,v5,v6,v1 (top)
			-1, 1, 1, -1, 1, -1, -1, -1, -1, -1, -1, 1, // v1,v6,v7,v2 (left)
			-1, -1, -1, 1, -1, -1, 1, -1, 1, -1, -1, 1, // v7,v4,v3,v2 (bottom)
			1, -1, -1, -1, -1, -1, -1, 1, -1, 1, 1, -1 }; // v4,v7,v6,v5 (back)

	private static final float[] normals = { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, // v0,v1,v2,v3
																					// (front)
			1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, // v0,v3,v4,v5 (right)
			0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, // v0,v5,v6,v1 (top)
			-1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, // v1,v6,v7,v2 (left)
			0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, // v7,v4,v3,v2 (bottom)
			0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1 }; // v4,v7,v6,v5 (back)

	private float r = 1.0f;
	private float g = 0.0f;
	private float b = 0.0f;
	private float[] colors = { r, g, b, r, g, b, r, g, b, r,
			g,
			b, // v0,v1,v2,v3
				// (front)
			r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b,
			r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b, r, g, b };

	private static final byte[] indices = { 0, 1, 2, 2, 3, 0, // front
			4, 5, 6, 6, 7, 4, // right
			8, 9, 10, 10, 11, 8, // top
			12, 13, 14, 14, 15, 12, // left
			16, 17, 18, 18, 19, 16, // bottom
			20, 21, 22, 22, 23, 20 };

	private static float[] global_ambient = { 0.0f, 0.0f, 1.0f, 1.0f };
	private static ByteBuffer bbGlobalAmbient;
	private static FloatBuffer ambientBuffer;

	public static void init() {
		ByteBuffer bbVertices;
		ByteBuffer bbNormals;

		bbVertices = ByteBuffer.allocateDirect(vertices.length * 4).order((ByteOrder.nativeOrder()));
		bbNormals = ByteBuffer.allocateDirect(normals.length * 4).order((ByteOrder.nativeOrder()));

		bbGlobalAmbient = ByteBuffer.allocateDirect(global_ambient.length * 4).order((ByteOrder.nativeOrder()));

		normalBuffer = bbNormals.asFloatBuffer();
		normalBuffer.put(normals);

		vertexBuffer = bbVertices.asFloatBuffer();
		vertexBuffer.put(vertices);

		indexBuffer = ByteBuffer.allocate(indices.length).order(ByteOrder.nativeOrder());
		indexBuffer.put(indices);

		ambientBuffer = bbGlobalAmbient.asFloatBuffer();
		ambientBuffer.put(global_ambient);

		vertexBuffer.position(0);
		normalBuffer.position(0);
		indexBuffer.position(0);
		ambientBuffer.position(0);

	};

	@Override
	public void draw(GL2 gl) {
		sizeMulti = (float) (Math.sqrt(getCollisionRadiusSquared()));
		gl.glPushMatrix();
		gl.glTranslated(x, y, z);
		gl.glScalef(sizeMulti, sizeMulti, sizeMulti);
		vertexBuffer.position(0);
		normalBuffer.position(0);
		colorBuffer.position(0);
		indexBuffer.position(0);
		gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glNormalPointer(GL2.GL_FLOAT, 0, normalBuffer);
		gl.glColorPointer(3, GL2.GL_FLOAT, 0, colorBuffer);
		gl.glVertexPointer(3, GL2.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawElements(GL2.GL_TRIANGLES, indices.length, GL2.GL_UNSIGNED_BYTE, indexBuffer);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
		gl.glPopMatrix();
	}
}
