.data
input: .byte


placeholder: .asciiz "DO NOT USE"
inst_or:     .asciiz "or $t2, $t0, $t1"
inst_add:    .asciiz "add $t2, $t0, $t1"
inst_ori:    .asciiz "ori $t1, $t0, x"
inst_addi:   .asciiz "addi $t1, $t0, x"
inst_lw:     .asciiz "lw $t1, x($t0)"
inst_sw:     .asciiz "sw $t1, x($t0)"
inst_j:      .asciiz "j Lx"
inst_beq:    .asciiz "beq $t1, $t0, Lx"
inst_bne:    .asciiz "bne $t1, $t0, Lx"

greeting:    .asciiz "Welcome to Auto Coder!"
option_menu: .asciiz "\nThe opcode (1-9 : 1=add, 2=addi, 3=or, 4=ori, 5=lw, 6=sw, 7=j, 8=beq, 9=bne)"
prompt_1:    .asciiz "please enter the 1st opcode:"
prompt_2:    .asciiz "please enter the 2nd opcode:"
prompt_3:    .asciiz "please enter the 3rd opcode:"
prompt_4:    .asciiz "please enter the 4th opcode:"
prompt_5:    .asciiz "please enter the 5th opcode:"
comp_code_msg:   .asciiz "\nThe completed code is"
mach_code_msg:   .asciiz "\nThe machine code is"
msg: .asciiz "IMMEDIATE"

mach_code_arr: .word 1, 2, 3, 4, 5

.text
############################################################################################################################################				
user_input:#################################################################################################################################
############################################################################################################################################

	li $v0, 4
	la $a0, greeting
	syscall #print greeting

	li $v0, 4
	la $a0, option_menu
	syscall #print option menu

	li $v0, 11
	li $a0, 0xa
	syscall #new line

	li $v0, 4
	la $a0, prompt_1
	syscall
	li $v0, 5
	syscall #user input for instruction 1
	sb $v0, input

	li $v0, 4
	la $a0, prompt_2
	syscall
	li $v0, 5
	syscall #user input for instruction 2
	sb $v0, input + 1

	li $v0, 4
	la $a0, prompt_3
	syscall
	li $v0, 5
	syscall #user input for instruction 3
	sb $v0, input + 2

	li $v0, 4
	la $a0, prompt_4
	syscall
	li $v0, 5
	syscall #user input for instruction 4
	sb $v0, input + 3

	li $v0, 4
	la $a0, prompt_5
	syscall
	li $v0, 5
	syscall #user input for instruction 5
	sw $v0, input + 4

	li $v0, 4
	la $a0, comp_code_msg
	syscall	#print completed code header

	li $a0, 0	
	j main_loop #begin auto coder program 
	
############################################################################################################################################				
main_loop:##################################################################################################################################		
############################################################################################################################################				
	move $t0, $a0
	
	lb $t1, input($t0) #the user's input for instruction 1-5
	
	ble $t1, 0, exit  #if instruction is out of range, exit 
	bge $t1, 10, exit #if instruction is out of range, exit 
	
	li $v0, 11
	li $a0, 0xa
	syscall #print a new line
	
	li $v0, 11
	li $a0, 0x4c
	syscall #print the char 'L'
	
	li $v0, 11
	li $a0, 0x31
	syscall #print the char '1'
	
	li $v0, 11
	li $a0, 0x30
	syscall #print the char '0'
	
	li $v0, 1
	la $a0, ($t0)
	syscall #print the iteration of instruction to be evaluated [0-4]
	
	li $v0, 11
	li $a0, 0x3a
	syscall #print the char ':'
	
	li $v0, 11
	li $a0, 0x20
	syscall #print a space 

	la $a0, inst_add	 	#
	beq $t1, 1, branch_add_or	#  branch_add_or is used for
	la $a0, inst_or			#  add and or instructions 
	beq $t1, 3, branch_add_or	#
	
	la $a0, inst_addi		#
	beq $t1, 2, branch_addi_ori     # branch_addi_ori is used for
	la $a0, inst_ori  		# addi and ori instructions
	beq $t1, 4, branch_addi_ori	#
	
	la $a0, inst_lw			#
	beq $t1, 5, branch_lw_sw	# branch_lw_sw is used for
	la $a0, inst_sw			# lw and sw instructions 
	beq $t1, 6, branch_lw_sw	#
	
	la $a0, inst_j			# branch_j is used for 
	beq $t1, 7, branch_j		# j instructions 
	
	la $a0, inst_beq		#
	beq $t1, 8, branch_b		# branch_b is used for
	la $a0, inst_bne		# beq and bne instructions 
	beq $t1, 9, branch_b		#

