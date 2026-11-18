import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * CP-090 TuneRecap: Music Listening Analytics and Year-in-Review Generator
 * Track: Java (Console Application)
 *
 * Designed using simple Phase-1 Java concepts:
 * - Parallel arrays for storing records
 * - Standard for-loops and if-else conditions
 * - Basic Selection Sort and Bubble Sort (easy to explain in Viva)
 * - Complete input validation for wrong inputs
 * - Clean screen transitions (hides main menu while viewing an option, press 'M' to return)
 */
public class Main {

    // Maximum number of log entries our program can store
    static final int MAX_LOGS = 500;

    // Parallel arrays to store the listening log (each index i is one record)
    static String[] songs = new String[MAX_LOGS];
    static String[] artists = new String[MAX_LOGS];
    static String[] genres = new String[MAX_LOGS];
    static String[] dates = new String[MAX_LOGS]; // Format: YYYY-MM-DD
    static int[] hours = new int[MAX_LOGS];       // 0 to 23 (hour of day)
    static int[] minutes = new int[MAX_LOGS];     // Minutes played (1 to 300)
    static int logCount = 0;

    // Month names for display
    static final String[] MONTH_NAMES = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    // Days in each month (standard non-leap year)
    static final int[] DAYS_IN_MONTH = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load default sample data at startup so the 2-minute demo works immediately
        loadSampleData();

        boolean running = true;
        clearScreen();

        while (running) {
            printMenu();
            int choice = readInt(sc, "Enter your choice (0-8): ", 0, 8);

            if (choice == 0) {
                clearScreen();
                System.out.println("Thank you for using TuneRecap! Goodbye.");
                running = false;
                break;
            }

            // Hide the main menu before showing the chosen option's details
            clearScreen();

            switch (choice) {
                case 1:
                    addListeningEntry(sc);
                    break;
                case 2:
                    viewAllLogs();
                    break;
                case 3:
                    showTopFive();
                    break;
                case 4:
                    showTotalTimeAndBusiestMonth();
                    break;
                case 5:
                    showHourlyChart();
                    break;
                case 6:
                    showLongestStreak();
                    break;
                case 7:
                    compareWithFriend(sc);
                    break;
                case 8:
                    exportShareableCard();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            // Pause on the current screen until the user presses 'M' to return to Main Menu
            waitForMainMenu(sc);
        }
        sc.close();
    }

    // Clears the console screen so the Main Menu is hidden when viewing an option
    static void clearScreen() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Waits for the user to press 'M' (or 'm') before returning to the Main Menu
    static void waitForMainMenu(Scanner sc) {
        System.out.println("\n--------------------------------------------------------");
        while (true) {
            System.out.print("Press 'M' and Enter to return to the Main Menu: ");
            String key = sc.nextLine().trim();
            if (key.equalsIgnoreCase("M")) {
                clearScreen();
                break;
            }
            System.out.println("  -> Invalid key! Please press 'M' to return to the Main Menu.");
        }
    }

    // Prints the main menu
    static void printMenu() {
        System.out.println("========================================================");
        System.out.println("   CP-090 TUNERECAP: MUSIC LISTENING ANALYTICS ENGINE   ");
        System.out.println("========================================================");
        System.out.println("1. [Core 1] Add a Listening Log Entry");
        System.out.println("2. [Core 1] View All Listening Logs (" + logCount + " records loaded)");
        System.out.println("3. [Core 2] Show Top 5 Songs, Artists, and Genres");
        System.out.println("4. [Core 3] Show Total Listening Time & Busiest Month");
        System.out.println("5. [Core 4] Show Listening by Hour of Day (Text Chart)");
        System.out.println("6. [Core 5] Find Longest Listening Streak (Consecutive Days)");
        System.out.println("7. [Stretch 1] Compare Two Users (Taste-Match %)");
        System.out.println("8. [Stretch 2] Export Year-in-Review Shareable Text Card");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------------------------");
    }

    // =========================================================================
    // CORE FEATURE 1: Load / Add a listening log (song, artist, genre, date, hour, minutes)
    // =========================================================================

