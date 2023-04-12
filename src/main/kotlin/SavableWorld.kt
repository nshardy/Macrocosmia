import org.json.JSONException
import org.json.JSONObject
import java.awt.Button
import java.awt.TextField
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.charset.Charset
import javax.swing.JOptionPane

class SavableWorld {
	// variables
	var worldName : String = ""
	var worldSeed : String = ""
	var intWorldSeed : Int = 0

	enum class worldPower { Fear , Desperation }
	enum class worldSize { Small , Medium , Large }
	enum class worldDifficulty { Nomad , Serene , Grim , Insufferable , Legendary }

	var WorldPower : worldPower = worldPower.Fear
	var WorldSize : worldSize = worldSize.Small
	var WorldDifficulty : worldDifficulty = worldDifficulty.Nomad


	// functions
	fun getWorldSettings(worldToLoad : String) {
		val file = FileReader("${getPath()}/${worldToLoad}.json").readText()
		val saveFile : JSONObject = JSONObject(file)

		val name = saveFile.getString("name")
		println(name)
		val power = saveFile.getString("power")
		println(power)
		val size = saveFile.getString("size")
		println(size)
		val difficulty = saveFile.getString("difficulty")
		println(difficulty)
		val seed = saveFile.getString("seed")
		println(seed)
		val intSeed = seed.slice(IntRange(6 , seed.length - 1))
		println(intSeed)
	}

	fun getPath() : String {
		val osName : String = System.getProperty("os.name")
		when {
			osName.contains("Windows") -> return System.getProperty("user.home") + "\\Documents\\Macrocosmia\\worlds\\"
			osName.contains("Mac")     -> return System.getProperty("user.home") + "/Documents/Macrocosmia/worlds/"
		}

		return ""
	}

	fun saveWorld() {
		var path : String = getPath()

		// creating the json
		val json = JSONObject()
		try {
			json.run {
				put("name" , worldName)
				put("power" , WorldPower)
				put("size" , WorldSize)
				put("difficulty" , WorldDifficulty)
				put(
					"seed" ,
					"${WorldPower.ordinal}.${WorldSize.ordinal}.${WorldDifficulty.ordinal}.${
						if (intWorldSeed == 0) (Math.random() * 1000000000).toInt() else intWorldSeed
					}"
				)
			}
		} catch (e : JSONException) {
			e.printStackTrace()
		}

		// creating the file
		try {
			PrintWriter(
				FileWriter(
					"$path$worldName.json" ,
					Charset.defaultCharset()
				)
			).use { it.write(json.toString(2)) }
			JOptionPane.showMessageDialog(
				window ,
				"SavableWorld created!\nName: [$worldName]\nLocation: $path$worldName.json\""
			)
		} catch (e : Exception) {
			e.printStackTrace()
		}

		// loading the world
		getWorldSettings(worldName)
	}

	fun setDifficulty(b : Button) {
		when (b.label) {
			"Nomad"        -> WorldDifficulty = worldDifficulty.Nomad
			"Serene"       -> WorldDifficulty = worldDifficulty.Serene
			"Grim"         -> WorldDifficulty = worldDifficulty.Grim
			"Insufferable" -> WorldDifficulty = worldDifficulty.Insufferable
			"Legendary"    -> WorldDifficulty = worldDifficulty.Legendary
		}
	}

	fun setPower(b : Button) {
		when (b.label) {
			"Fear"        -> WorldPower = worldPower.Fear
			"Desperation" -> WorldPower = worldPower.Desperation
		}
	}

	fun setSize(b : Button) {
		when (b.label) {
			"Small"  -> WorldSize = worldSize.Small
			"Medium" -> WorldSize = worldSize.Medium
			"Large"  -> WorldSize = worldSize.Large
		}
	}

	fun setSeed(field : TextField) {
		if (field.text.length >= 11)
			field.text = field.text.slice(IntRange(11 , field.text.length - 1))

		field.text = field.text.replace(Regex("[^0-9]") , "")
		this.intWorldSeed = if (field.text == "") 0 else field.text.toInt()
	}

	fun setName(field : TextField) {
		field.text = field.text.replace(Regex("[^a-zA-Z0-9\\s]") , "")

		worldName = field.text
	}
}
