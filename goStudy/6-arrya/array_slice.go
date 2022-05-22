// 切片截取

package main

import "fmt"

func main(){

	s := []int{3,2,1}

	//[0,2]
	s1 := s[0: 2]

	fmt.Println(s1)

	// s , s1 指针指向 同一个内存切片和python 不一样

	//copy 浅拷贝
	s2 := make([]int , 3)

	copy(s2, s)
	
	// 打印拷贝后的值
	fmt.Println(s2)
}