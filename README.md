# arkham-calc

ArkhamCalc is a small utility that is designed to be used in conjunction with Arkham Horror, a 2005 board game published by Fantasy Flight Games. Many of the encounters in Arkham Horror involve rolling a number of dice and counting each success, which in most cases require rolling a five or six. Given the number of dice that you are allowed to roll and the number of "successes" required to pass the encounter, ArkhamCalc calculates the probability of succeeding in the encounter. ArkhamCalc can also take game conditions into account, such as being "blessed" or "cursed", which affect the probabilities of success beyond the standard calculation.

ArkhamCalc is written in Java and uses the Android SDK. It is distributed for free in the Android Market. https://play.google.com/store/apps/details?id=com.kolita.arkhamcalc

Project logo adapted from Arkahm Horror Dice Set. https://www.fantasyflightgames.com/en/news/2009/11/30/the-time-is-near/

Feedback? arkhamcalc at gmail dot com

## Dice / Difficulty

When performing any type of skill check in Arkham Horror, a roll of the dice is used to determine if the character passes or fails the check. In general, the Difficulty of a skill check is defined as the number of 5s or 6s (“successes”) that must be rolled to pass the check. Use the Dice slider to indicate exactly how many dice the character will be rolling for a given check. If the number of Dice being rolled is less than the Difficulty of the check (e.g. you have one die to get two successes), the probability you will pass is 0%. However, there is no single skill check which is guaranteed to pass 100% of the time; the highest possible probability for a single check is reported as “>99.9%”.

## Chances

There are situations in the game where it is possible to attempt a certain skill check more than one time. For example, combat can continue as long as your character is conscious; if your character is fighting a monster that deals one stamina damage per attack, and your character has three stamina, you have three chances to pass the combat check. The Chances slider indicates how many times a check can be attempted and calculates the probability that at least one of the attempts passes. Put another way, when the Chances slider is set to ‘n’, it calculates 1.0 subtracted by the probability that you will fail all ‘n’ chances for the same check.

## Blessed / Cursed

Blessings and Curses are statuses that your character can obtain throughout the game. When Blessed, a character can count 4s as a success during a skill check. When Cursed, a character cannot count 5s as a success during a skill check. By rule, a character cannot be Blessed and Cursed at the same time.

## Add One

Certain skills allow your character to add 1 to the value of each die rolled for a certain check. These skills, introduced in the Dunwich expansion, are Dodge, Grapple, Hide, Library Use, Psychology, and Spot Hidden. This skill can “stack” with a Blessing or Curse; for example, if you are both Blessed and have an Add One skill, then for the relevant skill check, all 3s, 4s, 5s, and 6s rolled will count as successes. A Curse and an Add One skill cancel each other out, and all other things equal, the “base calc” (Dice / Difficulty calc with no options turned on) is the same as the calc with both of these options turned on.

## Shotgun

The Shotgun is a weapon used during combat checks which counts each 6 rolled as two successes each. For example, during a combat check with the Shotgun, if the difficulty of the check is three, and you roll a 5 and a 6, you succeed at the check, since the 5 counts for one success and the 6 counts for two successes. Note that the Shotgun does not increase the probability of success if the Difficulty of the check is one, since rolling a 6 with or without the Shotgun will give you enough successes to succeed at the check. Also note that the Shotgun only considers “natural 6s” to be worth two successes; a check with both a Shotgun and an Add One skill counts a roll of 5 as one success, even though the value of the die has been incremented to 6 by the Add One skill.

## Mandy

Mandy Thompson is an investigator that has a unique ability not replicated by any other item or character in the game: once per turn, she can let any character reroll any of the failures from his/her skill check. This is a far more powerful ability than simply rerolling a skill check, since the character can keep all of the success dice rolled as part of the initial roll. For example, if a character has a check with two Difficulty and can only roll two Dice, and on the initial roll he rolls one success and one failure, Mandy’s ability lets the character reroll the one failure to attempt to get a second success. Note that if the Chances slider is used, after the first Chance, all subsequent Chance calculations ignore Mandy’s ability, since she can only use her ability once per turn.

## Reroll Ones

Certain skills allow your character to, once per skill check, reroll all dice showing a result of 1. These skills are Camouflage, Linguistics, Listen, Persuade, Run, and Wrestle, introduced in the Kingsport expansion; and Clairvoyant, introduced in the Miskatonic expansion. Note that if the Chances slider is used, after the first Chance, all subsequent Chance calculations ignore the Reroll Ones skill, since these skills can only be used once per check.

## Skids

Skids O'Toole is an investigator from the Innsmouth Horror expansion with the following ability: once per skill check, he is allowed to roll two additional dice for each 1 he originally rolls. For example, if Skids has a skill check with two Difficulty and he is only allowed to roll one Dice, if he rolls a 1, he is then able to use his ability and roll two additional Dice to attempt to get two successes. Note that if the Chances slider is used, after the first Chance, all subsequent Chance calculations ignore Skids' ability, since it can only be used once per check.

## Frequently Asked Questions

Q: Are there any scenarios where being Cursed can increase your probability of success, and being Blessed can decrease your probability of success?

A: Yes. Consider the following scenario. When you have one die to do two damage, the only way you can win the fight is if you roll a six (because you have a shotgun). If you are playing with Mandy's ability and you are neither cursed nor blessed, and you roll a five, you cannot reroll, because you got a success...although that success was worthless, because it only did one damage. So you lose the fight. Now, imagine the exact scenario above, except you are cursed. If you roll a five, it doesn't count as a success, so you are able to roll again, using Mandy's ability. So, when you are cursed, you actually have two chances to roll a six, instead of one chance when not cursed. Similar logic can be applied to the scenario above when you are blessed.

## Version History

### v1.6, released 7/28/12

 - Long press labels on calculator to view associated help topics
 - Improved layout and font on help screen
 - Visit Wiki menu option
 - Changed color of results label

### v1.5, released 2/15/12

 - Added support for Android 4 (Ice Cream Sandwich)
 - Added Skids calc
 - Increased chances slider to max 6
 - Updating layout for improved usability
 - Fixing orientation toast bug

### v1.4, released 11/21/11

 - Added help screen
 - Moved unit tests out of distributed .apk
 - Showed >99.9% instead of 100%
 - Showed <0.1% instead of 0% in cases where it's not impossible to succeed

### v1.3, released 9/18/11

 - Added Reroll Ones calc
 - Added Add One calc
 - Feedback menu option
 - Scrollview added to UI

### v1.2, released 7/31/11

 - Added Mandy calc
 - Increased toughness slider to max 6

### v1.1, released 7/10/11

 - Added Shotgun calc
 - Added Number of Chances slider

### v1.0, released 7/4/11

 - Initial release
 - Number of Dice, Toughness; Blessed, Cursed
