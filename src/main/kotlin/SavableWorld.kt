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
	fun loadWorld(worldToLoad : String) {
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

		loadWorld(worldName)
	}

	fun setDifficulty(button : Button) {
		when (button.label) {
			"Nomad"        -> WorldDifficulty = worldDifficulty.Nomad
			"Serene"       -> WorldDifficulty = worldDifficulty.Serene
			"Grim"         -> WorldDifficulty = worldDifficulty.Grim
			"Insufferable" -> WorldDifficulty = worldDifficulty.Insufferable
			"Legendary"    -> WorldDifficulty = worldDifficulty.Legendary
		}
	}

	fun setPower(button : Button) {
		when (button.label) {
			"Fear"        -> WorldPower = worldPower.Fear
			"Desperation" -> WorldPower = worldPower.Desperation
		}
	}

	fun setSize(button : Button) {
		when (button.label) {
			"Small"  -> WorldSize = worldSize.Small
			"Medium" -> WorldSize = worldSize.Medium
			"Large"  -> WorldSize = worldSize.Large
		}
	}

	fun setSeed(textField : TextField) {
		val regex = Regex(pattern = "0-9")
		val result = regex.replace(textField.text , "")

		this.intWorldSeed = result.toInt()
		textField.text = result
	}

	fun setName(textField : TextField) {
		val regex = Regex(pattern = "A-Za-z0-9\\s")
		val result = regex.replace(textField.text , "")

		worldName = result
		textField.text = result
	}
}