    static void loadSampleData() {
        logCount = 0;
        // 16 realistic entries with 6+ Songs, 6+ Artists, and 6 Genres so Top 5 always ranks 5 out of 6+
        insertLog("Blinding Lights", "The Weeknd", "Synthpop", "2026-01-10", 9, 45);
        insertLog("Kesariya", "Arijit Singh", "Bollywood", "2026-02-14", 21, 60);
        insertLog("Believer", "Imagine Dragons", "Rock", "2026-03-01", 18, 35);
        insertLog("Starboy", "The Weeknd", "Synthpop", "2026-03-02", 22, 50);
        insertLog("Tum Hi Ho", "Arijit Singh", "Bollywood", "2026-03-03", 21, 40);
        insertLog("Thunder", "Imagine Dragons", "Rock", "2026-03-04", 19, 30);
        insertLog("Chaleya", "Arijit Singh", "Bollywood", "2026-03-05", 20, 55);
        insertLog("Levitating", "Dua Lipa", "Pop", "2026-05-12", 14, 25);
        insertLog("Blinding Lights", "The Weeknd", "Synthpop", "2026-07-19", 21, 65);
        insertLog("Numb", "Linkin Park", "Rock", "2026-08-08", 17, 40);
        insertLog("Lose Yourself", "Eminem", "Hip-Hop", "2026-09-20", 10, 50);
        insertLog("Kesariya", "Arijit Singh", "Bollywood", "2026-10-11", 22, 45);
        insertLog("Shape of You", "Ed Sheeran", "Pop", "2026-10-12", 8, 35);
        insertLog("Night Changes", "One Direction", "Pop", "2026-11-01", 23, 50);
        insertLog("Kun Faya Kun", "AR Rahman", "Sufi", "2026-11-02", 20, 40);
        insertLog("Ilahi", "Arijit Singh", "Indie", "2026-11-10", 16, 20);
    }

    static void insertLog(String song, String artist, String genre, String date, int hour, int mins) {
        if (logCount < MAX_LOGS) {
            songs[logCount] = song;
            artists[logCount] = artist;
            genres[logCount] = genre;
            dates[logCount] = date;
            hours[logCount] = hour;
            minutes[logCount] = mins;
            logCount++;
        }
    }

    static void addListeningEntry(Scanner sc) {
        if (logCount >= MAX_LOGS) {
            System.out.println("Error: Listening log is full!");
            return;
        }

        System.out.println("--- Add New Listening Log Entry ---");
        String song = readNonEmptyString(sc, "Enter Song Title: ");
        String artist = readNonEmptyString(sc, "Enter Artist Name: ");
        String genre = readNonEmptyString(sc, "Enter Genre (e.g., Pop, Rock, Bollywood): ");
        String date = readValidDate(sc, "Enter Date (YYYY-MM-DD, e.g., 2026-03-06): ");
        int hour = readInt(sc, "Enter Hour of Day (0 to 23): ", 0, 23);
        int mins = readInt(sc, "Enter Minutes Played (1 to 300): ", 1, 300);

        insertLog(song, artist, genre, date, hour, mins);
        System.out.println("Success! Added '" + song + "' by " + artist + " (" + mins + " mins on " + date + ").");
    }

    static void viewAllLogs() {
        if (logCount == 0) {
            System.out.println("No listening logs available.");
            return;
        }
        System.out.println("--- Current Listening Log (" + logCount + " entries) ---");
        System.out.printf("%-4s %-20s %-18s %-12s %-12s %-6s %-8s%n",
                "No.", "Song", "Artist", "Genre", "Date", "Hour", "Minutes");
        System.out.println("------------------------------------------------------------------------------------");
        for (int i = 0; i < logCount; i++) {
            System.out.printf("%-4d %-20s %-18s %-12s %-12s %02d:00  %-8d%n",
                    (i + 1), truncate(songs[i], 20), truncate(artists[i], 18),
                    truncate(genres[i], 12), dates[i], hours[i], minutes[i]);
        }
    }

    // =========================================================================
    // CORE FEATURE 2: Show the Top 5 Songs, Artists, and Genres
    // =========================================================================

    static void showTopFive() {
        if (logCount == 0) {
            System.out.println("No logs available to analyse.");
            return;
        }
        System.out.println("================ TOP 5 LISTENING RANKINGS ================");
        printTopCategory("TOP 5 SONGS", songs);
        System.out.println();
        printTopCategory("TOP 5 ARTISTS", artists);
        System.out.println();
        printTopCategory("TOP 5 GENRES", genres);
    }

