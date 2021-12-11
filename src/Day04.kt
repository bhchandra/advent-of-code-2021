import java.lang.RuntimeException

data class Coordinate(val x: Int, val y: Int)

class Board(
    private val numbersToCoordinates: Map<Int, Coordinate>
) {
    private var markedNumbers: MutableList<Int> = mutableListOf()
    private var isWinner: Boolean = false

    fun markNumber(i: Int) = markedNumbers.add(i)

    fun isWinner() = isWinner

    fun scoreIfWinnerOrNull(): Int? {
        if (markedNumbers.size < 5) return null

        val selectedCoordinates =
            numbersToCoordinates.filter { markedNumbers.contains(it.key) }.map { it.value }

        val xCoordinatesToCount = selectedCoordinates.groupingBy { it.x }.eachCount()
        val xCoordinate = xCoordinatesToCount.firstNotNullOfOrNull { it.takeIf { it.value >= 5 } }?.value

        val yCoordinatesToCount = selectedCoordinates.groupingBy { it.y }.eachCount()
        val yCoordinate = yCoordinatesToCount.firstNotNullOfOrNull { it.takeIf { it.value >= 5 } }?.value

        if (xCoordinate == null && yCoordinate == null) return null

        val sumOfUnmarkedNumbers = numbersToCoordinates
            .mapNotNull { it.key.takeIf { key -> !markedNumbers.contains(key) } }
            .sum()

        return (sumOfUnmarkedNumbers * markedNumbers.last())
            .also { isWinner = true }
    }

    override fun toString(): String {
        return numbersToCoordinates.toString()
    }

    companion object {
        fun create(input: List<String>): Board {
            check(input.size == 5) { "input should be of size 5 lines" }
            val map = mutableMapOf<Int, Coordinate>()
            for ((y, line) in input.withIndex()) {
                val charArr = line.trim().split("\\s+".toRegex())
                for ((x, c) in charArr.withIndex()) {
                    map[c.toInt()] = Coordinate(x, y)
                }
            }
            return Board(map)
        }
    }

}


fun main() {

    //test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day04_Test.txt")

    val (testBoards, testNumbersToDraw) = getBoardsAndNumbers(testInput)

    println(firstBoardToWin(testBoards, testNumbersToDraw))
    println(lastBoardToWin(testBoards, testNumbersToDraw))

    val input = readInputAsText("Day04_Input.txt")

    val (boards, numbersToDraw) = getBoardsAndNumbers(input)

    println(firstBoardToWin(boards, numbersToDraw))
    println(lastBoardToWin(boards, numbersToDraw))
}

fun getBoardsAndNumbers(input: String): Pair<List<Board>, List<Int>> {
    val lines = input.split(System.lineSeparator() + System.lineSeparator())
    val numbersToDraw = lines.first().split(",").map { it.toInt() }
    val boardSquares: List<String> = lines.drop(1)

    val boards = boardSquares
        .map { it.split(System.lineSeparator()) }
        .map { Board.create(it) }

    return boards to numbersToDraw
}

fun firstBoardToWin(boards: List<Board>, numbersToDraw: List<Int>): Int {
    for (number in numbersToDraw) {
        for (board in boards) {
            board.markNumber(number)
            board.scoreIfWinnerOrNull()?.let { return it }
        }
    }
    throw RuntimeException("No winner present")
}

fun lastBoardToWin(boards: List<Board>, numbersToDraw: List<Int>): Int {
    val winnerBoards = mutableListOf<Board>()
    for (number in numbersToDraw) {
        for (board in boards) {
            if (board.isWinner()) continue
            board.markNumber(number)
            board.scoreIfWinnerOrNull()?.let { winnerBoards.add(board) }
        }
    }
    if (winnerBoards.isEmpty())
        throw RuntimeException("No winner present")
    else
        return winnerBoards.last().scoreIfWinnerOrNull()!!
}


