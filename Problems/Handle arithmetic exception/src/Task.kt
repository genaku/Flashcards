import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val v1 = scanner.nextInt()
    val v2 = scanner.nextInt()
    try {
        println(v1/v2)
    } catch (e: ArithmeticException) {
        println("Division by zero, please fix the second argument!")
    }
}