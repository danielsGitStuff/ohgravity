package math;

import main.OSettings;
import data.OObject;
import debug.CallableStats;

public class OMath {

	public static void resetCounter() {

	}

	public static boolean isInBounds(OObject object, OSettings settings) {
		double bound = settings.getUniverseSize();
		if (object.getX() < bound && object.getY() < bound && object.getZ() < bound && object.getX() > -bound
				&& object.getY() > -bound && object.getZ() > -bound) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 
	 * @param first
	 * @param second
	 * @param stats
	 * @param settings
	 */
	public static void calculateForces(OObject first, OObject second, CallableStats stats, OSettings settings) {
		stats.start();
		double[] p1 = first.getCoordinates();
		double[] p2 = second.getCoordinates();

		double distanceSquared = 0;

		double e = p2[0] - p1[0];
		double v1x = e;
		e = e * e;
		distanceSquared += e;

		e = p2[1] - p1[1];
		double v1y = e;
		e = e * e;
		distanceSquared += e;

		e = p2[2] - p1[2];
		double v1z = e;
		e = e * e;
		distanceSquared += e;

		double firstMass = first.getMass();
		double secondMass = second.getMass();
		double massCombined = firstMass + secondMass;
		double massRatio = secondMass / massCombined;

		if (distanceSquared < first.getCollisionRadiusSquared() || distanceSquared < second.getCollisionRadiusSquared()) {
			if (first.getId() < second.getId())
				first.mergeWith(second);
			else
				second.mergeWith(first);
			return;
		}
		double multi = settings.getGravityConstant() * firstMass * secondMass / distanceSquared;
		double mSecond = multi * massRatio;
		v1x = (v1x * mSecond);
		v1y = (v1y * mSecond);
		v1z = (v1z * mSecond);

		// normalize vectors
		first.addVelocityVector(v1x, v1y, v1z);
		stats.stop();
	}

	public static double getVectorLength(double[] vector) {
		double x = vector[0];
		double y = vector[1];
		double z = vector[2];
		double length = Math.sqrt(x * x + y * y + z * z);
		return length;
	}

	public static void normalizeVector(double[] vector, double length) {
		double actualLenght = getVectorLength(vector);
		double factor = length / actualLenght;
		for (int i = 0; i < vector.length; i++) {
			vector[i] = vector[i] * factor;
		}
	}
}
