import json
import csv


teams = []
with open('teams.csv', newline='') as csvfile:
     spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
     for row in spamreader:
         teams.append(Team(row[0], row[1]))
        


