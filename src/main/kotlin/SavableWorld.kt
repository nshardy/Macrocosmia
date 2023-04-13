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
	private var saveName : String = ""
	private var intWorldSeed : Int = 0

	enum class WorldPower { Fear , Desperation }
	enum class WorldSize { Small , Medium , Large }
	enum class WorldDifficulty { Nomad , Serene , Grim , Insufferable , Legendary }

	private var chosenWorldPower : WorldPower = WorldPower.Fear
	private var chosenWorldSize : WorldSize = WorldSize.Small
	private var chosenWorldDifficulty : WorldDifficulty = WorldDifficulty.Nomad


	// functions
	fun getSaveFileSettings(worldToLoad : String) {
		val file = FileReader(getPath() + "${worldToLoad}.json").readText()
		val saveFile = JSONObject(file)

		println()
		val name = saveFile.getString("name")
		println("Name: $name")
		val power = saveFile.getString("power")
		println("Power: $power")
		val size = saveFile.getString("size")
		println("Size: $size")
		val difficulty = saveFile.getString("difficulty")
		println("Difficulty: $difficulty")
		val seed = saveFile.getString("seed")
		println("Seed: $seed")
	}

	fun getPath() : String {
		val osName : String = System.getProperty("os.name")
		when {
			osName.startsWith("Windows") -> return System.getProperty("user.home") + "\\Documents\\Macrocosmia\\worlds\\"
			osName.startsWith("Mac")     -> return System.getProperty("user.home") + "/Documents/Macrocosmia/worlds/"
		}

		throw Exception("Operating System not supported")
	}

	fun saveWorldToFile() {
		val path : String = getPath()

		// creating the json
		val json = JSONObject()
		try {
			json.run {
				put("name" , saveName)
				put("power" , chosenWorldPower)
				put("size" , chosenWorldSize)
				put("difficulty" , chosenWorldDifficulty)
				put(
					"seed" ,
					"${chosenWorldPower.ordinal}.${chosenWorldSize.ordinal}.${chosenWorldDifficulty.ordinal}.${
						if (intWorldSeed == 0) (Math.random() * 1000000000).toInt() else intWorldSeed
					}"
				)
			}
		} catch (exc : JSONException) {
			exc.printStackTrace()
		}

		// creating the file
		try {
			PrintWriter(
				FileWriter(
					"$path$saveName.json" ,
					Charset.defaultCharset()
				)
			).use { it.write(json.toString(2)) }
			toggleMenuPanels(mmPanel = false , spPanel = true , mpPanel = false , cwPanel = false , awPanel = false)
		} catch (exc : Exception) {
			exc.printStackTrace()
		}

		// loading the world
		getSaveFileSettings(saveName)
	}

	fun setSaveDifficulty(b : Button) {
		when (b.label) {
			"Nomad"        -> chosenWorldDifficulty = WorldDifficulty.Nomad
			"Serene"       -> chosenWorldDifficulty = WorldDifficulty.Serene
			"Grim"         -> chosenWorldDifficulty = WorldDifficulty.Grim
			"Insufferable" -> chosenWorldDifficulty = WorldDifficulty.Insufferable
			"Legendary"    -> chosenWorldDifficulty = WorldDifficulty.Legendary
		}
	}

	fun setSavePower(b : Button) {
		when (b.label) {
			"Fear"        -> chosenWorldPower = WorldPower.Fear
			"Desperation" -> chosenWorldPower = WorldPower.Desperation
		}
	}

	fun setSaveSize(b : Button) {
		when (b.label) {
			"Small"  -> chosenWorldSize = WorldSize.Small
			"Medium" -> chosenWorldSize = WorldSize.Medium
			"Large"  -> chosenWorldSize = WorldSize.Large
		}
	}

	fun setSaveSeed(field : TextField) {
		if (field.text.length >= 11)
			field.text = field.text.slice(IntRange(11 , field.text.length - 1))

		field.text = field.text.replace(Regex("[^0-9]") , "")
		this.intWorldSeed = if (field.text == "") 0 else field.text.toInt()
	}

	fun setSaveName(field : TextField) {
		field.text = field.text.replace(Regex("[^a-zA-Z0-9\\s]") , "")
		saveName = field.text
	}
}
