package main

fun solution(cards: Array<Array<Int>>, moves: Array<Array<Int>>, query: Int): Array<Int> {
    // Step 1: Create a map to keep track of card positions
    val cardPositions = mutableMapOf<Int, Pair<Int, Int>>()
    val grid = mutableMapOf<Pair<Int, Int>, Int>()

    for (card in cards) {
        val cardId = card[0]
        val row = card[1]
        val col = card[2]
        cardPositions[cardId] = Pair(row, col)
        grid[Pair(row, col)] = cardId
    }

    // Step 2: Process each move
    for (move in moves) {
        val cardId = move[0]
        val fromRow = move[1]
        val fromCol = move[2]
        val toRow = move[3]
        val toCol = move[4]

        val originalPosition = Pair(fromRow, fromCol)
        val newPosition = Pair(toRow, toCol)

        if (grid[originalPosition] == cardId) {
            // Move the card to the new position
            grid.remove(originalPosition)
            var current = newPosition

            while (grid.containsKey(current)) {
                val occupyingCard = grid[current]!!
                val nextPosition = Pair(current.first + 1, current.second)
                grid[nextPosition] = occupyingCard
                cardPositions[occupyingCard] = nextPosition
                current = nextPosition
            }

            grid[newPosition] = cardId
            cardPositions[cardId] = newPosition

            // Move up the cards in the original column
            var nextPosition = Pair(fromRow + 1, fromCol)
            while (grid.containsKey(nextPosition)) {
                val occupyingCard = grid[nextPosition]!!
                grid.remove(nextPosition)
                val previousPosition = Pair(nextPosition.first - 1, nextPosition.second)
                grid[previousPosition] = occupyingCard
                cardPositions[occupyingCard] = previousPosition
                nextPosition = Pair(nextPosition.first + 1, nextPosition.second)
            }
        }
    }

    // Return the final position of the queried card
    val finalPosition = cardPositions[query]!!
    return arrayOf(finalPosition.first, finalPosition.second)
}

fun main() {
    val cards = arrayOf(
        arrayOf(1, 1, 0),
        arrayOf(3, 0, 0),
        arrayOf(6, 0, 1),
        arrayOf(4, 0, 2),
        arrayOf(5, 2, 0),
        arrayOf(7, 1, 1),
        arrayOf(2, 1, 2)
    )

    val moves = arrayOf(
        arrayOf(6, 0, 1, 2, 0)
    )

    val query = 6

    val result = solution(cards, moves, query)
    println("Final position of card $query: [${result[0]}, ${result[1]}]")
}
