package main

import "fmt"

func modifyslice(myslice []int) {

	fmt.Println("==========midify========")
	myslice[0] = 100

}


func main(){

	// 数组定义写法
	var arr1 [2]int
	fmt.Printf("%T\n", arr1)

	myarray := [3]int{2,3,1}

	// 2种for循环
	for i:= 0;i < len(myarray);i++ {
		fmt.Println("value = ", myarray[i])
	}

	for index, value := range myarray {
		fmt.Println("index =",index,"value = ",value)
	}

	fmt.Printf("myarray type is =%T\n", myarray)


	// slice 切片
	arr3 := []int{3,4,1}

	for index, val := range arr3 {
		fmt.Println("index = ", index, "value = ", val)
	}

	// 切片引用传递
	modifyslice(arr3)
	fmt.Println("=======end===========")
	for index, v := range arr3 {
		fmt.Println("index = ",index,"value = ",v)
	}
}