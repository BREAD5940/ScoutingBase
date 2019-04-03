import PySimpleGUI as sg

# Very basic window.  Return values as a list

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
          [sg.Text('\t  '), sg.Multiline()]
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

print(button, values)