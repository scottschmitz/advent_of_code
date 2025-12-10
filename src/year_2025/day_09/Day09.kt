package year_2025.day_09

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import BaseDay
import util.Point
import util.x
import util.y
import kotlin.math.abs
import kotlin.math.max

class Day09 : BaseDay<List<Point>, Long, Long>("year_2025/day_09/Day09.txt") {

    override fun processFile(input: List<String>): List<Point> {
        return input.map { line ->
            val split = line.split(',')
            Point(split[0].toInt(), split[1].toInt())
        }
    }

    override fun solutionOne(input: List<Point>): Long {
        val combos = input
            .flatMapIndexed { index, pointA -> input.drop(index + 1).map { pointB -> pointA to pointB } }
            .map { (pointA, pointB) ->
                val area = (abs(pointA.x() - pointB.x()) + 1).toLong() * (abs(pointA.y() - pointB.y()) + 1).toLong()
                (pointA to pointB) to area
            }

        return combos.maxBy { it.second }.second
    }

    override fun solutionTwo(input: List<Point>): Long {

        if (input.isEmpty()) return 0L

        // Build coordinate sets (x, x+1, y, y+1)
        val xset = mutableSetOf<Int>()
        val yset = mutableSetOf<Int>()
        for (p in input) {
            xset.add(p.x())
            xset.add(p.x() + 1)
            yset.add(p.y())
            yset.add(p.y() + 1)
        }

        val xlst = xset.toMutableList().apply { sort() }
        val ylst = yset.toMutableList().apply { sort() }

        val xmap = mutableMapOf<Int, Int>()
        val ymap = mutableMapOf<Int, Int>()

        for ((i, x) in xlst.withIndex()) {
            xmap[x] = i
        }
        for ((i, y) in ylst.withIndex()) {
            ymap[y] = i
        }

        // grid[xIndex][yIndex]
        val grid = Array(xlst.size) { IntArray(ylst.size) }

        // Build edges in compressed grid (loop over polygon edges)
        val n = input.size
        for (i in 0 until n) {
            val p1 = input[i]
            val p2 = input[(i + 1) % n]

            var x1 = xmap[p1.x()]!!
            var y1 = ymap[p1.y()]!!
            var x2 = xmap[p2.x()]!!
            var y2 = ymap[p2.y()]!!

            if (x1 != x2) {
                require(y1 == y2) { "Non-horizontal edge encountered" }

                if (x1 > x2) {
                    val tmp = x1
                    x1 = x2
                    x2 = tmp
                }

                grid[x1][y1] = grid[x1][y1] or 1
                grid[x2][y1] = grid[x2][y1] or 2
                for (x in (x1 + 1) until x2) {
                    grid[x][y1] = grid[x][y1] or 3
                }
            }
        }

        // realgrid[xIndex][yIndex] = inside polygon
        val realgrid = Array(xlst.size) { BooleanArray(ylst.size) }

        for (i in grid.indices) {
            val row = grid[i]
            var nxt = 0
            for (j in row.indices) {
                val cell = row[j]
                realgrid[i][j] = (nxt > 0) || (cell > 0)
                nxt = nxt xor cell
            }
            check(nxt == 0) { "Scanline parity error at row $i" }
        }

        // 2D prefix sums over realgrid
        val realgridsums = Array(xlst.size + 1) { IntArray(ylst.size + 1) }
        for (i in realgrid.indices) {
            val row = realgrid[i]
            for (j in row.indices) {
                val cellVal = if (row[j]) 1 else 0
                realgridsums[i + 1][j + 1] =
                    cellVal +
                            realgridsums[i][j + 1] +
                            realgridsums[i + 1][j] -
                            realgridsums[i][j]
            }
        }

        var ans = 0L

        // For every pair of original points, check rectangle they define
        for (i in input.indices) {
            val pt = input[i]
            for (j in 0 until i) {
                val pt2 = input[j]

                var x1 = pt.x()
                var y1 = pt.y()
                var x2 = pt2.x()
                var y2 = pt2.y()

                if (x1 > x2) {
                    val tmp = x1
                    x1 = x2
                    x2 = tmp
                }
                if (y1 > y2) {
                    val tmp = y1
                    y1 = y2
                    y2 = tmp
                }

                val xr1 = xmap[x1]!!
                val xr2 = xmap[x2]!!
                val yr1 = ymap[y1]!!
                val yr2 = ymap[y2]!!

                val numGoods =
                    realgridsums[xr2 + 1][yr2 + 1] -
                            realgridsums[xr2 + 1][yr1] -
                            realgridsums[xr1][yr2 + 1] +
                            realgridsums[xr1][yr1]

                val areaCells = (xr2 - xr1 + 1) * (yr2 - yr1 + 1)
                if (numGoods == areaCells) {
                    val rectArea =
                        (x2 - x1 + 1).toLong() * (y2 - y1 + 1).toLong()
                    ans = max(ans, rectArea)
                }
            }
        }

        return ans
    }

    //<editor-fold desc="Samples">
    private val sampleText = listOf<String>(
        "7,1",
        "11,1",
        "11,7",
        "9,7",
        "9,5",
        "2,5",
        "2,3",
        "7,3",
    )

    @Test
    fun part1Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(50L, solutionOne(sampleInput))
    }

    @Test
    fun part1() {
        println("Part 1: " + solutionOne(parsedInput))
    }

    @Test
    fun part2Sample() {
        val sampleInput = processFile(sampleText)
        assertEquals(24L, solutionTwo(sampleInput))
    }

    @Test
    fun part2() {
        println("Part 2: " + solutionTwo(parsedInput))
    }
    //</editor-fold>
}