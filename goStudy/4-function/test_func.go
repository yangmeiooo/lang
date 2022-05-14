package main

import "fmt"

// 1 函数返回值
func t1() int {
	return 100
}

// 2 函数多返回值
func t2() (int ,int ) {
	return 100,200
}

// 3 函数返回值带参数
func t3()(r1 int, r2 int ) {

	return 300, 100
}

// 4 函数返回值带参数
func t4()(r1 int, r2 int ) {

	r1 = 500
	r2 = 600
	return 300, 100
}

// 5 函数返回值带参数
func t5()(r1 int, r2 int ) {

	r1 = 500
	r2 = 600
	return
}

func main(){

	c := t1()
	fmt.Println("c = ", c)

	ret1,ret2 := t2()
	fmt.Println("ret1 = ", ret1, "ret2 = ", ret2)

	ret3,ret4 := t3()
	fmt.Println("ret3 = ", ret3, "ret4 = ", ret4)

	ret5,ret6 := t4()
	fmt.Println("ret5 = ", ret5, "ret6 = ", ret6)

	ret7,ret8 := t5()
	fmt.Println("ret7 = ", ret7, "ret8 = ", ret8)

	var n1 = 100
	fmt.Println("n1 = ", n1)
}