############################################################################################################################################				
branch_add_or: #############################################################################################################################
############################################################################################################################################							
	move $t2, $a0
	
	next_char_add_or: #iterate over bytes and print until end of string
		
		lb $a0, ($t2)
		
		li $v0, 2 #set $v0 to the number of regiters used - 1
		li $v1, 0 #set $v1 to the number of immediate vales used
		beq $a0, $zero, continue #continue at end of string 
		
		beq $a0, 0x61, set_op_add #if the character is an "a" set op code accordingly
		j skip_set_op_add
		set_op_add: addi $t6, $0, 32
		
		skip_set_op_add:
		beq $a0, 0x6f, set_op_or #if the character is an "o" set op code accordingly
		j skip_set_op_add_or
		set_op_or: addi $t6, $0, 37
		
		skip_set_op_add_or:
		
		beq $a0, 0x32, register_add_or_rd #detect the destination register & branch 
		beq $a0, 0x30, register_add_or_rs #detect the source register & branch 
		beq $a0, 0x31, register_add_or_rt #detect the target register & branch 
		j print_add_or
		
		########################################################
		register_add_or_rd:#####################################
		########################################################
			add $a0, $a0, $t3 
			bgt $a0, 0x39, reset_register_add_or_rd
			j skip_reset_register_add_or_rd
	
			reset_register_add_or_rd:
				li $a0,0x30
			
			skip_reset_register_add_or_rd:
			subi $t7, $a0, 48
			bgt $t7, 7, high_register_add_or_rd
			low_register_add_or_rd:
				addi $t7, $t7, 8 #set t7 to the destination register adding 8 base 10 if the register is a low register 
				j print_add_or
				
			high_register_add_or_rd:			
				addi $t7, $t7, 16  #set t7 to the destination register adding 24 base 10 if the register is a high register 
				
			j print_add_or
		
		########################################################
		register_add_or_rs:#####################################
		########################################################
			add $a0, $a0, $t3  
			bgt $a0, 0x39, reset_register_add_or_rs
			j skip_reset_register_add_or_rs
	
			reset_register_add_or_rs:
				li $a0,0x30
			
			skip_reset_register_add_or_rs:
			
			subi $t8, $a0, 48
			bgt $t8, 7, high_register_add_or_rs
			
			low_register_add_or_rs:
				addi $t8, $t8, 8 #set t8 to the source register adding 8 base 10 if the register is a low register (<7)
				j print_add_or
				
			high_register_add_or_rs:			
				addi $t8, $t8, 16  #set t8 to the source register adding 24 base 10 if the register is a high register (>7) 
				j print_add_or
		
		########################################################
		register_add_or_rt:#####################################
		########################################################
			add $a0, $a0, $t3 
			bgt $a0, 0x39, reset_register_add_or_rt
			j skip_reset_register_add_or_rt
	
			reset_register_add_or_rt:
				li $a0,0x30
			
			skip_reset_register_add_or_rt:
			
			subi $t9, $a0, 48
			bgt $t9, 7, high_register_add_or_rt
			
			low_register_add_or_rt:
				addi $t9, $t9, 8 #set t9 to the target register adding 8 base 10 if the register is a low register 
				j skip_high_register_add_or_rt
				
			high_register_add_or_rt:			
				addi $t9, $t9, 16  #set t9 to the target register adding 24 base 10 if the register is a high register 

			skip_high_register_add_or_rt:	
			
			sll $t8, $t8, 21  #if we have read the target register (the last register) , $t6, $t7, $t8 have been populated	
			sll $t9, $t9, 16
			sll $t7, $t7, 11
			
			or $t6, $t6, $t8
			or $t6, $t6, $t9
			or $t6, $t6, $t7
			
			la $t5, mach_code_arr
			add $t7, $0, $t0
			add $t7, $t7, $t7
			add $t7, $t7, $t7
			add $t7, $t7, $t5
			sw $t6, 0($t7) 
			
			j print_add_or
		
		########################################################
		print_add_or:###########################################
		########################################################
			li $v0, 11
			syscall 
		
		addi $t2, $t2, 1 
		j next_char_add_or
		
