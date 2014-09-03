package main;

import gl.OGLCanvas;
import gui.IStatPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import measure.OTimer;
import data.OObject;

public class OSettings {

	private OTimer fpsTimer = new OTimer("fps");
	private double universeSize = 5000;
	private double galaxyCreationRoomSize = 200;
	private int noOfObjectsPerGalaxy = 1000;
	private int noOfThreads = 4;
	private double gravityConstant = .0001f;
	private int noOfGalaxies = 2;
	private double universeCreationRoomSize = 250;
	private int targetFrameRate = 24;
	private double timeScale = 1.0f;
	private int noOfExplosions = 0;

	private int merged = 0;
	private int outOfBounds = 0;
	private double outOfBoundsMass = 0;
	private double mass = 0;
	private int objectCount = 0;
	private List<Long> smoothFrameList = new ArrayList<Long>();
	private int frameRate;
	private double realTime = 0;
	private JSlider slider;
	private OGLCanvas oglCanvas;
	private IStatPresenter presenter;

	public double getTimeScale() {
		return timeScale;
	}

	public void setTimeScale(int timeScale) {
		this.timeScale = timeScale;
	}

	public double getUniverseCreationRoomSize() {
		return universeCreationRoomSize;
	}

	public int getNoOfGalaxies() {
		return noOfGalaxies;
	}

	public int getNoOfExplosions() {
		return noOfExplosions;
	}

	public double getUniverseSize() {
		return universeSize;
	}

	public double getGalaxyCreationRoomSize() {
		return galaxyCreationRoomSize;
	}

	public int getNoOfObjectsPerGalaxy() {
		return noOfObjectsPerGalaxy;
	}

	public int getNoOfThreads() {
		return noOfThreads;
	}

	public double getGravityConstant() {
		return gravityConstant;
	}

	public void setUniverseSize(float universeSize) {
		this.universeSize = universeSize;
	}

	public synchronized void explosion() {
		this.noOfExplosions++;
	}

	public void setGalaxyCreationRoomSize(float creationRoomSize) {
		this.galaxyCreationRoomSize = creationRoomSize;
	}

	public void setNoOfObjectsPerGalaxy(int noOfObjectsPerGalaxy) {
		this.noOfObjectsPerGalaxy = noOfObjectsPerGalaxy;
	}

	public void setNoOfThreads(int noOfThreads) {
		this.noOfThreads = noOfThreads;
	}

	public void setGravityConstant(float gravityConstant) {
		this.gravityConstant = gravityConstant;
	}

	public void setNoOfGalaxies(int noOfStarClusters) {
		this.noOfGalaxies = noOfStarClusters;
	}

	public void setUniverseCreationRoomSize(float universeCreationRoomSize) {
		this.universeCreationRoomSize = universeCreationRoomSize;
	}

	public void setTargetFrameRate(int targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}

	public int getTargetFrameRate() {
		return targetFrameRate;
	}

	public void setOglCanvas(OGLCanvas oglCanvas) {
		this.oglCanvas = oglCanvas;
		this.oglCanvas.setFrameRate(frameRate);
	}

	public OSettings(IStatPresenter presenter) {
		this.presenter = presenter;
	}

	public long getFPS() {
		return fpsTimer.fps();
	}

	public long getSmoothedFps() {
		smoothFrameList.add(fpsTimer.fps());
		if (smoothFrameList.size() > 25)
			smoothFrameList.remove(smoothFrameList.get(0));
		float smoothed = 0;
		long fps = 0;
		int count = 1;
		for (long f : smoothFrameList) {
			fps += f;
			count++;
		}
		smoothed = fps / count;
		return (long) smoothed;
	}

	public synchronized void merge(int howManyObjects) {
		merged += howManyObjects;
	}

	public int getObjectCount() {
		return this.objectCount;
	}

	public double getMass() {
		return mass;
	}

	public void addMass(float mass) {
		this.mass += mass;
	}

	public void prepare() {
		this.mass = 0;
	}

	public void reset() {
		this.mass = 0;
		this.noOfExplosions = 0;
		this.merged = 0;
		this.outOfBounds = 0;
		this.outOfBoundsMass = 0;
		this.realTime = 0;
	}

	public void setObjectCount(int objectCount) {
		this.objectCount = objectCount;
	}

	public int getMerged() {
		return merged;
	}

	public synchronized void outOfBounds(OObject oObject) {
		this.outOfBounds++;
		this.outOfBoundsMass += oObject.getMass();
	}

	public double getRealTime() {
		return realTime;
	}

	public void frameRendered() {
		this.realTime += 1.0 / 25;
	}

	public void bindFrameRateSlider(JSlider slider) {
		this.slider = slider;
		this.slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				timeScale = OSettings.this.slider.getValue() / 4.0f;
				targetFrameRate = (int) (timeScale * 24);
				if (oglCanvas != null)
					oglCanvas.setFrameRate(targetFrameRate);
			}
		});
		this.slider.setPaintLabels(true);
		this.slider.setToolTipText("from 0.25 to 10");
		this.slider.setSnapToTicks(true);
		this.slider.setMaximum(40);
		this.slider.setMinimum(1);
		this.slider.setValue(4); // sets time factor to one
	}

	public int getFrameRate() {
		return frameRate;
	}

	public void showStats() {
		presenter.showFps(getFPS());
		presenter.showMergedObjects(merged);
		presenter.showObjects(objectCount);
		presenter.showRunningTime(realTime);
		presenter.showTargetFps(targetFrameRate);
		presenter.showTimeScale(timeScale);
		presenter.showExplosions(noOfExplosions);
		presenter.showObjectsOob(outOfBounds);
		presenter.showMassOob(outOfBoundsMass);
		if (getObjectCount() == 0)
			presenter.stop();
	}

	public void bindOGLCanvas(OGLCanvas oglCanvas) {
		this.oglCanvas = oglCanvas;
	}

}
