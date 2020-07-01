package flashcards

import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

val scanner = Scanner(System.`in`)
val cards = mutableMapOf<String, String>()
val mistakes = mutableMapOf<String, Int>()
val logs = mutableListOf<String>()

var exportTo: String = ""

fun main(args: Array<String>) {
    if (args.size > 1) {
        parseArg(args[0], args[1])
    }
    if (args.size > 3) {
        parseArg(args[2], args[3])
    }
    menu()
}

fun parseArg(command: String, filename: String) {
    when (command) {
        "-import" -> importCards(filename)
        "-export" -> exportTo = filename
    }
}

fun menu() {
    while (true) {
        lprintln("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        val newLine = scanner.nextLine()
        logInput(newLine)
        when (newLine.toLowerCase()) {
            "add" -> inputCard()
            "remove" -> removeCard()
            "import" -> importCards("")
            "export" -> exportCards("")
            "ask" -> checkCards()
            "hardest card" -> hardestCard()
            "log" -> saveLog()
            "reset stats" -> resetStats()
            "exit" -> {
                lprintln("Bye bye!")
                if (exportTo.isNotBlank()) {
                    exportCards(exportTo)
                }
                return
            }
        }
        lprintln()
    }
}

fun resetStats() {
    mistakes.clear()
    lprintln("Card statistics have been reset.")
}

fun saveLog() {
    lprintln("File name:")
    val filename = scanner.nextLine()
    val file = File(filename)
    file.writeText("")
    logs.forEach {
        file.appendText(it)
    }
    lprintln("The log has been saved.")
}

fun hardestCard() {
    var max = 0
    mistakes.forEach { (_, i) ->
        if (i > max) max = i
    }
    if (max == 0) {
        lprintln("There are no cards with errors.")
    } else {
        val hardestCards = mistakes.filter { it.value == max }
        lprint("The hardest card")
        lprint(if (hardestCards.size > 1) "s are" else " is")
        var prefix = " "
        hardestCards.forEach {
            lprint("$prefix\"${it.key}\"")
            prefix = ", "
        }
        lprintln(". You have $max errors answering it.")
    }
}

fun askFilename(): String {
    lprintln("File name:")
    val filename = scanner.nextLine()
    logInput(filename)
    return filename
}

fun exportCards(filename: String) {
    try {
        val file = getFile(filename)
        file.writeText(cards.toString())
        file.appendText("***")
        file.appendText(mistakes.toString())
        lprintln("${cards.size} cards have been saved.")
    } catch (e: Exception) {
        lprintln("Can't export cards.")
    }
}

fun getFile(filename: String): File {
    return File(if (filename.isBlank()) askFilename() else filename)
}

fun importCards(filename: String) {
    try {
        val file = getFile(filename)
        val loadedText = file.readText()
        val cardsLoaded: Int
        if (loadedText.contains("***")) {
            cardsLoaded = loadCards(loadedText.substringBefore("***"))
            loadMistakes(loadedText.substringAfter("***"))
        } else {
            cardsLoaded = loadCards(loadedText)
        }
        lprintln("$cardsLoaded cards have been loaded.")
    } catch (e: Exception) {
        lprintln("File not found.")
    }
}

fun loadCards(text: String): Int {
    val loadedCards = text.removePrefix("{").removeSuffix("}").split(", ").associate {
        val (key, value) = it.split("=")
        key to value
    }
    cards.putAll(loadedCards)
    return loadedCards.size
}

fun loadMistakes(mistakesText: String) {
    val loadedMistakes: Map<String, Int> = mistakesText.removePrefix("{").removeSuffix("}").split(", ").associate {
        val (key, value) = it.split("=")
        key to value.toInt()
    }
    loadedMistakes.forEach {
        mistakes[it.key] = it.value // + (mistakes[it.key] ?: 0)
    }
}

fun inputCards(): Map<String, String> {
    lprintln("Input the number of cards:")
    val size = scanner.nextLine().toInt()
    logInput("$size")
    val cards = mutableMapOf<String, String>()
    for (i in 1..size) {
        val term = inputWord("The card #$i:", "card") { cards.containsValue(it) }
        val definition = inputWord("The definition of the card #$i:", "definition") { cards.containsKey(it) }
        cards[definition] = term
    }
    return cards
}

fun inputCard() {
    lprintln("The card:")
    val term = scanner.nextLine()
    logInput(term)
    if (cards.containsKey(term)) {
        lprintln("The card \"$term\" already exists.")
        return
    }
    lprintln("The definition of the card:")
    val definition = scanner.nextLine()
    logInput(definition)
    if (cards.containsValue(definition)) {
        lprintln("The definition \"$definition\" already exists.")
        return
    }
    cards[term] = definition
    lprintln("The pair (\"$term\":\"$definition\") has been added.")
}

fun removeCard() {
    lprintln("The card:")
    val term = scanner.nextLine()
    logInput(term)
    mistakes.remove(term)
    if (cards.remove(term) == null) {
        lprintln("Can't remove \"$term\"")
    } else {
        lprintln("The card has been removed.")
    }
}

fun inputWord(prompt: String, word: String, validate: (String) -> Boolean): String {
    lprintln(prompt)
    while (true) {
        val newLine = scanner.nextLine()
        logInput(newLine)
        if (validate(newLine)) {
            lprintln("The $word \"$newLine\" already exists. Try again:")
        } else {
            return newLine
        }
    }
}

fun checkCards() {
    lprintln("How many times to ask?")
    val numberOfCards: Int = scanner.nextInt()
    logInput("$numberOfCards")
    scanner.nextLine()
    for (i in 1..numberOfCards) {
        val pos = Random.nextInt(cards.size)
        val term = cards.keys.elementAt(pos)
        val definition = cards[term]
        lprintln("Print the definition of \"$term\":")
        val answer = scanner.nextLine()
        logInput(answer)
        if (answer == definition) {
            lprintln("Correct answer.")
        } else {
            mistakes[term] = (mistakes[term] ?: 0) + 1
            lprint("Wrong answer. The correct one is \"$definition\"")
            if (cards.containsValue(answer)) {
                val card = cards.asSequence().first { it.value == answer }
                lprintln(", you've just written the definition of \"${card.key}\".")
            } else {
                lprintln()
            }
        }
    }
}

fun lprint(message: String) {
    logs.add(message)
    print(message)
}

fun lprintln(message: String) {
    logs.add(message + "\n")
    println(message)
}

fun lprintln() {
    logs.add("\n")
    println()
}

fun logInput(message: String) {
    logs.add("> $message\n")
}