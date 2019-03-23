import json
import csv
from team import Team
from match import Match


teams = []
with open('ScoutingBase/teams.csv', newline='') as csvfile:
     spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
     for row in spamreader:
         teams.append(Team(int(row[0]), row[1]))

def addFile(file):
    print(file)
    with open(file, newline='') as csvfile:
        smap = csv.reader(csvfile, delimiter=',', quotechar='|')
        for m in smap:
            # print(m)
            i=0
            while(i<len(teams)):
                if(int(m[2])==teams[i].number):
                    teams[i].addMatch(m)
                i+=1

        
addFile('ScoutingBase/max.csv')
addFile('ScoutingBase/claire.csv')
# addFile('ScoutingBase/geran.csv')
addFile('ScoutingBase/nick.csv')
addFile('ScoutingBase/thomas.csv')

print(teams[59].toString())
for t in teams:
    file = open(str(t.number)+".txt","w") 
    
    file.write(t.toString()) 
    
    file.close() 

