package threading;

import java.util.ArrayList;
import java.util.List;

import main.OSettings;
import math.OMath;
import tools.OCreator;
import data.OObject;
import debug.CallableStats;

/**
 * applies all velocity vectors to the objects
 * 
 * @author DECK006
 * 
 */
public class ApplyCallable implements ICallable {

	private int startIndex, endIndex;
	private List<OObject> oObjects;
	private CallableStats stats = new CallableStats(Double.toString(Math.random()));
	private OSettings settings;

	public ApplyCallable(OSettings settings) {
		this.settings = settings;
	}

	public void prepare(List<OObject> oObjects, int startIndex, int endIndex) {
		this.oObjects = oObjects;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public CallableStats getStats() {
		return stats;
	}

	@Override
	public List<OObject> call() throws Exception {
		stats.reset();
		stats.start();
		List<OObject> result = new ArrayList<OObject>();
		for (int i = startIndex; i < endIndex; i++) {
			OObject oObject = this.oObjects.get(i);
			oObject.apply();
			if (!oObject.isMerged()) {
				if (oObject.doesExplode()) {
					List<OObject> particles = OCreator.explode(oObject, settings);
					result.addAll(particles);
				} else {
					if (OMath.isInBounds(oObject, settings)) {
						result.add(oObject);
					} else {
						settings.outOfBounds(oObject);
					}
				}
			}
		}
		stats.stop();
		return result;
	}
}