############################################################################################################################################				
branch_addi_ori:############################################################################################################################	
############################################################################################################################################							
	move $t2, $a0
	
	next_char_addi_ori: #iterate over bytes and print until end of string
		lb $a0, ($t2)
		
		li $v0, 1  #set $v0 to the number of regiters used - 1
		li $v1, 1  #set $v1 to the number of immediate vales used
		beq $a0, $zero, continue #continue at end of string 
		
		beq $a0, 0x61, set_op_addi #if the character is an "a" set op code accordingly
		j skip_set_op_addi
		set_op_addi: addi $t6, $0, 0x8
		
		skip_set_op_addi:
		beq $a0, 0x6f, set_op_ori #if the character is an "o" set op code accordingly
		j skip_set_op_addi_ori
		set_op_ori: addi $t6, $0, 0xd
		
		skip_set_op_addi_ori:
		
		beq $a0, 0x31, register_addi_ori_rd   #detect the destination register & branch 
		beq $a0, 0x30, register_addi_ori_rs   #detect the source register & branch 
		beq $a0, 0x78, immediate_addi_ori     #detect immediate value & branch 
		j print_addi_ori
		
		########################################################
		register_addi_ori_rd:###################################
		########################################################
			add $a0, $a0, $t3 
			bgt $a0, 0x39, reset_register_addi_ori_rd
			j skip_reset_register_addi_ori_rd
			
			reset_register_addi_ori_rd:
				li $a0,0x30
				
			skip_reset_register_addi_ori_rd:
			
			subi $t7, $a0, 48
			bgt $t7, 7, high_register_addi_ori_rd
			low_register_addi_ori_rd:
				addi $t7, $t7, 8   #set t7 to the destination register adding 8 base 10 if the register is a low register 
				j print_addi_ori
				
			high_register_addi_ori_rd:			
				addi $t7, $t7, 16  #set t7 to the destination register adding 24 base 10 if the register is a high register 
				j print_addi_ori

		########################################################
		register_addi_ori_rs:################################### 
		########################################################
			add $a0, $a0, $t3 
			bgt $a0, 0x39, reset_register_addi_ori_rs
			j skip_reset_register_addi_ori_rs
			
			reset_register_addi_ori_rs:
				li $a0,0x30
				
			skip_reset_register_addi_ori_rs:
			
			subi $t8, $a0, 48
			bgt $t8, 7, high_register_addi_ori_rs
			low_register_addi_ori_rs:
				addi $t8, $t8, 8 #set t8 to the source register adding 8 base 10 if the register is a low register 
				j print_addi_ori
				
			high_register_addi_ori_rs:			
				addi $t8, $t8, 16  #set t8 to the source register adding 24 base 10 if the register is a high register 
				j print_addi_ori
		
		########################################################
		immediate_addi_ori:#####################################
		########################################################	
			li $a0, 100
			add $a0, $a0, $t4
			li $v0, 1
			syscall
			
			move $t9, $a0
			
			sll $t6, $t6, 26 #if we have read the immediate register (the last register) $t6, $t7, $t8 have been populated
			sll $t8, $t8, 21
			sll $t7, $t7, 16
			
			or $t6, $t6, $t8
			or $t6, $t6, $t7
			or $t6, $t6, $t9
			
			la $t5, mach_code_arr
			add $t7, $0, $t0
			add $t7, $t7, $t7
			add $t7, $t7, $t7
			add $t7, $t7, $t5
			sw $t6, 0($t7) 
			
			j skip_print_addi_ori

		########################################################
		print_addi_ori:#########################################
		########################################################
			li $v0, 11
			syscall 
			
		########################################################
		skip_print_addi_ori:####################################
		########################################################
			addi $t2, $t2, 1 
			j next_char_addi_ori

