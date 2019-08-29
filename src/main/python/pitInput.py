import PySimpleGUI as sg
# import main

# as dumb as it sounds, this is linked in with the rest of the base. line 165 (ish)

robotColumnTwoElectricBoogaloo = [
          [sg.Text('         '), sg.Checkbox('Climbs')],     
          [sg.Text("\t\tLevel:", size=(19, 1)), sg.Radio('2', 'LVL'), sg.Radio('3', 'LVL'), sg.Radio('Assist', 'LVL')],      
          [sg.Text('\tType of intake:', size=(18, 1)), sg.Checkbox('Cargo'), sg.Checkbox('Hatch')], 
          [sg.Text('\tRocket Reach: '), sg.Radio('1', 'PLACE'), sg.Radio('2', 'PLACE'), sg.Radio('3', 'PLACE')],
          [sg.Text('\tMechanical Issues: '), sg.Slider(range=(0,5), orientation='h', default_value=1)],
          [sg.Text('         '), sg.Checkbox('Has camera')],
          [sg.Text('         '), sg.Checkbox('Has sensor/limelight')],
          [sg.Text('         '), sg.Checkbox('Uses presets')],
          [sg.Text('\tCenter of Gravity: '), sg.Radio('High', 'COG'), sg.Radio('Low', 'COG'), sg.Radio('Unknown', 'COG')],
          [sg.Text('         '), sg.Checkbox('Can reach over cargo')], 
          [sg.Text('         '), sg.Checkbox('Rampbot')],
          [sg.Text('\tRobot nicknames (comma-seperated): ')],
          [sg.Text('\t  '), sg.InputText()]
]

robotColumn = [
          [sg.Text('Robot')],      
          [sg.Column(robotColumnTwoElectricBoogaloo)]
]

sandColumn = [
          [sg.Text('\tSandstorm')],
          [sg.Text('\t\tStarting Hab: '), sg.Radio('1', 'START'), sg.Radio('2', 'START')],
          [sg.Text('\t\tMain Strategy: ')],
          # yes, I manually made this column. please shoot me.
          [sg.Text('\t\t   '), sg.Checkbox('Hatch on close side rocket')],
          [sg.Text('\t\t   '), sg.Checkbox('Hatch on far side rocket')],
          [sg.Text('\t\t   '), sg.Checkbox('Hatch on front cargo ship')],
          [sg.Text('\t\t   '), sg.Checkbox('Hatch somewhere else on the cargo ship')],
          [sg.Text('\t\t   '), sg.Checkbox('Cargo in cargo ship')],
          [sg.Text('\t\t   '), sg.Checkbox('Just across the line')],
          [sg.Text('\t\t   '), sg.Checkbox('Multi game piece (plz give notes)')],
          [sg.Text('\t\t   '), sg.Checkbox('Other (describe in notes)')],
          [sg.Text('\t\t    Notes: '), sg.Multiline()],
          [sg.Text('\t\tControl: '), sg.Radio('Driver + camera', 'CTRL'), sg.Radio('Auto', 'CTRL')],
          
]

teleColumn = [
          [sg.Text('\tMain Teleop Strategy: ')],
          # yes, I manually made this column. please shoot me.
          [sg.Text('\t   '), sg.Checkbox('Ship cargo and hatch')],
          [sg.Text('\t   '), sg.Checkbox('Ship cargo')],
          [sg.Text('\t   '), sg.Checkbox('Ship hatch')],
          [sg.Text('\t   '), sg.Checkbox('Rocket cargo and hatch')],
          [sg.Text('\t   '), sg.Checkbox('Rocket cargo')],
          [sg.Text('\t   '), sg.Checkbox('Rocket hatch')],
          [sg.Text('\t   '), sg.Checkbox('Mixed rocket/ship (give notes)')],
          [sg.Text('\t   '), sg.Checkbox('Defense')],
          [sg.Text('\t   '), sg.Checkbox('Flexible (give notes)')],
          [sg.Text('\t   '), sg.Checkbox('Other (describe in notes)')],
          [sg.Text('\t    Notes: '), sg.Multiline()],
]

otherStratColumn = [
          [sg.Text('\tGame Pieces per Match: '), sg.Slider(range=(0,40), orientation='h'), sg.Text('\tCycle Time (average): '), sg.Slider(range=(1,40), orientation='h')],
          [sg.Text('\tPrefered Game Piece: '), sg.Checkbox('Hatch'), sg.Checkbox('Cargo')]
]

stratColumn = [
          [sg.Text('Strategy')],
          [sg.Column(sandColumn), sg.Column(teleColumn)],
          [sg.Column(otherStratColumn)]
          
]

hpPrefsColumn = [
        [sg.Text('\tHuman Player: ')],
        [sg.Text('\t   '), sg.Checkbox('Relies on')],
        [sg.Text('\t   '), sg.Checkbox('Would like, but flexible')],
        [sg.Text('\t   '), sg.Checkbox('No preference')]
]

stratPrefsColumn = [
        [sg.Text('\tStrategy:')],
        [sg.Text('\t   '), sg.Checkbox('Has a strong strategy, sticks to it')],
        [sg.Text('\t   '), sg.Checkbox('Would prefer their own, but can switch')],
        [sg.Text('\t   '), sg.Checkbox('Very flexible')]
]

