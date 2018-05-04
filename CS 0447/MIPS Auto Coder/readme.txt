#The program prompts to get five opcodes from the user
#The allowed opcodes are these: add, addi, or, ori, lw, sw, j, beq, bne.

#Based on the opcode and its instruction type (R-format, I-format, and J-format),
#fill in missing registers, and/or immediate values.

#R-Type opcode, rs, rt, rd, shamt, funct
#I-Type opcode, rs, rt, immediate
#J-Type opcode, target
#add  $rd, $rs, $rt
#or   $rd, $rs, $rt
#addi $rt, $rs, immConstant
#ori  $rt, $rs, immConstant
#lw   $rt, $rs, memoryLocation
#sw   $rt, $rs, memoryLocation
#j 		jumpAddress
#beq  $rs, $rt, branchAddress
#bne  $rs, $rt, branchAddress

#    OPERATION   FORMAT STRING
#      add:      "o d, s, t"
#       or:      "o d, s, t"
#     addi:      "o t, s, i"
#      ori:      "o t, s, i"
#       lw:      "o t, i(s)"
#       sw:      "o t, i(s)"
#      beq:      "o s, t, l"
#      beq:      "o s, t, l"
#        j:      "o l"

#PSEUDO-CODE
# Get user input
# Store user input  TODO: Where to store? Where will I access from?
# Print TODO


#VARIANTS OF INSTRUCTIONS
#R-Type opcode, rs, rt, rd, shamt, funct
#I-Type opcode, rs, rt, immediate
#J-Type opcode, target
#add  $rd, $rs, $rt
#or   $rd, $rs, $rt
#addi $rt, $rs, immConstant
#ori  $rt, $rs, immConstant
#lw   $rt, $rs, memoryLocation
#sw   $rt, $rs, memoryLocation
#j 		jumpAddress
#beq  $rs, $rt, branchAddress
#bne  $rs, $rt, branchAddress

#RULES
# 1. sequentially use registers $t0 to $t9

# 2. if we run over, reuse registers $t0 to $t9 sequentially

# 3. build connections
#add $t2,$t0,$t1
#add $t4,$t2,$t3

#$t0, $t1, $t2
#$t2, $t3, $t4
#$t4, $t5, imm1

# 4. immediate values allowed are 10X, where X = 0 initially and increments after each use

# 5. if instructions have
