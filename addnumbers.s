# UNTITLED PROGRAM

	.data	# Data declaration section

numbers:
	.word 0x63
	.word 0x64
	.word 0x65
	.word 0x66
	.word 0x67
	.word 0x68
	.word 0x69
	.word 0x6a
	.word 0x6b
	.word 0x6c
	
	.text

main:		# Start of code section

	li $t0, 4		# Load increment
	li $t1, 0x28		# Load count
	li $t4, 0		# Clear sum
	la $t3, numbers		# Point at first number
	add $t1, $t1, $t3	# Compute final pointer
skip:
	lw $t2, 0($t3)
	add $t4, $t2, $t4	# Updating sum
	add $t3, $t3, $t0	# Advance to next number
	bne $t1, $t3, skip	# Add next number
	
	li $v0, 10	# Halt
	syscall
	

# END OF PROGRAM