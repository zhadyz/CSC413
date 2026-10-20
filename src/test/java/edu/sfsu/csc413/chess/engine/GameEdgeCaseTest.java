package edu.sfsu.csc413.chess.engine;

import edu.sfsu.csc413.chess.factory.BoardFactory;
import edu.sfsu.csc413.chess.model.Color;
import edu.sfsu.csc413.chess.model.Move;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Extra checks for promotion undo and keeping the game's history private. */
class GameEdgeCaseTest {

    @Test
    void everyPromotionCanBePlayedAndUndone() {
        for (Color color : Color.values()) {
            String fen = color == Color.WHITE ? "1r6/P7/8/8/8/8/8/8" : "8/8/8/8/8/8/p7/1R6";
            Game game = new Game(BoardFactory.fromFen(fen), color);
            String before = game.board().toString();
            List<Move> moves = game.legalMoves();
            assertEquals(8, moves.size(), "four quiet and four capture promotions");
            for (Move move : moves) {
                assertTrue(move.isPromotion());
                game.play(move);
                assertEquals(move.promotesTo(), game.board().pieceAt(move.to()).type());
                assertEquals(color, game.board().pieceAt(move.to()).color());
                assertTrue(game.board().isEmpty(move.from()));
                assertEquals(move, game.undoLastMove().orElseThrow());
                assertEquals(before, game.board().toString());
                assertSame(move.moved(), game.board().pieceAt(move.from()));
                assertSame(move.captured(), game.board().pieceAt(move.to()));
                assertEquals(color, game.sideToMove());
                assertTrue(game.history().isEmpty());
            }
        }
    }

    @Test
    void historyIsAnUnmodifiableSnapshot() {
        Game game = new Game();
        List<Move> before = game.history();
        game.play(game.findLegalMove("E2E4").orElseThrow());
        List<Move> after = game.history();
        assertTrue(before.isEmpty());
        assertThrows(UnsupportedOperationException.class, after::clear);
        game.undoLastMove();
        assertEquals(1, after.size());
        assertTrue(game.history().isEmpty());
    }

    @Test
    void invalidLookupDoesNotChangeTheGame() {
        Game game = new Game();
        String before = game.board().toString();
        for (String notation : new String[] {null, "", "bad", "e2e5"}) {
            assertTrue(game.findLegalMove(notation).isEmpty());
        }
        assertThrows(IllegalArgumentException.class, () -> game.play(null));
        assertEquals(before, game.board().toString());
        assertEquals(Color.WHITE, game.sideToMove());
        assertTrue(game.history().isEmpty());
    }
}
