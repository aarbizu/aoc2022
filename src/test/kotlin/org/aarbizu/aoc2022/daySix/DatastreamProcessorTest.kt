package org.aarbizu.aoc2022.daySix

import com.google.common.truth.Truth.assertThat
import org.aarbizu.aoc2022.util.INPUTS_DIR
import org.junit.jupiter.api.Test
import java.io.File

class DatastreamProcessorTest {

    @Test
    fun `sample tests`() {
        val sample1 = "bvwbjplbgvbhsrlpgdmjqwftvncz" // first marker after character 5
        val sample2 = "nppdvjthqldpwncqszvftbrmjlhg" // first marker after character 6
        val sample3 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" // first marker after character 10
        val sample4 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" // first marker after character 11

        val processor = DatastreamProcessor(4)

        assertThat(processor.ingest(sample1)).isEqualTo(5)
        assertThat(processor.ingest(sample2)).isEqualTo(6)
        assertThat(processor.ingest(sample3)).isEqualTo(10)
        assertThat(processor.ingest(sample4)).isEqualTo(11)
    }

    @Test
    fun `part one`() {
        val bufferContents = File("$INPUTS_DIR/d6-p1.txt").readLines()
        val processor = DatastreamProcessor(4)

        val tokenPosition = processor.ingest(bufferContents[0])
        assertThat(tokenPosition).isEqualTo(1042).also { println("start-of-packet marker @ $tokenPosition") }
    }

    @Test
    fun `part two`() {
        val bufferContents = File("$INPUTS_DIR/d6-p1.txt").readLines()
        val processor = DatastreamProcessor(14)

        val tokenPosition = processor.ingest(bufferContents[0])
        assertThat(tokenPosition).isEqualTo(2980).also { println("start-of-packet marker @ $tokenPosition") }
    }
}
