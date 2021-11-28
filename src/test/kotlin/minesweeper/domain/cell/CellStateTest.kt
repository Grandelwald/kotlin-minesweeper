package minesweeper.domain.cell

import minesweeper.domain.position.Position
import minesweeper.domain.position.Positions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CellStateTest {

    @ParameterizedTest
    @CsvSource("1, 4, 1", "2, 2, 3", "2, 3, 2", "4, 1, 0")
    fun `한 셀에 대하여 인접해있는 지뢰 카운팅`(x: Int, y: Int, count: Int) {
        // given
        val position = Position.of(x, y)
        val minePositions = Positions(listOf(Position(1, 1), Position(1, 2), Position(1, 3)))
        val allPositions = Positions(listOf(position) + minePositions)
        position.updateAdjacentPositions(allPositions)
        val cellState = CellState.from(position, minePositions)

        // when
        val adjacentMineCount = cellState.getValue()

        // then
        assertThat(adjacentMineCount).isEqualTo(count)
    }

    @Test
    fun `해당 셀은 지뢰가 아니다`() {
        // given
        val position = Position.of(1, 4)
        val minePositions = Positions(listOf(Position(1, 1), Position(1, 2), Position(1, 3)))
        val allPositions = Positions(listOf(position) + minePositions)
        position.updateAdjacentPositions(allPositions)
        val cellState = CellState.from(position, minePositions)

        // when
        val cellType = cellState.cellType

        // then
        assertThat(cellType).isEqualTo(CellType.NOT_MINE)
    }

    @Test
    fun `해당 셀은 지뢰다`() {
        // given
        val position = Position.of(1, 1)
        val minePositions = Positions(listOf(Position(1, 1), Position(1, 2), Position(1, 3)))
        val allPositions = Positions(listOf(position) + minePositions)
        position.updateAdjacentPositions(allPositions)
        val cellState = CellState.from(position, minePositions)

        // when
        val cellType = cellState.cellType

        // then
        assertThat(cellType).isEqualTo(CellType.IS_MINE)
    }
}