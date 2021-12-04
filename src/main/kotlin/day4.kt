import java.io.File

fun run4pt1():Int {

    val (drawnNumbersInput,bingoTableInput)  = File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day4input.txt").readText().split("\n".toRegex(),2)
    val drawnNumbers = drawnNumbersInput.split(",").map { it.trim().toInt() }
    val bingoTables = bingoTableInput.split("\n").filter { it.trim().isNotEmpty() }.map { it.trim().split("\\s+".toRegex()).map { it.toInt() } }.chunked(5)

    for (i in 1 until drawnNumbers.size) {
        val numbers= drawnNumbers.subList(0, i)
        val winner = checkWinner(numbers, bingoTables)
        if (winner != null) {
            val remaining = winner.flatten().toMutableList()
            remaining.removeAll(numbers)
            return remaining.sum() * numbers.last()
        }
    }

    return 0

}
fun run4pt2():Int {
    val (drawnNumbersInput,bingoTableInput)  = File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day4input.txt").readText().split("\n".toRegex(),2)
    val drawnNumbers = drawnNumbersInput.split(",").map { it.trim().toInt() }
    var bingoTables = bingoTableInput.split("\n").filter { it.trim().isNotEmpty() }.map { it.trim().split("\\s+".toRegex()).map { it.toInt() } }.chunked(5)
    var lastNumber = 0
    for (i in 1 until drawnNumbers.size) {
        val numbers= drawnNumbers.subList(0, i)
        var winner = checkWinner(numbers, bingoTables)
        while (winner != null) {
            bingoTables = bingoTables.filterNot { it == winner }
            val remaining = winner.flatten().toMutableList()
            remaining.removeAll(numbers)
            lastNumber = remaining.sum() * numbers.last()
            winner = checkWinner(numbers, bingoTables)
        }
    }

    return lastNumber
}


fun checkWinner(numbers: List<Int>, bingoTables: List<List<List<Int>>>) :List<List<Int>>?  {
    for (bingoTable in bingoTables) {
        // Check columns
        if (bingoTable.any { numbers.containsAll(it) }) {
            return bingoTable
        }
        // Check rows
        for (i in 0 .. 4) {
            if (numbers.containsAll(bingoTable.map { it[i] })) {
                return bingoTable
            }
        }

    }
    return null

}
