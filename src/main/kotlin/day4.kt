import java.io.File

private fun getInput(): Pair<List<Int>, List<List<List<Int>>>> {
    val (drawnNumbersInput, bingoTableInput) = File("/Users/jonas/Documents/workspace/advent-of-code-2021/src/main/kotlin/day4input.txt").readText()
        .split("\n".toRegex(), 2)
    val drawnNumbers = drawnNumbersInput.split(",").map { it.trim().toInt() }
    val bingoTables = bingoTableInput.split("\n").filter { it.trim().isNotEmpty() }
        .map { it.trim().split("\\s+".toRegex()).map { it.toInt() } }.chunked(5)
    return Pair(drawnNumbers, bingoTables)
}

fun run4pt1(): Int {
    val (drawnNumbers, bingoTables) = getInput()
    for (i in 1 until drawnNumbers.size) {
        val numbers = drawnNumbers.subList(0, i)
        val winner = getWinningTable(numbers, bingoTables)
        if (winner != null) {
            val remaining = winner.flatten().toMutableList().filterNot { numbers.contains(it) }
            return remaining.sum() * numbers.last()
        }
    }
    return 0
}

fun run4pt2(): Int {
    var (drawnNumbers, bingoTables) = getInput()
    var lastNumber = 0
    for (i in 1 until drawnNumbers.size) {
        val numbers = drawnNumbers.subList(0, i)
        var winner = getWinningTable(numbers, bingoTables)
        while (winner != null) {
            bingoTables = bingoTables.filterNot { it == winner }
            val remaining = winner.flatten().toMutableList().filterNot { numbers.contains(it) }
            lastNumber = remaining.sum() * numbers.last()
            winner = getWinningTable(numbers, bingoTables)
        }
    }
    return lastNumber
}


fun getWinningTable(numbers: List<Int>, bingoTables: List<List<List<Int>>>): List<List<Int>>? {
    for (bingoTable in bingoTables) {
        // Check columns
        if (bingoTable.any { numbers.containsAll(it) }) return bingoTable
        // Check rows
        for (i in 0..4) {
            if (numbers.containsAll(bingoTable.map { it[i] })) {
                return bingoTable
            }
        }
    }
    return null
}
