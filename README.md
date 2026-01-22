# 🎮 ToeTacTic - Limited Memory Tic Tac Toe with Timer

> A mind-bending twist on classic Tic Tac Toe where your moves disappear and time is ticking!

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Game](https://img.shields.io/badge/Game-Strategy-blue?style=for-the-badge)
![AI](https://img.shields.io/badge/AI-Minimax-green?style=for-the-badge)

## 🌟 What Makes This Special?

**ToeTacTic** isn't your grandmother's Tic Tac Toe! This game introduces two revolutionary mechanics that transform the simple 3x3 grid into a strategic battlefield:

- **🧠 Limited Memory**: Only your last 3 moves remain on the board. Your 4th move erases your oldest!
- **⏱️ Chess-Style Timer**: Each player gets 5 minutes total. Run out of time? Game over!

Think you can handle the pressure of vanishing moves and a ticking clock? Let's find out!

## 🎯 Game Features

### Core Mechanics

**Limited Memory System**
- Players can only have 3 marks on the board simultaneously
- Oldest mark automatically disappears when placing the 4th move
- Creates dynamic, ever-changing board states
- No more stalemates - the game stays fluid!

**Fair Play Timer**
- Each player starts with 5:00 minutes
- Computer uses ~5 seconds per move (realistic thinking time)
- Real-time countdown display
- Timeout = Instant loss

**Smart AI Opponent**
- Powered by Minimax algorithm with alpha-beta pruning
- Adapts to the limited memory constraint
- Challenging but beatable
- Fair time consumption

## 🎲 How to Play

### Starting the Game

```bash
# Compile
javac ToeTacTic.java

# Run
java ToeTacTic
```

### Game Flow

1. **Choose Your Turn**
   - Start first (X) or let Computer go first (O)

2. **Make Your Move**
   - Enter a number from 1-9 corresponding to the grid:
   ```
   1 | 2 | 3
   -----------
   4 | 5 | 6
   -----------
   7 | 8 | 9
   ```

3. **Watch Your Time!**
   - Timer displays after each move
   - Plan quickly but wisely

4. **Win Conditions**
   - Get 3 in a row (horizontal, vertical, or diagonal)
   - Opponent runs out of time
   - Opponent cannot make a valid move

### Example Gameplay

```
=============================================
   LIMITED MEMORY TIC TAC TOE WITH TIMER   
=============================================

Special Rules:
- Only your last 3 moves stay on the board
- Your 4th move removes your oldest move
- Each player has 5:00 minutes total
- Computer uses ~5 seconds per move (fair play)
- Time runs out = You lose!

---------------------------------------------
  [TIME] YOU: 5:00  |  COMPUTER: 5:00
---------------------------------------------

[YOUR TURN] Enter move (1-9): 5

         * | * | *
         -----------
         * | X | *
         -----------
         * | * | *

[COMPUTER] Thinking...
[COMPUTER] Played at position 1

         O | * | *
         -----------
         * | X | *
         -----------
         * | * | *
```

## 🧩 Strategic Tips

### Master the Vanishing Act

**Early Game (Moves 1-3)**
- Focus on controlling the center
- Don't commit to a strategy too early
- Your first move will disappear after move 4!

**Mid Game (Moves 4-6)**
- Think 2 moves ahead
- Remember which of your marks will vanish next
- Set up multiple winning threats

**Late Game (Moves 7+)**
- Time pressure increases
- Board state constantly shifts
- Force opponent into bad positions

### Time Management

- **First 10 moves**: Take your time (30-40 seconds each)
- **Mid game**: Speed up (15-20 seconds)
- **Critical moments**: Use your time bank wisely
- **Endgame**: Quick decisive moves (5-10 seconds)

### Advanced Tactics

**Fork Strategy**: Create two winning threats simultaneously
- Harder to execute with limited memory
- Most effective when opponent has only 2 moves on board

**Sacrifice Play**: Deliberately let an old move vanish to reposition
- Use the 3-move limit to your advantage
- Bait opponent into false security

**Time Pressure**: If ahead, play safe and burn opponent's clock
- Force them into complex calculations
- Every second counts!

## 🔧 Technical Details

### Architecture

**Core Components**
```
ToeTacTic
├── PlayerTimer       → Chess-style time tracking
├── MoveHistory       → Queue-based move management
├── Minimax AI        → Strategic decision making
└── Game Loop         → Turn management & validation
```

**Key Algorithms**

*Minimax with Depth Limiting*
- Evaluates game tree to depth 4
- Considers limited memory constraints
- Returns best move score (+10 for win, -10 for loss)

*Move History Management*
- Uses LinkedList Queue for FIFO behavior
- Automatically removes oldest moves
- Efficient O(1) operations

*Timer System*
- Millisecond precision tracking
- Separate timers for each player
- Fair time deduction for both human and AI

### Customization Options

Want to tweak the game? Here are the key constants:

```java
static final int MAX_MOVES = 3;              // Change memory limit (2-5 recommended)
static final int TIME_LIMIT_SECONDS = 300;   // Adjust time per player (seconds)
static final int AVG_HUMAN_MOVE_TIME = 5000; // Computer thinking time (ms)
```

**Difficulty Modes (Suggested)**
```java
// Easy Mode
MAX_MOVES = 4
TIME_LIMIT_SECONDS = 600 (10 minutes)
AVG_HUMAN_MOVE_TIME = 3000 (3 seconds)

// Normal Mode (Default)
MAX_MOVES = 3
TIME_LIMIT_SECONDS = 300 (5 minutes)
AVG_HUMAN_MOVE_TIME = 5000 (5 seconds)

// Hard Mode
MAX_MOVES = 2
TIME_LIMIT_SECONDS = 180 (3 minutes)
AVG_HUMAN_MOVE_TIME = 7000 (7 seconds)

// Blitz Mode (Extreme!)
MAX_MOVES = 3
TIME_LIMIT_SECONDS = 60 (1 minute)
AVG_HUMAN_MOVE_TIME = 2000 (2 seconds)
```

## 📋 Requirements

- **Java Version**: JDK 8 or higher
- **Memory**: Minimal (< 50 MB)
- **Display**: Terminal/Console with Unicode support

## 🎓 Learning Outcomes

This project demonstrates:

- ✅ Game theory and strategic AI
- ✅ Minimax algorithm implementation
- ✅ Queue data structures (FIFO behavior)
- ✅ Real-time timer management
- ✅ State management and validation
- ✅ Clean console UI design
- ✅ Exception handling

Perfect for:
- Computer Science students learning AI
- Game developers exploring turn-based mechanics
- Anyone wanting to understand Minimax in action

## 🤝 Contributing

Found a bug? Have an idea for improvement?

**Potential Enhancements**
- [ ] Difficulty levels (Easy/Normal/Hard)
- [ ] Undo last move (with time penalty)
- [ ] Replay saved games
- [ ] Statistics tracking (wins/losses/timeouts)
- [ ] Multiplayer mode (Human vs Human)
- [ ] Larger boards (4x4, 5x5)
- [ ] Custom time controls
- [ ] Sound effects
- [ ] GUI version (JavaFX/Swing)

## 📜 License

This project is open source and available for educational purposes.

## 🙏 Acknowledgments

- Classic Tic Tac Toe for the foundation
- Chess time controls for inspiration
- Minimax algorithm pioneers
- The Java community

## 📞 Questions?

Run into issues? The most common ones:

**Q: Computer moves too fast/slow?**
A: Adjust `AVG_HUMAN_MOVE_TIME` constant (in milliseconds)

**Q: Game too hard/easy?**
A: Modify `MAX_MOVES` (lower = harder) or `TIME_LIMIT_SECONDS`

**Q: Can I play against a friend?**
A: Not yet! But this would be a great feature to add

**Q: Timer not accurate?**
A: System.currentTimeMillis() is used - should be accurate to within 10-20ms

---

<div align="center">

**⭐ Star this project if you enjoyed it!**

Made with ☕ and 🧠

*"In ToeTacTic, your past moves fade... but your future victory remains!"*

</div>