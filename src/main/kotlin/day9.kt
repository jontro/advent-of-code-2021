import java.io.File

private fun getInput(): List<List<Int>>{
    return File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day9input.txt").readText()
        .split("\n").map {
            it.toCharArray().map { it.toString().toInt() }
        }
}

fun run9pt1(): Int {
    val input = getInput()
    val lowPoints = mutableListOf<Int>()
    for ((y, inp) in input.withIndex()) {
        loopa@ for ((x, v) in inp.withIndex())  {
            for ((ty, tx) in listOf(
                    y-1 to x,
                    y to x+1,
                    y+1 to x,
                    y to x-1)) {
                if (ty < 0 || tx < 0 || tx >= inp.size || ty >= input.size) {
                    continue
                }
                if (input[ty][tx] <= v) {
                    continue@loopa
                }
            }
            lowPoints.add(v)

        }
    }
    return lowPoints.sumBy { it+1 }
}
fun getAdjacentValues(input: List<List<Int>>, checked:MutableSet<Pair<Int,Int>>, x:Int, y:Int) : Int {
    var sum = 1
    checked.add(x to y)
    for ((ty, tx) in listOf(
        y-1 to x,
        y to x+1,
        y+1 to x,
        y to x-1)) {
        if (ty < 0 || tx < 0 || tx >= input[0].size || ty >= input.size) {
            continue
        }
        if (input[ty][tx] == 9) {
            continue
        }
        if (checked.contains(tx to ty)) {
            continue
        }
        sum += getAdjacentValues(input, checked, tx, ty)
    }
    return sum
}

fun run9pt2(): Int {
    val input = getInput()
    val lowPoints = mutableListOf<Triple<Int, Int, Int>>()
    for ((y, inp) in input.withIndex()) {
        loopa@ for ((x, v) in inp.withIndex())  {
            for ((ty, tx) in listOf(
                y-1 to x,
                y to x+1,
                y+1 to x,
                y to x-1)) {
                if (ty < 0 || tx < 0 || tx >= inp.size || ty >= input.size) {
                    continue
                }
                if (input[ty][tx] <= v) {
                    continue@loopa
                }
            }
            lowPoints.add(Triple(x,y,v))
        }
    }

    val list = mutableListOf<Int>()
    for (point in lowPoints) {
        list.add(getAdjacentValues(input, mutableSetOf(), point.first, point.second))
    }
    list.sortDescending()
    return list.subList(0, 3).reduce { acc, i -> i * acc }

}