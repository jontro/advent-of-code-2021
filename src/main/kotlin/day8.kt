import java.io.File

private fun getInput(): List<Pair<List<Set<Char>>, List<Set<Char>>>> {
    return File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day8input.txt").readText()
        .split("\n").map {
            it.split(" | ")
        }.map { Pair(it[0].split(" ").map { it.toCharArray().toSet() }, it[1].split(" ").map { it.toCharArray().toSet() }) }
}

fun run8pt1(): Int {
    val input = getInput()
    val charsToNum = mapOf(2 to 1, 4 to 4, 3 to 7, 7 to 8)

    return input.sumBy { it.second.mapNotNull {
        charsToNum[it.size]
    }.size }
}

fun run8pt2(): Int {
    val input = getInput()

    return input.sumBy { row ->
        val charsToNum = mapCharsToNum(row.first)
        row.second.map { charsToNum[it].toString() }.joinToString("").toInt()
    }
}
val inc = fun (str: Set<Char>, o: Collection<Char>?) = str.containsAll(o!!);
val exl = fun (str: Set<Char>, o: Collection<Char>?) = !str.containsAll(o!!)

fun mapCharsToNum(signals: List<Set<Char>>): Map<Set<Char>, Int> {
    val knownCharsToNumByLength = mapOf(2 to 1, 4 to 4, 3 to 7, 7 to 8)
    val known = signals.filter { knownCharsToNumByLength[it.size] != null }.associateBy({ knownCharsToNumByLength[it.size]!! }, {it}).toMutableMap()
    val remainingSignals = signals.filterNot {known.values.contains(it) }.sortedBy { it.size }.toMutableList()
    for ((match, fn, number) in listOf(
        Triple(known[1], inc, 3),
        Triple(known[4]!!.filterNot { known[1]!!.contains(it) }, inc, 5),
        Triple(null, inc, 2),
        Triple(known[7], exl, 6),
        Triple(known[4], exl, 0),
        Triple(null,exl, 9)
    )) {
        known[number] = if (match != null) {
            remainingSignals.removeAt(remainingSignals.indexOfFirst { fn(it, match)})
        } else {
            remainingSignals.removeFirst()
        }
    }
    return known.entries.associateBy({it.value}, {it.key})
}
