package tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.OSettings;
import math.OMath;
import data.OObject;

public class OCreator {

	public static List<OObject> explode(OObject explodingObject, OSettings settings) {
		Random random = new Random();
		settings.explosion();
		double[] explodePosition = explodingObject.getCoordinates();
		List<OObject> result = new ArrayList<OObject>();
		int howManyObjects = (int) Math.floor(explodingObject.getMass());
		for (int i = 0; i < howManyObjects; i++) {
			double[] vMomentum = new double[3];
			for (int ii = 0; ii < 3; ii++) {
				double value = random.nextDouble();
				vMomentum[ii] = (random.nextBoolean()) ? value : -value;
			}
			double distance = random.nextDouble() * 5;
			OMath.normalizeVector(vMomentum, distance);
			double[] vPosition = new double[3];
			for (int ii = 0; ii < 3; ii++) {
				vPosition[ii] = explodePosition[ii] + vMomentum[ii];
			}
			OObject object = new OObject(vPosition[0], vPosition[1], vPosition[2], 1, settings);
			OMath.normalizeVector(vMomentum, random.nextDouble() * 0.6);
			object.addVelocityVector(vMomentum);
			result.add(object);
		}
		return result;
	}

	public static List<OObject> createrandomData(OSettings settings) {
		Random random = new Random();
		float noOfGalaxies = settings.getNoOfGalaxies();
		double universeCreationRoomSize = settings.getUniverseCreationRoomSize();
		double galaxyCreationRoomSize = settings.getGalaxyCreationRoomSize();
		int objectsPerGalaxy = settings.getNoOfObjectsPerGalaxy();
		List<OObject> allObjects = new ArrayList<OObject>();
		for (int ii = 0; ii < noOfGalaxies; ii++) {
			double offsetX = (universeCreationRoomSize) * random.nextDouble() * (random.nextBoolean() ? -1 : 1);
			double offsetY = (universeCreationRoomSize) * random.nextDouble() * (random.nextBoolean() ? -1 : 1);
			double offsetZ = (universeCreationRoomSize) * random.nextDouble() * (random.nextBoolean() ? -1 : 1);
			for (int i = 0; i < objectsPerGalaxy; i++) {
				random.nextBoolean();
				random.nextDouble();
				double x = random.nextDouble()
						* (random.nextBoolean() ? galaxyCreationRoomSize : -galaxyCreationRoomSize) + offsetX;
				double y = random.nextDouble()
						* (random.nextBoolean() ? galaxyCreationRoomSize : -galaxyCreationRoomSize) + offsetY;
				double z = random.nextDouble()
						* (random.nextBoolean() ? galaxyCreationRoomSize : -galaxyCreationRoomSize) + offsetZ;
				OObject object = new OObject(x, y, z, 1f, settings);
				allObjects.add(object);
			}
		}
		return allObjects;
	}

}
