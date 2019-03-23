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

def writy(fl):
    toReturn = []
    with open(fl, newline='') as csvfile:
        smap = csv.reader(csvfile, delimiter=',', quotechar='|')
        for m in smap:
            if m[0]=="Qualifier":
                bot="R 0"
                lvl="0"
                bork="0"
                none="0"
                scale="1"
                if m[3]=="Red 1":
                    bot="R 1"
                elif m[3]=="Red 2":
                    bot="R 2"
                elif m[3]=="Red 3":
                    bot="R 3"
                elif m[3]=="Blue 1":
                    bot="B 1"
                elif m[3]=="Blue 2":
                    bot="B 2"
                elif m[3]=="Blue 3":
                    bot="B 3"

                if m[4]=="2":
                    lvl="1"
                else:
                    lvl="0"
                
                if m[22]=="true":
                    bork="1"
                else:
                    bork="0"

                if m[17]=="0":
                    none="1"
                else:
                    none="0"

                if m[17]=="1":
                    scale="1"
                elif m[17]=="2":
                    scale="2"
                elif m[17]=="3":
                    scale="3"

                toReturn.append(str(m[1])+","+bot+","+str(m[2])+","+lvl+","+str(m[5])+","+str(m[8])+","+str(m[6])+","+str(m[9])+","+"0"+","+m[11]+","+m[14]+","+m[12]+","+m[15]+","+bork+","+none+","+scale+"\n")
    return toReturn

        
def theLargeCsv():
    file = open("largeCSV.csv", "w")
    word1=writy('ScoutingBase/max.csv')
    for w in word1:
        file.write(w)
    word2=writy('ScoutingBase/claire.csv')
    for w in word2:
        file.write(w)
    word3=writy('ScoutingBase/geran.csv')
    for w in word3:
        file.write(w)
    word4=writy('ScoutingBase/nick.csv')
    for w in word4:
        file.write(w)
    word5=writy('ScoutingBase/thomas.csv')
    for w in word5:
        file.write(w)
    word6=writy('ScoutingBase/missing.csv')
    for w in word6:
        file.write(w)
    file.close()


addFile('ScoutingBase/max.csv')
addFile('ScoutingBase/claire.csv')
addFile('ScoutingBase/geran.csv')
addFile('ScoutingBase/nick.csv')
addFile('ScoutingBase/thomas.csv')
addFile('ScoutingBase/missing.csv')

print(teams[59].toString())
for t in teams:
    file = open(str(t.number)+".txt","w") 
    
    file.write(t.toString()) 
    
    file.close() 

theLargeCsv()

