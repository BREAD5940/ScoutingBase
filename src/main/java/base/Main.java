package base;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static void main(String[] args){

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
        System.out.println(tbaApi.getTeam(5940).getName());
    }


}