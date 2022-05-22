package main

import "fmt"


type Hero struct {
	Name string
	Age int
}

func (this *Hero) GetName() string {
	return this.Name
}

func (this *Hero) SetName(newName string) {
	this.Name = newName
}

func (this *Hero) Show()  {
	fmt.Println("name = ", this.Name)
	fmt.Println("age = ",this.Age)
}


func main()  {
	
	hero := Hero{Name: "lzn", Age: 72}

	hero.Show()

	hero.SetName("pqy")

	hero.Show()

}