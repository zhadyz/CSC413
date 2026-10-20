package edu.sfsu.csc413.chess;

import edu.sfsu.csc413.chess.factory.BoardFactory;
import edu.sfsu.csc413.chess.view.PieceGlyphs;
import edu.sfsu.csc413.chess.view.TextBoardRenderer;

/** Entry point. Prints the standard opening position. */
public final class Main {

    public static void main(String[] args) {
        System.out.println(new TextBoardRenderer(PieceGlyphs.LETTERS).render(BoardFactory.standard()));
    }

    private Main() {
    }
}