############################################################################################################################################				
branch_lw_sw:###############################################################################################################################	
############################################################################################################################################							
	move $t2, $a0

	next_char_lw_sw:
		lb $a0, ($t2)
		
		li $v0, 1
		li $v1, 1
		beq $a0, $zero, continue 
		
		beq $a0, 0x6c, set_op_lw #if the character is a "l" set op code accordingly
		j skip_set_op_lw
		set_op_lw: addi $t6, $0, 0x23
		
		skip_set_op_lw:
		beq $a0, 0x73, set_op_sw #if the character is a "s" set op code accordingly
		j skip_set_op_lw_sw
		
		set_op_sw: addi $t6, $0, 0x2b
		
		skip_set_op_lw_sw:
		
		beq $a0, 0x31, register_lw_sw_rt
		beq $a0, 0x30, register_lw_sw_base
		beq $a0, 0x78, offset_lw_sw
		j print_lw_sw
		
		########################################################
		register_lw_sw_base:####################################
		########################################################
			add $a0, $a0, $t3 
			bgt $a0, 0x39, reset_register_lw_sw_base
			j skip_reset_register_lw_sw_base
			
			reset_register_lw_sw_base:
				li $a0,0x30
				
			skip_reset_register_lw_sw_base:
			
			subi $t7, $a0, 48
			bgt $t7, 7, high_register_lw_sw_base
			low_register_lw_sw_base:
				addi $t7, $t7, 8   #set t7 to the destination register adding 8 base 10 if the register is a low register 
				j set_mach_code_lw_sw
				
			high_register_lw_sw_base:			
				addi $t7, $t7, 16  #set t7 to the destination register adding 24 base 10 if the register is a high register 
				j set_mach_code_lw_sw
			
			set_mach_code_lw_sw:
				sll $t6, $t6, 26 #if we have read the base register (the last register) $t6, $t7, $t8 have been populated
				sll $t8, $t8, 16
				sll $t7, $t7, 21
			
				or $t6, $t6, $t8
				or $t6, $t6, $t7
				or $t6, $t6, $t9
			
				la $t5, mach_code_arr
				add $t7, $0, $t0
				add $t7, $t7, $t7
				add $t7, $t7, $t7
				add $t7, $t7, $t5
				sw $t6, 0($t7) 
	
				j print_lw_sw

		########################################################
		register_lw_sw_rt:######################################
		########################################################
			add $a0, $a0, $t3 
			bgt $a0, 0x39, reset_register_lw_sw_rt
			j skip_reset_register_lw_sw_rt
			
			reset_register_lw_sw_rt:
				li $a0,0x30
				
			skip_reset_register_lw_sw_rt:
			
			subi $t8, $a0, 48
			bgt $t8, 7, high_register_lw_sw_rt
			low_register_lw_sw_rt:
				addi $t8, $t8, 8 #set t8 to the source register adding 8 base 10 if the register is a low register 
				j print_lw_sw
				
			high_register_lw_sw_rt:			
				addi $t8, $t8, 16  #set t8 to the source register adding 24 base 10 if the register is a high register 
				j print_lw_sw 
		
		########################################################
		offset_lw_sw:###########################################
		########################################################	
			li $a0, 100
			add $a0, $a0, $t4
			li $v0, 1
			syscall
			
			move $t9, $a0

			j skip_print_lw_sw
		########################################################
		print_lw_sw:############################################
		########################################################
			li $v0, 11
			syscall 
		
		########################################################
		skip_print_lw_sw:#######################################
		########################################################
			addi $t2, $t2, 1 
			j next_char_lw_sw

############################################################################################################################################				
branch_j:###################################################################################################################################	
############################################################################################################################################						
	move $t2, $a0
	
	next_char_j: #iterate over bytes and print until end of string
		lb $a0, ($t2)
		
		li $v0, 1
		li $v1, 1
		beq $a0, $zero, continue 
		
		li $t6, 0x2
		
		beq $a0, 0x78, immediate_j
		j print_j
				
		########################################################
		immediate_j:############################################
		########################################################
			li $a0, 100
			add $a0, $a0, $t4
			li $v0, 1
			syscall
			
			mul $t4, $t4, 4
			add $t7, $0, $t4
			
			div $t4, $t4, 4
			addi $t7, $t7, 0x00400000
			srl $t7, $t7, 2
			
			sll $t6, $t6, 26

			or $t6, $t6, $t7

			la $t5, mach_code_arr
			add $t7, $0, $t0
			add $t7, $t7, $t7
			add $t7, $t7, $t7
			add $t7, $t7, $t5
			sw $t6, 0($t7) 
						
			j skip_print_j
			
		########################################################
		print_j:################################################ 
		########################################################
			li $v0, 11
			syscall 
		
		########################################################
		skip_print_j:###########################################
		########################################################
			addi $t2, $t2, 1 
			j next_char_j

