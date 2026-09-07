package com.factory.wordflowdailymindpuzzles.domain

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.random.Random

data class GridCell(val row: Int, val col: Int)

data class PlacedWord(val word: String, val cells: List<GridCell>)

data class WordSearchPuzzle(
    val size: Int,
    val letters: List<List<Char>>,
    val placedWords: List<PlacedWord>
)

object WordSearchEngine {

    private val directions = listOf(
        0 to 1, 1 to 1, 1 to 0, 1 to -1, 0 to -1, -1 to -1, -1 to 0, -1 to 1
    )

    fun generate(words: List<String>, size: Int = 10, seed: Long = System.currentTimeMillis()): WordSearchPuzzle {
        val random = Random(seed)
        val grid = Array(size) { CharArray(size) { ' ' } }
        val placed = mutableListOf<PlacedWord>()

        val sorted = words.map { it.uppercase() }.sortedByDescending { it.length }
        for (word in sorted) {
            var placedOk = false
            var attempts = 0
            while (!placedOk && attempts < 200) {
                attempts++
                val (dr, dc) = directions[random.nextInt(directions.size)]
                val startRow = random.nextInt(size)
                val startCol = random.nextInt(size)
                val endRow = startRow + dr * (word.length - 1)
                val endCol = startCol + dc * (word.length - 1)
                if (endRow !in 0 until size || endCol !in 0 until size) continue

                val cells = (word.indices).map { i -> GridCell(startRow + dr * i, startCol + dc * i) }
                val fits = cells.withIndex().all { (i, cell) ->
                    val existing = grid[cell.row][cell.col]
                    existing == ' ' || existing == word[i]
                }
                if (!fits) continue

                cells.forEachIndexed { i, cell -> grid[cell.row][cell.col] = word[i] }
                placed.add(PlacedWord(word, cells))
                placedOk = true
            }
        }

        for (r in 0 until size) {
            for (c in 0 until size) {
                if (grid[r][c] == ' ') {
                    grid[r][c] = ('A'..'Z').random(random)
                }
            }
        }

        return WordSearchPuzzle(size, grid.map { it.toList() }, placed)
    }

    fun snapToLine(start: GridCell, current: GridCell, size: Int): GridCell {
        val dr = current.row - start.row
        val dc = current.col - start.col
        if (dr == 0 && dc == 0) return start

        val angle = atan2(dr.toDouble(), dc.toDouble())
        val dirIndex = Math.round(angle / (Math.PI / 4)).toInt()
        val normalized = ((dirIndex % 8) + 8) % 8
        val (ddr, ddc) = directions[normalized]
        val dist = maxOf(abs(dr), abs(dc))

        val endRow = (start.row + ddr * dist).coerceIn(0, size - 1)
        val endCol = (start.col + ddc * dist).coerceIn(0, size - 1)
        return GridCell(endRow, endCol)
    }

    fun cellsAlongLine(start: GridCell, end: GridCell): List<GridCell> {
        val dr = end.row - start.row
        val dc = end.col - start.col
        val steps = maxOf(abs(dr), abs(dc))
        if (steps == 0) return listOf(start)

        val stepR = if (dr == 0) 0 else dr / abs(dr)
        val stepC = if (dc == 0) 0 else dc / abs(dc)
        return (0..steps).map { i -> GridCell(start.row + stepR * i, start.col + stepC * i) }
    }
}
