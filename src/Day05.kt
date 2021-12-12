data class Point(val x: Int, val y: Int) {
    override fun toString(): String = "$x,$y"
}

data class Line(val p1: Point, val p2: Point) {

    fun pointsOccupied(): List<Point> {
        val horizontalLine = p1.y == p2.y
        val verticalLine = p1.x == p2.x
        if (horizontalLine) {
            val (startX, endX) = if (p1.x < p2.x) (p1.x to p2.x) else (p2.x to p1.x)
            return generateSequence(Point(startX, p1.y)) {
                Point(it.x + 1, p1.y).takeIf { p -> p.x <= endX }
            }.toList()
        } else if (verticalLine) {
            val (startY, endY) = if (p1.y < p2.y) (p1.y to p2.y) else p2.y to p1.y
            return generateSequence(Point(p1.x, startY)) {
                Point(p1.x, it.y + 1).takeIf { p -> p.y <= endY }
            }.toList()
        } else { //diagonal line
            val (startX, startY) = p1.x to p1.y
            val (endX, endY) = p2.x to p2.y
            val operX = if (startX < endX) Int::inc else Int::dec
            val operY = if (startY < endY) Int::inc else Int::dec
            val condition: (Int, Int) -> Boolean = if (startY < endY) { i1, i2 -> i1 <= i2 } else { i1, i2 -> i1 >= i2 }
            return generateSequence(Point(startX, startY)) {
                Point(operX(it.x), operY(it.y)).takeIf { p -> condition(p.y, endY) }
            }.toList()

        }
    }

    override fun toString(): String = "$p1 -> $p2"

    companion object {

        fun create(s: String, onlyStraight: Boolean = false): Line? {
            val points = s.split("->")
            val (start1, end1) = points.first().trim().split(",").map { it.toInt() }
            val (start2, end2) = points.last().trim().split(",").map { it.toInt() }
            val p1 = Point(start1, end1)
            val p2 = Point(start2, end2)
            if (onlyStraight) {
                if ((p1.x == p2.x || p1.y == p2.y).not()) return null
            }
            return Line(p1, p2)
        }
    }

}


fun main() {

    fun overlapPointsForOnlyHorizontalAndVerticalLines(input: List<String>): Int {
        val lines = input.mapNotNull { Line.create(it, onlyStraight = true) }
        val pointsToCount = lines.flatMap { it.pointsOccupied() }.groupingBy { it }.eachCount()
        val pointsOverlap = pointsToCount.filterValues { it >= 2 }
        return pointsOverlap.count()
    }

    fun overlapPointsForAllLines(input: List<String>): Int {
        val lines = input.mapNotNull { Line.create(it) }
        val pointsToCount = lines.flatMap { it.pointsOccupied() }.groupingBy { it }.eachCount()
        val pointsOverlap = pointsToCount.filterValues { it >= 2 }
        return pointsOverlap.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_Test.txt")
    println(overlapPointsForOnlyHorizontalAndVerticalLines(testInput))
    println(overlapPointsForAllLines(testInput))

    val input = readInput("Day05_Input.txt")
    println(overlapPointsForOnlyHorizontalAndVerticalLines(input))
    println(overlapPointsForAllLines(input))
}
