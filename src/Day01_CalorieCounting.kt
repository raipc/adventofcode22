import java.util.*

fun main() {
    checkAndSolve("Day01", 24000) { findTop1Sum(it) }
    checkAndSolve("Day01", 45000) { findSumOfTopSums(it) }
}

private fun findTop1Sum(lines: Collection<String>): Int {
    var currentSum = 0
    var maxSum = 0
    lines.forEach {
        if (it.isEmpty()) {
            if (currentSum > maxSum) maxSum = currentSum
            currentSum = 0
        } else {
            currentSum += it.toInt()
        }
    }
    return maxOf(maxSum, currentSum)
}

private fun findSumOfTopSums(lines: Collection<String>, count: Int = 3): Int {
    var currentSum = 0
    val maxSums = PriorityQueue<Int>()
    lines.forEach {
        if (it.isEmpty()) {
            maxSums.add(currentSum)
            if (maxSums.size > count) {
                maxSums.remove()
            }
            currentSum = 0
        } else {
            currentSum += it.toInt()
        }
    }
    maxSums.add(currentSum)
    if (maxSums.size > count) {
        maxSums.remove()
    }
    return maxSums.sum()
}