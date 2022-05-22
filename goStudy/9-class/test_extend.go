package main

import "fmt"

type Person struct {

	Name string
	Age int

}

func (this *Person) Wlak() {
	fmt.Println("Person wlak...")
}

func (this *Person) Run() {
	fmt.Println("Person run...")
}


// son class
type Superman struct {

	Person
	femela int
}

func (this *Superman) Wlak() {
	fmt.Println("Superman wlak..")
}



func main()  {
	person := Person{Name: "lzn", Age: 25}

	person.Wlak()

	superman := Superman{Person{Name:"pqy",Age: 24}, 1}
	
	superman.Wlak()

	// 定义子类
	var s Superman
	s.Name = "opp"
	s.Age = 20
	s.femela = 0
}