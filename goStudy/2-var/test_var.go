package main

import "fmt"

func main(){

	// 1.声明变量 不赋值，会自动初始化默认值
	var a int
	fmt.Println("a = ", a)

	// 2 声明变量，并赋值
	var b int  = 3
	fmt.Println("b = ", b)

	// 3 声明变量，不写类型
	var c = 100
	fmt.Println("c = ", c)
	// 4 :
	d := 100
	fmt.Println("d = ", d)

	// 打印变量类型
	e := "abcd"
	fmt.Println("e = ", e)
	fmt.Printf("e type is of = %T\n", e)


	// 全局变量声明
	// 1,2,3 都是可以的
	// 4 不行, 不行原因是 c := ，全局变量传入函数不兼容


	// 多行赋值 1
	var x,y = 10, "test string"
	fmt.Println("x = ",x, "y = ", y)

	// 多行赋值 2
	var (
		vv int = 100
		bb int = 200
		cc string = "多行赋值 string"
	)
	fmt.Println("vv = ", vv, "bb = ", bb, "cc = ",cc)
}