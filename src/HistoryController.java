import java.util.*;

public class HistoryController {

	HashMap<String, Date> history;
	private HistoryController() {
		//TODO: initialize
		history = new HashMap<>();
		
	}

	private static class HistoryCreator {
		private static final HistoryController INSTANCE = new HistoryController();
	}

	public static HistoryController getInstance() {
		return HistoryCreator.INSTANCE;
	}
	
	public void addUrl(String url, Date timestamp){
		
	}
}
