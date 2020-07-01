import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val name = scanner.next()
    val surname = scanner.next()
    val age = scanner.nextInt()
    println("${name.first()}. $surname, $age years old")
}