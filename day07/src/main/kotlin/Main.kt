fun helloWorld() = "hello world"

data class Directory(val name:String, val parent:Directory?, val files:MutableList<File>, val children:MutableList<Directory>) {

    val size:Int by lazy { files.sumOf { it.size } + children.sumOf { it.size } }

    fun sizeSmallerThan(max:Int):Int = allDirectories().filter{it.size <= max}.sumOf { it.size }

    fun directoryToDelete(spaceNeeded: Int) = allDirectories().filter{it.size >= spaceNeeded}.sortedBy { it.size }.first()

    fun allDirectories():List<Directory> = children.flatMap { directory -> directory.allDirectories()} + this

    override fun toString(): String {
        return "name=$name, files=${files.size} children=${children.size}"
    }
}

data class File(val name:String, val size:Int)

fun partOne(data:List<String>):Int {
    return parseIntoDirectory(data).sizeSmallerThan(100000)
}

fun parseIntoDirectory(data:List<String>):Directory {
    val root = Directory("root", null, mutableListOf(), mutableListOf())
    var currentDirectory = root
    var directoryBeingListed = root
    data.forEach{ line ->
        when(true) {
            (line.startsWith("$ cd /")) -> currentDirectory = root
            (line.startsWith("$ cd ..")) -> currentDirectory = currentDirectory.parent ?: root
            (line.startsWith("$ cd")) -> {
                val directoryName = line.split(" ")[2]
                if (currentDirectory.children.none { it.name == directoryName }) {
                    currentDirectory.children.add(
                        Directory(directoryName, parent = currentDirectory, mutableListOf(), mutableListOf())
                    )
                }
                currentDirectory  = currentDirectory.children.first { it.name == directoryName }
            }
            (line.startsWith("dir")) -> {
                val listDirectoryName = line.split(" ").last()
                if (currentDirectory.children.none { it.name == listDirectoryName }) {
                    currentDirectory.children.add(
                        Directory(listDirectoryName, parent = currentDirectory, mutableListOf(), mutableListOf())
                    )
                }
            }
            (line.startsWith("$ ls")) -> directoryBeingListed = currentDirectory
            else -> {
                val size = line.split(" ")[0].toInt()
                val fileName = line.split(" ")[1]
                directoryBeingListed.files.add(File(fileName, size))
            }
        }
    }
    return root
}

fun partTwo(data:List<String>):Directory {
    val directory = parseIntoDirectory(data)
    val spaceNeeded = directory.size - 40000000
    return directory.directoryToDelete(spaceNeeded)
}