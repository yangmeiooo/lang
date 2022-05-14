package main

import (
	"goStudy/lib1/lib1"
	"goStudy/lib2/lib2"
)

// 1. init 函数是 module 初始化函数
// 2. main 函数调用逻辑, 去调import lib,init， 再最后去调main
// 3. lib module 里, 方法名大写是 public 方法,方法名小写是 private

func main() {
	lib1.test()
	lib2.test()
}