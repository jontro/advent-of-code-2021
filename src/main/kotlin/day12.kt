import java.io.File
import java.lang.IndexOutOfBoundsException

private fun getInput(): Map<String, List<String>> {
    return File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day12input.txt").readText()
        .split("\n").map {
            val str = it.split("-")
            return@map listOf(Pair(str[0], str[1]),Pair(str[1], str[0]))

        }.flatten().groupBy({ it.first }, {it.second})
}
fun String.isSmallCave() = this != "end" && this.toLowerCase() == this

fun mapPath(input: Map<String, List<String>>, s: List<String>, smallCave: String? = null):List<List<String>> {
    val check = s.last()
    if (check == "end") {
        return listOf(s)
    }
    val ret = mutableListOf<List<String>>()
    for(next in input[check]!!) {
        if (next.isSmallCave() && s.count { it == next } > (if (smallCave == next) 1 else 0)) {
            continue
        }
        ret.addAll(mapPath(input, s + next, smallCave))
    }
    return ret
}


fun run12pt1(): Int {
    val input = getInput()
    val ret = mapPath(input, listOf("start"))
    return ret.size
}

fun run12pt2(): Int {
    val input = getInput()
    val ret = input.keys.filter { it != "start" && it.isSmallCave() }.map{ mapPath(input, listOf("start"), it)} .flatten().toSet()
    return ret.size
}