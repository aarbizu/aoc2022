package org.aarbizu.aoc2022.daySeven

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test

class FilesystemUtilityTest {

    @Test
    fun `part 1`() {
        val commands = java.io.File("$INPUTS_DIR/d7-p1.txt").readLines()
        val fs = FilesystemUtility()
        val initDir = fs.processCommands(commands)
        val sizeMap = mutableMapOf<String, Long>()
        fs.computeSize(initDir.dirs[0], sizeMap)
        assertThat(sizeMap).isNotEmpty()

        println("sizemap\n ${sizeMap.entries.joinToString("\n")}")

        val dirSizeSum = sizeMap.values.filter { it <= 100000 }.sum()
        assertThat(dirSizeSum).isEqualTo(1306611).also { println("sum of candidate dirs = $dirSizeSum") }
    }

    @Test
    fun `test command parsing`() {
        val commands = sample.lines()
        val fs = FilesystemUtility()
        val initDir = fs.processCommands(commands)
        assertThat(initDir).isNotNull()

        val sizeMap = mutableMapOf<String, Long>()
        fs.computeSize(initDir.dirs[0], sizeMap)
        assertThat(sizeMap).isNotEmpty()

        val dirSizeSum = sizeMap.values.filter { it <= 100000 }.sum()
        assertThat(dirSizeSum).isEqualTo(95437).also { println("sum of candidate dirs = $dirSizeSum") }
    }

    private val sample = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()
}
