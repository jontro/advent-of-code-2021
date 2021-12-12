import java.io.File
import java.lang.IndexOutOfBoundsException

private fun getInput(): MutableList<MutableList<Int>>{
    return File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day11input.txt").readText()
        .split("\n").map {
            it.toCharArray().map { it.toString().toInt() }.toMutableList()
        }.toMutableList()
}
private fun flash(input: MutableList<MutableList<Int>>, x:Int ,y: Int) {
    for ((newX, newY) in listOf(
        -1 to -1, 0 to -1, 1 to -1,
        -1 to  0,          1 to  0,
        -1 to  1, 0 to  1, 1 to  1)) {
        val checkX = x + newX
        val checkY = y + newY
        try {
            input[checkY][checkX]++
            if (input[checkY][checkX] == 10) {
                flash(input, checkX, checkY)
            }
        }catch (t: IndexOutOfBoundsException) {}
    }
}


private fun doFlashRun(_input: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
    var input = _input
    input = input.map { it.map { it + 1 }.toMutableList() }.toMutableList()
    input.withIndex().map { (y, row) -> row.withIndex().filter { (_, v) -> v == 10 }.map { (x, _) -> x to y } }
        .flatten().forEach { (x, y) -> flash(input, x, y) }
    input = input.map { it.map { if (it > 9) 0 else it }.toMutableList() }.toMutableList()
    return input
}

fun run11pt1(): Int {
    var input = getInput()
    var totalSum = 0

    for (i in 0 .. 99) {
        input = doFlashRun(input)
        totalSum += input.sumBy { it.count { it == 0 } }
    }
    return totalSum
}

fun run11pt2(): Int {
    var input = getInput()

    for (i in 0 .. 9999) {
        input = doFlashRun(input)
        if (input.sumBy { it.count { it == 0 } } == input.size * input[1].size)
            return i + 1
    }

    return 0
}