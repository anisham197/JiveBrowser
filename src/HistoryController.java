import java.io.IOException;
import java.util.*;

public class HistoryController {

	private HashMap<String, Date> history;
	private HistoryController() {

		try {
			history = Helpers.readHistory();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(history == null )
				history = new HashMap<>();
		}
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
		try {
			Helpers.writeHistory(history);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HashMap<String, Date> getHistory() {
		return history;
	}
	
	public void printHistory(){
		Helpers.printMap(Helpers.sortByDate(history, Helpers.DESC));
	}
}
