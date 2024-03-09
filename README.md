# JMine 1.0

## Introduction

JMine is port to Java of the popular Windows game Minesweeper (or Winmine). The objective is to find all the mines without uncovering any in the quickest time possible. Although it is not necessary, the game is best played with a mouse having 2 or more buttons. A quick note on using a mouse:

- If your mouse has only one button, hold down the meta key on your keyboard to emulate a right-click. You will not be able to perform a two button click (not that I know of).
- If your mouse has two buttons, you are a little better off. You can left-click, right click, and two button click (pressing down both buttons at once).
- If your mouse has 3 buttons or more, assume button 1 to be a left click, button 3 to be a right click, and button 2 to be a two button click.

Now, with that out of the way...

## How to Play JMine

To start a new game, left-click on the smiling face.

Left-click on a tile to uncover it. After the first tile is uncovered, the timer is started.

If you uncover a mine, you lose. You cannot lose on the first click of the game.

If a square displays a number, that number represents the number of mines directly adjacent to that square (a maximum of 8, a minimum of zero - blank). Use these numbers to determine where you think a mine might be located.

You can mark a tile as a mine by right-clicking on it. A number at the top left keeps track of how many mines you have left to find.

### Tips

If you are not sure if a tile covers a mine, but you think it may (for example, if two tiles are known to cover one mine), you may mark those tiles as questionable by right-clicking twice on the mine. It will be marked with a question mark. Right click on the tile again to remove any markers.

If you have a mouse with two or more buttons, you can perform a two button click if the following are true:
1) The tile displays a number
1) You have marked adjacent squares as mines, and 
1) The total number of adjacent squares marked as mines equals the number displayed on the square, you may two button click on the numbered square to reveal all other hidden adjacent tiles.

Be careful though. If you have incorrectly marked a tile as a mine, you will uncover a mine in one of the other squares.

## Options

Right-click on the smiling face (or sometimes frowning face) to bring up the options dialog. Here you may choose from various levels of difficulty, read the high scores, or play a custom game of your own design.

## Disclaimer

JMine / Minesweeper has the potential to be addictive. If you feel yourself drawn away from the world around you, if you haven't been outdoors in a couple of days, if your only remaining objective in life is to beat a 150 second score on the expert level -- **Stop!** Take a deep breath, turn off your computer, and go outside.

## Credits:
JMine was originally developed by Brandon McPhail.

## Feedback and Contributions:
Feedback and contributions are welcome! If you encounter any issues, have suggestions for improvements, or want to contribute code, please submit a pull request or contact us at the JCPS Discord.

## License:
This project is licensed under the GPL v3.0. See the LICENSE file for more information.