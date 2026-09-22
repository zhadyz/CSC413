package edu.sfsu.csc413.chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The 8 by 8 grid of squares.
 *
 * <p>The board stores which piece is on which square, and nothing else. It
 * does not know the rules and it does not draw itself.
 *
 * <p>The board has an array; it is not the array. The array is private so no
 * other class can reach past these methods, and it could be swapped for a
 * different layout without anything else changing.
 */
public class Board {

    /** Indexed [file][rank], both 0-based, the same order as Position. */
    private final Piece[][] squares =
            new Piece[Position.BOARD_SIZE][Position.BOARD_SIZE];

    /** An empty board. */
    public Board() {
    }

    /** The piece on this square, or null if the square is empty. */
    public Piece pieceAt(Position position) {
        return squares[position.file()][position.rank()];
    }

    public boolean isEmpty(Position position) {
        return pieceAt(position) == null;
    }

    /** Puts a piece on this square, replacing whatever was there. Null clears it. */
    public void place(Position position, Piece piece) {
        squares[position.file()][position.rank()] = piece;
    }

    /** Every square holding a piece of this color. */
    public List<Position> positionsOf(Color color) {
        List<Position> positions = new ArrayList<>();
        for (int file = 0; file < Position.BOARD_SIZE; file++) {
            for (int rank = 0; rank < Position.BOARD_SIZE; rank++) {
                Piece piece = squares[file][rank];
                if (piece != null && piece.color() == color) {
                    positions.add(new Position(file, rank));
                }
            }
        }
        return positions;
    }

    /**
     * The FEN placement field, for example 8/8/8/8/8/8/8/8 for an empty
     * board. Rank 8 comes first, ranks are separated by /, each piece is its
     * letter, and each run of empty squares becomes one digit. This is the
     * only place in Board that counts ranks downward.
     */
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (int rank = Position.BOARD_SIZE - 1; rank >= 0; rank--) {
            int emptyRun = 0;
            for (int file = 0; file < Position.BOARD_SIZE; file++) {
                Piece piece = squares[file][rank];
                if (piece == null) {
                    emptyRun++;
                } else {
                    if (emptyRun > 0) {
                        text.append(emptyRun);
                        emptyRun = 0;
                    }
                    text.append(piece.symbol());
                }
            }
            if (emptyRun > 0) {
                text.append(emptyRun);
            }
            if (rank > 0) {
                text.append('/');
            }
        }
        return text.toString();
    }
}
