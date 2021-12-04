fun main() {
    fun part1(input: List<String>): Int {
        val ints = input.mapNotNull { it.toIntOrNull() }
        var prev = 0
        var count = -1
        ints.forEach {
            if (it > prev) count++
            prev = it
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val ints = input.mapNotNull { it.toIntOrNull() }
        var prevSum = 0
        var count = -1
        for (i in ints.indices) {
            if (i == ints.size - 2) break
            val sum = ints.slice(i..i + 2).sum()
            if (sum > prevSum) count++
            prevSum = sum
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_Test.txt")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day01_Input.txt")
    println(part1(input))
    println(part2(input))
}
