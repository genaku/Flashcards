import java.util.Scanner

// do not change this data class
data class Box(val height: Int, val length: Int, val width: Int)

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    // implement your code here
    val height = input.nextInt()
    val length1 = input.nextInt()
    val length2 = input.nextInt()
    val width = input.nextInt()
    val box1 = Box(height, length1, width)
    val box2 = box1.copy(length = length2)
    println(box1)
    println(box2)
}