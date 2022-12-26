val operMap = mapOf<String, (Long, Long)->Long>(
    "+" to { v1, v2 -> v1 + v2 },
    "-" to { v1, v2 -> v1 - v2 },
    "*" to { v1, v2 -> v1 * v2 },
    "/" to { v1, v2 -> v1 / v2 }
    )

sealed class Job {
    class LoneNumber(val value: Long) : Job()
    class MathOperation(val node1: Node, val node2: Node, val operation: (Long, Long) -> Long) : Job()
    object None : Job()
}

class Node(var job:Job) {
    fun total():Long {
        if (job is Job.LoneNumber) return (job as Job.LoneNumber).value
        else {
            val mathOperation = (job as Job.MathOperation)
            return mathOperation.operation( mathOperation.node1.total(), mathOperation.node2.total())
        }
    }
}

fun List<String>.parse():Map<String, Node> {
    val nodeRegister = registerNodes()
    forEach { line ->
        val node = nodeRegister.getValue(line.jobName())
        if (line.lineIsANumber()) {
            node.job = Job.LoneNumber(line.param(1).toLong())
        } else {
            node.job = line.getMathOperations(nodeRegister, node)
        }
    }
    return nodeRegister
}

fun List<String>.registerNodes(nodeRegister: MutableMap<String, Node> = mutableMapOf()):Map<String, Node> {
    forEach { line -> nodeRegister[line.jobName()] = Node(Job.None) }
    return nodeRegister
}

fun String.getMathOperations(nodeRegister: Map<String, Node>, node: Node) =
    Job.MathOperation(getNodeForParameter(1, nodeRegister), getNodeForParameter(3, nodeRegister), oper())

fun String.getNodeForParameter(n:Int, nodeRegister:Map<String, Node>) = nodeRegister.getValue(param(n))

fun String.jobName() = split(":")[0]
fun String.lineIsANumber() = param(1).toLongOrNull() != null
fun String.param(n:Int) = split(" ")[n]
fun String.oper() = operMap.getValue( split(" ")[2])

fun partOne(data:List<String>):Long {
    val nodeMap = data.parse()
    val root = nodeMap.getValue("root")
    return root.total()
}

fun partTwo(data:List<String>):Long {
    val nodes = puzzleInput.parse()
    val zfhnTotal = nodes.getValue("zfhn").total()
    val humnNode = nodes.getValue("humn")
    val jgtbNode =  nodes.getValue("jgtb")
    return findNumber(zfhnTotal, jgtbNode, humnNode, 3757270000000L,3757272500000L)
}

fun findNumber(target:Long, otherNode:Node, human:Node, min:Long, max:Long):Long {
    val testValue = min + (max - min)/2
    human.job = Job.LoneNumber(testValue)
    val total = otherNode.total()
    if (target == total) return testValue
    if (target < total)
        return findNumber(target, otherNode, human, testValue, max )
    else
        return findNumber(target, otherNode, human, min, testValue )
}