prefsColumn = [
          [sg.Text('Preferences')],
          [sg.Column(hpPrefsColumn), sg.Column(stratPrefsColumn)]
]

notesColumn = [
          [sg.Text('Notes: ')],
          [sg.Multiline(size=(100, 10))]
]

layout = [    
          [sg.Text('Team Number: '), sg.InputText(), sg.Text('Team Name: '), sg.InputText()],
          [sg.Column(robotColumn, background_color='#e8e6e5'), sg.Column(stratColumn, background_color='#e8e6e5')],
          [sg.Column(prefsColumn, background_color='#e8e6e5'), sg.Column(notesColumn, background_color='#e8e6e5')],
          [sg.Submit(), sg.Cancel()]      
         ]

window = sg.Window('Pit Scouting Data Entry', resizable=True).Layout(layout)         
button, values = window.Read()

'''
0: number
1: name
2: Climbs (t/f)
3: Climb level 2
4: climb level 3
5: climb assist
6: cargo intake
7: hatch intake
8: reach level 1
9: reach level 2
10: reach level 3
11: mechanical issues (float)
12: camera
13: sense/lime
14: presets
15: cog high
16: cog low
17: cog unknown
18: long arm
19: rampy boi
20: nicknames
21: level 1 start
22: level 2 start
23: hatch close rocket
24: hatch far rocket
25: hatch front
26: hatch cargo
27: cargo
28: line
29: multi
30: other
31: sandstorm strat notes
32: driver + cam
33: auto
34: ship c + h
35: ship c
36: ship h
37: rocket c+h
38: rocket c
39: rocket h
40: mixed rocket/ship
41: defense
42: flexible
43: other
44: teleop strat notes
45: gps per match
46: av cycle time
47: pref h
48: pref c
49: relies on hp
50: would like hp
51: no pref on hp
52: strong strat
53: pref strat
54: flex strat
55: notes
'''
print(values)
i=0
while i < len(values):
        if isinstance(values[i], str):
                values[i] = values[i].replace('\n', '   ')
        i=i+1

climbLvl = 1
reachLvl=0
cog='u'
startLvl=1
sandStrat=[]
autoDrive=False
teleStrat=[]
gpPref=[]
humanPrefs=[]
stratPrefs=[]

if values[3]:
        climbLvl=2
elif values[4]:
        climbLvl=3

if values[8]:
        reachLvl=1
elif values[9]:
        reachLvl=2
elif values[10]:
        reachLvl=3

if values[15]:
        cog='h'
elif values[16]:
        cog='l'
elif values[17]:
        cog='u'

if values[21]:
        startLvl=1
elif values[22]:
        startLvl=2

if values[23]:
        sandStrat.append("hatch close side rocket")
if values[24]:
        sandStrat.append("hatch far side rocket")
if values[25]:
        sandStrat.append("hatch front cargo ship")
if values[26]:
        sandStrat.append("hatch cargo ship")
if values[27]:
        sandStrat.append("cargo cargo ship")
if values[28]:
        sandStrat.append("across hab line")
if values[29]:
        sandStrat.append("god-tier multi piece")
if values[30]:
        sandStrat.append("other")

if values[32]:
        autoDrive=False
elif values[33]:
        autoDrive=True

if values[34]:
        teleStrat.append("cargo and hatch cargo ship")
if values[35]:
        teleStrat.append("cargo cargo ship")
if values[36]:
        teleStrat.append("hatch cargo ship")
if values[37]:
        teleStrat.append("cargo and hatch rocket")
if values[38]:
        teleStrat.append("cargo rocket")
if values[39]:
        teleStrat.append("hatch rocket")
if values[40]:
        teleStrat.append("rocket/cargo ship mixed")
if values[41]:
        teleStrat.append("defense")
if values[42]:
        teleStrat.append("flexible")
if values[43]:
        teleStrat.append("other")

if values[47]:
        gpPref.append("h")
if values[48]:
        gpPref.append("c")

if values[49]:
        humanPrefs.append("relies on")
if values[50]:
        humanPrefs.append("would like")
if values[51]:
        humanPrefs.append("no pref")

if values[52]:
        stratPrefs.append("strong plan")
if values[53]:
        stratPrefs.append("prefers their plan")
if values[54]:
        stratPrefs.append("flexible on plan")

# outFile = open(main.dataDirectory+"pitData.csv", "a")
# outFile.write(str(values[0])+","+str(values[2])+","+str(climbLvl)+","+str(values[5])+","+str(values[6])+","+str(values[7])+","+str(reachLvl)+","+str(values[11])+","+str(values[12])+","+str(values[13])+","+str(values[14])+","+cog+","+str(values[18])+","+str(values[19])+","+str(startLvl)+","+str(sandStrat)+","+"|"+str(values[31])+"|"+","+str(autoDrive)+","+str(teleStrat)+","+"|"+str(values[44])+"|"+","+str(values[45])+","+str(values[46])+","+str(gpPref)+","+str(humanPrefs)+","+str(stratPrefs)+","+"|"+str(values[20])+"|"+","+"|"+str(values[55])+"|\n")
