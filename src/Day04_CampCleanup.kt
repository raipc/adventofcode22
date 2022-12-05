fun main() {
    checkAndSolve("Day04", 2) { it.count { line -> lineHasIncludingRanges(line) } }
    checkAndSolve("Day04", 4) { it.count { line -> lineHasOverlappingRanges(line) } }
}

private fun parseRange(value: String) = value.split('-').let { IntRange(it[0].toInt(), it[1].toInt()) }

private fun lineHasIncludingRanges(value: String): Boolean =
    value.split(',').let { oneContainsTheOther(parseRange(it[0]), parseRange(it[1])) }

private fun lineHasOverlappingRanges(value: String): Boolean =
    value.split(',').let { oneOverlapsTheOther(parseRange(it[0]), parseRange(it[1])) }

private fun oneContainsTheOther(first: IntRange, second: IntRange): Boolean =
    (first.first <= second.first && first.last >= second.last) ||
            (second.first <= first.first && second.last >= first.last)

private fun oneOverlapsTheOther(first: IntRange, second: IntRange): Boolean =
    (second.first <= first.last && first.first <= second.first) ||
            (first.first <= second.last && second.first <= first.first)

