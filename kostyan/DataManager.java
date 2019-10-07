package kostyan;

import javafx.scene.control.TextArea;
import java.util.*;

public class DataManager {

    public static List<String> readString(String file) { //reading string from TextArea


        String s = file.replaceAll("\\s|\\pP|\\n", " "); //delete all empty space

        String[] data = s.split(" "); // create arrays of string

        List<String> list = new ArrayList<>(); //create list

        for(int i = 0; i < data.length; i++) {
            if(data[i].length()> 2) {
                list.add(data[i].toLowerCase());
            }
        }

        return list;
    }


    public static List<String> sortList(List<String> list) {

        // Create a new LinkedHashSet
        Set<String> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        Collections.sort(list, String::compareTo);

        return list;
    }


    public static void printToTextField(List<String> list, TextArea text) {

        for(int i = 0; i < list.size(); i++) {
            text.appendText(list.get(i) + "\n");
        }
    }



}
