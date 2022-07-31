package main

import (
	"context"
	"net/http"
	"os"
	"strconv"

	"github.com/gin-gonic/gin"
	"github.com/go-redis/redis/v8"
)

var ctx = context.Background()

func getNumberOfItems(c *gin.Context) {
	REDIS_HOST := getEnv("REDIS_HOST", "localhost:6379")
	PASSWORD := getEnv("PASSWORD", "")

	rdb := redis.NewClient(&redis.Options{
		Addr:     REDIS_HOST,
		Password: PASSWORD,
		DB:       0,
	})

	number := rdb.Do(ctx, "DBSIZE")
	numeric, err := number.Result()
	if err != nil {
		//panic
	}
	myInt := numeric.(int64)
	s := strconv.FormatInt(myInt, 10)
	c.String(http.StatusOK, "Number of items: "+s)
	//To return JSON:
	/*myInt := numeric.(int64)
	numberOfItems := []numberOfItems{{Number: myInt}}
	c.IndentedJSON(http.StatusOK, numberOfItems)
	*/
}

/*
type numberOfItems struct {
	Number int64 `json:"number_of_items"`
}
*/

func getEnv(key, defaultValue string) string {
	value := os.Getenv(key)
	if len(value) == 0 {
		return defaultValue
	}
	return value
}

func handler(c *gin.Context) {

	c.String(http.StatusOK, "")

}
func main() {
	HOST := getEnv("HOST", "0.0.0.0:9090")
	router := gin.Default()
	router.GET("/get", getNumberOfItems)
	router.GET("/healthz", handler)
	router.Run(HOST)
}
