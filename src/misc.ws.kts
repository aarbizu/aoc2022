import java.lang.StringBuilder

(1 .. 26)

('a' .. 'z').forEach { print(it) }

('A' .. 'Z').forEach { print(it) }

(1 .. 26).toList().zip(('a' .. 'z').toList()).toMap()


var one = (1 .. 10)
var two = (7 .. 10)

one.all { two.contains(it) } || two.all { one.contains(it) }

val regex = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
regex
val (v1, v2, v3, v4) = regex.find("30-31,2-31")!!.destructured

v1
v2
v3
v4

val s = StringBuilder()

(1..10).forEach { s.append("t")}


