package base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class Lib {

    public static int max(ArrayList<Integer> ints){
        int temp=0;
        for (int i : ints){
            temp = i>temp ? i : temp;
        }

        return temp;
    }

    //FIXME this is a terrible horible no good very bad way to do this
    public static int mode(ArrayList<Integer> ints){
        int[] mags = new int[10];
        int tempIn = 0;
        int tempVal = 0;

        for (int i : ints){
            mags[i]++;
        }

        for(int i=0; i<mags.length; i++){
            tempIn = tempVal>mags[i] ? tempIn : i;
        }

        return tempIn;
    }


    public static void report(String re){
        //TODO this should report to the gui, not the console
        System.out.println(re);
    }

    //FIXME why are there so many  h e c k i n  try catches
    public static List<CustomMatch> convertMatches(String filePath){
        CSVReader reader;
        try{
            reader = new CSVReader(new FileReader(filePath), ',', '|');
        }catch(FileNotFoundException e){
            report("Match Convert file not found:\n"+e);
            return null;
        }

        List<CustomMatch> collectedMatches = new ArrayList<CustomMatch>();
        List<String[]> rows=null;
        try{
            rows = reader.readAll();
        }catch(IOException e){
            report("Match Read IO Exception:\n"+e);
        }
        for(String[] row : rows){
            System.out.println(row[3].charAt(0));
            collectedMatches.add(new CustomMatch(row));
        }

        try{
            reader.close();
        }catch(IOException e){
            report("Reader close IO Exception:\n"+e);
        }

        return collectedMatches;
        
    }

    public static boolean InternettyChecky() throws Exception { 
        Process process = java.lang.Runtime.getRuntime().exec("ping www.thebluealliance.com"); 
        int x = process.waitFor(); 
        if (x == 0) { 
            // System.out.println("Connection Successful, "
            //                    + "Output was " + x); 
            report("Internet connection checked and active, output "+x);
            return true;
        } 
        else { 
            // System.out.println("Internet Not Connected, "
            //                    + "Output was " + x); 
            report("Internet connection failed, output "+x);
            return false;
        } 
    } 

}