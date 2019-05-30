package base

import java.util.HashMap

import com.cpjd.models.matches.Match
import com.cpjd.utils.exceptions.DataNotFoundException

class CustomMatch(csvRow: Array<String>) {

    var teamNum = 0
    var matchType: String
    var matchNum = 0
    var alliancePosition: String
    var isBlueAlliance: Boolean = false

    var HPShipGame = 0
    var HPShipSand = 0
    var HPRocketGame = 0
    var HPRocketSand = 0
    var HPDropGame = 0
    var HPDropSand = 0

    var CShipGame = 0
    var CShipSand = 0
    var CRocketGame = 0
    var CRocketSand = 0
    var CDropGame = 0
    var CDropSand = 0

    var scaleLevel = 0
    var isHelp = false

    var startHab = 0
    var crossedLine = false

    var fouls: Long? = null
    var techs: Long? = null
    var yellow: Boolean = false
    var red: Boolean = false
    var eStopped: Boolean = false
    var borked: Boolean = false

    var points = 0
    var nonFoulPoints = 0
    var rankingPoints = 0
    var rRocket = false
    var lRocket = false
    var habRP = false
    var crRP = false

    internal var scoreBreakdown: HashMap<String, Any>? = null
    var tbaSynced = false

    var matchNotes: String

    val sandPlaces: Int
        get() = HPShipSand + HPRocketSand + CShipSand + CRocketSand

    init {
        this.matchType = csvRow[0]
        this.matchNum = csvRow[1].toInt()
        this.teamNum = csvRow[2].toInt()
        println(csvRow[3][0])
        this.alliancePosition = csvRow[3]
        this.isBlueAlliance = this.alliancePosition[0] == 'B'

        this.startHab = csvRow[4].toInt()

        this.CShipSand = csvRow[5].toInt()
        this.CRocketSand = csvRow[6].toInt()
        this.CDropSand = csvRow[7].toInt()
        this.HPShipSand = csvRow[8].toInt()
        this.HPRocketSand = csvRow[9].toInt()
        this.HPDropSand = csvRow[10].toInt()

        this.CShipGame = csvRow[11].toInt()
        this.CRocketGame = csvRow[12].toInt()
        this.CDropGame = csvRow[13].toInt()
        this.HPShipGame = csvRow[14].toInt()
        this.HPRocketGame = csvRow[15].toInt()
        this.HPDropGame = csvRow[16].toInt()

        try {
            this.scaleLevel = csvRow[17].toInt()
        } catch (e: NumberFormatException) {
            Lib.report("Scale Level is not a number. Setting to 0.")
            this.scaleLevel = 0
        }

        this.techs = csvRow[18].toLong()
        this.fouls = csvRow[19].toLong()
        this.yellow = csvRow[20].equals("true", ignoreCase = true)
        this.red = csvRow[21].equals("true", ignoreCase = true)
        this.borked = csvRow[22].equals("true", ignoreCase = true)
        this.points = csvRow[23].toInt()

        this.matchNotes = csvRow[24]

        this.matchNotes = this.matchNotes.replace("\n", ".  ")
    }

