import os
import json

"""
Скрипт генерации уровня с помощью представления загона в текстовой форме

В папку leveltxt кладется текстовый файл в формате txt по следующему примеру:
#  @  #$
$  $  . 
########

Пробел обозначает пустую клетку
# - стена
$ - простая коробка
<, >, ^, U, (, ) - магнитная коробка с разным направлением полюсов
@ - коза
. - капуста
M - металлическая коробка
"""

levelnum = 1 # уровень, с которого нужно начинать отсчет уровней

if not os.path.exists("leveljson"):
    os.mkdir("leveljson")
if not os.path.exists("leveltxt"):
    os.mkdir("leveltxt")


def convert(levelnum, file):
    leveljson = {
        "name": "Level 1",
        "field": {
            "width": 7,
            "height": 7
        },
        "goat": {
            "position": [0, 0],
            "stepAmount": 30
        },
        "commands": [
        {
            "command": "placeEntities",
            "type": "box",
            "positions": [
            ]
        },
        {
        "command": "placeEntities",
        "type": "wall",
        "positions": [
        ]
        },
        {"command": "placeEntities", "type": "cabbage", "positions": []}
        ]
    }
    with open(file, "r") as fobj:
        print(file)
        level = fobj.readlines()
        rows, cols = len(level), 0
        row, col = 1, 1
        for line in level:
            line = line.rstrip()
            cols = max(len(line), cols)
            for char in line:
                if char == "#":
                    leveljson["commands"][1]["positions"].append([row, col]) # wall
                elif char == "$":
                    leveljson["commands"][0]["positions"].append([row, col]) # box
                elif char == "*":
                    leveljson["commands"][0]["positions"].append([row, col]) # box
                    leveljson["commands"][2]["positions"].append([row, col]) # cabbage
                elif char == ".":
                    leveljson["commands"][2]["positions"].append([row, col]) # cabbage
                elif char == "@":
                    leveljson["goat"]["position"] = [row, col]
                elif char == "^":
                    leveljson["commands"].append({
                        "command": "placeEntity",
                        "type": "magnet_box",
                        "position": [
                            row, col
                        ],
                        "properties": {"northPole": "east", "rotateTopRight": False}
                    })
                elif char == ">":
                    leveljson["commands"].append({
                        "command": "placeEntity",
                        "type": "magnet_box",
                        "position": [
                            row, col
                        ],
                        "properties": {"northPole": "south", "rotateTopRight": False}
                    })
                elif char == "<":
                    leveljson["commands"].append({
                        "command": "placeEntity",
                        "type": "magnet_box",
                        "position": [
                            row, col
                        ],
                        "properties": {"northPole": "north", "rotateTopRight": True}
                    })
                elif char == "(":
                    leveljson["commands"].append({
                        "command": "placeEntity",
                        "type": "magnet_box",
                        "position": [
                            row, col
                        ],
                        "properties": {"northPole": "south", "rotateTopRight": True}
                    })
                elif char == ")":
                    leveljson["commands"].append({
                        "command": "placeEntity",
                        "type": "magnet_box",
                        "position": [
                            row, col
                        ],
                        "properties": {"northPole": "north", "rotateTopRight": True}
                    })
                elif char == "U":
                    leveljson["commands"].append({
                        "command": "placeEntity",
                        "type": "magnet_box",
                        "position": [
                            row, col
                        ],
                        "properties": {"northPole": "west", "rotateTopRight": True}
                    })
                elif char == "M":
                    leveljson["commands"].append({
                        "command": "placeEntity",
                        "type": "metal_box",
                        "position": [
                            row, col
                        ]
                    })
                col += 1
            col = 1
            row += 1
    leveljson["field"]["width"] = cols
    leveljson["field"]["height"] = rows
    if rows >= 15 or cols >= 24:
        raise ValueError()
    leveljson["name"] = f"Уровень {levelnum}"
    with open(f"leveljson/level_{levelnum}.level", "w", encoding="utf-8") as fobj:
        json.dump(leveljson, fobj, ensure_ascii=False, indent=4)



for root, dirs, files in os.walk("./leveltxt"):
    for file in files:
        if file.endswith(".txt"):
            file = os.path.join(root, file)
            try:
                convert(levelnum, file)
                levelnum += 1
            except ValueError:
                pass