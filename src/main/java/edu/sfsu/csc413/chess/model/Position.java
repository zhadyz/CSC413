package edu.sfsu.csc413.chess.model;

/**
 * One square on the chess board.
 *
 * <p>Both numbers are 0-based. file goes left to right (a to h is 0 to 7).
 * rank goes bottom to top (1 to 8 is 0 to 7). So e2 is new Position(4, 1).
 *
 * <p>This is a record, not a class. Two positions with the same numbers are
 * the same square, so they must compare equal. A record does that for us and
 * also writes the constructor, file(), rank(), equals and hashCode.
 *
 * <p>The constructor rejects anything off the board. So if you are holding a
 * Position, it is a real square. Nothing else needs to check.
 */
public record Position(int file, int rank) {

    /** The board is 8 by 8, so files and ranks run 0 to 7. */
    public static final int BOARD_SIZE = 8;

    /**
     * Runs before the fields are set and throws if the square is off the
     * board. This is a compact constructor, so there is no parameter list.
     * The record fills in file and rank itself after this check passes.
     */
    public Position {
        if (!isOnBoard(file, rank)) {
            throw new IllegalArgumentException(
                    "Position off board: file=" + file + ", rank=" + rank);
        }
    }

    /**
     * True when both numbers are between 0 and 7.
     * Static so it can be called before a Position exists.
     */
    public static boolean isOnBoard(int file, int rank) {
        return file >= 0 && file < BOARD_SIZE && rank >= 0 && rank < BOARD_SIZE;
    }

    /**
     * Turns text like "e2" into a Position.
     * Only lowercase two-character input is accepted. Anything else throws
     * IllegalArgumentException with the bad input in the message.
     */
    public static Position parse(String algebraic) {
        // Check the shape first so we never index into a null or short string.
        if (algebraic == null || algebraic.length() != 2) {
            throw new IllegalArgumentException(
                    "Expected a two-character square like \"e2\" but got: " + algebraic);
        }

        // A char is really a number. 'e' - 'a' is 4 and '2' - '1' is 1.
        int file = algebraic.charAt(0) - 'a';
        int rank = algebraic.charAt(1) - '1';

        // Check here instead of letting the constructor throw, so the error
        // message shows the text the caller typed.
        if (!isOnBoard(file, rank)) {
            throw new IllegalArgumentException("Not a square on the board: " + algebraic);
        }
        return new Position(file, rank);
    }

    /**
     * The square this many files and ranks away, or null if that lands off
     * the board. Null is not an error here. Move generation steps off the
     * edge all the time and just skips those squares.
     */
    public Position offsetOrNull(int fileDelta, int rankDelta) {
        int newFile = file + fileDelta;
        int newRank = rank + rankDelta;
        if (!isOnBoard(newFile, newRank)) {
            return null;
        }
        return new Position(newFile, newRank);
    }

    /** This square as text, for example "e2". */
    @Override
    public String toString() {
        // The "" at the front turns this into string joining. Without it,
        // 'a' + file is number math and e2 would print as 151.
        return "" + (char) ('a' + file) + (char) ('1' + rank);
    }
}
