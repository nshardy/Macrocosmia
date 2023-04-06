class World {
	var worldName : String = "HelloWorld"

	enum class worldPower { Fear , Desperation }
	enum class worldSize { Small , Medium , Large }
	enum class worldDifficulty { Nomad , Serene , Grim , Insufferable }

	var WorldPower : worldPower = worldPower.Fear
	var WorldSize : worldSize = worldSize.Small
	var WorldDifficulty : worldDifficulty = worldDifficulty.Nomad
	var intWorldSeed : Int = 0
	var worldSeed : String = ""


	// worldSeed = "${worldPower}.${worldSize}.${worldDifficulty}.${intWorldSeed}"
	fun startNewWorld() {
		var world = this
		intWorldSeed = (Math.random() * 1000000000).toInt()
		worldSeed = "${WorldPower.ordinal}.${WorldSize.ordinal}.${WorldDifficulty.ordinal}.${intWorldSeed}"
	}

	fun saveWorld() {
		// TODO: saving not working

//		val worldSaves = "~/Library/Application Support/org.example/worlds/"
//		val files = Path(worldSaves)
//		val path : String = if (files.exists())
//			"~/Library/Application Support/org.example/worlds/"
//		else {
//			Files.createDirectories(Paths.get("~/Library/Application Support/org.example/worlds"))
//			"~/Library/Application Support/org.example/worlds"
//		}

//		val json = JSONObject()
//		try {
//			json.put("power" , WorldPower)
//			json.put("size" , WorldSize)
//			json.put("difficulty" , WorldDifficulty)
//			json.put("seed" , "${WorldPower.ordinal}.${WorldSize.ordinal}.${WorldDifficulty.ordinal}.${intWorldSeed}")
//		} catch (e : JSONException) {
//			e.printStackTrace()
//		}


//		try {
//			PrintWriter(FileWriter(path , Charset.defaultCharset()))
//				.use { it.write(json.toString()) }
//		} catch (e : Exception) {
//			e.printStackTrace()
//		}

	}
}