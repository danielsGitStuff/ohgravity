package threading;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import main.OSettings;
import data.OObject;

/**
 * coordinates the thread pool
 * 
 * @author DECK006
 * 
 */
public class OProcessor {
	private List<OObject> allObjects;
	private ExecutorService executor;
	private List<CalculationCallable> calculationCallables;
	private List<ApplyCallable> applyCallables;
	private OSettings settings;

	public static final int CALCMODE_APPLY = 3;
	public static final int CALCMODE_CALC_STUPID = 5;

	private void initCallables() {
		calculationCallables = new ArrayList<CalculationCallable>();
		applyCallables = new ArrayList<ApplyCallable>();
		for (int i = 0; i < settings.getNoOfThreads(); i++) {
			CalculationCallable calcCallable = new CalculationCallable(settings);
			calculationCallables.add(calcCallable);
			ApplyCallable applyCallable = new ApplyCallable(settings);
			applyCallables.add(applyCallable);
		}
	}

	public OProcessor(OSettings settings) {
		this.settings = settings;
		executor = Executors.newFixedThreadPool(settings.getNoOfThreads());
		initCallables();
	}

	List<Future<List<OObject>>> futures = new ArrayList<Future<List<OObject>>>();

	private List<OObject> threadCalculate(List<OObject> objects, int noOfThreads, List<? extends ICallable> callables)
			throws InterruptedException {
		List<OObject> result = new ArrayList<OObject>();
		try {
			int size = objects.size();
			if (size < noOfThreads)
				noOfThreads = size;
			if (size < 1)
				return objects;
			int width = size / noOfThreads;
			int rest = size - noOfThreads * width;
			int startIndex = 0;
			int endIndex = width;
			List<ICallable> callablesToExecute = new LinkedList<>();
			for (int i = 0; i < noOfThreads; i++) {
				ICallable task = callables.get(i);
				callablesToExecute.add(task);
				if (rest > 0) {
					endIndex++;
					rest--;
				}
				task.prepare(objects, startIndex, endIndex);
				startIndex = endIndex;
				endIndex += width;
			}
			futures = executor.invokeAll(callablesToExecute);
			for (int i = 0; i < noOfThreads; i++) {
				Future<List<OObject>> future = futures.get(i);
				List<OObject> futureResult = future.get();
				if (futureResult != null) {
					result.addAll(futureResult);
				}
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void calcMultiThread() throws InterruptedException {
		this.threadCalculate(allObjects, settings.getNoOfThreads(), calculationCallables);
		List<OObject> result = this.threadCalculate(allObjects, settings.getNoOfThreads(), applyCallables);
		this.allObjects = result;
	}

	public List<OObject> calculate(List<OObject> sortList) throws InterruptedException {
		this.allObjects = sortList;
		calcMultiThread();
		return allObjects;
	};

	public int getObjectCount() {
		return allObjects.size();
	}

	public void shutDown() {
		this.executor.shutdownNow();
		this.executor = null;
		this.calculationCallables = null;
	}
}
