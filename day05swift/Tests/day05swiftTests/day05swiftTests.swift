import XCTest
@testable import day05swift

final class day05swiftTests: XCTestCase {
        
    let sampleData = readFile(name:"/Users/michaelneilens/github/advent2022/day05swift/Tests/day05swiftTests/sampleInput.txt")
    func testExample() throws {
        XCTAssertEqual(day05swift().text, "Hello, World!")
    }
    func testCharOnLineReturnsCorrectly() throws {
        XCTAssertTrue("[N] [C]".char(at:0) == "N"  )
    }
    func testFindsLinesWithStackData() throws {
        XCTAssertEqual( sampleData.findStackData().count, 3)
    }
    func testFindsQtyData() throws {
        XCTAssertEqual( sampleData.findQtyData(), " 1   2   3")
    }
    func testFindsMoveData() throws {
        XCTAssertEqual( sampleData.findMoveData().count, 4)
    }
    func testFindNoOfStacks() throws {
        XCTAssertEqual( sampleData.noOfStacks(), 3)
    }
    func testCreatingEmptyStacks() throws {
        XCTAssertEqual( sampleData.createEmptyStacks(), [[],[],[]])
    }
    func testInitialisingStacks() throws {
        XCTAssertEqual(initialiseStacks(data: sampleData), [["Z", "N"], ["M", "C", "D"], ["P"]])
    }
    func testParseIntoCommands() throws {
        XCTAssertEqual(parseIntoCommands(data: sampleData), [Command(qty: 1, from: 1, to: 0), Command(qty: 3, from: 0, to: 2), Command(qty: 2, from: 1, to: 0), Command(qty: 1, from: 0, to: 1)])
    }
    func testPartOneWithSampleInput() throws {
        XCTAssertEqual(partOne(data: sampleData), "CMZ")
    }
    func testPartTwoWithSampleInput() throws {
        XCTAssertEqual(partTwo(data: sampleData), "MCD")
    }
    
}
