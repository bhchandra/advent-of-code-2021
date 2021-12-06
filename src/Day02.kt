fun main() {
    fun part1(input: List<String>): Int {
        var dx = 0;
        var dy = 0;
        input.forEach {
            val (direction, value) = it.split(" ")
            when (direction) {
                "forward" -> dx += value.toInt()
                "down" -> dy += value.toInt()
                "up" -> dy -= value.toInt()
            }
        }
        return dx * dy
    }

    fun part2(input: List<String>): Int {
        var aim = 0;
        var dx = 0
        var dy = 0;
        input.forEach {
            val (direction, value) = it.split(" ")
            when (direction) {
                "forward" -> {
                    dx += value.toInt()
                    dy += (aim * value.toInt())
                }
                "down" -> aim += value.toInt()
                "up" -> aim -= value.toInt()
            }
        }
        return dx * dy
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_Test.txt")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day02_Input.txt")
    println(part1(input))
    println(part2(input))
}
