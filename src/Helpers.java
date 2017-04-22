import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Map.Entry;

public class Helpers
{
    public static boolean ASC = true;
    public static boolean DESC = false;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
    
//    public static void main(String[] args) throws ParseException
//    {
//
//        // Creating dummy unsorted map
//        Map<String, Date> unsortMap = new HashMap<String, Date>();        
//        unsortMap.put("www.facebook.com",sdf.parse("2018-1-23"));
//        unsortMap.put("www.google.com",sdf.parse("2017-1-23"));	
//        unsortMap.put("www.yahoo.com",sdf.parse("2015-1-23"));
//        System.out.println("Before sorting......");
//        printMap(unsortMap);
//        
//
//        System.out.println("After sorting ascending order......");
//        Map<String, Date> sortedMapAsc = sortByComparator(unsortMap, ASC);
//        printMap(sortedMapAsc);
//
//
//        System.out.println("After sorting descindeng order......");
//        Map<String, Date> sortedMapDesc = sortByComparator(unsortMap, DESC);
//        printMap(sortedMapDesc);
//
//    }

    private static Map<String, Date> sortByComparator(Map<String, Date> unsortMap, final boolean order)
    {

        List<Entry<String, Date>> list = new LinkedList<Entry<String, Date>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Date>>()
        {
            public int compare(Entry<String, Date> o1,
                    Entry<String, Date> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Date> sortedMap = new LinkedHashMap<String, Date>();
        for (Entry<String, Date> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void printMap(Map<String, Date> map)
    {
        for (Entry<String, Date> entry : map.entrySet())
        {
            System.out.println("Key : " + entry.getKey() + " Value : "+ sdf.format(entry.getValue()));
        }
    }
}
