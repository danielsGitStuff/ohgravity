package gui;

public interface IStatPresenter {
	public void showFps(double fps);
	public void showObjects(int objectCount);
	public void showMergedObjects(int mergeCount);
	public void showRunningTime(double runningTime);
	public void showTimeScale(double timeScale);
	public void showTargetFps(int fps);
	public void showExplosions(int explosions);
	public void showObjectsOob(int objectsOob);
	public void showMassOob(double massOob);
	public void stop();
}
