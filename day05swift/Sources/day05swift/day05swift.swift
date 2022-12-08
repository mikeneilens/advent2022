public struct day05swift {
    public private(set) var text = "Hello, World!"

    public init() {
    }
}

typealias InputData = Array<String>
typealias Stack = Array<Character>
typealias Stacks = Array<Stack>

struct Command: Equatable{
    let qty:Int
    let from:Int
    let to:Int
}

func initialiseStacks(data: InputData) -> Stacks {
    data.findStackData().reduce(data.createEmptyStacks()){ stacks, line in line.addToStacks(stacks: stacks)} }

func parseIntoCommands(data: InputData) -> Array<Command> { data.findMoveData().map{$0.toCommand() } }

extension InputData {
    func findStackData() -> Array<String> {
        Array(filter{ $0.starts(with:" ") || $0.starts(with: "[")}.reversed().dropFirst())
    }
    func findQtyData() -> String { first{$0.starts(with:" 1")} ?? "" }
    
    func findMoveData() -> Array<String> { filter{$0.starts(with: "move")} }
    
    func noOfStacks() -> Int { Int(String(findQtyData().last ?? "0"  ).trimmingCharacters(in: .whitespaces)) ?? 0 }
    
    func createEmptyStacks()-> Array<Stack> { [Stack](repeating: [], count: noOfStacks()) }
}

extension String {
    public func char(at i: Int) -> Character { self[index(startIndex, offsetBy: i * 4 + 1)]}

    func addToStacks(stacks: Stacks) -> Stacks {
        var index = -1
        return stacks.map{ stack in
            index += 1;
            return addLineToStack(index: index, stack: stack)}
    }
    
    func addLineToStack(index: Int, stack: Stack)-> Stack {
        if (char(at: index) != " ") {
            return stack + [char(at: index)]
        } else {
            return stack
        }
    }
    
    func toCommand() -> Command {
        let components = split(separator: " ")
        return Command(qty: Int(components[1]) ?? 0,from: (Int(components[3])  ?? 1) - 1, to: (Int(components[5]) ?? 1) - 1)
    }
}

func crateMover9000(stacks: Stacks, command: Command) -> Stacks {
    stacks.mapIndexed{index, stack in stacks.move(index: index, stack: stack, command: command, shouldReverseStack: true) }
}

func crateMover9001(stacks: Stacks, command: Command) -> Stacks {
    stacks.mapIndexed{index,  stack in stacks.move(index: index, stack: stack, command: command, shouldReverseStack: false) }
}

extension Stacks {
    
    func process(commands: Array<Command>, mover: (Stacks, Command)-> Stacks) -> Stacks {
        commands.reduce(self){ stacks, command in mover(stacks, command)}
    }
        
    func move(index:Int, stack:Stack, command: Command, shouldReverseStack:Bool) ->Stack {
        switch(true) {
        case (command.from == index) :
            return stack.dropLast(command.qty)
        case ((command.to == index) && shouldReverseStack) :
            return  stack + self[command.from].suffix(command.qty).reversed()
        case (command.to == index) :
            return stack + self[command.from].suffix(command.qty)
        default : return stack
        }
    }
}

func partOne(data: InputData) -> String {
    let commands = parseIntoCommands(data: data)
    let stacks = initialiseStacks(data: data)
    return String( stacks.process(commands: commands, mover: crateMover9000).map{$0.last ?? Character("")})
}

func partTwo(data: InputData) -> String {
    let commands = parseIntoCommands(data: data)
    let stacks = initialiseStacks(data: data)
    return String( stacks.process(commands: commands, mover: crateMover9001).map{$0.last ?? Character("")})
}

extension Collection {
    func mapIndexed<T>(_ transform: (Int, Self.Element) throws -> T) rethrows -> [T] {
        var index = -1
        return try map{
            index += 1
            return try transform(index, $0)
        }
    }
}
