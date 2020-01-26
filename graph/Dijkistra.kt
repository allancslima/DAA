package br.ufal.ic.ufood

import java.util.*
import kotlin.Comparator

class Graph(private val numberOfVertices: Int) {

    /**
     * Each array position contains a linked list for neighbor vertices, which a vertex is represented by a pair
     * where first element is the vertex index and second element is the edge weight.
     */
    private val vertices: Array<LinkedList<Pair<Int, Int>>> by lazy {
        Array(numberOfVertices) { LinkedList<Pair<Int, Int>>() }
    }

    fun addEdge(vertex1: Int, vertex2: Int, weight: Int) {
        vertices[vertex1].add(Pair(vertex2, weight))
    }

    fun removeEdge(vertex1: Int, vertex2: Int) {
        vertices[vertex1].removeIf { it.first == vertex2 }
    }

    fun dijkistra(sourceIndex: Int): Array<Int> {
        val visited = Array(numberOfVertices) { false }
        val distances = Array(numberOfVertices) { Integer.MAX_VALUE }
        val queue = PriorityQueue<Pair<Int, Int>>(Comparator<Pair<Int, Int>> { p0, p1 ->
            p0.second.compareTo(p1.second)
        })
        val source = Pair(sourceIndex, 0)

        distances[source.first] = source.second
        queue.add(source)

        while (queue.isNotEmpty()) {
            val vertex = queue.remove()
            
            if (visited[vertex.first]) continue
            visited[vertex.first] = true

            val neighbors = vertices[vertex.first].iterator()

            while (neighbors.hasNext()) {
                val neighbor = neighbors.next()
                val distance = vertex.second + neighbor.second

                if (distance < distances[neighbor.first]) {
                    distances[neighbor.first] = distance
                    queue.add(neighbor)
                }
            }
        }
        return distances
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val graph = Graph(5)
            graph.addEdge(0, 2, 3)
            graph.addEdge(0, 4, 6)
            graph.addEdge(1, 0, 2)
            graph.addEdge(1, 2, 7)
            graph.addEdge(1, 3, 6)
            graph.addEdge(2, 4, 5)
            graph.addEdge(3, 4, 1)

            graph.dijkistra(1).forEachIndexed { index, distance ->
                println("To vertex $index, distance of $distance")
            }
        }
    }

}
