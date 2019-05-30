package base

import java.io.FileWriter
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

import com.cpjd.main.TBA
import com.cpjd.models.events.Award
import com.cpjd.models.teams.Team

object Main {
    val tbaApi: TBA = TBA()
    internal var teams = ArrayList<CustomTeam>()
    internal var matches = ArrayList<CustomMatch>()

    //TODO make some sort of session file to store these
    internal val season = 2019
    internal val event = "Sacramento"
    internal val tbaEventKey = "2019cada"
    internal val eventDir = "sac_2019/"
    internal val dataDir = eventDir + "data/"
    internal val sortsDir = eventDir + "sorts/"
    internal val teamsDir = eventDir + "teams/"

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {

        print("\n" +
                "                            ***********************                             \n" +
                "                        *******************************                         \n" +
                "                      *******+                   ?*******                       \n" +
                "                   ******                             ******                    \n" +
                "                :********                             ********:                 \n" +
                "             :***********                            :***********~              \n" +
                "           *******    ****                           ****    *******            \n" +
                "         ******        ***                           ***        ******          \n" +
                "       $*****          ****                         ****          ******        \n" +
                "      *****            ****                         ***:            *****       \n" +
                "     ****               ***                         ***               ****      \n" +
                "    ****                ****                       ****                ****     \n" +
                "    ***                  ***                       ***                  ***~    \n" +
                "    ****+                ****                     ****                :*****    \n" +
                "   *******               ****                     ***                *******    \n" +
                "  **** ****               ***+                   ?***               **** ****   \n" +
                " ****   *****             ****                   ****             *****   ****  \n" +
                " ***      ****             ***                   ***             ****      ***  \n" +
                "****       *****           ****                 ****           *****       **** \n" +
                "***:         ****           ***                 ***           ****          *** \n" +
                "***           *****         ****               ****         *****           *** \n" +
                "***            ~****     ~***************************=     ****~            ***:\n" +
                "***              ***** ********************************* *****              *** \n" +
                "***              **********                         **********              *** \n" +
                "****           *********                               *********           **** \n" +
                " ****        ******                                         ******        ****  \n" +
                "  *****  :*******                                             *******   *****   \n" +
                "    ***********                                                 ************    \n" +
                "      *****                                                         *****       \n" +
                "                                                                                \n" +
                "\n\nBREAD scouting base. Current robot: Croissant\t" +
                "Current event: " + event + "\n")



        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD")
        // System.out.println(tbaApi.getTeam(5940).getName());

        val matches = ArrayList<CustomMatch>()

        matches.addAll(Lib.convertMatches(dataDir + "claire.csv")!!)
        matches.addAll(Lib.convertMatches(dataDir + "geran.csv")!!)
        matches.addAll(Lib.convertMatches(dataDir + "max.csv")!!)
        // matches.addAll(Lib.convertMatches(dataDir+"missing.csv"));
        matches.addAll(Lib.convertMatches(dataDir + "nick.csv")!!)
        matches.addAll(Lib.convertMatches(dataDir + "thomas.csv")!!)


        matches.addAll(Lib.convertMatches(dataDir + "claire2.csv")!!)
        matches.addAll(Lib.convertMatches(dataDir + "geran2.csv")!!)
        matches.addAll(Lib.convertMatches(dataDir + "max2.csv")!!)
        matches.addAll(Lib.convertMatches(dataDir + "nick2.csv")!!)
        matches.addAll(Lib.convertMatches(dataDir + "thomas2.csv")!!)

        var writer: FileWriter? = null
        try {
            writer = FileWriter(eventDir + "matchOutput.txt", true)
        } catch (e: IOException) {
            println("File not found error")
            println(e)
        }

        try {
            writer!!.write("\n")
        } catch (e: IOException) {
        }

        for (match in matches) {
            if (!Lib.InternettyChecky()) {
                throw Exception("Internet has disconnected. Aborting sync.")
            }
            // System.out.println("SCOUTED MATCH: ");
            // System.out.println(match.toString());
            try {
                writer!!.write("\nSCOUTED MATCH: $match")
            } catch (e: IOException) {
            }

            match.syncTBA()
            // System.out.println("SYNCED MATCH: ");
            // System.out.println(match.toString());
            // System.out.println("------------------------------------------------------------------------------------");
            if (match.tbaSynced) {
                try {
                    writer!!.write("\nSYNCED MATCH: $match------------------------------------------------------------------------------------\n")
                } catch (e: IOException) {
                }

            } else {
                try {
                    writer!!.write("\nMATCH NOT SYNCED" + "------------------------------------------------------------------------------------\n")
                } catch (e: IOException) {
                }

            }
        }
    }


}