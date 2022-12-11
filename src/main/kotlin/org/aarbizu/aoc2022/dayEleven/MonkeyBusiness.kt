package org.aarbizu.aoc2022.dayEleven

val idRegex = """Monkey (\d+):""".toRegex()
val itemsRegex = """Starting items: ([\d,\s]*)""".toRegex()
val worryOpRegex = """Operation: new = old (\S+ \S+)""".toRegex()
val divisRegex = """Test: divisible by (\d+)""".toRegex()
val trueMonkeyRegex = """If true: throw to monkey (\d+)""".toRegex()
val falseMonkeyRegex = """If false: throw to monkey (\d+)""".toRegex()

class MonkeyBusiness {

    fun monkeyAround(totalRounds: Int, monkeys: List<Monkey>, worryReductionMode: Boolean = true) {
        val commonDivisor = monkeys.map { it.divisibility }.reduce { a, b -> a * b }

        repeat(totalRounds) {
            monkeys.forEach { m ->
                m.items.forEach {
                    m.inspections++
                    val nextItemAndDivisibilityCheck =
                        m.transformAndCheckDivisibility(it, commonDivisor, worryReductionMode)
                    if (nextItemAndDivisibilityCheck.second) {
                        monkeys[m.trueMonkey].items.add(nextItemAndDivisibilityCheck.first)
                    } else {
                        monkeys[m.falseMonkey].items.add(nextItemAndDivisibilityCheck.first)
                    }
                }.also { m.items.clear() }
            }
        }
    }

    /**
     * Monkey 0:
     *    Starting items: 79, 98
     *    Operation: new = old * 19
     *    Test: divisible by 23
     *        If true: throw to monkey 2
     *        If false: throw to monkey 3
     */
    fun parseMonkey(description: List<String>): Monkey {
        val (id) = idRegex.find(description[0].trim())!!.destructured
        val (itemsAsStr) = itemsRegex.find(description[1].trim())!!.destructured
        val (worryOpStr) = worryOpRegex.find(description[2].trim())!!.destructured
        val (divis) = divisRegex.find(description[3].trim())!!.destructured
        val (trueMonkey) = trueMonkeyRegex.find(description[4].trim())!!.destructured
        val (falseMonkey) = falseMonkeyRegex.find(description[5].trim())!!.destructured

        return Monkey(
            id.toInt(),
            itemsAsStr.trim().split(",").map { it.trim().toLong() }.toMutableList(),
            parseOp(worryOpStr),
            divis.toInt(),
            trueMonkey.toInt(),
            falseMonkey.toInt(),
            opAndOperand(worryOpStr)
        )
    }

    private fun opAndOperand(opFragment: String): Pair<String, String> {
        val (op, operand) = opFragment.split(" ")
        return Pair(op, operand)
    }

    private fun parseOp(opFragment: String): (Long) -> Long {
        val (op, operandStr) = opFragment.split(" ")
        return when (op) {
            "+" -> when (operandStr) {
                "old" -> { i -> i + i }
                else -> { i -> i + operandStr.toInt() }
            }
            "*" -> when (operandStr) {
                "old" -> { i -> i * i }
                else -> { i -> i * operandStr.toInt() }
            }
            else -> throw Exception("unknown op")
        }
    }
}

data class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val worryTransform: (Long) -> Long,
    val divisibility: Int,
    val trueMonkey: Int,
    val falseMonkey: Int,
    val opAndOperand: Pair<String, String>
) {
    var inspections: Long = 0
    override fun toString(): String {
        return "$id[i:$inspections][d:$divisibility,t:$trueMonkey,f:$falseMonkey] => ${items.joinToString(",")}"
    }

    fun transformAndCheckDivisibility(item: Long, commonDivisor: Int, worryReduction: Boolean): Pair<Long, Boolean> {
        val nextItem = if (worryReduction) (worryTransform(item) / 3) % commonDivisor else worryTransform(item) % commonDivisor
        return Pair(nextItem, nextItem % divisibility == 0L)
    }
}
