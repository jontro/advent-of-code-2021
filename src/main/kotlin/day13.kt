import java.io.File

data class Point12(val x: Int, val y: Int)
data class Fold(val direction: String, val value: Int)
private fun getInput(): Pair<List<Point12>, List<Fold>> {

    val points = mutableListOf<Point12>()
    val folds = mutableListOf<Fold>()
    File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day13input.txt").readText()
        .split("\n").forEach {
            if (it.contains(",")) {
                points.add(Point12(it.split(",")[0].toInt(),it.split(",")[1].toInt()))
            }
            if (it.contains("=")) {
                folds.add(Fold(it.split("=")[0].split(" ").last(), it.split("=")[1].toInt()))
            }
      }
    return points to folds
}
fun run13pt1(): Int {
    val (points,folds) = getInput()
    val maxX = points.maxByOrNull { it.x }!!.x
    val maxY = points.maxByOrNull { it.y }!!.y
    val grid = MutableList(maxY + 1) {MutableList(maxX + 1) {0} }
    for (point in points) {
        grid[point.y][point.x] = 1
    }
    var merged: List<List<Int>> = grid
    for(fold in folds) {
        if (fold.direction == "x") {
            merged = foldX(merged, fold.value)
        } else if (fold.direction == "y") {
            merged = foldY(merged, fold.value)
        }
        break
    }
    return merged.sumBy { it.count { it > 0 } }
}
private fun foldY(
    grid: List<List<Int>>,
    fold1: Int
): List<List<Int>> {
    val (list1, list2) = grid.filterIndexed { index, _ ->
        index != fold1
    }.chunked(fold1)
    return mergeLists(list1, list2.reversed())
}
private fun foldX(
    grid: List<List<Int>>,
    fold1: Int
): List<List<Int>> {
    val list1 = mutableListOf<List<Int>>()
    val list2 = mutableListOf<List<Int>>()
    grid.map {
        val (a, b) = it.filterIndexed { index, _ -> index != fold1 }.chunked(fold1)
        Pair(a, b.reversed())
    }.forEach { list1.add(it.first); list2.add(it.second) }
    return mergeLists(list1, list2)
}

private fun mergeLists(
    pt2: List<List<Int>>,
    pt1: List<List<Int>>
): List<List<Int>> {
    return pt2.mapIndexed { index, row ->
        pt1[index].mapIndexed { x, col ->
            if (col + row[x] > 0) 1 else 0
        }
    }
}

fun run13pt2(): Int {
    val (points,folds) = getInput()
    val maxX = points.maxByOrNull { it.x }!!.x
    val maxY = points.maxByOrNull { it.y }!!.y
    val grid = MutableList(maxY + 1) {MutableList(maxX + 1) {0} }
    for (point in points) {
        grid[point.y][point.x] = 1
    }
    var merged: List<List<Int>> = grid
    for(fold in folds) {
        if (fold.direction == "x") {
            merged = foldX(merged, fold.value)
        } else if (fold.direction == "y") {
            merged = foldY(merged, fold.value)
        }
    }
    println("---")
    merged.forEach { println(it.map { if (it == 0) "." else "#" }.joinToString ("" )) }
    println("---")
    return 0
}