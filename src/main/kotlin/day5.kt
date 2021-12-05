import java.io.File
import java.lang.Integer.max
import java.lang.Math.abs

data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point) {
    fun distance(): Int = max(abs(start.x - end.x), abs(start.y - end.y))
    fun pointAtOffset(offset: Int): Point {
        fun partAtOffset(start: Int, end: Int): Int =
            start + if (end == start) 0 else if (end > start) offset else -offset
        return Point(partAtOffset(start.x, end.x), partAtOffset(start.y, end.y))
    }
}

private fun getInput(): List<Line> {
    return File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day5input.txt").readText()
        .split("\n").map {
            val numbers = it.replace(" -> ", ",").split(",").map { it.toInt() }
            Line(Point(numbers[0], numbers[1]), Point(numbers[2], numbers[3]))
        }
}

fun run5pt1(diagonal: Boolean = false): Int {
    val lines = getInput()
    val board = mutableMapOf<Point, Int>()
    for (line in lines) {
        if (line.start.x == line.end.x || line.start.y == line.end.y || diagonal) {
            for (i in 0..line.distance()) {
                val point = line.pointAtOffset(i)
                board[point] = board.getOrDefault(point, 0) + 1;
            }
        }
    }
    return board.values.count { it > 1 }
}

fun run5pt2(): Int = run5pt1(true)