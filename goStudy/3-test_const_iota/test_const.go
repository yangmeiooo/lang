package main

import "fmt"

const (
	ONE = 1
	TWO = 2
	THREE = 3
)

const (
	a = iota + 1
	b   // 1
	c   // 2
)



func main(){

	//1 常量简单用法
	const len int  = 10
	fmt.Println("len = ", len)

	const len2 = 20
	fmt.Println("len2 = ", len2)

	const len3 = "test const"
	fmt.Println("len3 = ", len3)

	//2 常量 枚举用法
	fmt.Println("ONE = ", ONE)

	//3 常量 iota 用法，能作用在行和列
	fmt.Println("a = ", a)
	fmt.Println("b = ", b)
	fmt.Println("c = ", c)
}