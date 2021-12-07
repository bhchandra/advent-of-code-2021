fun main() {

    data class Freq(var freq1: Int = 0, var freq0: Int = 0) {
        fun inc1() = freq1++
        fun inc0() = freq0++
        fun mostCommon() = if (freq1 >= freq0) '1' else '0'
        fun leastCommon() = if (freq1 < freq0) '1' else '0'
    }

    fun indicesToFreqMap(input: List<String>, indices: IntRange): MutableMap<Int, Freq> {
        val indexToFreq = mutableMapOf<Int, Freq>()
        input.forEach {
            val arr = it.toCharArray()
            for (i in indices) {
                indexToFreq[i] = indexToFreq.getOrDefault(i, Freq())
                    .apply { if (Character.getNumericValue(arr[i]) == 1) inc1() else inc0() }
            }
        }
        return indexToFreq
    }

    fun powerConsumption(input: List<String>): Int {

        val allIndices = input.first().toCharArray().indices

        val indicesToFreq = indicesToFreqMap(input, allIndices)

        val mostCommonBits = indicesToFreq.values
            .map { it.mostCommon() }
            .toCharArray()

        val gammaRate = Integer.parseInt(java.lang.String.copyValueOf(mostCommonBits), 2)

        val leastCommonBits = mostCommonBits.map { if (it == '1') '0' else '1' }.toCharArray()

        val epsilonRate = Integer.parseInt(java.lang.String.copyValueOf(leastCommonBits), 2)

        return gammaRate * epsilonRate
    }

    fun lifeSupportRating(input: List<String>): Int {

        val allIndices = input.first().toCharArray().indices

        fun recursiveReduction(input: List<String>, indices: IntRange, criteria: (Freq) -> Char): String {
            val position = indices.first
            val indicesToFreq = indicesToFreqMap(input, IntRange(position, position))
            val bit = indicesToFreq.values.first().let { freq -> criteria(freq) }
            val stringsWithBitInPosition = input.filter { it[position] == bit }
            if (stringsWithBitInPosition.size == 1) return stringsWithBitInPosition.first()
            return recursiveReduction(stringsWithBitInPosition, IntRange(position + 1, indices.last), criteria)
        }

        val oxygenRatingBinary = recursiveReduction(input, allIndices, Freq::mostCommon)
        val co2ScrubberRatingBinary = recursiveReduction(input, allIndices, Freq::leastCommon)

        return oxygenRatingBinary.toInt(2) * co2ScrubberRatingBinary.toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_Test.txt")
    println(powerConsumption(testInput))
    println(lifeSupportRating(testInput))

    val input = readInput("Day03_Input.txt")
    println(powerConsumption(input))
    println(lifeSupportRating(input))
}
