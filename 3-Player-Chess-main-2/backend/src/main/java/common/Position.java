package common;

/**
 * Class containing the positions enums of the chess board
 * also, methods to access the properties.
 **/
public enum Position {
  A1(Colour.WHITE, 0, 0), A2(Colour.WHITE, 1, 0), A3(Colour.WHITE, 2, 0), A4(Colour.WHITE, 3, 0),
  A5(Colour.WHITE, 4, 0), A6(Colour.WHITE, 5, 0), A7(Colour.WHITE, 6, 0), A8(Colour.WHITE, 7, 0),
  B1(Colour.WHITE, 0, 1), B2(Colour.WHITE, 1, 1), B3(Colour.WHITE, 2, 1), B4(Colour.WHITE, 3, 1),
  B5(Colour.WHITE, 4, 1), B6(Colour.WHITE, 5, 1), B7(Colour.WHITE, 6, 1), B8(Colour.WHITE, 7, 1),
  C1(Colour.WHITE, 0, 2), C2(Colour.WHITE, 1, 2), C3(Colour.WHITE, 2, 2), C4(Colour.WHITE, 3, 2),
  C5(Colour.WHITE, 4, 2), C6(Colour.WHITE, 5, 2), C7(Colour.WHITE, 6, 2), C8(Colour.WHITE, 7, 2),
  D1(Colour.WHITE, 0, 3), D2(Colour.WHITE, 1, 3), D3(Colour.WHITE, 2, 3), D4(Colour.WHITE, 3, 3),
  D5(Colour.WHITE, 4, 3), D6(Colour.WHITE, 5, 3), D7(Colour.WHITE, 6, 3), D8(Colour.WHITE, 7, 3),
  E1(Colour.WHITE, 0, 4), E2(Colour.WHITE, 1, 4), E3(Colour.WHITE, 2, 4), E4(Colour.WHITE, 3, 4),
  E5(Colour.WHITE, 4, 4), E6(Colour.WHITE, 5, 4), E7(Colour.WHITE, 6, 4), E8(Colour.WHITE, 7, 4),
  F1(Colour.WHITE, 0, 5), F2(Colour.WHITE, 1, 5), F3(Colour.WHITE, 2, 5), F4(Colour.WHITE, 3, 5),
  F5(Colour.WHITE, 4, 5), F6(Colour.WHITE, 5, 5), F7(Colour.WHITE, 6, 5), F8(Colour.WHITE, 7, 5),
  G1(Colour.WHITE, 0, 6), G2(Colour.WHITE, 1, 6), G3(Colour.WHITE, 2, 6), G4(Colour.WHITE, 3, 6),
  G5(Colour.WHITE, 4, 6), G6(Colour.WHITE, 5, 6), G7(Colour.WHITE, 6, 6), G8(Colour.WHITE, 7, 6),
  H1(Colour.WHITE, 0, 7), H2(Colour.WHITE, 1, 7), H3(Colour.WHITE, 2, 7), H4(Colour.WHITE, 3, 7),
  H5(Colour.WHITE, 4, 7), H6(Colour.WHITE, 5, 7), H7(Colour.WHITE, 6, 7), H8(Colour.WHITE, 7, 7),

