package util

typealias Line = Pair<Point, Point>

fun Line.crossingPoint(line2: Line): Point? {
    /*
        Diagonal lines for point distances can be expressed either as:
            x + y = c      or
            x - y = c
        Crossing lines have different forms, and so if:
            x + y = c1     and
            x - y = c2
        then:
            x = (c1 + c2) / 2
            y = (c1 - c2) / y
        */

    val c1 = run {
        val line1c1 = this.first.first + this.first.second
        val line1c2 = this.second.first + this.second.second
        val line2c1 = line2.first.first + line2.first.second
        val line2c2 = line2.second.first + line2.second.second
        when {
            line1c1 == line1c2 -> line1c1
            line2c1 == line2c2 -> line2c1
            else -> return null
        }
    }

    val c2 = run {
        val line1c1 = this.first.first - this.first.second
        val line1c2 = this.second.first - this.second.second
        val line2c1 = line2.first.first - line2.first.second
        val line2c2 = line2.second.first - line2.second.second
        when {
            line1c1 == line1c2 -> line1c1
            line2c1 == line2c2 -> line2c1
            else -> return null
        }
    }

    return ((c1 + c2) / 2 to (c1 - c2) / 2)
}