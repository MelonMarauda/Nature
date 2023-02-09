# Nature Documentation

A simple plugin for Spigot based Minecraft servers to generate trees, vines and other things (maybe).
It is completely standalone and procedurally generates every tree or vine, there are no schematics involved.
This means every time you will get a different tree.

---

### MINECRAFT VERSION
- API Compatible - 1.19.2+
- Latest tested - 1.19.3

---

# Commands

## Permissions
- `nature.use`  
  This grants the ability to use all of natures commands.
- `nature.norestrictions`  
  This grants the ability to bypass size restrictions. Use carefully.

## Trees
- `/trees`  
  Lists all available tree commands
- `/trees <pattern> <variation> <stemHeightRange> <stemCountRange> <logBlocks> <rootBlocks> <leafBlocks> <tipBlocks> <straight> <thinness> <branchDensity>`  
  Generates a tree of the input parameters where you are looking
- `/trees variations`  
  Shows all available tree types

## Vines
- `/vines`  
  Returns available vine commands
- `/vines <type> <direction> <size> <quantity> <clingyness> <thickness> <standardBlocks> <rootBlocks>`  
  Generates a vine of the input parameters where you are standing
- `/vines directions`  
  Lists all available vine directions (there are alot)

## Nature
- `/nature version`  
  Returns the version as well as the link to these docs.
- `/nature undo`  
  Reverses the effects of the most recent nature command you used. Can undo up to 10 commands in a row. 
- `/nature undos`  
  Lists the number of undo states available.

---

# Trees

## Parameters

- `pattern`  
  The pattern in which the trees are generated. Currently only `tree` is available which spawns a single tree where you are looking.
- `variation`  
  **Type: pattern**  
  The type of tree. A full list of types can be seen below. Accepts worldedit style patterns to set percentage chances for certain tree types. More details below.
- `stemHeightRange`  
  **Type: number range**  
  **Max: 10**  
  The height of each stem section. Can be a range of numbers or a single number. If a range the format would be 2 numbers seperated by a `-` like `2-3`. The purpose of being able to define a range is so that the command can be run over and over and different trees are spat out so that a whole forest can be generated without having to change commands. 
- `stemCountRange`  
  **Type: number range**  
  **Max: 7**    
  The count of stem sections. The height of the sections is defined above. Again can be either be a range or a single number.
- `logBlocks`  
  **Type: pattern**  
  The block pattern for the trunk blocks. Supports worldedit style block patterns. More details below.
- `rootBlocks`  
  **Type: pattern**  
  The block pattern for the root blocks. Supports worldedit style block patterns. More details below.
- `leafBlocks`  
  **Type: pattern**  
  The block pattern for the leaf blocks. Supports worldedit style block patterns. More details below.
- `tipBlocks`  
  **Type: pattern**  
  The block pattern for the tree tip blocks (if the tree type has them). Still required for trees that don't have a tip. Supports worldedit style block patterns. More details below.
- `straight`  
  **Type: pattern**  
  Whether or not the tree is straight or bendy. Either `true` or `false`. Accepts worldedit style patterns to set percentage chances for certain tree types. More details below.
- `thinness`  
  **Type: number range**
  For trees that support width values, can be a number range with format as described above.
- `branchDensity`    
  **Type: number range**  
  For trees that have branches and support density values, allows you to control the chance of a branch spawning. Allows for number ranges with format as described above.

## Variations

`Pointy` `WidePointy` `Palm` `PointyPalm` `StarPalm` `DroopyPalm` `DensePalm` `DeadPointy` `DeadFlat`

Also supports branching versions of all mentioned variations. To enable branching add `branching` to the name.
For example a branching pointy type tree would be `BranchingPointy`. 
Branching generates a fork halfway up the main trunk generating basically a new copy of the tree without the roots. 

### Pointy

**Does not use `thinness` or `branchDensity` values specified in the command**

Creates a thin pointy tree. My favorite kind of tree :)

Example command:  
`/trees `

### WidePointy

**Does not use `branchDensity` values specified in the command**

Creates a wide pointy tree, kinda like a squat spruce tree in vanilla MC.

Example command:  
`/trees `

### Palm

**Does not use `branchDensity` values specified in the command**

Creates a Palm style tree with 8 droopy leafy bits.

Example command:  
`/trees `

### PointyPalm

**Does not use `branchDensity` values specified in the command**

Creates a palm style tree with 8 droopy leafy bits that are raised higher than a regular palm.

Example command:  
`/trees `

### StarPalm

**Does not use `branchDensity` values specified in the command**

Creates a combination palm of pointy and regular. Has 16 leafy bits.

Example command:  
`/trees `

### DroopyPalm

**Does not use `branchDensity` values specified in the command**

Creates a more droopy version of the regular 8 leafy bit palm.

Example command:  
`/trees `

### DensePalm

**Does not use `branchDensity` values specified in the command**

Creates a dense palm with 24 leafy bits of style droopy, regular and pointy.

Example command:  
`/trees `

### DeadPointy

Creates a tree with pointy branches. Uses the leafBlocks pattern for its branches. The branches tend to point up alot.

Example command:  
`/trees `

### DeadFlat

Creates a tree very similar to DeadPointy but with its branches sticking out more.

Example command:  
`/trees `

---

# Vines

## Parameters

- `type`  
  Either `patch` for a bunch of vines in an area or `vine` for a single vine.
- `direction`  
  The direction that the vine will generate. A full list of directions can be found below as well as description for how they work.
- `size`  
  **Type: number**  
  **Max: 200**  
  The length of the vines
- `quantity`  
  **Type: number**  
  **Max: 7**  
  Only used for patch. When generating a single vine doesn't have any effect so just leave as `1`.
- `clingyness`  
  **Type: number**  
  **Max: 6**  
  How aggressively the vine will try and stick to the direction you've specified. Higher the value the more aggressively it will stick.
- `thickness`  
  **Type: number**  
  **Max: 6**  
  Thickness allows for parallel vines to be generated to make the vine seem thicker.
- `standardBlocks`  
  **Type: pattern**  
  The main block composition of the vine. Supports worldedit style patterns as described below.
- `rootBlocks`  
  **Type: pattern**  
  Every few blocks a root will generate and this is the composition of that. Supports worldedit style patterns as described below.

## Directions

`northdown` `eastdown` `southdown` `westdown` `northup` `eastup` `southup` `westup` `wallwestup` `wallwestdown` `wallwestleft` `wallwestright` `walleastup` `walleastdown` `walleastleft` `walleastright` `wallnorthup` `wallnorthdown` `wallnorthleft` `wallnorthright` `wallsouthup` `wallsouthdown` `wallsouthleft` `wallsouthright`

---

# Other info

## Patterns
As described above many inputs support worldedit style patterns for their values.
Worldedit patterns allow you to define percentage values for different possible outcomes.
These values are comma seperated blocks with the below format:

`<number>%<input>`  
Those blocks are then strung together like:  
`<number1>%<input1>,<number2>%<input2>,<numberX>%<inputX>`  
An example of a filled in pattern:  
`25%Pointy,25%WidePointy,50%Palm`

The combination of the percentages should add up to 100%.  
Correct usage (adds up to 100%):  
`25%grass_block,25%stone,50%air`  
Incorrect usage (adds up to 50%):  
`25%grass_block,25%stone`

If you don't want multiple types then just use the `<input>` value by itself without percentages.  
Correct usage:  
`true`  
Incorrect usage:  
`100%true`