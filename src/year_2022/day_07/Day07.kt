package year_2022.day_07

import readInput
import java.util.Stack

sealed class IFile {
    data class Directory(
        val name: String,
        var files: MutableList<IFile> = mutableListOf()
    ): IFile() {
        fun totalSize(): Int {
            return files.sumOf { file ->
                when (file) {
                    is Directory -> file.totalSize()
                    is File -> file.size
                }
            }
        }

        override fun toString(): String {
            return "$name - ${totalSize()}"
        }
    }

    data class File(
        val fileName: String,
        val size: Int
    ): IFile() {
        override fun toString(): String {
            return "$fileName - $size"
        }
    }
}

object Day07 {

    /**
     * @return
     */
    fun solutionOne(text: List<String>): Int {
        val (_, directoryMap) = buildFileSystem(text)

        return directoryMap
            .map { it.value.totalSize() }
            .filter { it <= 100_000 }
            .sumOf { it }
    }

    /**
     * @return
     */
    fun solutionTwo(text: List<String>): Int {
        val (fileStructure, directoryMap) = buildFileSystem(text)

        val maxDiskSize = 70_000_000
        val requiredEmptySpace = 30_000_000
        val amountToClear = requiredEmptySpace - (maxDiskSize - fileStructure.totalSize())

        return directoryMap
            .map { it.value.totalSize() }
            .filter { it >= amountToClear }
            .minByOrNull { it }!!
    }

    private fun buildFileSystem(text: List<String>): Pair<IFile.Directory, Map<String, IFile.Directory>> {
        val rootDirectory = IFile.Directory("/")
        val mutableDirectoryMap = mutableMapOf<String, IFile.Directory>()
        mutableDirectoryMap["/"] = rootDirectory

        var currentDirectory = Stack<IFile.Directory>()
        currentDirectory.add(rootDirectory)

        text.forEach { line ->
            val split = line.split(" ")
            if (line.startsWith("$")) {
                val command = split[1]
                if (command == "cd") {
                    val directoryName = split[2]
                    if (directoryName == "..") {
                        currentDirectory.pop()
                    } else if (directoryName == "/") {
                        currentDirectory = Stack<IFile.Directory>()
                        currentDirectory.add(rootDirectory)
                    } else {
                        val fullPathName = "${currentDirectory.toTypedArray().joinToString("/") { it.name }}/$directoryName"

                        val exists = mutableDirectoryMap[fullPathName]
                        if (exists != null) {
                            currentDirectory.push(exists)
                        } else {
                            val directory = IFile.Directory(directoryName)
                            mutableDirectoryMap[fullPathName] = directory
                            currentDirectory.push(directory)
                        }
                    }
                } else if (command == "ls") {
                    // no-op
                } else {
                    println("$line - UNKNOWN COMMAND")
                }
            } else {
                if (split[0] == "dir") {
                    val directoryName = split[1]
                    val fullPathName = "${currentDirectory.toTypedArray().joinToString("/") { it.name }}/$directoryName"
                    val exists = mutableDirectoryMap[fullPathName]
                    if (exists != null) {
                        currentDirectory.push(exists)
                    } else {
                        val directory = IFile.Directory(directoryName)
                        mutableDirectoryMap[fullPathName] = directory
                        currentDirectory.peek().files.add(directory)
                    }
                } else {
                    val size = Integer.parseInt(split[0])
                    val fileName = split[1]
                    val file = IFile.File(
                        fileName = fileName,
                        size = size
                    )
                    currentDirectory.peek().files.add(file)
                }
            }
        }


        return rootDirectory to mutableDirectoryMap
    }

    private fun printFileSystem(directory: IFile.Directory, indentation: Int = 0) {
        var indentationString = ""
        for (i in 0 until indentation) {
            indentationString += "\t"
        }

        println("dir: $indentationString${directory.name}\t${directory.totalSize()}")
        directory.files.forEach { file ->
            when (file) {
                is IFile.Directory -> printFileSystem(file, indentation + 1)
                is IFile.File -> println("file: $indentationString\t${file.fileName}\t${file.size}")
            }
        }
    }
}

fun main() {
    val inputText = readInput("year_2022/day_07/Day07.txt")

    val solutionOne = Day07.solutionOne(inputText)
    println("Solution 1: $solutionOne")

    val solutionTwo = Day07.solutionTwo(inputText)
    println("Solution 2: $solutionTwo")
}