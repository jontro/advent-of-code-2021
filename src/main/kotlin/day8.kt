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
enum class Op {
    INC {
        override fun op(str: Set<Char>, o: Collection<Char>?) = str.containsAll(o!!)
    }, EXC {
        override fun op(str: Set<Char>, o: Collection<Char>?) = !str.containsAll(o!!)
    };
    abstract fun op(str: Set<Char>, o: Collection<Char>?): Boolean
}
data class Row(val str: Collection<Char>?, val op: Op)
fun mapCharsToNum(signals: List<Set<Char>>): Map<Set<Char>, Int> {
    val knownCharsToNumByLength = mapOf(2 to 1, 4 to 4, 3 to 7, 7 to 8)
    val known = signals.filter { knownCharsToNumByLength[it.size] != null }.associateBy({ knownCharsToNumByLength[it.size]!! }, {it}).toMutableMap()
    val remainingSignals = signals.filterNot {known.values.contains(it) }.sortedBy { it.size }.toMutableList()
    for ((match, number) in listOf(
        Row(known[1], Op.INC) to 3,
        Row(known[4]!!.filterNot { known[1]!!.contains(it) }, Op.INC) to 5,
        null to 2,
        Row(known[7], Op.EXC) to 6,
        Row(known[4], Op.EXC) to 0,
        null to 9
    )) {
        known[number] = if (match != null) {
            remainingSignals.removeAt(remainingSignals.indexOfFirst { match.op.op(it, match.str)})
        } else {
            remainingSignals.removeFirst()
        }

    }
    return known.entries.associateBy({it.value}, {it.key})
}
