package edu.sfsu.csc413.chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A chess piece: a color, a type, and its movement rules.
 *
 * <p>A class rather than a record because M2 adds subclasses like Knight and
 * Rook, and a record cannot be extended. Every field is still final, so a
 * piece never changes once it is made. A piece knows how it moves. Its position stays on the board.
 */
public abstract class Piece {

    private final Color color;
    private final PieceType type;

    /** Color first, then type, the way you would say "white rook". */
    protected Piece(Color color, PieceType type) {
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

    /** Geometrically possible moves, without checking king safety. */
    public abstract List<Move> pseudoLegalMoves(Board board, Position from);

    /** By default, a piece attacks the squares it can move to. */
    public boolean attacks(Board board, Position from, Position target) {
        for (Move move : pseudoLegalMoves(board, from)) {
            if (move.to().equals(target)) {
                return true;
            }
        }
        return false;
    }

    /** Follow each direction until the edge or the first occupied square. */
    protected List<Move> slidingMoves(Board board, Position from, int[][] directions) {
        List<Move> moves = new ArrayList<>();
        for (int[] direction : directions) {
            Position to = from.offsetOrNull(direction[0], direction[1]);
            while (to != null) {
                Piece target = board.pieceAt(to);
                if (target == null) {
                    moves.add(Move.quiet(from, to, this));
                } else {
                    if (target.color() != color) {
                        moves.add(Move.capture(from, to, this, target));
                    }
                    break;
                }
                to = to.offsetOrNull(direction[0], direction[1]);
            }
        }
        return moves;
    }

    /** Try each offset once. A friendly piece blocks its destination. */
    protected List<Move> steppingMoves(Board board, Position from, int[][] offsets) {
        List<Move> moves = new ArrayList<>();
        for (int[] offset : offsets) {
            Position to = from.offsetOrNull(offset[0], offset[1]);
            if (to == null) {
                continue;
            }
            Piece target = board.pieceAt(to);
            if (target == null) {
                moves.add(Move.quiet(from, to, this));
            } else if (target.color() != color) {
                moves.add(Move.capture(from, to, this, target));
            }
        }
        return moves;
    }
}
