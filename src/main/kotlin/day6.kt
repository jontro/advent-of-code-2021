private fun getInput(): List<Int> {
    return "5,3,2,2,1,1,4,1,5,5,1,3,1,5,1,2,1,4,1,2,1,2,1,4,2,4,1,5,1,3,5,4,3,3,1,4,1,3,4,4,1,5,4,3,3,2,5,1,1,3,1,4,3,2,2,3,1,3,1,3,1,5,3,5,1,3,1,4,2,1,4,1,5,5,5,2,4,2,1,4,1,3,5,5,1,4,1,1,4,2,2,1,3,1,1,1,1,3,4,1,4,1,1,1,4,4,4,1,3,1,3,4,1,4,1,2,2,2,5,4,1,3,1,2,1,4,1,4,5,2,4,5,4,1,2,1,4,2,2,2,1,3,5,2,5,1,1,4,5,4,3,2,4,1,5,2,2,5,1,4,1,5,1,3,5,1,2,1,1,1,5,4,4,5,1,1,1,4,1,3,3,5,5,1,5,2,1,1,3,1,1,3,2,3,4,4,1,5,5,3,2,1,1,1,4,3,1,3,3,1,1,2,2,1,2,2,2,1,1,5,1,2,2,5,2,4,1,1,2,4,1,2,3,4,1,2,1,2,4,2,1,1,5,3,1,4,4,4,1,5,2,3,4,4,1,5,1,2,2,4,1,1,2,1,1,1,1,5,1,3,3,1,1,1,1,4,1,2,2,5,1,2,1,3,4,1,3,4,3,3,1,1,5,5,5,2,4,3,1,4"
        .split(",").map {
            it.toInt()
        }
}

fun run6pt1(): Int {
    var fish = getInput()
    for (i in 1 .. 80) {
        fish = fish.map { if (it == 0) listOf(7, 9) else listOf(it) }.flatten().map { it - 1 }
   }
   return fish.size
}

fun run6pt2(): Long {
    val input = getInput()
    var fish = mutableMapOf<Int, Long>()
    input.forEach {
        fish[it] = fish.getOrDefault(it, 0).plus(1)
    }
    for (i in 1 .. 256) {
        val fish0 = fish.getOrDefault(0, 0)
        if (fish0 > 0) {
            fish[7] = fish.getOrDefault(7, 0) + fish0
            fish[9] = fish.getOrDefault(9, 0) + fish0
            fish[0] = 0
        }
        val copy = fish.toMutableMap()
        for (entry in fish.entries.sortedBy { it.key }) {
            copy[entry.key - 1] = copy[entry.key]!!
            copy[entry.key] = 0
        }
        fish = copy
    }
    return fish.values.sum()
}
