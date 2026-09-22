package edu.sfsu.csc413.chess.model;

/**
 * The six kinds of chess piece.
 *
 * <p>An enum because the set is closed and fixed. Each constant carries the
 * uppercase letter that FEN and algebraic notation use for it. The letter
 * lives here rather than in a view so every part of the program reads the
 * same one.
 */
public enum PieceType {
    PAWN('P'),
    KNIGHT('N'),
    BISHOP('B'),
    ROOK('R'),
    QUEEN('Q'),
    KING('K');

    private final char symbol;

    PieceType(char symbol) {
        this.symbol = symbol;
    }

    /** The uppercase letter for this type. The knight is N because the king already has K. */
    public char symbol() {
        return symbol;
    }

    /**
     * The type for a letter, in either case.
     * Throws IllegalArgumentException for a letter that names no piece.
     */
    public static PieceType fromSymbol(char letter) {
        char upper = Character.toUpperCase(letter);
        for (PieceType type : values()) {
            if (type.symbol == upper) {
                return type;
            }
        }
        throw new IllegalArgumentException("No piece type has the symbol: " + letter);
    }
}
