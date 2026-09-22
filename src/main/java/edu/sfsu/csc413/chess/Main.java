package edu.sfsu.csc413.chess;

import edu.sfsu.csc413.chess.model.Board;
import edu.sfsu.csc413.chess.model.Color;
import edu.sfsu.csc413.chess.model.Piece;
import edu.sfsu.csc413.chess.model.PieceType;
import edu.sfsu.csc413.chess.model.Position;
import edu.sfsu.csc413.chess.view.PieceGlyphs;
import edu.sfsu.csc413.chess.view.TextBoardRenderer;

/**
 * Entry point. Sets up the starting position and prints it.
 *
 * <p>The board does not know how chess starts, so the setup lives here for
 * now. M2 moves it into a factory.
 */
public final class Main {

    /** The back rank from the a-file to the h-file, the same for both sides. */
    private static final PieceType[] BACK_RANK = {
            PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
            PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
    };

    public static void main(String[] args) {
        Board board = new Board();
        setUpSide(board, Color.WHITE);
        setUpSide(board, Color.BLACK);
        System.out.println(new TextBoardRenderer(PieceGlyphs.LETTERS).render(board));
    }

    /** Places one side's sixteen pieces: the back rank, and a row of pawns in front of it. */
    private static void setUpSide(Board board, Color color) {
        // The back rank is where the other side's pawns promote.
        int backRank = color.opposite().promotionRank();
        int pawnRank = color.pawnStartRank();

        for (int file = 0; file < Position.BOARD_SIZE; file++) {
            board.place(new Position(file, backRank), new Piece(color, BACK_RANK[file]));
            board.place(new Position(file, pawnRank), new Piece(color, PieceType.PAWN));
        }
    }

    private Main() {
    }
}
