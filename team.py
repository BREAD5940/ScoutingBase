from statistics import mode

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
        self.isHelper=False
        self.startHabs=[]
        self.consistStartHab=1
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
        self.avGPSand = (self.avGPSand + (int(match[5])+int(match[6])+int(match[8])+int(match[9])))/2 #average
        self.avHPShip = (self.avHPShip + int(match[14]))/2
        self.avHPRocket = (self.avHPRocket +int(match[15]))/2
        self.avHPDrop = (self.avHPDrop + int(match[16]))/2
        self.avCShip = (self.avCShip + int(match[11]))/2
        self.avCRocket = (self.avCRocket +int(match[12]))/2
        self.avCDrop = (self.avCDrop + int(match[13]))/2
        if match[17]!="Assist":
            self.scaleLevels.append(int(match[17]))
        try:
            self.consistScaleLevel = mode(self.scaleLevels)
        except:
            print("multi num")
            self.consistScaleLevel = self.consistScaleLevel
        self.isHelper = self.isHelper or match[17]=="Assist" 
        self.startHabs.append(int(match[4]))
        try:
            self.consistStartHab = mode(self.startHabs)
        except:
            print("multi num")
            self.consistStartHab = self.consistStartHab
        # self.habs.append(match[]) #reeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
        self.avFoul = (self.avFoul + int(match[19]))/2
        self.avTech = (self.avTech +int(match[18]))
        if(match[20]=="true"):
            self.totalYellow +=1
        if(match[21]=="true"):
            self.totalRed +=1
        if match[23]=="true":
            self.borks += 1

    def toString(self):
        string= "\n%4d  --  %s\n"%(self.number, self.name)
        string+="-----------------------------------------------------------------------\n\n"
        string+="Most common starting hab: %1d\n\n"%(self.consistStartHab)
        string+="Average game pieces in sandstorm: %2d\n\n"%(self.avGPSand)
        string+="AVERAGE HATCH PANELS:                     AVERAGE CARGO:\n"
        string+="	Cargo ship: %2d                             Cargo ship: %2d\n"%(self.avHPShip, self.avCShip)
        string+="	Rocket: %2d                                 Rocket: %2d\n"%(self.avHPRocket, self.avCRocket)
        string+="	Dropped: %2d                                Dropped: %2d\n\n"%(self.avHPDrop, self.avCDrop)
        string+="Most common scale level: %1d                 Is assistant? %s\n\n"%(self.consistScaleLevel, self.isHelper)
        string+="Average fouls: %3d                         Average tech fouls: %3d\n\n"%(self.avFoul, self.avTech)
        string+="TOTAL yellow cards: %3d                    TOTAL red cards: %3d\n\n"%(self.totalYellow, self.totalRed)
        string+="TOTAL E-stops/breaks: %3d\n\n"%(self.borks)
        string+="-------------------------------------------------------------------------\n"
        string+="NOTES:\n\n"
        string+="Qualifier number     |  Note\n"
        for match in self.matches:
            print(match)
            try:
                string+="%2d                   | %s\n"%(int(match[1]), match[24])
            except:
                print("nullllllllllllllllllllllllllllllllllll")
        string+="\n\n\nMATCHES:\n\n"
        string+="Number | Position | Starting Hab Level | S.S. Cargo in Ship | SS Cargo in Rocket | SS Cargo Dropped | SS Hatch in Ship | SS Hatch in Rocket | SS Hatch Dropped | Game Cargo in Ship | Game Cargo in Rocket | Game Cargo Dropped | Game Hatch in Ship | Game Hatch in Rocket | Game Hatch Dropped | Scale | Tech Foul | Foul | Yellow Card | Red Card | Broke | Final Points | Notes\n"
        for match in self.matches:
            try:
                string+=match[0]+", "+match[1]+", "+match[2]+", "+match[3]+", "+match[4]+", "+match[5]+", "+match[6]+", "+match[7]+", "+match[8]+", "+match[9]+", "+match[10]+", "+match[11]+", "+match[12]+", "+match[13]+", "+match[14]+", "+match[15]+", "+match[16]+", "+match[17]+", "+match[18]+", "+match[19]+", "+match[20]+", "+match[21]+", "+match[22]+", "+match[23]+", "+match[24]+"\n"
            except:
                print("nulllllllllllllllllllllll")
                string+=match[0]+", "+match[1]+", "+match[2]+", "+match[3]+", "+match[4]+", "+match[5]+", "+match[6]+", "+match[7]+", "+match[8]+", "+match[9]+", "+match[10]+", "+match[11]+", "+match[12]+", "+match[13]+", "+match[14]+", "+match[15]+", "+match[16]+", "+match[17]+", "+match[18]+", "+match[19]+", "+match[20]+", "+match[21]+", "+match[22]+", "+match[23]+"\n"

        return string

