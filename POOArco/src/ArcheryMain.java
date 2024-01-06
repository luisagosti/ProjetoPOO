import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Archer {
    String name;
    int totalPoints;
    String category;

    Archer(String name, String category) {
        this.name = name;
        this.category = category;
        this.totalPoints = 0;
    }

    // Method to simulate shooting arrows and calculating scores
    void shootArrow(int arrowNumber) {
        int score = (int) (Math.random() * 11); // Generate a random score between 0 and 10
        System.out.println(name + " - " + (arrowNumber + 1) + "º shot: " + score);
        totalPoints += score;
    }
}

class Competition {
    String season;
    String location;
    String category;
    List<Archer> archers;

    Competition(String season) {
        this.season = season;
        this.archers = new ArrayList<>();
    }

    void addArcher(Archer archer) {
        archers.add(archer);
    }

    void setCategory(String category) {
        this.category = category;
    }

    void conductCompetition() {
        int arrowsPerSeries = 3; // Number of arrows per series

        if (season.equalsIgnoreCase("Winter") && category.equalsIgnoreCase("provas normais")) {
            // Winter with "provas normais"
            conductNormalCompetition(arrowsPerSeries);
        } else if (season.equalsIgnoreCase("Winter") && category.equalsIgnoreCase("provas-torneio")) {
            // Winter with "provas-torneio"
            conductTournamentCompetition(arrowsPerSeries);
        } else {
            // Default behavior for other seasons or categories
            for (int arrowNumber = 0; arrowNumber < arrowsPerSeries; arrowNumber++) {
                for (Archer archer : archers) {
                    archer.shootArrow(arrowNumber);
                }
            }
        }
    }

    private void conductNormalCompetition(int arrowsPerSeries) {
        int numberOfSeries = 10; // Number of series per archer

        for (int seriesNumber = 0; seriesNumber < numberOfSeries; seriesNumber++) {
            System.out.println("================================");
            System.out.println("           SERIES " + (seriesNumber + 1));
            System.out.println("================================");

            for (int arrowNumber = 0; arrowNumber < arrowsPerSeries; arrowNumber++) {
                for (Archer archer : archers) {
                    archer.shootArrow(arrowNumber);
                }
            }
        }
    }

    private void conductTournamentCompetition(int arrowsPerSeries) {
        // Check if the number of archers is exactly 16, otherwise return
        if (archers.size() != 16) {
            System.out.println("Error: Winter provas-torneio must have exactly 16 archers.");
            return;
        }

        // Sort archers based on total points
        archers.sort((a1, a2) -> Integer.compare(a2.totalPoints, a1.totalPoints));

        // Eightfinals: Top 16 archers
        List<Archer> eightfinals = archers.subList(0, 16);
        conductTournamentRound("Eightfinals", eightfinals, arrowsPerSeries);

        // Quarterfinals: Top 8 archers from eightfinals
        List<Archer> quarterfinals = eightfinals.subList(0, 8);
        conductTournamentRound("Quarterfinals", quarterfinals, arrowsPerSeries);

        // Semifinals: Top 4 archers from quarterfinals
        List<Archer> semifinals = quarterfinals.subList(0, 4);
        conductTournamentRound("Semifinals", semifinals, arrowsPerSeries);

        // Finals: Top 2 archers from semifinals
        List<Archer> finals = semifinals.subList(0, 2);
        conductTournamentRound("Finals", finals, arrowsPerSeries);
    }

    private void conductTournamentRound(String roundName, List<Archer> roundArchers, int arrowsPerSeries) {
        System.out.println(roundName + ":");

        for (int seriesNumber = 0; seriesNumber < 3; seriesNumber++) { // Assuming 3 series in each round
            System.out.println("================================");
            System.out.println("           SERIES " + (seriesNumber + 1));
            System.out.println("================================");

            for (Archer archer : roundArchers) {
                for (int arrowNumber = 0; arrowNumber < arrowsPerSeries; arrowNumber++) {
                    archer.shootArrow(arrowNumber);
                }
            }
        }
    }

    Archer getWinner() {
        return archers.isEmpty() ? null : archers.get(0);
    }
}

