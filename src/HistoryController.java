import java.util.*;

public class HistoryController {

	private HashMap<String, Date> history;
	private HistoryController() {
		//TODO: initialize
		history = new HashMap<>();
		//TODO: Read from file
		
		
		Collection<Integer> ints2 = gson.fromJson(json, collectionType);
		
	}

	private static class HistoryCreator {
		private static final HistoryController INSTANCE = new HistoryController();
	}

	public static HistoryController getInstance() {
		return HistoryCreator.INSTANCE;
	}
	
	public void addUrl(String url, Date timestamp){
		history.put(url, timestamp);
		//TODO Write to file
		String json = gson.toJson(ints); ==> json is [1,2,3,4,5]
	}
	
	public HashMap<String, Date> getHistory() {
		return history;
	}
	
	public void printHistory(){
		Helpers.printMap(Helpers.sortByDate(history, Helpers.DESC));
	}
}
