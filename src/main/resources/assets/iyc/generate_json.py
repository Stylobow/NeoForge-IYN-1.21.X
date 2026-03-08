import os

materials = {
    "oak": "minecraft:block/oak_planks",
    "spruce": "minecraft:block/spruce_planks",
    "birch": "minecraft:block/birch_planks",
    "jungle": "minecraft:block/jungle_planks",
    "acacia": "minecraft:block/acacia_planks",
    "dark_oak": "minecraft:block/dark_oak_planks",
    "mangrove": "minecraft:block/mangrove_planks",
    "cherry": "minecraft:block/cherry_planks",
    "bamboo": "minecraft:block/bamboo_planks",
    "crimson": "minecraft:block/crimson_planks",
    "warped": "minecraft:block/warped_planks",
    "stone": "minecraft:block/stone",
    "cobblestone": "minecraft:block/cobblestone",
    "stone_brick": "minecraft:block/stone_bricks",
    "deepslate_brick": "minecraft:block/deepslate_bricks"
}

blockstate_template = """{
    "variants": {
        "east=false,north=false,south=false,west=false": { "model": "iyc:block/MAT_table" },
        "east=false,north=true,south=false,west=false": { "model": "iyc:block/MAT_table_e", "y": 270, "uvlock": true },
        "east=true,north=false,south=false,west=false": { "model": "iyc:block/MAT_table_e", "uvlock": true },
        "east=false,north=false,south=true,west=false": { "model": "iyc:block/MAT_table_e", "y": 90, "uvlock": true },
        "east=false,north=false,south=false,west=true": { "model": "iyc:block/MAT_table_e", "y": 180, "uvlock": true },
        "east=true,north=true,south=false,west=false": { "model": "iyc:block/MAT_table_es", "y": 270, "uvlock": true },
        "east=true,north=false,south=true,west=false": { "model": "iyc:block/MAT_table_es", "uvlock": true },
        "east=false,north=false,south=true,west=true": { "model": "iyc:block/MAT_table_es", "y": 90, "uvlock": true },
        "east=false,north=true,south=false,west=true": { "model": "iyc:block/MAT_table_es", "y": 180, "uvlock": true },
        "east=false,north=true,south=true,west=false": { "model": "iyc:block/MAT_table_empty", "uvlock": true },
        "east=true,north=false,south=false,west=true": { "model": "iyc:block/MAT_table_empty", "uvlock": true },
        "east=true,north=true,south=true,west=false": { "model": "iyc:block/MAT_table_empty", "uvlock": true },
        "east=true,north=false,south=true,west=true": { "model": "iyc:block/MAT_table_empty", "uvlock": true },
        "east=false,north=true,south=true,west=true": { "model": "iyc:block/MAT_table_empty", "uvlock": true },
        "east=true,north=true,south=false,west=true": { "model": "iyc:block/MAT_table_empty", "uvlock": true },
        "east=true,north=true,south=true,west=true": { "model": "iyc:block/MAT_table_empty", "uvlock": true }
    }
}"""

block_model_template = """{
    "parent": "iyc:block/PARENT",
    "textures": {
        "texture": "TEX"
    }
}"""

item_model_template = """{
  "parent": "iyc:block/MAT_table",
  "display": {
    "thirdperson_righthand": { "rotation": [75, 45, 0], "translation": [0, 2.5, 0], "scale": [0.375, 0.375, 0.375] },
    "thirdperson_lefthand": { "rotation": [75, 45, 0], "translation": [0, 2.5, 0], "scale": [0.375, 0.375, 0.375] },
    "firstperson_righthand": { "rotation": [0, 45, 0], "translation": [0, 0, 0], "scale": [0.4, 0.4, 0.4] },
    "firstperson_lefthand": { "rotation": [0, 225, 0], "translation": [0, 0, 0], "scale": [0.4, 0.4, 0.4] },
    "ground": { "translation": [0, 3, 0], "scale": [0.25, 0.25, 0.25] },
    "gui": { "rotation": [30, 225, 0], "translation": [0, -1, 0], "scale": [0.625, 0.625, 0.625] },
    "fixed": { "scale": [0.5, 0.5, 0.5] }
  }
}"""

dirs = ["blockstates", "models/block", "models/item"]
for d in dirs:
    os.makedirs(d, exist_ok=True)

for mat, tex in materials.items():
    with open(f"blockstates/{mat}_table.json", "w") as f:
        f.write(blockstate_template.replace("MAT", mat))
    
    parents = ["table", "table_e", "table_es", "table_empty"]
    for p in parents:
        model_name = f"{mat}_{p}" if p != "table" else f"{mat}_table"
        with open(f"models/block/{model_name}.json", "w") as f:
            f.write(block_model_template.replace("PARENT", p).replace("TEX", tex))
            
    with open(f"models/item/{mat}_table.json", "w") as f:
        f.write(item_model_template.replace("MAT", mat))

print("Génération de toutes les tables terminée avec succès !")