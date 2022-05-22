package main

import "fmt"

func main(){

	//切片 slice 定义的4种类型
	//1 声明并默认初始化
	//slice1 := []int{2,3,4}

	//2 声明切片，但是不分配空间
	var slice1 []int

	//3 声明并分配空间
	//var slice1 = make([]int , 3)

	//4 推出切片
	//slice1 := make([]int ,3)
	fmt.Printf("len = %d, slice = %v\n",len(slice1),slice1)

	//判断切片是否为空
	if slice1 == nil {
		fmt.Println("slice1 is emplty slice")
	} else {
		fmt.Println("slice1 not empty")
	}
}