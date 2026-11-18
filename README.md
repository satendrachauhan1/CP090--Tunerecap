# 🎵 CP-090 TuneRecap
### Music Listening Analytics and Year-in-Review Generator

A Java-based console application that analyzes music listening history and generates personalized music statistics and a Year-in-Review summary.

## 👥 Team

- **Team Member:** Satendra Chauhan
- **Track:** Java
- **Project Code:** CP-090

## 📌 Project Overview

TuneRecap is a music listening analytics application designed to track and analyze users' music listening activities.

It allows users to maintain listening records, discover their favourite songs and artists, analyze listening patterns, compare music preferences, and generate a shareable Year-in-Review summary.

## ✨ Features

### Core Features

1. **Add Listening Records**
   - Enter song name, artist, genre, date, hour, and listening duration.
   - Includes input validation.

2. **View All Records**
   - Display all saved music listening entries.

3. **Top 5 Rankings**
   - Find the top 5 songs, artists, and genres based on total listening minutes.

4. **Listening Statistics**
   - Calculate total listening time.
   - Identify the busiest listening month.

5. **Hourly Listening Chart**
   - Display listening activity from 00:00 to 23:00.
   - Uses a text-based bar chart.

6. **Longest Listening Streak**
   - Identify the longest consecutive days of music listening.

### Additional Features

7. **Music Taste Comparison**
   - Compare music preferences between two users.
   - Calculate a taste-match percentage based on shared artists and genres.

8. **Year-in-Review Export**
   - Generate a personalized music summary.
   - Export the summary into `TuneRecap_Card.txt`.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| Arrays | Store listening records |
| Loops | Process and analyze data |
| Conditional Statements | Apply logical conditions |
| Methods | Organize program functionality |
| Selection Sort | Rank listening statistics |
| Bubble Sort | Sort listening dates |
| Scanner | Accept user input |
| FileWriter | Export summary to a text file |

## ⚙️ Requirements

- Java Development Kit (JDK)
- Command Prompt / Terminal or IntelliJ IDEA
- Basic knowledge of Java

## 🚀 How to Run

**Step 1: Clone the repository**

```bash
git clone <your-repository-url>
```

**Step 2: Open the project folder**

```bash
cd TuneRecap
```

**Step 3: Compile the Java program**

```bash
javac Main.java
```

**Step 4: Run the application**

```bash
java Main
```

## 📋 Application Menu

```text
========================================================
   CP-090 TUNERECAP: MUSIC LISTENING ANALYTICS ENGINE
========================================================

1. Add a Listening Log Entry
2. View All Listening Logs
3. Show Top 5 Songs, Artists, and Genres
4. Show Total Listening Time & Busiest Month
5. Show Listening by Hour of Day
6. Find Longest Listening Streak
7. Compare Two Users (Taste-Match %)
8. Export Year-in-Review Shareable Text Card
0. Exit

--------------------------------------------------------
Enter your choice (0-8):
```

## 📊 Sample Results

Based on the preloaded sample data:

| Statistic | Result |
|---|---|
| Total Listening Sessions | 16 |
| Total Listening Time | 685 minutes |
| Listening Duration | 11 hrs 25 mins |
| Top Song | Blinding Lights |
| Top Artist | Arijit Singh |
| Top Genre | Bollywood |
| Busiest Month | March |
| Longest Listening Streak | 5 consecutive days |

### Top Listening Records

- **Top Song:** Blinding Lights – 110 minutes
- **Top Artist:** Arijit Singh – 220 minutes
- **Top Genre:** Bollywood – 200 minutes
- **Busiest Month:** March – 210 minutes
- **Longest Streak:** 5 days (March 1–5, 2026)

## 📁 Project Structure

```text
TuneRecap/
│
├── Main.java
├── TuneRecap_Card.txt
└── README.md
```

*Note: TuneRecap_Card.txt is generated when the export feature is executed.*

## 🎯 Learning Outcomes

Through this project, we demonstrate practical applications of:

- Java programming fundamentals.
- Array-based data management.
- Searching and sorting algorithms.
- Data analysis using loops and conditions.
- Input validation and exception handling.
- File handling using Java.

## 🔮 Future Enhancements

- Store listening records permanently using a database.
- Develop a graphical user interface.
- Add music platform integration.
- Generate graphical listening reports.
- Support multiple user profiles.

## 🏁 Conclusion

TuneRecap demonstrates how core Java programming concepts can be used to build a practical music analytics application.

It transforms basic listening records into meaningful statistics and personalized music summaries.

---

**Developed by Satendra Chauhan | Java Track | CP-090**
