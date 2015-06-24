package gui;

public interface IStatPresenter {
	void showFps(double fps);

	void showObjects(int objectCount);

	void showMergedObjects(int mergeCount);

	void showRunningTime(double runningTime);

	void showTimeScale(double timeScale);

	void showTargetFps(int fps);

	void showExplosions(int explosions);

	void showObjectsOob(int objectsOob);

	void showMassOob(double massOob);

	void stop();
}
