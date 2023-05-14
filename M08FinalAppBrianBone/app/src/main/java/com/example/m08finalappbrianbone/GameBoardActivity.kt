package com.example.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.m08finalappbrianbone.R
import java.util.*

class GameBoardActivity : AppCompatActivity() {

    private var activePlayer = 1
    private var playerOneScore = 0
    private var playerTwoScore = 0
    private var powerupEnabled = true

    private val boardState = Array(3) { IntArray(3) }

    private lateinit var boardButtons: Array<Array<Button>>
    private lateinit var playerOneScoreTextView: TextView
    private lateinit var playerTwoScoreTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_board)

        boardButtons = arrayOf(
            arrayOf(
                findViewById(R.id.button00),
                findViewById(R.id.button01),
                findViewById(R.id.button02)
            ),
            arrayOf(
                findViewById(R.id.button10),
                findViewById(R.id.button11),
                findViewById(R.id.button12)
            ),
            arrayOf(
                findViewById(R.id.button20),
                findViewById(R.id.button21),
                findViewById(R.id.button22)
            )
        )

        playerOneScoreTextView = findViewById(R.id.playerOneScoreTextView)
        playerTwoScoreTextView = findViewById(R.id.playerTwoScoreTextView)

        resetBoard()
    }

    private fun resetBoard() {
        activePlayer = 1
        boardState.forEach { it.fill(0) }
        boardButtons.forEach { row ->
            row.forEach { button ->
                button.text = ""
                button.setBackgroundColor(Color.WHITE)
                button.setOnClickListener { makeMove(button) }
            }
        }
        updateScoreboard()
    }

    private fun makeMove(button: Button) {
        val row = button.tag.toString().toInt() / 10
        val col = button.tag.toString().toInt() % 10

        if (boardState[row][col] != 0) {
            return
        }

        if (activePlayer == 1) {
            button.text = "X"
            boardState[row][col] = 1
            button.setBackgroundColor(Color.parseColor("#ff6699"))
            activePlayer = 2
        } else {
            button.text = "O"
            boardState[row][col] = 2
            button.setBackgroundColor(Color.parseColor("#66ccff"))
            activePlayer = 1
        }

        if (checkForWin()) {
            val winner = if (activePlayer == 1) 2 else 1
            val message = "Player $winner wins!"
            displayEndGameDialog(message)
        } else if (checkForTie()) {
            displayEndGameDialog("It's a tie!")
        } else {
            if (activePlayer == 2) {
                activatePowerup(button)
            }
        }
        // Private function to check if the game has been won by a player
        private fun checkForWin() {
            // Check if any player has won horizontally
            for (i in 0..2) {
                if (gameBoard[i][0] == currentPlayer && gameBoard[i][1] == currentPlayer && gameBoard[i][2] == currentPlayer) {
                    // Player has won horizontally
                    handleWin()
                    return
                }
            }

            // Check if any player has won vertically
            for (i in 0..2) {
                if (gameBoard[0][i] == currentPlayer && gameBoard[1][i] == currentPlayer && gameBoard[2][i] == currentPlayer) {
                    // Player has won vertically
                    handleWin()
                    return
                }
            }

            // Check if any player has won diagonally
            if (gameBoard[0][0] == currentPlayer && gameBoard[1][1] == currentPlayer && gameBoard[2][2] == currentPlayer) {
                // Player has won diagonally (top-left to bottom-right)
                handleWin()
                return
            } else if (gameBoard[0][2] == currentPlayer && gameBoard[1][1] == currentPlayer && gameBoard[2][0] == currentPlayer) {
                // Player has won diagonally (top-right to bottom-left)
                handleWin()
                return
            }

            // Check if the game is a tie (no empty spaces left on the board)
            var emptySpaces = 0
            for (i in 0..2) {
                for (j in 0..2) {
                    if (gameBoard[i][j] == 0) {
                        emptySpaces++
                    }
                }
            }
            if (emptySpaces == 0) {
                handleTie()
                return
            }

            // Switch to the other player's turn
            switchPlayer()
        }

        // Private function to handle when a player has won
        private fun handleWin() {
            // Increment the winning player's score
            if (currentPlayer == 1) {
                playerOneScore++
            } else {
                playerTwoScore++
            }

            // Update the scoreboard text
            scoreboard.text = "$playerOneScore - $playerTwoScore"

            // Show a message indicating which player has won
            val message = if (currentPlayer == 1) "Player 1 wins!" else "Player 2 wins!"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            // Reset the game board and switch back to the first player's turn
            resetBoard()
            currentPlayer = 1
        }

        // Private function to handle when the game is a tie
        private fun handleTie() {
            // Show a message indicating that the game is a tie
            Toast.makeText(this, "It's a tie!", Toast.LENGTH_LONG).show()

            // Reset the game board and switch back to the first player's turn
            resetBoard()
            currentPlayer = 1
        }

        // Private function to reset the game board
        private fun resetBoard() {
            for (i in 0..2) {
                for (j in 0..2) {
                    gameBoard[i][j] = 0
                    val button = gameButtons[i][j]
                    button.text = ""
                    button.isEnabled = true
                }
            }
        }

        // Switches to the next player
        private fun switchPlayer() {
            if (currentPlayer == Player.ONE) {
                currentPlayer = Player.TWO
            } else {
                currentPlayer = Player.ONE
            }
            updateDisplay()
        }

        // Updates the display with the current game state
        private fun updateDisplay() {
            // Update player turn text
            playerTurnText.text = getString(R.string.player_turn, currentPlayer.toString())

            // Update scoreboard
            scoreboard.text = "$playerOneScore-$playerTwoScore"
        }

        // Resets the game board and score
        private fun resetGame() {
            // Reset game board
            for (button in buttons) {
                button.text = ""
                button.isEnabled = true
            }
            movesPlayed = 0
            gameBoard = Array(3) { CharArray(3) { ' ' } }

            // Reset score
            playerOneScore = 0
            playerTwoScore = 0
            updateDisplay()
        }

        // Shows a dialog prompting the user to confirm that they want to exit the game
        private fun showExitConfirmationDialog() {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.exit_confirmation_message)
                .setCancelable(false)
                .setPositiveButton(R.string.yes_exit) { _, _ ->
                    finish()
                }
                .setNegativeButton(R.string.no_stay) { dialog, _ ->
                    dialog.dismiss()
                }
            val dialog = builder.create()
            dialog.show()
        }
    }
}