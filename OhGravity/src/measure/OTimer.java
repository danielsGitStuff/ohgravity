package measure;

public class OTimer {

	private String name;

	private long sum;
	private long startTime;
	private long fps = 0;
	private long startCount = 0;

	public OTimer(String name) {
		this.name = name;
	}

	public void start() {
		startTime = System.nanoTime();
		startCount++;
	}

	public long fps() {
		long duration = (System.currentTimeMillis() - fps);
		fps = System.currentTimeMillis();
		if (duration > 0)
			return 1000 / duration;
		return 0;
	}

	@Override
	public String toString() {
		return "Timer[" + name + "]: " + getDurationInMS() + "ms";
	}

	public void stop() {
		sum = System.nanoTime() - startTime + sum;
	}

	public long getDurationInMS() {
		return (sum / 1000000);
	}

	public long getDurationInNS() {
		return sum;
	}

	public long getDurationInS() {
		return ((System.nanoTime() - startTime) / 1000000000);
	}

	public void print() {
		System.out.println(this.getClass().getSimpleName() + ".'" + name + "'.print: " + sum / 1000000);
	}

	public void reset() {
		sum = 0;
		startCount = 0;
	}

	public long getStartCount() {
		return startCount;
	}

	public long getAverageDuration() {
		return sum / startCount;
	}
}
