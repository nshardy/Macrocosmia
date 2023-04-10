import org.json.JSONException
import org.json.JSONObject
import java.awt.Button
import java.awt.TextField
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.JOptionPane
import kotlin.io.path.Path
import kotlin.io.path.exists

class World {
	var worldName : String = ""

	enum class worldPower { Fear , Desperation }
	enum class worldSize { Small , Medium , Large }
	enum class worldDifficulty { Nomad , Serene , Grim , Insufferable , Legendary }

	var WorldPower : worldPower = worldPower.Fear
	var WorldSize : worldSize = worldSize.Small
	var WorldDifficulty : worldDifficulty = worldDifficulty.Nomad
	var intWorldSeed : Int = 0
	var worldSeed : String = ""


	// worldSeed = "${worldPower}.${worldSize}.${worldDifficulty}.${intWorldSeed}"
	fun startNewWorld() {
		var world = this
		if (intWorldSeed == 0)
			intWorldSeed = (Math.random() * 1000000000).toInt()
		worldSeed = "${WorldPower.ordinal}.${WorldSize.ordinal}.${WorldDifficulty.ordinal}.${intWorldSeed}"
	}

	fun saveWorld() {
		var path = ""
		val osName = System.getProperty("os.name")
		when {
			osName.contains("Windows") -> path = System.getProperty("user.home") + "\\Documents\\"
			osName.contains("Mac")     -> path = System.getProperty("user.home") + "/Documents/"
		}


		// getting the saves folder
		val files = Path(path + "Macrocosmia/worlds/")
		if (! files.exists())
			Files.createDirectories(Paths.get("$path/Macrocosmia/worlds/"))
		val worldSaves = path + "Macrocosmia/worlds/"


		// world to save
		val json = JSONObject()
		try {
			json.put("name" , worldName)
			json.put("power" , WorldPower)
			json.put("size" , WorldSize)
			json.put("difficulty" , WorldDifficulty)
			json.put("seed" , "${WorldPower.ordinal}.${WorldSize.ordinal}.${WorldDifficulty.ordinal}.${intWorldSeed}")
		} catch (e : JSONException) {
			e.printStackTrace()
		}

		try {
			PrintWriter(
				FileWriter(
					"$worldSaves$worldName.json" ,
					Charset.defaultCharset()
				)
			).use { it.write(json.toString(2)) }
			JOptionPane.showMessageDialog(
				window ,
				"World created!\nName: [$worldName]\nLocation: $worldSaves$worldName.json\""
			)
		} catch (e : Exception) {
			e.printStackTrace()
		}

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
		val regex = Regex("[^0-9]")
		val result = regex.replace(textField.text , "")

		intWorldSeed = result.toInt(); textField.text = result
	}

	fun setName(textField : TextField) {
		val regex = Regex("a-zA-Z0-9\\s")
		val result = regex.replace(textField.text , "")

		worldName = result; textField.text = result
	}
}
