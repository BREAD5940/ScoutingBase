import stats

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
        self.consistScaleLevel=0
        self.isHelper=False
        self.consistStartHab=1
        self.consistOffHab=False
        self.avFoul=0.0
        self.avTech=0.0
        self.totalYellow=0
        self.totalRed=0
        self.eStops=0
        self.borks=0

    #add match row
    def addMatch(self,match):
        self.matches.append(match)
        self.avGPSand = Statistics.mean(self.avGPSand, )

