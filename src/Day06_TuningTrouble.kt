fun main() {
    checkAndSolve("Day06", 7) { locateSequenceOfNonRepeatingCharacters(it[0], 4) }
    checkAndSolve("Day06", 19) { locateSequenceOfNonRepeatingCharacters(it[0], 14) }
}

private fun locateSequenceOfNonRepeatingCharacters(input: String, length: Int): Int {
    val countMap = hashMapOf<Int, Int>()
    for (i in input.indices) {
        if (i >= length) {
            countMap.compute(input[i-length].code) { _, value -> if (value != null && value > 1) value - 1 else null }
        }
        countMap.compute(input[i].code) { _, prev -> if (prev == null) 1 else prev + 1 }
        if (countMap.size == length) {
            println(countMap)
            return i + 1
        }
    }
    error("Must have located a sequence")
}