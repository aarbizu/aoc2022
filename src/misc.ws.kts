
val points: MutableMap<Int, MutableList<Int>> = mutableMapOf()

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

val pts = listOf(P(498,4), P(498,6), P(496,6))
pts.windowed(2).map { println(it) }

val pts2 = listOf(P(503,4), P(502,4), P(502,9), P(504,9))
pts2.windowed(2).map { println(it) }

val s = listOf((1 .. 20), (13 .. 31))

listOf(-1, 0, 1, 2, 3, 4, 5).count() == (-1 .. 5).count()

val sthing = (6 .. 26).scan(0) { a: Int, b:Int -> a + b }
sthing

val ints = listOf(12, 13, 15, 16, 17, 18, 19)

val allXord = ints.reduce { a: Int, b: Int -> a.xor(b) }
allXord

val rangeXord = (ints.first() .. ints.last()).reduce { a: Int, b: Int -> a.xor(b) }
rangeXord

2.xor(3.xor(4.xor(5)))

1.xor(2.xor(3.xor(4.xor(5))))

0.xor(1)

val ranges = listOf((1 .. 10), (-7 .. 25), (4 .. 18), (-5 .. 37))
val reduced = ranges.collapse()
reduced


