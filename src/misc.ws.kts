val points: MutableMap<Int,MutableList<Int>> = mutableMapOf()

points.getOrPut(-7) { mutableListOf() }.add(-8)
points.getOrPut(9) { mutableListOf() }.add(-2)
points.getOrPut(4) { mutableListOf() }.add(3)
points.getOrPut(-1) { mutableListOf() }.add(3)

for (y in -1 .. 10) {
    for (x in 480 .. 520) {
        if (y == -1 && x == 500) { print(" + ") }
        if (y == -1) { print("---") }
        else if (x == 480) { print(" | ") }
        else if (points.contains(y) && points[y]?.contains(x) == true) {
            print(" X ")
        } else {
            if (y >= 0) print(" . ")
        }
    }
    println()
}

class P(val x: Int, val y:Int) {
    override fun toString(): String {
        return "($x,$y)"
    }
}

val list = listOf(P(498,4), P(498,6), P(496,6))
list.windowed(2).map { println(it) }

val list2 = listOf(P(503,4), P(502,4), P(502,9), P(504,9))
list2.windowed(2).map { println(it) }