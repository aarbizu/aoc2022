package org.aarbizu.aoc2022.dayThirteen

class DistressSignal {
    fun comparePackets(packetPairs: List<String>): Int {
        return packetPairs.filter { it.isNotBlank() }
            .map { Packet.of(it) }.chunked(2).mapIndexed { index, packets ->
                if (packets.first() < packets.last()) index + 1 else 0
            }.sum()
    }

    fun sorted(packetPairs: List<String>): Int {
        val div1 = Packet.of("[[2]]")
        val div2 = Packet.of("[[6]]")
        val packets = packetPairs.filter { it.isNotBlank() }.map { Packet.of(it) }
        val ordered = (packets + div1 + div2).sorted()
        return (ordered.indexOf(div1) + 1) * (ordered.indexOf(div2) + 1)
    }

    sealed class Packet : Comparable<Packet> {
        companion object {
            fun of(input: String): Packet = of(
                input.split("""((?<=[\[\],])|(?=[\[\],]))""".toRegex()).filter { it.isNotBlank() }.filter { it != "," }.iterator()
            )

            private fun of(items: Iterator<String>): Packet {
                val packets = mutableListOf<Packet>()
                while (items.hasNext()) {
                    when (val token = items.next()) {
                        "]" -> return PacketList(packets)
                        "[" -> packets.add(of(items))
                        else -> packets.add(PacketInt(token.toInt()))
                    }
                }
                return PacketList(packets)
            }
        }
    }

    class PacketInt(val value: Int) : Packet() {
        fun asList(): PacketList {
            return PacketList(listOf(this))
        }

        override fun toString(): String {
            return value.toString()
        }

        override fun compareTo(other: Packet): Int =
            when (other) {
                is PacketInt -> value.compareTo(other.value)
                is PacketList -> asList().compareTo(other)
            }
    }

    class PacketList(val subPackets: List<Packet>) : Packet() {

        override fun toString(): String {
            return "[$subPackets]"
        }

        override fun compareTo(other: Packet): Int =
            when (other) {
                is PacketInt -> compareTo(other.asList())
                is PacketList -> subPackets.zip(other.subPackets)
                    .map { it.first.compareTo(it.second) }
                    .firstOrNull { it != 0 } ?: subPackets.size.compareTo(other.subPackets.size)
            }
    }
}
