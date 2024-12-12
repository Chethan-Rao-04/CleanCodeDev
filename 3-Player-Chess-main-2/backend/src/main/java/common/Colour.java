package common;

/**
 * Enum class containing the colors of different players.
 * Also has a method for String representation to use in web app.
 */
public enum Colour {

    WHITE, BLACK;

    public Colour next() {
        if (this == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }
    }

    @Override
    public String toString() {
        if (this == WHITE) {
            return "W";
        } else {
            return "B";
        }
    }
}

