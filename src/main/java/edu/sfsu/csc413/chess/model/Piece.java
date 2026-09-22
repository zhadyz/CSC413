package edu.sfsu.csc413.chess.model;

/**
 * A chess piece: a color and a type.
 *
 * <p>A class rather than a record because M2 adds subclasses like Knight and
 * Rook, and a record cannot be extended. Every field is still final, so a
 * piece never changes once it is made. A piece knows what it is. It does not
 * know where it stands or how it moves.
 */
public class Piece {

    private final Color color;
    private final PieceType type;

    /** Color first, then type, the way you would say "white rook". */
    public Piece(Color color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    public Color color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

    /** This piece's letter: uppercase for white, lowercase for black. */
    public char symbol() {
        char letter = type.symbol();
        return color == Color.WHITE ? letter : Character.toLowerCase(letter);
    }

    @Override
    public String toString() {
        return String.valueOf(symbol());
    }
}
