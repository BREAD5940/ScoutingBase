package base

import java.util.ArrayList
import base.CustomMatch
import base.Lib

class CustomTeam(internal var scoutedName: String, internal var number: Int) {
    internal var tbaName: String = "" // TODO is a null name acceptable? blank name?
    internal var sponsors: String = ""
    internal var isFullySync = false

    internal var matches = ArrayList<CustomMatch>()

    internal var avGPSand: Double = 0.toDouble()
    internal var avHPShip: Double = 0.toDouble()
    internal var avHPRocket: Double = 0.toDouble()
    internal var avHPDrop: Double = 0.toDouble()
    internal var avCShip: Double = 0.toDouble()
    internal var avCRocket: Double = 0.toDouble()
    internal var avCDrop: Double = 0.toDouble()

    internal var scaleLevels = ArrayList<Int>()
    internal var consistScaleLevel: Double = 0.toDouble()
    internal var maxScaleLevel: Double = 0.toDouble()

    internal var isRamp: Boolean = false

    internal var startHabs = ArrayList<Int>()
    internal var consistStartHab: Double = 0.toDouble()
    internal var maxStartHab: Double = 0.toDouble()
    internal var consistOffHab: Boolean = false

    internal var avFoul: Double = 0.toDouble()
    internal var avTech: Double = 0.toDouble()
    internal var totalYellow: Double = 0.toDouble()
    internal var totalRed: Double = 0.toDouble()

    internal var eStops: Double = 0.toDouble()
    internal var borks: Double = 0.toDouble()

    internal var totalRPs: Int = 0

    internal var groups = ArrayList<Groups>()
    internal var matchNotes = ArrayList<String>()

    enum class Groups {
        Level3Climbers, Level2Climbers, Level2Starters, GotRed,
        HasBorked, LowScorers, HighScorers, Completed3Rockets
    }


    fun addMatch(match: CustomMatch) {
        this.avGPSand = (this.avGPSand + match.sandPlaces) / 2

        this.avHPShip = (this.avHPShip + (match.HPShipGame + match.HPShipSand)) / 2
        this.avHPRocket = (this.avHPRocket + (match.HPRocketGame + match.HPRocketSand)) / 2
        this.avHPDrop = (this.avHPDrop + (match.HPDropGame + match.HPDropSand)) / 2
        this.avCShip = (this.avCShip + (match.CShipGame + match.CShipSand)) / 2
        this.avCRocket = (this.avCRocket + (match.CRocketGame + match.CRocketSand)) / 2
        this.avCDrop = (this.avCDrop + (match.CDropGame + match.CDropSand)) / 2

        this.scaleLevels.add(match.scaleLevel)
        this.consistScaleLevel = Lib.mode(this.scaleLevels).toDouble()
        this.maxScaleLevel = Lib.max(this.scaleLevels).toDouble()
        this.isRamp = this.isRamp || match.isHelp

        this.startHabs.add(match.startHab)
        this.consistStartHab = Lib.mode(this.startHabs).toDouble()
        this.maxStartHab = Lib.max(this.startHabs).toDouble()
        this.consistOffHab = consistStartHab != 0.0

        this.avFoul = (this.avFoul + match.fouls!!) / 2
        this.avTech = (this.avTech + match.techs!!) / 2

        if (match.yellow)
            this.totalYellow++

        if (match.red)
            this.totalRed++

        if (match.eStopped)
            this.eStops++

        if (match.borked)
            this.borks++

        this.totalRPs += match.rankingPoints

        this.matchNotes.add(match.matchNotes)
        this.isFullySync = this.isFullySync && match.tbaSynced

    }

    fun addPit(pitData: Pit) {

    }

    fun addGroups() {

    }

    fun syncTBA() {
        this.tbaName = Main.tbaApi.getTeam(this.number).nickname
        this.sponsors = Main.tbaApi.getTeam(this.number).name //i stg this is the sponsors
    }

    fun toReadableString(): String {
        return this.toCSVString() //TODO fix
    }

    fun toCSVString(): String {
        return scoutedName + "," + number + "," + avGPSand + "," + avHPShip + "," + avHPRocket + "," +
                avHPDrop + "," + avCShip + "," + avCRocket + "," + avCDrop + "," + scaleLevels + "," +
                consistScaleLevel + "," + maxScaleLevel + "," + isRamp + "," + startHabs + "," +
                consistStartHab + "," + maxStartHab + "," + consistOffHab + "," + avFoul + "," +
                avTech + "," + totalYellow + "," + totalRed + "," + eStops + "," + borks + "," +
                groups + "," + matches
    }
}