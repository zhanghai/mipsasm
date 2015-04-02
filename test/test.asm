          add $1, $2, $3
          and $2, $3, $4
label:    sll $2, $3, 10
          jr  $zero
          addi $2, $3, 100
          addi $5, $6, -100
          lui $10, 0x100
          beq $1, $2, label
          bne $1, $2, 0b00101
          j label
