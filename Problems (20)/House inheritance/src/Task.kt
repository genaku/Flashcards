fun main() {
    val rooms = readLine()!!.toInt()
    val price = readLine()!!.toInt()
    val house = if (rooms > 7) {
        Palace(rooms, price)
    } else if (rooms > 4) {
        Mansion(rooms, price)
    } else if (rooms == 4) {
        Cottage(rooms, price)
    } else if (rooms > 1) {
        Bungalow(rooms, price)
    } else {
        Cabin(rooms, price)
    }
    println(house.finalPrice().toInt())
}

open class House(val rooms: Int, val price: Int) {

    private val basePrice: Double = if (price > 1000000) 1000000.0 else if (price < 0) 0.0 else price.toDouble()

    open fun finalPrice(): Double {
        return basePrice
    }
}

class Cabin(rooms: Int, price: Int) : House(rooms, price)

class Bungalow(rooms: Int, price: Int) : House(rooms, price) {
    override fun finalPrice(): Double {
        return 1.2 * super.finalPrice()
    }
}

class Cottage(rooms: Int, price: Int) : House(rooms, price) {
    override fun finalPrice(): Double {
        return 1.25 * super.finalPrice()
    }
}

class Mansion(rooms: Int, price: Int) : House(rooms, price) {
    override fun finalPrice(): Double {
        return 1.4 * super.finalPrice()
    }
}

class Palace(rooms: Int, price: Int) : House(rooms, price) {
    override fun finalPrice(): Double {
        return 1.6 * super.finalPrice()
    }
}
