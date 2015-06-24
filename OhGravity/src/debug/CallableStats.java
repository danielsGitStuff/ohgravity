package debug;

import measure.OTimer;

/**
 * does some performance measuring
 * 
 * @author DECK006
 * 
 */
public class CallableStats {

	private OTimer timer;
	private String name;

	public CallableStats(String name) {
		this.timer = new OTimer("stats." + name);
		this.name = name;
	}

	public void start() {
		this.timer.start();
	}

	public void stop() {
		this.timer.stop();
	}

	public void print() {
		System.out.println(this.toString());
	}

	public long getRunTime() {
		return timer.getDurationInNS();
	}

	@Override
	public String toString() {
		return "stats." + name + "\n" + "runtime: " + this.timer.getDurationInNS() + "\n" + "runs: "
				+ this.timer.getStartCount() + "\n" + "avg runtime: " + this.timer.getAverageDuration();
	}

	public void reset() {
		timer.reset();
	}

}
