import java.io.*;
import java.util.*;
public class googlephoto 
{   
    public static Photo[] photos;
    public static HashMap<String, Integer> map;
    public static void main (String[] args) throws Exception {
        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br.readLine());
        photos = new Photo[n];
        map = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            String[] str = br.readLine().split(" ");
            List<String> tags = new ArrayList<>();
            for (int j = 2; j < str.length; j++) {
                tags.add(str[j]);
                if (map.containsKey(str[j])) {
                    map.put(str[j], map.get(str[j]) + 1);
                } 
                else {
                    map.put(str[j], 1);
                }
            }
            boolean vert = false;
            if (str[0].equals("V")) {
                vert = true;
            }
            photos[i] = new Photo(i, vert, tags);
        }
        br.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter(args[0] + ".result.txt"));

        List<String> similar = new ArrayList<>();
        int nLeastCommon = 100000;
        int x = 0, y = 0;
        for (int i = 0; i < photos.length; i++) {
            similar.addAll(photos[i].tags);

            for (int j = 0; j < photos.length; i++) {
                similar.retainAll(photos[j].tags); //Take the common of these two

                if (similar.size() < nLeastCommon) {
                    nLeastCommon = similar.size();
                    x = i;
                    y = j;
                }
                similar.removeAll(similar);
            }
            
            photos[x].tags.addAll(photos[y].tags);
            List<String> newList = new ArrayList<>(photos[x].tags);
            photos[x].tags.removeAll(photos[x].tags);
            photos[x].tags.addAll(removeDuplicates(newList));
        }

        bw.write("asd");
        bw.close();
    }

    public static ArrayList<String> removeDuplicates(List<String> list) { 
        ArrayList<String> newList = new ArrayList<>(); 
  
        for (String element : list) { 
            if (!newList.contains(element)) { 
  
                newList.add(element); 
            } 
        } 
        return newList; 
    } 


    public static String slideshow() {
        Photo[] slide = new Photo[photos.length];
        
        return "";
    }
    public static int commonTags(Photo p1, Photo p2) {
        int count = 0;
        for (int i = 0; i < p1.tags.size(); i++) {
            for (int j = 0; j < p2.tags.size(); j++) {
                if (p1.tags.get(i).equals(p2.tags.get(j))) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}

