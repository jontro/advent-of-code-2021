import java.io.File

private fun getInput(): List<String>{
    return File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day10input.txt").readText()
        .split("\n")
}

fun run10pt1(): Int {
    val input = getInput()
    val scores = mapOf(')' to 3,
        ']'  to 57,
        '}'  to 1197,
        '>' to 25137)
    val pattern = "\\(\\)|\\[]|\\{}|<>".toRegex()
    var ret = 0
    for (line in input) {

        var replacedLine = line
        while (true) {
            val newResult = pattern.replace(replacedLine, "")
            if (newResult.length == replacedLine.length) {
                break
            }
            replacedLine = newResult
        }
        val endChar = replacedLine.firstOrNull { scores.keys.contains(it)  }
        if (endChar != null) {
            ret += scores[endChar]!!
        }
    }

    return ret
}

fun run10pt2(): Long {
    val input = getInput()
    val scores = mapOf('(' to 1,
        '['  to 2,
        '{'  to 3,
        '<' to 4)
    val pattern = "\\(\\)|\\[]|\\{}|<>".toRegex()
    val ret = mutableListOf<Long>()
    for (line in input) {

        var replacedLine = line
        while (true) {
            val newResult = pattern.replace(replacedLine, "")
            if (newResult.length == replacedLine.length) {
                break
            }
            replacedLine = newResult
        }
        if (replacedLine.contains("[)|\\]|}|>]".toRegex())) {
            continue
        }
        ret.add(replacedLine.reversed().fold(0L) { acc, c ->
            acc * 5 + scores[c]!!.toLong()
        })
    }
    return ret.sorted()[ret.size/2]

}