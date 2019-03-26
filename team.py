from statistics import mode
from scipy import stats

class Team:

    def __init__(self, number, name):
        self.name = name
        self.number = number
        self.matches = []
        #averages
        self.avGPSand=0.0
        self.avHPShip=0.0
        self.avHPRocket=0.0
        self.avHPDrop=0.0
        self.avCShip=0.0
        self.avCRocket=0.0
        self.avCDrop=0.0
        self.scaleLevels = []
        self.consistScaleLevel=0
        self.maxScaleLevel=0
        self.isHelper=False
        self.startHabs=[]
        self.consistStartHab=1
        self.maxStartHab=1
        # self.habs = [False]
        # self.consistOffHab=False
        self.avFoul=0.0
        self.avTech=0.0
        self.totalYellow=0
        self.totalRed=0
        # self.eStops=0
        self.borks=0

    #add match row
    def addMatch(self,match):
        self.matches.append(match)
        self.avGPSand = (self.avGPSand + (float(match[5])+float(match[6])+float(match[8])+float(match[9])))/2 #average
        self.avHPShip = (self.avHPShip + float(match[14]))/2
        self.avHPRocket = (self.avHPRocket +float(match[15]))/2
        self.avHPDrop = (self.avHPDrop + float(match[16]))/2
        self.avCShip = (self.avCShip + float(match[11]))/2
        self.avCRocket = (self.avCRocket +float(match[12]))/2
        self.avCDrop = (self.avCDrop + float(match[13]))/2
        if match[17]!="Assist":
            self.scaleLevels.append(float(match[17]))
        try:
            # data = Counter(self.scaleLevels)
            # data.most_common()   # Returns all unique items and their counts
            # data.most_common(1)  # Returns the highest occurring item
            self.consistScaleLevel = mode(self.scaleLevels)
        except:
            print("multi num")
            self.consistScaleLevel = self.consistScaleLevel
        self.maxScaleLevel = max(self.scaleLevels)
        self.isHelper = self.isHelper or match[17]=="Assist" 
        self.startHabs.append(float(match[4]))
        try:
            self.consistStartHab = mode(self.startHabs)
        except:
            print("multi num")
            self.consistStartHab = self.consistStartHab
        self.maxStartHab = max(self.startHabs)
        # self.habs.append(match[]) #reeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
        self.avFoul = (self.avFoul + float(match[19]))/2
        self.avTech = (self.avTech +float(match[18]))
        if(match[20]=="true" or match[20]=="1"):
            self.totalYellow +=1
        if(match[21]=="true" or match[21]=="1"):
            self.totalRed +=1
        if match[23]=="true" or match[23]=="1":
            self.borks += 1

    def toString(self):
        string= "\n%4d  --  %s\n"%(self.number, self.name)
        string+="-----------------------------------------------------------------------\n\n"
        string+="Most common starting hab: %f                 Highest starting hab: %f\n\n"%(self.consistStartHab, self.maxStartHab)
        string+="Average game pieces in sandstorm: %f\n\n"%(self.avGPSand)
        string+="AVERAGE HATCH PANELS:                     AVERAGE CARGO:\n"
        string+="	Cargo ship: %f                             Cargo ship: %f\n"%(self.avHPShip, self.avCShip)
        string+="	Rocket: %f                                 Rocket: %f\n"%(self.avHPRocket, self.avCRocket)
        string+="	Dropped: %f                                Dropped: %f\n\n"%(self.avHPDrop, self.avCDrop)
        string+="Most common scale level: %f                 Highest scale level: %f\n\n"%(self.consistScaleLevel, self.maxScaleLevel)
        string+="Is assistant? %s\n\n"%(self.isHelper)
        string+="Average fouls: %f                         Average tech fouls: %f\n\n"%(self.avFoul, self.avTech)
        string+="TOTAL yellow cards: %f                    TOTAL red cards: %f\n\n"%(self.totalYellow, self.totalRed)
        string+="TOTAL E-stops/breaks: %f\n\n"%(self.borks)
        string+="-------------------------------------------------------------------------\n"
        string+="NOTES:\n\n"
        string+="Qualifier number     |  Note\n"
        for match in self.matches:
            # print(match)
            try:
                string+="%2d \t\t| %s\n"%(float(match[1]), match[24])
            except:
                print("nullllllllllllllllllllllllllllllllllll")
        string+="\n\n\nMATCHES:\n\n"
        string+="Number | Position | Starting Hab Level | S.S. Cargo in Ship | SS Cargo in Rocket | SS Cargo Dropped | SS Hatch in Ship | SS Hatch in Rocket | SS Hatch Dropped | Game Cargo in Ship | Game Cargo in Rocket | Game Cargo Dropped | Game Hatch in Ship | Game Hatch in Rocket | Game Hatch Dropped | Scale | Tech Foul | Foul | Yellow Card | Red Card | Broke | Final Points | Notes\n"
        for match in self.matches:
            try:
                string+=match[0]+"\t"+match[1]+"\t"+match[2]+"\t"+match[3]+"\t"+match[4]+"\t"+match[5]+"\t"+match[6]+"\t"+match[7]+"\t"+match[8]+"\t"+match[9]+"\t"+match[10]+"\t"+match[11]+"\t"+match[12]+"\t"+match[13]+"\t"+match[14]+"\t"+match[15]+"\t"+match[16]+"\t"+match[17]+"\t"+match[18]+"\t"+match[19]+"\t"+match[20]+"\t"+match[21]+"\t"+match[22]+"\t"+match[23]+"\t"+match[24]+"\n"
            except:
                print("nulllllllllllllllllllllll")
                string+=match[0]+", "+match[1]+", "+match[2]+", "+match[3]+", "+match[4]+", "+match[5]+", "+match[6]+", "+match[7]+", "+match[8]+", "+match[9]+", "+match[10]+", "+match[11]+", "+match[12]+", "+match[13]+", "+match[14]+", "+match[15]+", "+match[16]+", "+match[17]+", "+match[18]+", "+match[19]+", "+match[20]+", "+match[21]+", "+match[22]+", "+match[23]+"\n"

        return string

