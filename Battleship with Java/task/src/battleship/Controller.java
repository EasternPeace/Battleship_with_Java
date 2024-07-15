package battleship;

import battleship.exceptions.AdjacentShipException;
import battleship.exceptions.WrongShipCoordinates;

import java.util.Scanner;

class Controller {
    Scanner scanner = new Scanner(System.in);
    Status status = Status.INIT;
    Player firstPlayer = new Player(1, new BattleField());
    Player secondPlayer = new Player(2, new BattleField());
    boolean firstTurn = true;


    void play() {
        start(firstPlayer);
        start(secondPlayer);
        while (status != Status.FINISHED) {
            if (firstTurn) {
                performTurn(firstPlayer);
            } else {
                performTurn(secondPlayer);
            }
        }
    }

    void handTurn() {
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        firstTurn = !firstTurn;
    }

    Player getAnotherPlayer() {
        if (firstTurn) {
            return secondPlayer;
        } else {
            return firstPlayer;
        }
    }

    void performTurn(Player player) {
        printStateForPlayer(player);
        System.out.printf("Player %d, it's your turn:", player.id);
        Cell target = null;
        while (target == null) {
            try {
                String input = takeUserInput();
                target = getAnotherPlayer().field.getCell(transformInputToCell(input));
            } catch (IllegalArgumentException e) {
                System.out.println("Error! You entered the wrong coordinates! Try again: ");
            }
        }
        try {
            switch (target.state) {
                case WAVE:
                    getAnotherPlayer().field.getCell(target).setState(CellState.MISS);
                    System.out.println("You missed!");
                    break;
                case SHIP:
                    getAnotherPlayer().field.getCell(target).setState(CellState.HIT_SHIP);
                    if (getAnotherPlayer().field.isThereShips()) {
                        if (getAnotherPlayer().field.validateAdjacent(target)) {
                            System.out.println("You sank a ship! Specify a new target:");
                        } else {
                            System.out.println("You hit a ship! Try again:");
                        }
                    } else {
                        status = Status.FINISHED;
                        break;
                    }
                case MISS:
                    System.out.println("You missed!");
                    break;
                case HIT_SHIP:
                    System.out.println("Again?");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error! Unhandled");
        }
        if (status == Status.FINISHED) {
            System.out.println("You sank the last ship. You won. Congratulations!");
        }
        handTurn();
    }

    void printStateForPlayer(Player player) {
        if (firstTurn) {
            secondPlayer.field.printFogged();
        } else {
            firstPlayer.field.printFogged();
        }
        separateField();
        player.field.print();
    }

    void separateField() {
        System.out.println("---------------------");
    }

    void start(Player player) {
        System.out.printf("Player %d, place your ships on the game field\n", player.id);
        player.field.print();

        for (Ships ship : Ships.values()) {
            Cell[] cellSequence;
            askForCoordinates(ship);
            while (true) {
                String input = takeUserInput();
                try {
                    cellSequence = processUserInput(input);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    continue;
                }
                try {
                    player.field.validateShipPlace(cellSequence, ship);
                } catch (WrongShipCoordinates | AdjacentShipException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                break;
            }
            for (Cell cell : cellSequence) {
                player.field.getCell(cell).setState(CellState.SHIP);
            }
            player.field.print();
        }
        handTurn();
    }

    private void askForCoordinates(Ships ship) {
        System.out.printf("Enter the coordinates of the %s (%d cells): ", ship.commonName, ship.capacity);
    }

    private String takeUserInput() {
        return scanner.nextLine();
    }


    private Cell transformInputToCell(String userInput) {
        String[] cell = parseCoordinate(userInput);
        return new Cell(cell[0].charAt(0), Integer.parseInt(cell[1]));
    }

    private Cell[] processUserInput(String userInput) {
        String[] coordinates = userInput.split(" ");
        String[] start = parseCoordinate(coordinates[0]);
        String[] end = parseCoordinate(coordinates[1]);

        Cell startCell = new Cell(start[0].charAt(0), Integer.parseInt(start[1]));
        Cell endCell = new Cell(end[0].charAt(0), Integer.parseInt(end[1]));

        return Cell.calculateCellSequence(startCell, endCell);
    }


    public String[] parseCoordinate(String coordinate) {
        String letter = coordinate.replaceAll("[^A-Za-z]", "");
        String number = coordinate.replaceAll("[^0-9]", "");
        return new String[]{letter, number};
    }
}
