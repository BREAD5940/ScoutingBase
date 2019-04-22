import json
import csv
from team import Team
import os
import functions


teams = []
mainDirectory = "ScoutingBase/sac_2019/"
dataDirectory = mainDirectory+"data/"
resultDirectory = mainDirectory+"teams/"
groupDirectory = mainDirectory+"sorts/"
with open(mainDirectory+"teams.csv", newline='') as csvfile:
     spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
     for row in spamreader:
         teams.append(Team(int(row[0]), row[1]))

print("\n"+
"                            ***********************                             \n"+
"                        *******************************                         \n"+
"                      *******+                   ?*******                       \n"+
"                   ******                             ******                    \n"+
"                :********                             ********:                 \n"+
"             :***********                            :***********~              \n"+
"           *******    ****                           ****    *******            \n"+
"         ******        ***                           ***        ******          \n"+
"       $*****          ****                         ****          ******        \n"+
"      *****            ****                         ***:            *****       \n"+
"     ****               ***                         ***               ****      \n"+
"    ****                ****                       ****                ****     \n"+
"    ***                  ***                       ***                  ***~    \n"+
"    ****+                ****                     ****                :*****    \n"+
"   *******               ****                     ***                *******    \n"+
"  **** ****               ***+                   ?***               **** ****   \n"+
" ****   *****             ****                   ****             *****   ****  \n"+
" ***      ****             ***                   ***             ****      ***  \n"+
"****       *****           ****                 ****           *****       **** \n"+
"***:         ****           ***                 ***           ****          *** \n"+
"***           *****         ****               ****         *****           *** \n"+
"***            ~****     ~***************************=     ****~            ***:\n"+
"***              ***** ********************************* *****              *** \n"+
"***              **********                         **********              *** \n"+
"****           *********                               *********           **** \n"+
" ****        ******                                         ******        ****  \n"+
"  *****  :*******                                             *******   *****   \n"+
"    ***********                                                 ************    \n"+
"      *****                                                         *****       \n"+
"                                                                                \n"+
"\n\nBREAD scouting base. Current robot: Croissant\t"+
"Current working directory: %s\n"%(mainDirectory))


directory = os.fsencode(dataDirectory)

for file in os.listdir(directory):
     filename = os.fsdecode(file)
     if filename.endswith(".csv"): 
         print(filename)
         functions.addFile(dataDirectory+filename, teams)
         continue
     else:
         continue

functions.theLargeCsv(dataDirectory)

functions.booleans(groupDirectory, teams)

# print(teams[59].toString())
for t in teams:
    file = open(resultDirectory+str(t.number)+".txt","w") 
    
    file.write(t.toString()) 
    
    file.close() 


