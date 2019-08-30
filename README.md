# Connect-Four-AI--Java---UCI-ICS-45J

This Connect Four AI comes with the algorithm and an interface to play the game against the AI.

This AI is built with a mini-max algorithm to evaluate a score a move will yield. The AI looks 4 steps ahead to assess the net value yielded by a move. This value can be modified for further optimization.

Weighted values for the board states are for the following:
- Open ended 3 in a row (-XXX-)
- 3 in a row in the negative diagonal direction
- 3 in a row in the positive diagonal direction
- 3 in a row in the vertical direction
- 3 in a row in the horizontal direction
- 2 in a row in the negative diagonal direction
- 2 in a row in the positive diagonal direction
- 2 in a row in the vertical direction
- 2 in a row in the horizontal direction

The values chosen for each state being sought by the AI is arbitrarily chosen based on a variety of game theory articles with respect to connect four

This is not the most optimal AI, and needs a lot more optimization to the heuristic evaluations for improvement.
