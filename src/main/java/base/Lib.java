package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;

import base.CustomTeam.Groups;

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
        HashMap<Integer, Integer> map = new HashMap<>();
        int tempIn = 0;
        int tempVal = 0;

        for(Integer i : ints){
            if(!map.keySet().contains(i)){
                map.put(i, 1);
            }else{
                map.put(i, map.get(i)+1);
            }
        }

        for (Integer key : map.keySet()){
            if(tempVal<=map.get(key)){
                tempIn = key;
                tempVal = map.get(key);
            }
        }

        return tempIn;
    }

    public static String arrayToString(Object[] groups){
        String str="|";
        if(groups.length>1){
            for(int i=0; i<=groups.length-1; i++){
                str+=groups[i].toString();
                str+=",";
            }
            str+=groups[groups.length-1].toString();
        }
        return str+"|";
    }

    public static String[] stringToArray(String string, boolean hasSides) {
        String commaString = "";
        if(hasSides){
            commaString = string.substring(1, string.length()-2);
        }else{
            commaString = string;
        }
        return commaString.split(",");
    }

    public static String[] stringToArray(String string){
        return stringToArray(string, false);
    }


    public static void report(String re){
        //TODO this should report to the gui, not the console
        System.out.println(re);
    }

    //FIXME why are there so many  h e c k i n  try catches
    public static List<CustomMatch> convertMatches(String filePath){
        report("NOW CONVERTING MATCHES FROM: "+filePath);
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
            if(row[0].equals("Qualifier")){
                collectedMatches.add(new CustomMatch(row));
                // report("Match created");
            }else{
                report("Not a qualifier. Passing.");
            }
        }

        try{
            reader.close();
        }catch(IOException e){
            report("Reader close IO Exception:\n"+e);
        }

        return collectedMatches;
        
    }

    public static List<CustomTeam> generateTeams(String teamCSVPath){
        report("NOW GENERATING TEAMS FROM: "+teamCSVPath);
        CSVReader reader;
        try{
            reader = new CSVReader(new FileReader(teamCSVPath), ',');
        }catch(FileNotFoundException e){
            report("Team Generation file not found:\n"+e);
            return null;
        }

        List<CustomTeam> gennedTeams = new ArrayList<CustomTeam>();
        List<String[]> rows=null;
        try{
            rows = reader.readAll();
        }catch(IOException e){
            report("Team Read IO Exception:\n"+e);
        }
        for(String[] row : rows){
            gennedTeams.add(new CustomTeam(row[1], Integer.valueOf(row[0])));
        }

        try{
            reader.close();
        }catch(IOException e){
            report("Reader close IO Exception:\n"+e);
        }

        return gennedTeams;
    }

    @Deprecated //this b the Big Slow
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