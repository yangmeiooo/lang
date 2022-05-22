package main

import "fmt"

func main(){

	var numbers = make([]int , 3, 5)

	fmt.Printf("len = %d, cap = %d, slice = %v\n",len(numbers), cap(numbers), numbers)

	//向number 追加元素
	numbers = append(numbers, 1)
	fmt.Printf("len = %d, cap = %d, slice = %v\n",len(numbers), cap(numbers), numbers)

	
	// append 元素时，底层容量不够会自动扩充2倍	
}