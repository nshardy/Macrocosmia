import org.json.JSONException
import org.json.JSONObject
import utilities.FileManager
import java.awt.Button
import java.awt.TextField
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.charset.Charset

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
	fun saveWorldToFile() {
		val path : String = FileManager.getFolder("worlds")

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
		} catch (e : JSONException) {
			e.printStackTrace()
		}

		// creating the file
		try {
			PrintWriter(
				FileWriter(
					"$path$saveName.json" ,
					Charset.defaultCharset()
				)
			).use { it.write(json.toString(1)) }

			toggleMenuPanels(
				mmPanel = false ,
				spPanel = true ,
				mpPanel = false ,
				cwPanel = false ,
				awPanel = false ,
				cpPanel = false ,
				apPanel = false
			)
		} catch (e : Exception) {
			e.printStackTrace()
		}
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
