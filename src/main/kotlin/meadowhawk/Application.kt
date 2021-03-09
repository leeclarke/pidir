package meadowhawk

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("meadowhawk")
		.start()
}

