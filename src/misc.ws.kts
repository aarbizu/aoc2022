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

val regex2 = """\[(\w)\]\s[\s\[]([\s\w])[\s\]][\s\[]([\s\w])[\s\]][\s\[]([\s\w])[\s\]][\s\[]([\s\w])[\s\]][\s\[]([\s\w])[\s\]][\s\[]([\s\w])[\s\]][\s\[]([\s\w])[\s\]][\s\[]([\s\w])[\s\]]""".toRegex()
regex2
//val (x1, x2, x3, x4, x5, x6, x7, x8, x9) = regex2.find("[V]         [T]         [J]")!!.destructured







