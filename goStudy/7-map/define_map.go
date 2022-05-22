package main

import "fmt"

func main()  {
	
	//map 定义三种方式
	// 1
	// var map1 = map[string]string 错误
	var map1 map[string]string
	if(map1 ==  nil) {
		fmt.Println("empty")
	}

	map1 = make(map[string]string,10)
	map1["one"] = "java"
	map1["two"] = "python"


	fmt.Println(map1)

	// 2
	map2 := make(map[int]string, 3)
	map2[1] = "c++"

	fmt.Println(map2)

	// 3
	map3 := map[string]string{
		"one": "c = b",
		"two": "cdd",
	}

	fmt.Println(map3)
}