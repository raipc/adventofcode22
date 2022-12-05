import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

fun <T>checkAndSolve(day: String, testOutput: T, solution: (List<String>) -> T) {
    check(solution(readInput("${day}_test")) == testOutput)
    println(solution(readInput(day)))
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
