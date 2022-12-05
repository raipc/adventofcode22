fun main() {
    checkAndSolve("Day02", 15) { calculateScore(it, Strategy.SUGGEST_EXACT_MOVE) }
    checkAndSolve("Day02", 12) { calculateScore(it, Strategy.SUGGEST_OUTPUT) }
}

private fun calculateScore(lines: List<String>, strategy: Strategy): Int = lines
    .sumOf { calculateScoreForSingleGame(it, strategy) }

private fun calculateScoreForSingleGame(game: String, strategy: Strategy) =
    strategy.calculateScore(game[0], game[2])

private enum class Strategy {
    SUGGEST_EXACT_MOVE {
        override fun calculateScore(input: Char, suggestion: Char): Int {
            val opponentCode = normalizeValue(input, 'A')
            val playerCode = normalizeValue(suggestion, 'X')
            return playerCode + when (playerCode - opponentCode) {
                0 -> 3
                1, -2 -> 6
                else -> 0
            }
        }
    },
    SUGGEST_OUTPUT {
        override fun calculateScore(input: Char, suggestion: Char): Int {
            val pointsFromSuggestion = (suggestion - 'X') * 3
            return pointsFromSuggestion + when (pointsFromSuggestion) {
                3 -> normalizeValue(input, 'A')
                6 -> if (input == 'C') 1 else normalizeValue(input, 'A') + 1
                0 -> if (input == 'A') 3 else normalizeValue(input, 'A') - 1
                else -> throw IllegalArgumentException()
            }
        }
    };

    fun normalizeValue(value: Char, base: Char) = value.code - base.code + 1

    abstract fun calculateScore(input: Char, suggestion: Char): Int
}