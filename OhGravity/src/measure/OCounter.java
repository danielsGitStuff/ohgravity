package measure;

public class OCounter {

	public static final OCounter countCollisionCalculations = new OCounter("colCalculations");
	public static final OCounter countCalcSomewayCalls = new OCounter("countCalcSomewayCalls");

	private long count = 0;

	private String name;

	public OCounter(String name) {
		this.name = name;
	}

	public void inc() {
		count++;
	}

	public void reset() {
		count = 0;

	}

	public long getCount() {
		return count;
	}

	public void print() {
		System.out.println("[OCounter]: " + name + " count: " + count);
	}
}
