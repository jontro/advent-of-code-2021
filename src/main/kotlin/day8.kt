import java.io.File

private fun getInput(): List<Pair<List<String>, List<String>>> {
    return File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day8input.txt").readText()
        .split("\n").map {
            it.split(" | ")
        }.map { Pair(it[0].split(" ").map { it.toCharArray().sorted().joinToString("") }, it[1].split(" ").map { it.toCharArray().sorted().joinToString("") }) }
}

fun run8pt1(): Int {
    val input = getInput()
    val charsToNum = mapOf(2 to 1, 4 to 4, 3 to 7, 7 to 8)

    return input.sumBy { it.second.mapNotNull {
        charsToNum[it.length]
    }.size }
}

fun run8pt2(): Int {
    val input = getInput()

    return input.sumBy { row ->
        val charsToNum = mapCharsToNum(row.first)
        row.second.map { charsToNum[it].toString() }.joinToString("").toInt()
    }
}
fun String.toList() = toCharArray().toList()
fun String.containsAll(str: String?) = toList().containsAll(str!!.toList())
fun mapCharsToNum(signals: List<String>): Map<String, Int> {
    val knownCharsToNumByLength = mapOf(2 to 1, 4 to 4, 3 to 7, 7 to 8)
    val known = signals.filter { knownCharsToNumByLength[it.length] != null }.associateBy({ knownCharsToNumByLength[it.length]!! }, {it}).toMutableMap()
    val remainingSignals = signals.toMutableList().filterNot {known.values.contains(it) }
    known[2] = remainingSignals.filter { it.length == 5 }.filter {
        if (it.containsAll(known[1])) {
            known[3] = it
            false
        } else true
    }.filter {
        if (it.toList().containsAll(known[4]!!.toList().filterNot { known[1]!!.toList().contains(it) })) {
            known[5] = it
            false
        } else true
    }.first()
    known[9] = remainingSignals.filter { it.length == 6 }.filter {
        if (!it.containsAll(known[7])) {
            known[6] = it
            false
        } else true
    }.filter {
        if (!it.containsAll(known[4])) {
            known[0] = it
            false
        } else true
    }.first()
    return known.entries.associateBy({it.value}, {it.key!!})
}
