package main


import "fmt"

// 引用传递
func Print(map1 map[string]string) {

	for key,value := range map1 {
		fmt.Println("key = ", key,"value = ", value)
	}
}


func main()  {
	
	map1 := make(map[string]string, 10)

	// add
	map1["china"] = "beijing"
	map1["japan"] = "tokoy"

	//print
	Print(map1)

	// delete
	delete(map1, "japan")

	Print(map1)


	map1["china"] = "shenzhen"

	Print(map1)

}