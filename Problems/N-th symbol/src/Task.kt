import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val str = scanner.nextLine()
    val num = scanner.nextInt()
    println("Symbol # $num of the string \"$str\" is '${str[num-1]}'")
}