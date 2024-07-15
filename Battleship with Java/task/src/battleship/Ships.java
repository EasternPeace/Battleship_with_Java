package battleship;

enum Ships {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    final int capacity;
    final String commonName;

    Ships(int capacity, String commonName) {
        this.capacity = capacity;
        this.commonName = commonName;
    }
}
