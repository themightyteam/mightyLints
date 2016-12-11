# Mighty Lints

Lints are sooooo bored in this museum’s room, but they know how to get some fun! They have formed two teams to play a match. You have to help Cyan Team score to win.


## How to score
 - 1 point if an enemy lint is evaporated (by collision or firing at it).
 - 50 points if a normal lint collides with a big lint of the other team. You have to wait 3 seconds to score again.


You win if you reach 500 points before the orange team does.


There are three types of lints:
 - Normal lint: only moves to reach a big lint of the other team.
 - Weaponed lint: armed(?) with missiles, can shoot other lints.
 - Big lint: Try to escape from your enemies.


_Controls_: Arrow or ASDW for moving. Space for firing.


This game has been written by a team composed of:


 - hmightypirate: idea, code, AI, procedural generated lint sprites.
 - punkto: idea, code, background, minimap, music, sound effects.
 - eLeDe: idea, code, logic, sound effects, initial screens.

#Tools

We have used LIBGDX, as in our past games. This time we have tested Ashley as our Entity System.
GIMP and a python based procedural generator for sprites and background.
Sound effects: Sfxr and Audacity
Music: Milkytracker


Keywords:
HTML5
Web
Java
arcade
battle
team
ai



# Run the game:

Desktop:
./gradlew desktop:run

HTML:
./gradlew html:dist

cd ./html/build/dist ; python -m SimpleHTTPServer ; cd ../../../

Use your favorite browser and point it to http://0.0.0.0:8000


#LINKS
Check this link for the LD Entry: TBD