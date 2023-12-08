package year_2023.day_07

import readInput

enum class HandType(val strength: Int) {
    FiveOfAKind(6),
    FourOfAKind(5),
    FullHouse(4),
    ThreeOfAKind(3),
    TwoPair(2),
    OnePair(1),
    HighCard(0),
    ;

    companion object {
        fun fromString(text: String, jIsJoker: Boolean): HandType {
            val map = mutableMapOf<Char, Int>()

            text.forEach { char ->
                val curr = map[char] ?: 0
                map[char] = curr + 1
            }

            if (jIsJoker) {
                val jokerCount = map['J'] ?: 0

                val maxMatches = map.toList().filterNot { it.first == 'J' }.maxByOrNull { it.second }
                if (maxMatches == null) {
                    map['A'] = jokerCount
                } else {
                    map[maxMatches.first] = maxMatches.second + jokerCount
                }

                map.remove('J')
            }

            val maxMatches = map.toList().maxByOrNull { it.second }!!

            return when(maxMatches.second) {
                5 -> FiveOfAKind
                4 -> FourOfAKind
                3 -> {
                    when (map.size) {
                        2 -> FullHouse
                        else -> ThreeOfAKind
                    }
                }
                2 -> {
                    when (map.size) {
                        3 -> TwoPair
                        else -> OnePair
                    }
                }
                else -> HighCard
            }
        }
    }
}

data class Hand(
    val type: HandType,
    val cards: String,
    val bid: Long,
    val jIsJoker: Boolean
): Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        if (type.strength > other.type.strength) {
            return 1
        } else if (type.strength < other.type.strength) {
            return -1
        }

        cards.forEachIndexed { index, myCard ->
            if (cardToInt(myCard) > cardToInt(other.cards[index])) {
                return 1
            } else if (cardToInt(myCard) < cardToInt(other.cards[index])) {
                return -1
            }
        }

        return 0
    }

    // Solution 1
    private fun cardToInt(char: Char): Int {
        return when (char) {
            '2'-> 2
            '3' -> 3
            '4' -> 4
            '5' -> 5
            '6' -> 6
            '7' -> 7
            '8' -> 8
            '9' -> 9
            'T' -> 10
            'J' -> if (jIsJoker) 1  else 11
            'Q' -> 12
            'K' -> 13
            'A' -> 14
            else -> throw IllegalArgumentException("invalid card")
        }
    }
}

object Day07 {
    /**
     *
     */
    fun solutionOne(text: List<String>): Long {
        val hands = text.map { parseHand(it, false) }.sorted()

        var sum = 0L
        hands.forEachIndexed { index, hand ->
            sum += (hand.bid * (index + 1))
        }


        return sum
    }

    /**
     *
     */
    fun solutionTwo(text: List<String>): Long {
        val hands = text.map { parseHand(it, true) }.sorted()

        var sum = 0L
        hands.forEachIndexed { index, hand ->
            sum += (hand.bid * (index + 1))
        }

        return sum
    }

    private fun parseHand(text: String, jIsJoker: Boolean): Hand {
        val split = text.split(" ")
        return Hand(
            HandType.fromString(split[0], jIsJoker),
            split[0],
            split[1].toLong(),
            jIsJoker
        )
    }

}

fun main() {
    val text = readInput("year_2023/day_07/Day07.txt")

    val solutionOne = Day07.solutionOne(text)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day07.solutionTwo(text)
    println("Solution 2: $solutionTwo")
}