############################################################################################################################################				
branch_b:###################################################################################################################################	
############################################################################################################################################						
	move $t2, $a0
	
	next_char_b:
		lb $a0, ($t2)
		
		li $v0, 1
		li $v1, 1
		beq $a0, $zero, continue 
		
		beq $a0, 0x71, set_op_q #if the character is a "q" set op code accordingly
		j skip_set_op_q
		set_op_q: addi $t6, $0, 0x4
		
		skip_set_op_q:
		beq $a0, 0x6e, set_op_n #if the character is a "n" set op code accordingly
		j skip_set_op_q_n
		set_op_n: addi $t6, $0, 0x5
		
		skip_set_op_q_n:
		
		beq $a0, 0x30, register_b_rt  
		beq $a0, 0x31, register_b_rs  
		beq $a0, 0x78, offset_b   
		j print_b
		
		########################################################
		register_b_rs:########################################## 
		########################################################
			add $a0, $a0, $t3 
			bgt $a0, 0x39, reset_register_b_rs
			j skip_reset_register_b_rs
			
			reset_register_b_rs:
				li $a0,0x30
				
			skip_reset_register_b_rs:
			
			subi $t7, $a0, 48
			bgt $t7, 7, high_register_b_rs
			low_register_b_rs:
				addi $t7, $t7, 8 #set t7 to the source register adding 8 base 10 if the register is a low register 
				j print_b
				
			high_register_b_rs:			
				addi $t7, $t7, 16  #set t7 to the source register adding 24 base 10 if the register is a high register 
				j print_b
							
		########################################################
		register_b_rt:########################################## 
		########################################################
			add $a0, $a0, $t3 
			bgt $a0, 0x39, reset_register_b_rt
			j skip_reset_register_b_rt
			
			reset_register_b_rt:
				li $a0,0x30
				
			skip_reset_register_b_rt:
			
			subi $t8, $a0, 48
			bgt $t8, 7, high_register_b_rt
			low_register_b_rt:
				addi $t8, $t8, 8 #set t7 to the source register adding 8 base 10 if the register is a low register 
				j print_b
				
			high_register_b_rt:			
				addi $t8, $t8, 16  #set t7 to the source register adding 24 base 10 if the register is a high register 
				j print_b
								
		########################################################
		offset_b:###############################################
		########################################################	
			li $a0, 100
			add $a0, $a0, $t4
			li $v0, 1
			syscall
			
			la $a0, ($t4)
			li $v0, 1
			syscall
			
			sub $t4, $t4, 1
			
			sub $t9, $0, $t4
			sll $t9, $t9, 16
			srl $t9, $t9, 16
			
			addi $t4, $t4, 1
			
			sll $t6, $t6, 26
			sll $t7, $t7, 21
			sll $t8, $t8, 16
			
			or $t6, $t6, $t7
			or $t6, $t6, $t8
			or $t6, $t6, $t9
			
			la $t5, mach_code_arr
			add $t7, $0, $t0
			add $t7, $t7, $t7
			add $t7, $t7, $t7
			add $t7, $t7, $t5
			sw $t6, 0($t7) 
			
			j skip_print_b
			
		########################################################
		print_b:################################################
		########################################################
			li $v0, 11
			syscall 
			
		########################################################
		skip_print_b:###########################################
		########################################################
			addi $t2, $t2, 1 
			j next_char_b

############################################################################################################################################				
continue:###################################################################################################################################	
############################################################################################################################################					
	add $t3, $t3, $v0
	add $t4, $t4, $v1
	bge $t3, 10, reset_register
	j skip_reset_register
	
	reset_register:
		li $t3, 0

	skip_reset_register:	
	
	#reset registers used to store the machine code 		
	li $t6, 0
	li $t7, 0
	li $t8, 0
	li $t9, 0
	
	addi $t0, $t0, 1 #increment to next instrruction
	beq $t0, 5, print_mach_code #if it is the last instruction, exit
	move $a0, $t0 	 #load in loop variable
	j main_loop

############################################################################################################################################					
print_mach_code:############################################################################################################################
############################################################################################################################################
	li $v0, 11
	li $a0, 0xa
	syscall #print a new line
	
	li $v0, 4
	la $a0, mach_code_msg
	syscall
	
	li $t0, 0
	
	la $t4, mach_code_arr
	
	print_mach_code_loop:		
		
		li $v0, 11
		li $a0, 0xa
		syscall #print a new line
		
		li $v0, 34
		lw $a0, 0($t4)
		syscall
		
		addi $t0, $t0, 1
		addi $t4, $t4, 4
		bgt $t0, 4, exit
		
		j print_mach_code_loop
															
############################################################################################################################################					
exit:#######################################################################################################################################	
############################################################################################################################################						
	li $v0, 10
	syscall
