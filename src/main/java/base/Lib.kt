package base

import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.util.ArrayList

import com.opencsv.CSVReader

object Lib {

    fun max(ints: ArrayList<Int>): Int {
        var temp = 0
        for (i in ints) {
            temp = if (i > temp) i else temp
        }

        return temp
    }

    //FIXME this is a terrible horible no good very bad way to do this
    fun mode(ints: ArrayList<Int>): Int {
        val mags = IntArray(10)
        var tempIn = 0
        val tempVal = 0

        for (i in ints) {
            mags[i]++
        }

        for (i in mags.indices) {
            tempIn = if (tempVal > mags[i]) tempIn else i
        }

        return tempIn
    }


    fun report(re: String) {
        //TODO this should report to the gui, not the console
        println(re)
    }

    //FIXME why are there so many  h e c k i n  try catches
    fun convertMatches(filePath: String): List<CustomMatch>? {
        val reader: CSVReader
        try {
            reader = CSVReader(FileReader(filePath), ',', '|')
        } catch (e: FileNotFoundException) {
            report("Match Convert file not found:\n$e")
            return null
        }

        val collectedMatches = ArrayList<CustomMatch>()
        var rows: List<Array<String>>? = null
        try {
            rows = reader.readAll()
        } catch (e: IOException) {
            report("Match Read IO Exception:\n$e")
        }

        for (row in rows!!) {
            println(row[3][0])
            collectedMatches.add(CustomMatch(row))
        }

        try {
            reader.close()
        } catch (e: IOException) {
            report("Reader close IO Exception:\n$e")
        }

        return collectedMatches

    }

    @Throws(Exception::class)
    fun InternettyChecky(): Boolean {
        val process = java.lang.Runtime.getRuntime().exec("ping www.thebluealliance.com")
        val x = process.waitFor()
        return if (x == 0) {
            // System.out.println("Connection Successful, "
            //                    + "Output was " + x);
            true
        } else {
            // System.out.println("Internet Not Connected, "
            //                    + "Output was " + x);
            false
        }
    }

}