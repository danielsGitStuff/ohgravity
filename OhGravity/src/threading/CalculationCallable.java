package threading;

import java.util.List;

import main.OSettings;
import math.OMath;
import data.OObject;
import debug.CallableStats;

public class CalculationCallable implements ICallable {

	private int startIndex, endIndex;
	private List<OObject> oObjects;
	private CallableStats stats = new CallableStats(Double.toString(Math.random()));
	private OSettings settings;

	public CalculationCallable(OSettings settings) {
		this.settings = settings;
	}

	public void prepare(List<OObject> oObjects, int startIndex, int endIndex) {
		this.oObjects = oObjects;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	};

	public CallableStats getStats() {
		return stats;
	}

	@Override
	public List<OObject> call() throws Exception {
		int size = oObjects.size();
		for (int i = startIndex; i < endIndex; i++) {
			OObject big = oObjects.get(i);
			for (int j = 0; j < size; j++) {
				OObject small = oObjects.get(j);
				if (big.getId() != small.getId() && !small.isMerged() && !big.isMerged()) {
					OMath.calculateForces(big, small, stats, settings);
				}
			}
		}
		return null;
	}
}
