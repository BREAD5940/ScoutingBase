import os
import csv

def addFile(file, teams):
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

        
def theLargeCsv(dataDirectory):
    outfile = open("largeCSV.csv", "w")

    csvdirectory = os.fsencode(dataDirectory)

    for file in os.listdir(csvdirectory):
        filename = os.fsdecode(file)
        if filename.endswith(".csv"): 
            print(filename)
            word1=writy(dataDirectory+filename)
            for w in word1:
                outfile.write(w)            
            continue
        else:
            continue

def addToGroup(groupDir, group, team):
    file = open(groupDir+group+".txt","a")

    if group not in team.groups:
        file.write(str(team.number)+"\n")
        team.groups.append(group)
    
    file.close() 
    
def booleans(groupDir, teams):
    # jankily clear files
    file = open(groupDir+"LevelThreeClimbers"+".txt", "w")
    file.close()
    file = open(groupDir+"LevelTwoClimbers"+".txt", "w")
    file.close()
    file = open(groupDir+"LevelTwoStarters"+".txt", "w")
    file.close()
    file = open(groupDir+"GotRed"+".txt", "w")
    file.close()
    file = open(groupDir+"HasBorked"+".txt", "w")
    file.close()

    for t in teams:
        if 3 in t.scaleLevels:
            addToGroup(groupDir, "LevelThreeClimbers", t)
        if 2 in t.scaleLevels:
            addToGroup(groupDir, "LevelTwoClimbers", t)
        if 2 in t.startHabs:
            addToGroup(groupDir, "LevelTwoStarters", t)
        if t.totalRed >= 1:
            addToGroup(groupDir, "GotRed", t)
        if t.borks >= 1:
            addToGroup(groupDir, "HasBorked", t)