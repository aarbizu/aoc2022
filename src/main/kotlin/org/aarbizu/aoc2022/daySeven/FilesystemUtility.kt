package org.aarbizu.aoc2022.daySeven

class FilesystemUtility {

    fun processCommands(output: List<String>): Directory {
        var init = Directory(".", null)
        var pos = 0
        val cmdBuffer = Array(3) { "" }
        var cwd = init
        while (pos <= output.lastIndex) {
            if (output[pos].startsWith("$")) {
                // ignore the '$"
                val thisCmd = output[pos].split(" ")
                for (i in 1..thisCmd.lastIndex) {
                    cmdBuffer[i - 1] = thisCmd[i]
                }
            }
            when (cmdBuffer[0]) {
                "cd" -> {
                    cwd = if (cmdBuffer[1] == ".." && cwd.name != "/") {
                        cwd.parent!!
                    } else {
                        cwd.dirs
                            .find { it.name == cwd.name + "/" + cmdBuffer[1] }
                            ?: Directory(cwd.name + "/" + cmdBuffer[1], cwd)
                    }
                    if (cmdBuffer[1] == "/") {
                        init.dirs.add(cwd)
                    }
                    pos++
                }
                "ls" -> {
                    pos++
                    val cmdResult = mutableListOf<String>()
                    while (pos <= output.lastIndex && !output[pos].startsWith("$")) {
                        cmdResult.add(output[pos++])
                    }
                    processOutput(cmdResult, cwd)
                }
                else -> throw Exception("unknown command!")
            }
        }
        return init
    }

    private fun processOutput(lines: List<String>, dir: Directory) {
        var pos = 0
        while (pos <= lines.lastIndex) {
            val (first, second) = lines[pos].split(" ")
            when (first) {
                "dir" -> dir.dirs.add(Directory(dir.name + "/" + second, dir))
                in Regex("\\d+") -> {
                    dir.files.add(File(second, first.toLong(), dir))
                }
            }
            pos++
        }
    }

    fun computeSize(root: Directory, index: MutableMap<String, Long>): Long {
        return computeSizeHelper("  ", root, index)
    }

    private fun computeSizeHelper(pfx: String, root: Directory, index: MutableMap<String, Long>): Long {
        var size = 0L
        // println("$pfx - ${root.name} parent = ${root.parent?.name}")
        for (file in root.files) {
            // println("$pfx - ${file.name} ${file.size} parent = ${file.parent.name}")
            size += file.size
        }

        for (dir in root.dirs) {
            size += computeSizeHelper("$pfx  ", dir, index)
        }

        // println("$pfx - dir ${root.name} $size")
        index[root.name] = size
        return size
    }
}

operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)
sealed interface FsObject {
    val name: String
    val parent: Directory?
    val size: Long
}

class Directory(override val name: String, override val parent: Directory?) : FsObject {
    override var size: Long = 0
    val dirs = mutableListOf<Directory>()
    val files = mutableListOf<File>()
    override fun toString(): String {
        return "dir $name"
    }
}

class File(override val name: String, override val size: Long, override val parent: Directory) : FsObject {
    override fun toString(): String {
        return "$name ($size)"
    }
}
