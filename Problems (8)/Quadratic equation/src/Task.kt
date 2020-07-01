import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val a = scanner.nextDouble()
    val b = scanner.nextDouble()
    val c = scanner.nextDouble()
    val d = b * b - 4.0 * a * c
    val x1 = (-b - Math.sqrt(d)) / (2 * a)
    val x2 = (-b + Math.sqrt(d)) / (2 * a)
    println("${Math.min(x1, x2)} ${Math.max(x1, x2)}")
}