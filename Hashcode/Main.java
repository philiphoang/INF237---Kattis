package HashCode;

import sun.awt.image.ImageWatched;
import sun.util.resources.cldr.zh.CalendarData_zh_Hans_HK;

import java.io.*;
import java.util.*;
public class Main {
    public static Photo[] photos;
    public static HashMap<String, Integer> map;
    public static HashMap<Integer, ArrayList> pic;
    public static String[][] mat;

    public static void main (String[] args) throws Exception {
        File file = new File("C:\\Users\\kenap\\OneDrive\\UiB\\INF237\\prob1\\src\\HashCode\\b_lovely_landscapes.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int n = Integer.parseInt(br.readLine());

        pic = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String[] str = br.readLine().split(" ");
            ArrayList<String> l = new ArrayList<>();
            for (int j = 2; j < str.length; j++) {
                l.add(str[j]);
            }
            pic.put(i, l);
        }

        ArrayList arr = new ArrayList();

        String sol = "";

        for (int i : pic.keySet()) {
            boolean done = false;
            for (int j = 0; j < pic.get(i).size() && !done; j++) {
                for (int k = i; k < pic.size() && !done; k++) {
                    for (int l = 0; l < pic.get(k).size() && !done; l++) {
                        if (j == l) {
                            sol += k + "\n";
                            done = true;

                        }
                    }
                }
            }
        }






        br.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter("b_lovely_landscapes" + ".result.txt"));
        bw.write(sol);
        bw.close();
    }

}