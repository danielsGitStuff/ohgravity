package threading;

import java.util.List;

import main.OSettings;
import data.OObject;

public class OProcessorManager {

	private List<OObject> oObjects;
	private OProcessor processor;
	private OSettings settings;

	public OProcessorManager(List<OObject> oObjects, OSettings settings) {
		this.oObjects = oObjects;
		this.processor = new OProcessor(settings);
		this.settings = settings;
	}

	public OSettings getSettings() {
		return settings;
	}

	public OProcessorManager(OSettings settings) {
		this.settings = settings;
	}

	public List<OObject> calculateMesh() throws InterruptedException {
		this.settings.prepare();
		this.oObjects = processor.calculate(oObjects);
		this.settings.setObjectCount(this.oObjects.size());
		return this.oObjects;
	}

	public void prepare(List<OObject> oObjects, OSettings settings) {
		this.oObjects = oObjects;
		this.settings = settings;
		if (this.processor != null)
			this.processor.shutDown();
		this.processor = new OProcessor(this.settings);
	}

}
