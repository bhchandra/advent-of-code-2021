import kotlin.math.abs
import kotlin.math.ceil

fun main() {
    fun part1(input: String): Int {
        val positions = input.split(",")
            .map { it.trim().toInt() }
            .sorted()

        val avg = ceil(positions.average()).toInt()
        val smallest = positions.first()
        val largest = positions.last()

        val costs = mutableListOf<Int>()

        for (i in smallest..avg) {
            val cost = positions.sumOf { abs(it - i) }
            costs.add(cost)
        }

        for (i in avg..largest) {
            val cost = positions.sumOf { abs(it - i) }
            costs.add(cost)
        }

        return costs.minOf { it }
    }

    //TODO: refactor
    fun part2(input: String): Int {
        val positions = input.split(",")
            .map { it.trim().toInt() }
            .sorted()

        val avg = ceil(positions.average()).toInt()
        val smallest = positions.first()
        val largest = positions.last()

        val costs = mutableListOf<Int>()

        for (i in smallest..avg) {
            val cost = positions.sumOf {
                var sum = 0
                var last = 0
                for (x in 1..abs(it - i)) {
                    val now = last + 1
                    sum += now
                    last = now
                }
                sum
            }
            costs.add(cost)
        }

        for (i in avg..largest) {
            val cost = positions.sumOf {
                var sum = 0
                var last = 0
                repeat(abs(it - i)) {
                    val now = last + 1
                    sum += now
                    last = now
                }
                sum
            }
            costs.add(cost)
        }

        return costs.minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day07_Test.txt")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInputAsText("Day07_Input.txt")
    println(part1(input))
    println(part2(input))
}
