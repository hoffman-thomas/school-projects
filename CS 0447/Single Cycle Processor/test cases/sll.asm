	# this program tests shift left
	#
	# this program assumes you've passed addi.asm, addui.asm, put.asm
	#
	addi $r0,0x11
	sll	 $r0,0		# should not shift
	put	 $r0		# correct output is 0x0011
	sll	 $r0,1		# try moving one bit to left
	put	 $r0		# correct output is 0x0022
	sll	 $r0,8		# shift "beyond" left side
	put	 $r0		# correct output is 0x2200
	sll	 $r0,8
	put	 $r0		# correct output is 0x0000
	halt
	
