import java.math.BigInteger

typealias TimerValue = Int
typealias FishCount = BigInteger


fun main() {


    //test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day06_Test.txt")
    println(fishesAfter(testInput, 80))
    println(fishesAfter(testInput, 256))

    val input = readInputAsText("Day06_Input.txt")
    println(fishesAfter(input, 80))
    println(fishesAfter(input, 256))

}

private fun fishesAfter(input: String, days: Int): FishCount {
    val initialState = input.split(",").map { it.trim().toInt() }

    var timerValueToFishCount: MutableMap<TimerValue, FishCount> =
        initialState.groupingBy { it }.eachCount()
            .mapValues { it.value.toBigInteger() }
            .toMutableMap()

    for (day in 1..days) {
        val transferMap = mutableMapOf<TimerValue, FishCount>()
        val fishesWithZeroTimer = timerValueToFishCount.getOrZero(0)
        for (timerValue in 1..8) {
            val count = timerValueToFishCount.getOrZero(timerValue)
            if (count > BigInteger.ZERO) {
                transferMap[timerValue - 1] = count
                transferMap[timerValue] = timerValueToFishCount.getOrZero(timerValue) - count
            }
        }
        transferMap[8] = transferMap.getOrZero(8) + fishesWithZeroTimer
        transferMap[6] = transferMap.getOrZero(6) + fishesWithZeroTimer

        timerValueToFishCount = transferMap
//        println("after day $day  : " + timerToFishCount.asString())
    }
    return timerValueToFishCount.values.reduce { a, b -> a + b }
}

fun MutableMap<TimerValue, FishCount>.getOrZero(timerValue: TimerValue) =
    this.getOrDefault(timerValue, BigInteger.ZERO)

//fun MutableMap<TimerValue, FishCount>.asString(): String = this
//    .filterValues { it > BigInteger.ZERO }
//    .map {
//        val b = StringJoiner(",")
//        repeat(it.value.toInt()) { _ -> b.add(it.key.toString()) }
//        b.toString()
//    }.reduce { a, b -> "$a,$b" }