  A8B(Colour.BLACK, 0, 0), A7B(Colour.BLACK, 1, 0), A6B(Colour.BLACK, 2, 0), A5B(Colour.BLACK, 3, 0),
  A4B(Colour.BLACK, 4, 0), A3B(Colour.BLACK, 5, 0), A2B(Colour.BLACK, 6, 0), A1B(Colour.BLACK, 7, 0),
  B8B(Colour.BLACK, 0, 1), B7B(Colour.BLACK, 1, 1), B6B(Colour.BLACK, 2, 1), B5B(Colour.BLACK, 3, 1),
  B4B(Colour.BLACK, 4, 1), B3B(Colour.BLACK, 5, 1), B2B(Colour.BLACK, 6, 1), B1B(Colour.BLACK, 7, 1),
  C8B(Colour.BLACK, 0, 2), C7B(Colour.BLACK, 1, 2), C6B(Colour.BLACK, 2, 2), C5B(Colour.BLACK, 3, 2),
  C4B(Colour.BLACK, 4, 2), C3B(Colour.BLACK, 5, 2), C2B(Colour.BLACK, 6, 2), C1B(Colour.BLACK, 7, 2),
  D8B(Colour.BLACK, 0, 3), D7B(Colour.BLACK, 1, 3), D6B(Colour.BLACK, 2, 3), D5B(Colour.BLACK, 3, 3),
  D4B(Colour.BLACK, 4, 3), D3B(Colour.BLACK, 5, 3), D2B(Colour.BLACK, 6, 3), D1B(Colour.BLACK, 7, 3),
  E8B(Colour.BLACK, 0, 4), E7B(Colour.BLACK, 1, 4), E6B(Colour.BLACK, 2, 4), E5B(Colour.BLACK, 3, 4),
  E4B(Colour.BLACK, 4, 4), E3B(Colour.BLACK, 5, 4), E2B(Colour.BLACK, 6, 4), E1B(Colour.BLACK, 7, 4),
  F8B(Colour.BLACK, 0, 5), F7B(Colour.BLACK, 1, 5), F6B(Colour.BLACK, 2, 5), F5B(Colour.BLACK, 3, 5),
  F4B(Colour.BLACK, 4, 5), F3B(Colour.BLACK, 5, 5), F2B(Colour.BLACK, 6, 5), F1B(Colour.BLACK, 7, 5),
  G8B(Colour.BLACK, 0, 6), G7B(Colour.BLACK, 1, 6), G6B(Colour.BLACK, 2, 6), G5B(Colour.BLACK, 3, 6),
  G4B(Colour.BLACK, 4, 6), G3B(Colour.BLACK, 5, 6), G2B(Colour.BLACK, 6, 6), G1B(Colour.BLACK, 7, 6),
  H8B(Colour.BLACK, 0, 7), H7B(Colour.BLACK, 1, 7), H6B(Colour.BLACK, 2, 7), H5B(Colour.BLACK, 3, 7),
  H4B(Colour.BLACK, 4, 7), H3B(Colour.BLACK, 5, 7), H2B(Colour.BLACK, 6, 7), H1B(Colour.BLACK, 7, 7);

  private final Colour colour;
  private final int row;
  private final int column;

  Position(Colour colour, int row, int column) {
    this.colour = colour;
    this.row = row;
    this.column = column;
  }

  public Colour getColour() {
    return colour;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  @Override
  public String toString() {
    return colour.toString() + getColumnChar(column) + (row + 1);
  }

  public static Position get(Colour colour, int row, int column) throws InvalidPositionException {
    for (Position position : Position.values()) {
      if (position.colour == colour && position.row == row && position.column == column) {
        return position;
      }
    }
    throw new InvalidPositionException("No such position.");
  }

  private char getColumnChar(int column) {
    switch (column) {
      case 0: return 'a';
      case 1: return 'b';
      case 2: return 'c';
      case 3: return 'd';
      case 4: return 'e';
      case 5: return 'f';
      case 6: return 'g';
      case 7: return 'h';
      default: return '\0';
    }
  }

  public Position neighbour(Direction direction) throws InvalidPositionException {
    int newRow = this.row;
    int newColumn = this.column;

    switch (direction) {
      case FORWARD:
        newRow += (this.colour == Colour.WHITE) ? 1 : -1;
        break;
      case BACKWARD:
        newRow += (this.colour == Colour.WHITE) ? -1 : 1;
        break;
      case LEFT:
        newColumn -= 1;
        break;
      case RIGHT:
        newColumn += 1;
        break;
    }

    return Position.get(this.colour, newRow, newColumn);
  }
}