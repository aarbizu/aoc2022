package org.aarbizu.aoc2022.util

const val INPUTS_DIR = "src/main/resources"

/**
 * Thank you, https://play.kotlinlang.org/byExample/01_introduction/06_Generics
 */
class MutableStack<E>(vararg items: E) {

    private val elements = items.toMutableList()
    fun push(element: E) = elements.add(element)
    fun peek(): E = elements.last()
    fun pop(): E = elements.removeAt(elements.size - 1)
    fun isEmpty() = elements.isEmpty()
    fun size() = elements.size
    override fun toString() = "MutableStack(${elements.joinToString()})"

}
fun <E> mutableStackOf(vararg elements: E) = MutableStack(*elements)

/**
 * support pop()'ing more than one element at a time, preserving order
 * basically a slice() operation
 */
fun <E> MutableStack<E>.multipop(numElements: Int): Collection<E> {
    val popped = mutableListOf<E>()
    (0 until numElements).forEach { _ ->
        popped.add(this.pop())
    }
    return popped.toList()
}

fun <E> MutableStack<E>.multipush(pushed: Collection<E>) {
    for (n in pushed.size - 1 downTo  0) {
        this.push(pushed.elementAt(n))
    }
}
