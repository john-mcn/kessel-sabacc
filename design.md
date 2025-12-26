# Design Document

## Sabacc Rules (Kessel Sabacc)
Cards have rank 1 to 6, with two special cards:
- **Imposter**: Roll two *d6* dice and choose one to be the rank of the imposter card
- **Sylop**: Match the paired card's rank

There are 44 cards in the game, specifically, for each family, there are:
- 3 cards for each rank 1-6
- 3 **Imposter** cards
- 1 **Sylop** card

### Setup
- Each player initially has a random hand - a pair of cards with one of each family: one **blood** card (red) and one **sand** card (yellow).
- Each player has a **stock** with an equal amount of chips
- Each player has 3 **Shift Tokens**

### Gameplay
The goal is to *have the lowest difference between hand cards*. Two cards of equal rank (from each family) is a **Sabacc** hand, in this case a *lower rank* **Sabacc** wins. 

Players end their turn by either:
- **Drawing**: spend 1 chip (transfer from their **stock** to their **pot**) to draw 1 new card of either family into their hand, then discard either the drawn card or the previous card
- **Stand**: end their turn without spending any chips


A round ends after 3 turns by each player revealing their hand. If every player stands during a turn, the round ends prematurely and players reveal their hands.

To win a round, a player must have the best hand at the end of 3 turns.
- If multiple players have the same winning hand they are joint winners and each get back invested chips
- If a revealed hand has a pair of **Sylop** cards (a *Pure Sabacc*), that is the best hand in the game
- If no players have a **Sabacc** hand, *and* multiple players have an equal difference in their hand card ranks, then the lowest sum of ranks wins

The winner takes all invested chips back from their **pot** to their **stock**. Other players lose chips (**stock** -> **pot**):
- If they have a **Sabacc** hand, they lose 1 chip
- If they do not, they lose an amount of chips equal to the difference of their hand ranks

At the end of the reveal phase, if a player's **stock** is empty, they are eliminated. The winner of the game is the last remaining player.

#### Shift Tokens
Game modifiers that can be played once in the game, played before standing or drawing.

## Copyright
The game "Sabacc" was created for Star Wars, and as such is intellectual property of Lucasfilm.

This project is independent and not for commercial use. I do not own the name or game concept of "Sabacc".

---

## Technical
### Classes
`SabaccGame` which represents a single game of Sabacc.

Attributes:
- `players`, an array of `Player`s (array bc size won't change)
- `rounds`, a list of `SabaccRound` data (list bc unknown size)
- `buyIn`, integer amount that each player buys in, winner receives `buyIn * players.length` credits

Methods:
- TODO


`SabaccRound`?? which represents a single round of 3 turns in a `SabaccGame`.

Attributes:
- `tokensInPlay`, a list of `ShiftToken`s active (list bc unknown size)
- `players`, an array of `Player`s (array bc size won't change, no setter bc should be set by `SabaccGame`)
- 

Methods:
- TODO


`Person` which represents an individual outside of a `SabaccGame` context

Attributes:
- `credits`, integer value of credits owned
- `shiftTokens`, list of `ShiftToken`s owned (list bc can gain more)


  > `Player`, subclass of `Person` and represents an individual in the `SabaccGame` context
  >
  > Attributes:
  > - `hand`, array of `Card`s, the **Sand** and **Blood** cards in hand (array bc only 2)
  > - `selectedTokens`, array of `ShiftToken`s owned (when played set to null or special value e.g. UNAVAILABLE, array because can only have 3)
  >
  > Methods:
  > - TODO


`Card`

Attributes:
- `family`, ENUM {BLOOD, SAND}
- `rank`, ENUM {1-6, IMPOSTER, SYLOP}

Methods:
- Constructor, getters & setters (validation for rank)
- Compare rank (GT, LT, EQ)


`ShiftToken`, enum with values:
- TODO


`GameUtils`
Methods:
- Method to convert from integer to RANK
- Sort list of cards by winning status

## Notes
- Logic for drawing and choosing? Need a class/attribute for drawn card?