    // Helper method that groups unique names, sums their minutes, sorts descending, and prints top 5
    static void printTopCategory(String title, String[] sourceArray) {
        String[] uniqueNames = new String[logCount];
        int[] totalMins = new int[logCount];
        int[] playCounts = new int[logCount];
        int uniqueCount = 0;

        // Step 1: Aggregate minutes and play counts for each unique item
        for (int i = 0; i < logCount; i++) {
            String current = sourceArray[i];
            int foundIndex = -1;

            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueNames[j].equalsIgnoreCase(current)) {
                    foundIndex = j;
                    break;
                }
            }

            if (foundIndex == -1) {
                uniqueNames[uniqueCount] = current;
                totalMins[uniqueCount] = minutes[i];
                playCounts[uniqueCount] = 1;
                uniqueCount++;
            } else {
                totalMins[foundIndex] += minutes[i];
                playCounts[foundIndex] += 1;
            }
        }

        // Step 2: Simple Selection Sort from highest minutes to lowest
        for (int i = 0; i < uniqueCount - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < uniqueCount; j++) {
                if (totalMins[j] > totalMins[maxIdx]) {
                    maxIdx = j;
                }
            }
            // Swap minutes
            int tempMin = totalMins[i];
            totalMins[i] = totalMins[maxIdx];
            totalMins[maxIdx] = tempMin;

            // Swap play counts
            int tempCount = playCounts[i];
            playCounts[i] = playCounts[maxIdx];
            playCounts[maxIdx] = tempCount;

            // Swap names
            String tempName = uniqueNames[i];
            uniqueNames[i] = uniqueNames[maxIdx];
            uniqueNames[maxIdx] = tempName;
        }

        // Step 3: Print up to Top 5
        System.out.println("--- " + title + " ---");
        int limit = Math.min(5, uniqueCount);
        for (int i = 0; i < limit; i++) {
            System.out.printf("  #%d  %-22s : %4d mins (%d plays)%n",
                    (i + 1), uniqueNames[i], totalMins[i], playCounts[i]);
        }
    }

    // Helper to get the #1 item name for the summary card
    static String getTopOneItem(String[] sourceArray) {
        if (logCount == 0) return "N/A";
        String[] uniqueNames = new String[logCount];
        int[] totalMins = new int[logCount];
        int uniqueCount = 0;

        for (int i = 0; i < logCount; i++) {
            int idx = -1;
            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueNames[j].equalsIgnoreCase(sourceArray[i])) {
                    idx = j;
                    break;
                }
            }
            if (idx == -1) {
                uniqueNames[uniqueCount] = sourceArray[i];
                totalMins[uniqueCount] = minutes[i];
                uniqueCount++;
            } else {
                totalMins[idx] += minutes[i];
            }
        }

        int bestIdx = 0;
        for (int i = 1; i < uniqueCount; i++) {
            if (totalMins[i] > totalMins[bestIdx]) {
                bestIdx = i;
            }
        }
        return uniqueNames[bestIdx] + " (" + totalMins[bestIdx] + " mins)";
    }

    // =========================================================================
    // CORE FEATURE 3: Show Total Listening Time and Busiest Month
    // =========================================================================

    static void showTotalTimeAndBusiestMonth() {
        if (logCount == 0) {
            System.out.println("No logs available.");
            return;
        }

        int totalMinutes = 0;
        int[] monthTotals = new int[12]; // Index 0 = Jan, 11 = Dec

        for (int i = 0; i < logCount; i++) {
            totalMinutes += minutes[i];
            int month = extractMonth(dates[i]); // 1 to 12
            if (month >= 1 && month <= 12) {
                monthTotals[month - 1] += minutes[i];
            }
        }

        int busiestMonthIndex = 0;
        for (int m = 1; m < 12; m++) {
            if (monthTotals[m] > monthTotals[busiestMonthIndex]) {
                busiestMonthIndex = m;
            }
        }

        int hrs = totalMinutes / 60;
        int remMins = totalMinutes % 60;

        System.out.println("========== TOTAL LISTENING TIME & BUSIEST MONTH ==========");
        System.out.println("Total Sessions Logged : " + logCount);
        System.out.println("Total Listening Time  : " + totalMinutes + " minutes (" + hrs + " hrs " + remMins + " mins)");
        System.out.println("Busiest Month         : " + MONTH_NAMES[busiestMonthIndex]
                + " (" + monthTotals[busiestMonthIndex] + " minutes)");

        System.out.println("\nMonthly Breakdown (Active Months):");
        for (int m = 0; m < 12; m++) {
            if (monthTotals[m] > 0) {
                System.out.printf("  %-12s : %4d mins%n", MONTH_NAMES[m], monthTotals[m]);
            }
        }
    }

    // =========================================================================
    // CORE FEATURE 4: Show Listening by Hour of Day as a Text Chart
    // =========================================================================

    static void showHourlyChart() {
        if (logCount == 0) {
            System.out.println("No logs available.");
            return;
        }

        int[] hourTotals = new int[24];
        int peakHour = 0;

        for (int i = 0; i < logCount; i++) {
            int h = hours[i];
            if (h >= 0 && h <= 23) {
                hourTotals[h] += minutes[i];
            }
        }

        for (int h = 1; h < 24; h++) {
            if (hourTotals[h] > hourTotals[peakHour]) {
                peakHour = h;
            }
        }

        System.out.println("========== LISTENING BY HOUR OF DAY (TEXT CHART) ==========");
        System.out.println("Scale: Each '#' represents 5 minutes of listening\n");

        for (int h = 0; h < 24; h++) {
            int mins = hourTotals[h];
            int barLength = mins / 5;
            if (mins > 0 && barLength == 0) {
                barLength = 1; // Show at least 1 '#' if > 0 mins
            }

            StringBuilder bar = new StringBuilder();
            for (int b = 0; b < barLength; b++) {
                bar.append("#");
            }

            System.out.printf("  %02d:00 - %02d:59 | %-25s (%d mins)%n", h, h, bar.toString(), mins);
        }

        System.out.printf("%nPeak Listening Hour: %02d:00 (%d minutes)%n", peakHour, hourTotals[peakHour]);
    }

    // =========================================================================
    // CORE FEATURE 5: Find the Longest Listening Streak in Consecutive Days
    // =========================================================================

    static void showLongestStreak() {
        if (logCount == 0) {
            System.out.println("No logs available.");
            return;
        }

        // Step 1: Convert each date to a Day-of-Year integer and collect unique active days
        int[] uniqueDays = new int[logCount];
        String[] uniqueDateStrings = new String[logCount];
        int uniqueDayCount = 0;

        for (int i = 0; i < logCount; i++) {
            int dayNum = dateToDayOfYear(dates[i]);
            boolean exists = false;
            for (int j = 0; j < uniqueDayCount; j++) {
                if (uniqueDays[j] == dayNum) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                uniqueDays[uniqueDayCount] = dayNum;
                uniqueDateStrings[uniqueDayCount] = dates[i];
                uniqueDayCount++;
            }
        }

        // Step 2: Sort uniqueDays in ascending order using Bubble Sort
        for (int i = 0; i < uniqueDayCount - 1; i++) {
            for (int j = 0; j < uniqueDayCount - 1 - i; j++) {
                if (uniqueDays[j] > uniqueDays[j + 1]) {
                    int tempDay = uniqueDays[j];
                    uniqueDays[j] = uniqueDays[j + 1];
                    uniqueDays[j + 1] = tempDay;

                    String tempDate = uniqueDateStrings[j];
                    uniqueDateStrings[j] = uniqueDateStrings[j + 1];
                    uniqueDateStrings[j + 1] = tempDate;
                }
            }
        }

        // Step 3: Walk through sorted days and count consecutive streak (day[i] == day[i-1] + 1)
        int maxStreak = 1;
        int currentStreak = 1;
        int bestStartIdx = 0;
        int bestEndIdx = 0;
        int currentStartIdx = 0;

        for (int i = 1; i < uniqueDayCount; i++) {
            if (uniqueDays[i] == uniqueDays[i - 1] + 1) {
                currentStreak++;
                if (currentStreak > maxStreak) {
                    maxStreak = currentStreak;
                    bestStartIdx = currentStartIdx;
                    bestEndIdx = i;
                }
            } else {
                currentStreak = 1;
                currentStartIdx = i;
            }
        }

        System.out.println("========== LONGEST DAILY LISTENING STREAK ==========");
        System.out.println("Total Active Listening Days : " + uniqueDayCount + " days");
        System.out.println("Longest Consecutive Streak  : " + maxStreak + " day(s)");
        System.out.println("Streak Period               : " + uniqueDateStrings[bestStartIdx]
                + " to " + uniqueDateStrings[bestEndIdx]);
    }

    // Helper to get longest streak number for the summary card
    static int calculateMaxStreakValue() {
        if (logCount == 0) return 0;
        int[] uniqueDays = new int[logCount];
        int uniqueDayCount = 0;
        for (int i = 0; i < logCount; i++) {
            int dayNum = dateToDayOfYear(dates[i]);
            boolean exists = false;
            for (int j = 0; j < uniqueDayCount; j++) {
                if (uniqueDays[j] == dayNum) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                uniqueDays[uniqueDayCount++] = dayNum;
            }
        }
        for (int i = 0; i < uniqueDayCount - 1; i++) {
            for (int j = 0; j < uniqueDayCount - 1 - i; j++) {
                if (uniqueDays[j] > uniqueDays[j + 1]) {
                    int t = uniqueDays[j];
                    uniqueDays[j] = uniqueDays[j + 1];
                    uniqueDays[j + 1] = t;
                }
            }
        }
        int maxStreak = 1;
        int currentStreak = 1;
        for (int i = 1; i < uniqueDayCount; i++) {
            if (uniqueDays[i] == uniqueDays[i - 1] + 1) {
                currentStreak++;
                if (currentStreak > maxStreak) maxStreak = currentStreak;
            } else {
                currentStreak = 1;
            }
        }
        return maxStreak;
    }

    // =========================================================================
    // STRETCH FEATURE 1: Compare Two Users and Show Taste-Match Percentage
    // =========================================================================

    static void compareWithFriend(Scanner sc) {
        System.out.println("========== USER TASTE-MATCH COMPARISON ==========");
        System.out.println("1. Compare with Pre-loaded Friend (Aarav)");
        System.out.println("2. Enter Second User's Favourite Artists & Genres Manually");
        int mode = readInt(sc, "Select mode (1 or 2): ", 1, 2);

        String friendName = "Aarav";
        String[] friendArtists;
        String[] friendGenres;

        if (mode == 1) {
            friendArtists = new String[]{"Arijit Singh", "The Weeknd", "Coldplay", "Dua Lipa", "AR Rahman"};
            friendGenres = new String[]{"Bollywood", "Pop", "Synthpop", "Indie"};
        } else {
            friendName = readNonEmptyString(sc, "Enter Second User's Name: ");
            friendArtists = new String[3];
            System.out.println("Enter 3 Favourite Artists for " + friendName + ":");
            for (int i = 0; i < 3; i++) {
                friendArtists[i] = readNonEmptyString(sc, "  Artist #" + (i + 1) + ": ");
            }
            friendGenres = new String[3];
            System.out.println("Enter 3 Favourite Genres for " + friendName + ":");
            for (int i = 0; i < 3; i++) {
                friendGenres[i] = readNonEmptyString(sc, "  Genre #" + (i + 1) + ": ");
            }
        }

        // Find unique artists and genres for main user
        String[] myUniqueArtists = getUniqueItems(artists, logCount);
        String[] myUniqueGenres = getUniqueItems(genres, logCount);

        int matchingArtists = countMatches(myUniqueArtists, friendArtists);
        int matchingGenres = countMatches(myUniqueGenres, friendGenres);

        // Calculate overlap score: 60% weight on artist overlap, 40% weight on genre overlap
        double artistScore = (double) matchingArtists / friendArtists.length * 60.0;
        double genreScore = (double) matchingGenres / friendGenres.length * 40.0;
        int tasteMatchPercent = (int) Math.round(artistScore + genreScore);
        if (tasteMatchPercent > 100) tasteMatchPercent = 100;

        System.out.println("\nComparing: You (User A) vs. " + friendName + " (User B)");
        System.out.println("-------------------------------------------------");
        System.out.println("Shared Artists Count       : " + matchingArtists + " out of " + friendArtists.length);
        System.out.println("Shared Genres Count        : " + matchingGenres + " out of " + friendGenres.length);
        System.out.println("Overall Taste-Match Score  : " + tasteMatchPercent + "%");

        if (tasteMatchPercent >= 75) {
            System.out.println("Verdict                    : Super Music Twins! High compatibility.");
        } else if (tasteMatchPercent >= 40) {
            System.out.println("Verdict                    : Good Vibe Match! Plenty of shared favourites.");
        } else {
            System.out.println("Verdict                    : Opposites Attract! Time to swap playlists.");
        }
    }

    static String[] getUniqueItems(String[] source, int length) {
        String[] temp = new String[length];
        int count = 0;
        for (int i = 0; i < length; i++) {
            boolean found = false;
            for (int j = 0; j < count; j++) {
                if (temp[j].equalsIgnoreCase(source[i])) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                temp[count++] = source[i];
            }
        }
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }
        return result;
    }

    static int countMatches(String[] listA, String[] listB) {
        int matches = 0;
        for (int i = 0; i < listA.length; i++) {
            for (int j = 0; j < listB.length; j++) {
                if (listA[i].equalsIgnoreCase(listB[j])) {
                    matches++;
                    break;
                }
            }
        }
        return matches;
    }

    // =========================================================================
    // STRETCH FEATURE 2: Export the Summary as a Shareable Text Card
    // =========================================================================

    static void exportShareableCard() {
        int totalMinutes = 0;
        int[] monthTotals = new int[12];
        for (int i = 0; i < logCount; i++) {
            totalMinutes += minutes[i];
            int m = extractMonth(dates[i]);
            if (m >= 1 && m <= 12) monthTotals[m - 1] += minutes[i];
        }

        int busiestMonth = 0;
        for (int m = 1; m < 12; m++) {
            if (monthTotals[m] > monthTotals[busiestMonth]) busiestMonth = m;
        }

        String topSong = getTopOneItem(songs);
        String topArtist = getTopOneItem(artists);
        String topGenre = getTopOneItem(genres);
        int longestStreak = calculateMaxStreakValue();

        StringBuilder card = new StringBuilder();
        card.append("+------------------------------------------------------+\n");
        card.append("|            TUNERECAP 2026: YEAR IN REVIEW            |\n");
        card.append("+------------------------------------------------------+\n");
        card.append(String.format("| Total Listening Time : %-29s |\n", totalMinutes + " mins (" + (totalMinutes / 60) + "h " + (totalMinutes % 60) + "m)"));
        card.append(String.format("| #1 Top Song          : %-29s |\n", truncate(topSong, 29)));
        card.append(String.format("| #1 Top Artist        : %-29s |\n", truncate(topArtist, 29)));
        card.append(String.format("| #1 Top Genre         : %-29s |\n", truncate(topGenre, 29)));
        card.append(String.format("| Busiest Month        : %-29s |\n", MONTH_NAMES[busiestMonth] + " (" + monthTotals[busiestMonth] + " mins)"));
        card.append(String.format("| Longest Daily Streak : %-29s |\n", longestStreak + " consecutive days"));
        card.append("+------------------------------------------------------+\n");
        card.append("|         Shared via TuneRecap Analytics Engine        |\n");
        card.append("+------------------------------------------------------+\n");

        System.out.println(card.toString());

        String fileName = "TuneRecap_Card.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(card.toString());
            System.out.println("Shareable summary card successfully exported to: " + fileName);
        } catch (IOException e) {
            System.out.println("Could not write to file: " + e.getMessage());
        }
    }

    // =========================================================================
    // INPUT VALIDATION & DATE HELPER METHODS
    // =========================================================================

    // Reads a valid integer within [min, max], handling wrong/non-numeric input safely
    static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) {
                    return val;
                } else {
                    System.out.println("  -> Invalid range! Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("  -> Invalid input! Please enter a valid whole number.");
            }
        }
    }

    // Reads a non-empty string
    static String readNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("  -> Error: Input cannot be empty. Please try again.");
        }
    }

    // Validates YYYY-MM-DD date format and checks real calendar month/day limits
    static String readValidDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (isValidDate(input)) {
                return input;
            }
            System.out.println("  -> Invalid date! Use YYYY-MM-DD format with a valid month (01-12) and day.");
        }
    }

    static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    static boolean isValidDate(String date) {
        if (date.length() != 10 || date.charAt(4) != '-' || date.charAt(7) != '-') {
            return false;
        }
        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));

            if (year < 2000 || year > 2100) return false;
            if (month < 1 || month > 12) return false;

            int maxDays = DAYS_IN_MONTH[month - 1];
            if (month == 2 && isLeapYear(year)) {
                maxDays = 29;
            }
            return day >= 1 && day <= maxDays;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Extracts month (1 to 12) from YYYY-MM-DD
    static int extractMonth(String date) {
        return Integer.parseInt(date.substring(5, 7));
    }

    // Converts YYYY-MM-DD into day of year (1 to 365/366), accounting for leap years
    static int dateToDayOfYear(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        int dayOfYear = 0;
        for (int m = 0; m < month - 1; m++) {
            dayOfYear += DAYS_IN_MONTH[m];
            if (m == 1 && isLeapYear(year)) {
                dayOfYear += 1;
            }
        }
        return dayOfYear + day;
    }

    static String truncate(String text, int maxLen) {
        if (text.length() <= maxLen) return text;
        return text.substring(0, maxLen - 2) + "..";
    }
}