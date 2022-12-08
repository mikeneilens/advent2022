import Foundation

public enum InputErrors: Error {
    case invalidFilename
    case invalidFileData
}

func readFile(name: String) -> Array<String> {
    
    do {
    let contents = try String(contentsOfFile: name)
        return contents.split(separator:"\n").map{String($0)}
    } catch {
        print("Error reading file")
        return []
    }
}
