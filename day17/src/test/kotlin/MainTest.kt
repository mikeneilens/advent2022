import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest: WordSpec( {
    "Part one" should ({
        "Can put fall square at position (1,-1) onto map with position(1,-1) is empty"{
            val map = mutableMapOf(Position(0,0) to "#", Position(1,0) to "#",Position(2,0) to "#", )
            val fallingSquare = FallingShape(Position(1,-1), squareShape)
            fallingSquare.canBePlacedOnMap(map) shouldBe true
        }
        "Can put falling vline at position (1,0) onto map when position(1,0) is empty, but can't put a square there"{
            val map = mutableMapOf(Position(0,0) to "#", Position(2,0) to "#", )
            val fallingLine = FallingShape(Position(1,0), vLineShape)
            val fallingSquare = FallingShape(Position(1,0), squareShape)
            fallingLine.canBePlacedOnMap(map) shouldBe true
            fallingSquare.canBePlacedOnMap(map) shouldBe false
        }
        "applying first jet moves shape to 4,-3" {
            val game = setUpGame(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
            val updatedGame = game.applyJet()
            updatedGame.jetNdx shouldBe 0
            updatedGame.fallingShape.p shouldBe Position(4,-4)
        }
        "applying first jet and dropping shape 1 moves shape to 4,-2" {
            val game = setUpGame(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
            val updatedGame = game.applyJet().dropShape()
            updatedGame.jetNdx shouldBe 0
            updatedGame.fallingShape.p shouldBe Position(4,-3)
        }
        "applying first jet and dropping shape 1 and applying 2nd jet causes shape not to move" {
            val game = setUpGame(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
            val updatedGame = game.applyJet().dropShape().applyJet()
            updatedGame.jetNdx shouldBe 1
            updatedGame.fallingShape.p shouldBe Position(4,-3)
        }
        "process a game until the shape stops moving" {
            val game = setUpGame(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
            val processedGame = game.moveShape()
            processedGame.fallingShape.p shouldBe Position(3,-1)
        }
        "processing one shape" {
            val game = setUpGame(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
            game.processShapes(1)
            game.tunnelMap.rowAsString(-3) shouldBe "......."
            game.tunnelMap.rowAsString(-2) shouldBe "......."
            game.tunnelMap.rowAsString(-1) shouldBe "..####."
        }
        "processing two shapes" {
            val game = setUpGame(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
            game.processShapes(2)
            game.tunnelMap.rowAsString(-4) shouldBe "...#..."
            game.tunnelMap.rowAsString(-3) shouldBe "..###.."
            game.tunnelMap.rowAsString(-2) shouldBe "...#..."
            game.tunnelMap.rowAsString(-1) shouldBe "..####."
        }
        "processing three shapes" {
            val game = setUpGame(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
            game.processShapes(3)
            game.tunnelMap.rowAsString(-6) shouldBe "..#...."
            game.tunnelMap.rowAsString(-5) shouldBe "..#...."
            game.tunnelMap.rowAsString(-4) shouldBe "####..."
            game.tunnelMap.rowAsString(-3) shouldBe "..###.."
            game.tunnelMap.rowAsString(-2) shouldBe "...#..."
            game.tunnelMap.rowAsString(-1) shouldBe "..####."
        }
        "part one using sample input" {
              val tunnel = partOne(sampleInput)
              tunnel.tunnelMap.keys.filter { it.x in 1..7 }.minOf { it.y } shouldBe -3068
        }
        "part one using puzzle input" {
              val tunnel = partOne(puzzleInput)
              tunnel.tunnelMap.keys.filter { it.x in 1..7 }.minOf { it.y } shouldBe -3202
        }
        "processing 2022 shapes using puzzle input" {
//            val game = setUpGame(puzzleInput)
//            val gameAfterProcessing = game.processShapes(2022)
//            gameAfterProcessing.map.keys.filter { it.x in 1..7 }.minOf { it.y } shouldBe -3202
        }
    })
    "Part two" should ({
        "part two find repeating patterns" {
//            val result = partTwo(sampleInput)
//            println("result is $result")
        }
        "part two find repeating patterns with puzzle input" {
            val result = partTwo(puzzleInput)
//            println("result is $result")
        }
    })

})