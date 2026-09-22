package edu.sfsu.csc413.chess.model;

/**
 * The two sides in a game of chess.
 *
 * <p>An enum rather than a boolean or an int. The compiler rejects any value
 * that is not WHITE or BLACK, and a switch over it can be checked for
 * covering both.
 *
 * <p>The four method signatures are fixed. Later milestones call them.
 * The bodies were written in M0b.
 */
public enum Color {
    WHITE,
    BLACK;

    /** The other side. WHITE gives BLACK and BLACK gives WHITE. */
    public Color opposite() {
        // A switch expression over an enum must list every constant, or the
        // code will not compile. That protects us if a case is ever missed.
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
        };
    }

    /**
     * Which way pawns of this color move, counted in ranks.
     * White goes up the board (+1). Black goes down (-1).
     */
    public int pawnDirection() {
        return switch (this) {
            case WHITE -> 1;
            case BLACK -> -1;
        };
    }

    /**
     * The rank pawns of this color start on, 0-based.
     * White pawns start on the second rank (index 1).
     * Black pawns start on the seventh rank (index 6).
     */
    public int pawnStartRank() {
        return switch (this) {
            case WHITE -> 1;
            case BLACK -> 6;
        };
    }

    /**
     * The rank a pawn of this color must reach to promote, 0-based.
     * White promotes on the eighth rank (index 7).
     * Black promotes on the first rank (index 0).
     */
    public int promotionRank() {
        return switch (this) {
            case WHITE -> 7;
            case BLACK -> 0;
        };
    }
}
