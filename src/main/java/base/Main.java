package base;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cpjd.main.TBA;
import com.cpjd.models.events.Award;
import com.cpjd.models.teams.Team;

public class Main{
    public static TBA tbaApi;
    static ArrayList<CustomTeam> teams = new ArrayList<CustomTeam>();
    static ArrayList<CustomMatch> matches = new ArrayList<CustomMatch>();

    //TODO make some sort of session file to store these
    static final int season = 2019;
    static final String event = "Sacramento";
    static final String tbaEventKey = "2019cada";
    static final String eventDir = "sac_2019/";
    static final String dataDir = eventDir+"data/";
    static final String sortsDir = eventDir+"sorts/";
    static final String teamsDir = eventDir+"teams/";

    public static void main(String[] args) throws Exception{

        System.out.print("\n"+
        "                            ***********************                             \n"+
        "                        *******************************                         \n"+
        "                      *******+                   ?*******                       \n"+
        "                   ******                             ******                    \n"+
        "                :********                             ********:                 \n"+
        "             :***********                            :***********~              \n"+
        "           *******    ****                           ****    *******            \n"+
        "         ******        ***                           ***        ******          \n"+
        "       $*****          ****                         ****          ******        \n"+
        "      *****            ****                         ***:            *****       \n"+
        "     ****               ***                         ***               ****      \n"+
        "    ****                ****                       ****                ****     \n"+
        "    ***                  ***                       ***                  ***~    \n"+
        "    ****+                ****                     ****                :*****    \n"+
        "   *******               ****                     ***                *******    \n"+
        "  **** ****               ***+                   ?***               **** ****   \n"+
        " ****   *****             ****                   ****             *****   ****  \n"+
        " ***      ****             ***                   ***             ****      ***  \n"+
        "****       *****           ****                 ****           *****       **** \n"+
        "***:         ****           ***                 ***           ****          *** \n"+
        "***           *****         ****               ****         *****           *** \n"+
        "***            ~****     ~***************************=     ****~            ***:\n"+
        "***              ***** ********************************* *****              *** \n"+
        "***              **********                         **********              *** \n"+
        "****           *********                               *********           **** \n"+
        " ****        ******                                         ******        ****  \n"+
        "  *****  :*******                                             *******   *****   \n"+
        "    ***********                                                 ************    \n"+
        "      *****                                                         *****       \n"+
        "                                                                                \n"+
        "\n\nBREAD scouting base. Current robot: Croissant\t"+
        "Current event: "+event+"\n");



        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();
        // System.out.println(tbaApi.getTeam(5940).getName());

        List<CustomMatch> matches = new ArrayList<CustomMatch>();

        matches.addAll(Lib.convertMatches(dataDir+"claire.csv"));
        matches.addAll(Lib.convertMatches(dataDir+"geran.csv"));
        matches.addAll(Lib.convertMatches(dataDir+"max.csv"));
        // matches.addAll(Lib.convertMatches(dataDir+"missing.csv"));
        matches.addAll(Lib.convertMatches(dataDir+"nick.csv"));
        matches.addAll(Lib.convertMatches(dataDir+"thomas.csv"));

        
        matches.addAll(Lib.convertMatches(dataDir+"claire2.csv"));
        matches.addAll(Lib.convertMatches(dataDir+"geran2.csv"));
        matches.addAll(Lib.convertMatches(dataDir+"max2.csv"));
        matches.addAll(Lib.convertMatches(dataDir+"nick2.csv"));
        matches.addAll(Lib.convertMatches(dataDir+"thomas2.csv"));

        FileWriter writer = null;
        try{
            writer = new FileWriter(eventDir+"matchOutput.txt", true);
        }catch(IOException e){
            System.out.println("File not found error");
            System.out.println(e);
        }

        try{
            writer.write("\n");
        }catch(IOException e){}

        for(CustomMatch match : matches){
            if(!Lib.InternettyChecky()){
                throw new Exception("Internet has disconnected. Aborting sync.");
            }
            // System.out.println("SCOUTED MATCH: ");
            // System.out.println(match.toString());
            try{
                writer.write("\nSCOUTED MATCH: "+match.toString());
            }catch(IOException e){}
            match.syncTBA();
            // System.out.println("SYNCED MATCH: ");
            // System.out.println(match.toString());
            // System.out.println("------------------------------------------------------------------------------------");
            if(match.tbaSynced){
                try{
                    writer.write("\nSYNCED MATCH: "+match.toString()+"------------------------------------------------------------------------------------\n");
                }catch(IOException e){}
            }else{
                try{
                    writer.write("\nMATCH NOT SYNCED"+"------------------------------------------------------------------------------------\n");
                }catch(IOException e){}
            }
        }
    }


}