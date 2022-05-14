// 1.go mod 命令
//go mod init  初始化go mod file
//go mod download 下载go.mod 文件中指明的所有依赖
//go mod tidy 整理现有依赖(会自动下载项目的依赖)
//go mod graph 查看现有的依赖结构
//go mod edit 编辑go.mod 文件
//go mod vendor 导出项目所有的依赖到vender目录
//go mod verify 校验一个模块是否被篡改过
//go mod why 查看为什么需要依赖某模块


//2.go env -w GOPROXY=https://goproxy.cn,direct
//module 下载地址，下载不到 再去github(direct)

//3.gosumdb
//拉取库的 checksum 校验和检查。
//go env | grep GOSUMDB
//不设置GOSUMDB, 默认从下载镜像网站校验。

//4.GONOPROXY / GONOSUMDB / GOPRIVATE
//最后一个会覆盖 前2个配置
//go env -w GOPRIVATE="git.example.com, github/com"

//5.通过go env 查看环境变量

//6.通过 go env -w xx="" 写入环境变量


//7. 改变项目模块的版本依赖关系.  go mod edit -replace=xxx=xx