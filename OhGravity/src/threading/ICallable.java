package threading;

import java.util.List;
import java.util.concurrent.Callable;

import data.OObject;

public interface ICallable extends Callable<List<OObject>> {

	public void prepare(List<OObject> oObjects, int startIndex, int endIndex);
	
}
