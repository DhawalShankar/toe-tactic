# 🎯 TicTacToe — Classical Game AI in Java

A **console-based Tic-Tac-Toe game** implemented in **Java**, featuring a **classical AI opponent** powered by the **Minimax algorithm**.
The project focuses on **decision-making, state evaluation, and algorithmic correctness**, not UI or machine learning.

---

## 🚀 Features

* Human vs Computer gameplay
* **Unbeatable AI** using Minimax
* Proper **winner detection**
* **Depth-aware scoring** (faster wins, delayed losses)
* Robust input validation
* Clean, readable, single-file Java implementation

---

## 🧠 AI Approach

This project uses **Classical AI (Game AI)**, not Machine Learning.

### Algorithm: **Minimax**

* Explores all possible future game states
* Assumes an optimal opponent
* Chooses the move that **maximizes the minimum guaranteed outcome**

### Scoring Logic

* `+10 - depth` → AI win (faster wins preferred)
* `depth - 10` → Human win (slower losses preferred)
* `0` → Draw

---

## 🧩 Data Structures Used

* **2D char array (`char[3][3]`)** → Board representation
* **Recursion (implicit stack)** → Game tree traversal
* **Constants & enums** → Game state management

---

## ⏱ Time & Space Complexity

* **Time Complexity:** `O(b^d)`

  * `b` = branching factor (≤ 9)
  * `d` = depth of the game tree (≤ 9)
* **Space Complexity:** `O(d)` (recursive call stack)

---

## 🛠️ Tech Stack

* **Language:** Java
* **Concepts:** DSA, Recursion, Backtracking, Game Theory
* **Type:** Console application

---

## ▶️ How to Run

```bash
javac TicTacToe.java
java TicTacToe
```

Follow on-screen instructions to play.

---

## 📌 Design Decisions

* **Single-file implementation** to emphasize algorithmic clarity
* Logic-first approach (UI intentionally minimal)
* Designed to be easily **modularized or wrapped in a web API** later (e.g., Spring Boot)

---

## 🔮 Future Enhancements

* Alpha–Beta Pruning
* Variable board size (NxN)
* Disappearing-move variant (dynamic game state)
* Web interface via Java backend (Spring Boot)

---

## 🧠 Key Learning Outcomes

* Understanding Minimax beyond textbook usage
* Proper game-state evaluation
* Avoiding common logical pitfalls in AI scoring
* Writing defensible, interview-ready code

---

## 📄 License

This project is open for learning and educational use.
