import java.util.*;

public class Diary {
    public List<DiaryEntry> diaryEntries = new LinkedList<>();
    public Map<Integer, Set<String>> searchMap = new HashMap<>();

	public Diary() {
    	//TODO
	}
	
    public void createEntry() {
    	//TODO
        // Practice 1 - Create a diary entry
        String title = DiaryUI.input("title: ");
        String content = DiaryUI.input("content: ");
        DiaryEntry entry = new DiaryEntry(title, content);
        diaryEntries.add(entry);

        addSearchKeywords(entry);

        DiaryUI.print("The entry is saved");

    }

    public DiaryEntry findEntry(int id){
	    for (DiaryEntry entry: diaryEntries){
	        if(entry.getID() == id) return entry;
        }
	    return null;
    }

    public void readEntry(int id) {
    	//TODO
        // Practice 1 - Read the entry of given id
        DiaryEntry entry = findEntry(id);
        if(entry == null){
            DiaryUI.print("There is no entry with id " + id + ".");
            return;
        }
        DiaryUI.print(entry.getFullString());
    }

    public void deleteEntry(int id) {
    	//TODO
        // Practice 1 - Delete the entry of given id
        DiaryEntry entry = findEntry(id);
        if(entry == null){
            DiaryUI.print("There is no entry with id " + id + ".");
            return;
        }
        diaryEntries.remove(entry);
        searchMap.remove(entry.getID());
        DiaryUI.print("Entry "+ id + " is removed.");
    }

    private void addSearchKeywords(DiaryEntry entry){
	    Set<String> keywords = new HashSet<>();

	    for(String keyword: entry.getTitle().split("\\s"))
	        keywords.add(keyword);

	    for(String keyword: entry.getContent().split("\\s"))
	        keywords.add(keyword);

	    searchMap.put(entry.getID(), keywords);
    }

    public void searchEntry(String keyword) {
        //TODO
        // Practice 1 - Search and print all the entries containing given keyword
        List<DiaryEntry> searchResult = new ArrayList<>();

        for(int id: searchMap.keySet()){
            if(searchMap.get(id).contains(keyword)){
                searchResult.add(findEntry(id));
            }
        }

        if(searchResult.isEmpty()){
            DiaryUI.print("There is no entry that contains \"" + keyword + "\".");
            return;
        }

        for(DiaryEntry entry: searchResult){
            DiaryUI.print(entry.getFullString() + "\n");
        }
    }

    public void listEntries() {
        //TODO
        // Practice 2 - List all the entries - sorted in ascending order of the ID
        Collections.sort(diaryEntries, new IDSort());
        ListIterator<DiaryEntry> iterator = diaryEntries.listIterator();
        while (iterator.hasNext()){
            DiaryEntry currentDiaryEntry = iterator.next();
            DiaryUI.print(currentDiaryEntry.getShortString());
        }
    }
    public void listEntries(String condition1) {
        //TODO
        // Practice 2 - List all the entries - sorted in ascending order of the title
        Collections.sort(diaryEntries, new titleSort());
        DiaryUI.print("List of entries sorted by the " + condition1 + ".");
        ListIterator<DiaryEntry> iterator = diaryEntries.listIterator();
        while (iterator.hasNext()){
            DiaryEntry currentEntry = iterator.next();
            DiaryUI.print(currentEntry.getShortString());
        }
    }
    public void listEntries(String condition1, String condition2) {
        //TODO
        // Practice 2 - List all the entries - sorted in ascending order of the title
        //                                      then in descending order of the content word count
        //                                      then in ascending order of the ID
        Collections.sort(diaryEntries, new IDSort());
        Collections.sort(diaryEntries, new lengthSort());
        Collections.sort(diaryEntries, new titleSort());

        DiaryUI.print("List of entries sorted by the" + condition1 + " and " + condition2 + ".");
        ListIterator<DiaryEntry> iterator = diaryEntries.listIterator();
        while (iterator.hasNext()){
            DiaryEntry currentEntry = iterator.next();
            DiaryUI.print(currentEntry.getShortString() + ", length: " + currentEntry.getContent().split("\\s").length);
        }
	}

    class IDSort implements Comparator<DiaryEntry>{
        @Override
        public int compare(DiaryEntry entry1, DiaryEntry entry2){
            //TODO
            // Practice 2 - List all the entries - sorted in ascending order of the ID
            return Integer.compare(entry1.getID(), entry2.getID());
        }
    }
    class titleSort implements Comparator<DiaryEntry>{
        @Override
        public int compare(DiaryEntry entry1, DiaryEntry entry2){
            //TODO
            // Practice 2 - List all the entries - sorted in ascending order of the title
            return entry1.getTitle().compareTo(entry2.getTitle());
        }
    }

    class lengthSort implements Comparator<DiaryEntry>{
        @Override
        public int compare(DiaryEntry entry1, DiaryEntry entry2){
            //TODO
            // Practice 2 - List all the entries - sorted in descending order of the content word count
            int length1 = entry1.getContent().split("\\s").length;
            int length2 = entry2.getContent().split("\\s").length;

            if(length1==length2)
                return 0;

            return length1 < length2 ? 1 : -1;
        }
    }
}