    fun syncTBA() {
        var foundMatch: Match
        try {
            foundMatch = Main.tbaApi.getMatch(Main.tbaEventKey + "_qm" + this.matchNum)
        } catch (e: DataNotFoundException) {
            println("Match not found. \n$e\n\n")
            return
        }

        //get the correct points TODO find a better way to do blue vs red
        if (this.points.toLong() != foundMatch.blue.score && this.isBlueAlliance) {
            Lib.report(String.format("Scouted points for match %d, Blue Alliance are incorrect. %nScouted points: %d%nTBA points: %d", this.matchNum, this.points, foundMatch.blue.score))
            this.points = foundMatch.blue.score.toInt()
        }

        if (this.points.toLong() != foundMatch.red.score && !this.isBlueAlliance) {
            Lib.report(String.format("Scouted points for match %d, Red Alliance are incorrect. %nScouted points: %d%nTBA points: %d", this.matchNum, this.points, foundMatch.red.score))
            this.points = foundMatch.red.score.toInt()
        }

        //get the big fancy hashmap
        if (this.isBlueAlliance) {
            this.scoreBreakdown = foundMatch.blueScoreBreakdown
        } else {
            this.scoreBreakdown = foundMatch.redScoreBreakdown
        }

        //sync fouls
        //TODO fix the 0 thing
        if (this.fouls!! > scoreBreakdown!!["foulCount"] as Long) {
            Lib.report(String.format("Fouls for match %d, alliance position %s are more than the total fouls. %nReported fouls: %d%nTotal fouls: %d",
                    this.matchNum, this.alliancePosition, this.fouls, this.scoreBreakdown!!["foulCount"]))
            // this.fouls = (Long)scoreBreakdown.get("foulCount");
        }

        //sync tech fouls
        if (this.techs!! > scoreBreakdown!!["techFoulCount"] as Long) {
            Lib.report(String.format("Tech fouls for match %d, alliance position %s are more than the total tech fouls. %nReported tech fouls: %d%nTotal fouls: %d",
                    this.matchNum, this.alliancePosition, this.techs, this.scoreBreakdown!!["techFoulCount"]))
            // this.techs = (Long)scoreBreakdown.get("techFoulCount");
        }

        //get the points excluding points from fouls
        this.nonFoulPoints = this.points - (this.scoreBreakdown!!["foulPoints"] as Long).toInt()

        val alPos = Character.getNumericValue(this.alliancePosition[this.alliancePosition.length - 1])
        println("alpos:$alPos")
        //the starting hab
        if (this.startHab != Character.getNumericValue(this.scoreBreakdown!!["preMatchLevelRobot$alPos"].toString()[8])) {
            Lib.report(String.format("Start level for match %d, alliance position %s is incorrect. %nReported level: %d%nTBA level: %d",
                    this.matchNum, this.alliancePosition, this.startHab, Character.getNumericValue(this.scoreBreakdown!!["preMatchLevelRobot$alPos"].toString()[8])))
            this.startHab = Character.getNumericValue(this.scoreBreakdown!!["preMatchLevelRobot$alPos"].toString()[8])
        }

        //get the number of ranking points
        this.rankingPoints = (this.scoreBreakdown!!["rp"] as Long).toInt()

        this.rRocket = this.scoreBreakdown!!["completedRocketNear"] as Boolean
        this.lRocket = this.scoreBreakdown!!["completedRocketFar"] as Boolean

        this.habRP = this.scoreBreakdown!!["habDockingRankingPoint"] as Boolean
        this.crRP = this.scoreBreakdown!!["completeRocketRankingPoint"] as Boolean

        //TODO add more?

        this.tbaSynced = true
    }

    override fun toString(): String {
        return String.format("Team Number: %d, Match Type: %s, Match Number: %d, Position: %s, isBlue: %b, "
                + "HPShipGame: %d, HPShipSand: %d, HPRocketGame: %d, HPRocketSand: %d, HPDropGame: %d, "
                + "HPDropSand: %d, CShipGame: %d, CShipSand: %d, CRocketGame: %d, CRocketSand: %d, CDropGame: %d, "
                + "CDropSand: %d, Scale Level: %d, Is a Helper: %b, Starting Level: %d, Crossed the Line: %b, Fouls: %d, Tech Fouls: %d, Yellow Card: %b, "
                + "Red Card: %b, Emergency Stop: %b, Broken: %b, Total Points: %d, Points w/o Penalties: %d, "
                + "Ranking Points: %d, Filled Right Rocket: %b, Filled Left Rocket: %b, Hab Docking RP: %b, "
                + "Rocket RP: %b, Synced? %b, Notes: %s%n",
                this.teamNum, this.matchType, this.matchNum, this.alliancePosition, this.isBlueAlliance,
                this.HPShipGame, this.HPShipSand, this.HPRocketGame, this.HPRocketSand, this.HPDropGame,
                this.HPDropSand, this.CShipGame, this.CShipSand, this.CRocketGame, this.CRocketSand, this.CDropGame,
                this.CDropSand, this.scaleLevel, this.isHelp, this.startHab, this.crossedLine, this.fouls, this.techs, this.yellow,
                this.red, this.eStopped, this.borked, this.points, this.nonFoulPoints,
                this.rankingPoints, this.rRocket, this.lRocket, this.habRP,
                this.crRP, this.tbaSynced, this.matchNotes)
    }
}