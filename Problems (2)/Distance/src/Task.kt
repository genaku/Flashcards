import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val distance = scanner.nextDouble()
    val hours = scanner.nextDouble()
    println(distance/hours)
}