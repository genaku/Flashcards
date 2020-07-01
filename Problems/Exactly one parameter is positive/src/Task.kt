import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val numbers = mutableListOf<Int>()
    for (i in 1..3) {
        numbers.add(scanner.nextInt())
    }
    println(numbers.filter { it > 0 }.count() == 1)
}