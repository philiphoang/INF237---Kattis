
#Adding items to the shoppinglist
N = int(input())
list = []
for i in range(0, N):
    item = input()
    list.append(item)

M = int(input())

#Pair items that must not be bought together
bad_combination = [] 
for j in range(0, M):
    items = input()
    
    #bad_combination.append(items.split())
    bad_pair = tuple(items.split())
    bad_combination.append(bad_pair)

list.reverse()
bad_combination.reverse()
bc = bad_combination.copy()

walter = []
jesse = []

for i in range(0, len(list)):
    for bad_pair in bad_combination:
        if (bad_pair[0] in list):
            if (bad_pair[0] not in walter):
                walter.append(bad_pair[0])
            else:
                jesse.append(bad_pair[0])
            
            list.remove(bad_pair[0])
        
        if (bad_pair[1] in list):
            if (bad_pair[1] not in jesse):
                jesse.append(bad_pair[1])
            else:
                walter.append(bad_pair[1])
            
            list.remove(bad_pair[1])

    if list:
        if (len(list) == 1):
            walter.append(list.pop())
        else:
            walter.append(list.pop())
            jesse.append(list.pop())

possible = True

for bad_pair in bc:
    if (bad_pair[0] in walter and bad_pair[1] in walter):
        possible = False
    if (bad_pair[0] in jesse and bad_pair[1] in jesse):
        possible = False

if not possible:
    print("impossible")
else:
    print(walter)
    print(jesse)
    
