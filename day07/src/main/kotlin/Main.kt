data class Directory(val name:String, val parent:Directory?, val files:MutableList<File>, val children:MutableList<Directory>) {

    val size:Int by lazy { files.sumOf { it.size } + children.sumOf { it.size } }

    fun allDirectories():List<Directory> = children.flatMap { directory -> directory.allDirectories()} + this

    fun totalSizeOfDirectoriesSmallerThan(max:Int) = allDirectories().filter{it.size <= max}.sumOf { it.size }

    fun directoryToDelete(spaceNeeded: Int) = allDirectories().filter { it.size >= spaceNeeded }.minByOrNull { it.size }!!

    fun addChildDirectory(name: String):Directory {
        if (children.none{ it.name == name }) {
            children.add(Directory(name, this, mutableListOf(), mutableListOf()))
        }
        return this
    }
    fun addFile(fileName: String, fileSize: Int):Directory {
        files.add(File(fileName, fileSize))
        return this
    }

    override fun toString() = "name=$name, files=${files.size} children=${children.size}"
}

data class File(val name:String, val size:Int)

typealias Processor = (line: String, directory: Directory, root:Directory) -> Directory

val changeToRoot:Processor = { _, _, root -> root}

val moveOutOneLevel:Processor = {_, directory, root -> directory.parent ?: root}

val listDirectory:Processor = {_, directory,_ -> directory}

val addFileToDirectory:Processor = { line: String, directory: Directory, _:Directory -> directory.addFile(line.fileName(), line.fileSize()) }

val addDirectory:Processor = { line: String, directory: Directory, _:Directory -> directory.addChildDirectory(line.directoryName()) }

val changeDirectory:Processor  = { line: String, directory: Directory, _:Directory ->
    directory.addChildDirectory(line.directoryName())
    directory.children.first { it.name == line.directoryName() }
}

val processInstruction = mapOf(
    """^\$ cd /""".toRegex() to changeToRoot,
    """^\$ cd ..""".toRegex() to moveOutOneLevel,
    """^\$ cd [0-9A-Za-z.]+""".toRegex() to changeDirectory,
    """^\$ ls""".toRegex() to listDirectory,
    """^dir [0-9A-Za-z.]+""".toRegex() to addDirectory
)

fun parseIntoDirectory(data:List<String>):Directory {
    val root = Directory("root", null, mutableListOf(), mutableListOf())
    data.fold(root) { directory, line ->
        val processor = processInstruction.filter{ it.key matches line }.values.firstOrNull() ?: addFileToDirectory
        processor(line, directory, root)
    }
    return root
}

fun partOne(data:List<String>):Int {
    return parseIntoDirectory(data).totalSizeOfDirectoriesSmallerThan(100000)
}

fun partTwo(data:List<String>):Directory {
    val directory = parseIntoDirectory(data)
    val spaceNeeded = directory.size - 40000000
    return directory.directoryToDelete(spaceNeeded)
}

fun String.fileSize() = split(" ")[0].toInt()
fun String.fileName() = split(" ")[1]
fun String.directoryName() = split(" ").last()
