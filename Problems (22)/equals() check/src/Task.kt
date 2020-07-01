import java.util.Scanner

data class Client(val name: String, val age: Int, val balance: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is Client) {
            return false
        }
        return this.age == other.age && this.name == other.name
    }
}

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val client1 = readClient(input)
    val client2 = readClient(input)
    println(client1==client2)
}

fun readClient(input: Scanner): Client {
    val name = input.next()
    val age = input.nextInt()
    val balance = input.nextInt()
    return Client(name, age, balance)
}