public class ArcheryMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printMainMenu();
        String selectedSeason = parseSeasonInput(scanner.nextLine());

        printCategoryMenu();
        String selectedCategory = parseCategoryInput(scanner.nextLine());

        // Prompt the user to enter the number of players (archers)
        printPlayerNumberPrompt();
        int numberOfPlayers;

        // Validate user input for the number of players
        while (true) {
            try {
                numberOfPlayers = Integer.parseInt(scanner.nextLine());
                if (numberOfPlayers >= 2 && numberOfPlayers <= 16) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 2 and 16.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        Competition competition;

        if (selectedSeason.equalsIgnoreCase("Winter")) {
            // Prompt the user to select the winter category
            printWinterCompetitionMenu();
            String winterCategory = parseWinterCategoryInput(scanner.nextLine());

            competition = new Competition("Winter");
            competition.setCategory(winterCategory);
        } else if (selectedSeason.equalsIgnoreCase("Summer")) {
            // Prompt the user to select the summer competition place
            printSummerCompetitionMenu();
            String selectedPlace = parseSummerPlaceInput(scanner.nextLine());

            competition = new Competition("Summer");
            competition.location = selectedPlace;
        } else {
            System.out.println("Invalid season selected.");
            return;
        }

        // Create archers based on user input
        for (int i = 1; i <= numberOfPlayers; i++) {
            Archer archer = createArcher(scanner, "Archer " + i);
            competition.addArcher(archer);
        }

        conductAndPrintResults(competition);
        printWinner(competition);

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("================================");
        System.out.println("       ARCHERY GAME");
        System.out.println("================================");
        System.out.println("1. Winter Competition");
        System.out.println("2. Summer Competition");
        System.out.println("3. Exit");
        System.out.println("-------------------------");
        System.out.print("Enter your choice: ");
    }

    private static void printWinterCompetitionMenu() {
        System.out.println("================================");
        System.out.println("    WINTER COMPETITION");
        System.out.println("================================");
        System.out.println("1. Provas Normais");
        System.out.println("2. Provas-Torneio");
        System.out.println("3. Back to Main Menu");
        System.out.println("-------------------------");
        System.out.print("Enter your choice: ");
    }

    private static void printSummerCompetitionMenu() {
        System.out.println("================================");
        System.out.println("    SUMMER COMPETITION");
        System.out.println("================================");
        System.out.println("1. Football Fields");
        System.out.println("2. Rugby Fields");
        System.out.println("3. Flat Field");
        System.out.println("4. Back to Main Menu");
        System.out.println("-------------------------");
        System.out.print("Enter your choice: ");
    }

    private static void printCategoryMenu() {
        System.out.println("================================");
        System.out.println("      CATEGORY SELECTION");
        System.out.println("================================");
        System.out.println("1. Flechas");
        System.out.println("2. Robins");
        System.out.println("3. Juvenis");
        System.out.println("4. Cadetes");
        System.out.println("5. Juniores");
        System.out.println("6. Seniores");
        System.out.println("7. Veteranos");
        System.out.println("-------------------------");
        System.out.print("Enter your choice: ");
    }

    private static void printPlayerNumberPrompt() {
        System.out.println("================================");
        System.out.println("    NUMBER OF PLAYERS");
        System.out.println("================================");
        System.out.println("Enter the number of players (2 to 16): ");
    }


    static String parseSeasonInput(String input) {
        return input.equals("1") ? "Winter" : (input.equals("2") ? "Summer" : "Invalid");
    }

    static String parseCategoryInput(String input) {
        return switch (input) {
            case "1" -> "Flechas";
            case "2" -> "Robins";
            case "3" -> "Juvenis";
            case "4" -> "Cadetes";
            case "5" -> "Juniores";
            case "6" -> "Seniores";
            case "7" -> "Veteranos";
            default -> "Invalid";
        };
    }

    static String parseWinterCategoryInput(String input) {
        return input.equals("1") ? "provas normais" : (input.equals("2") ? "provas-torneio" : "Invalid");
    }

    static String parseSummerPlaceInput(String input) {
        return switch (input) {
            case "1" -> "Football Fields";
            case "2" -> "Rugby Fields";
            case "3" -> "Flat Field";
            default -> "Invalid";
        };
    }

    static Archer createArcher(Scanner scanner, String defaultName) {
        String[] names = {"João", "Santiago", "Martim", "Rodrigo", "Afonso", "Francisco", "Tiago", "Diogo", "Miguel", "Tomás",
                "Maria", "Leonor", "Matilde", "Beatriz", "Ana", "Mariana", "Madalena", "Catarina", "Carolina", "Francisca"};

        // Randomly choose a name from the list
        String archerName = names[(int) (Math.random() * names.length)];

        return new Archer(archerName, "");
    }

    static void conductAndPrintResults(Competition competition) {
        System.out.println(competition.season + " Competition at " + competition.location + " - Category: " + competition.category + ":");
        competition.conductCompetition();
    }

    static void printWinner(Competition competition) {
        Archer winner = competition.getWinner();
        if (winner != null) {
            System.out.println("Winner: " + winner.name + " - Total Points: " + winner.totalPoints +
                    " - Category: " + winner.category);
        } else {
            System.out.println("No winner. The competition had no participants.");
        }
    }
}
