import random
import json

l = []
lrand = []
data = {}

for i in range(7):
	for j in range(i+1):
		l.append([i,j])
		
while(len(l)!=0):
	rand = random.choice(l)
	l.remove(rand)
	lrand.append(rand)	

for par in lrand:
	print(par)
	
print("list length:",len(lrand))

data['soup'] = lrand

with open("./data/soup.json",'w') as outFile:
	json.dump(data,outFile)