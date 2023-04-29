package utilities

import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import kotlin.io.path.Path

class FileManager {
	companion object {
		private val osName : String = System.getProperty("os.name")
		private val macMainFolderPath : String = System.getProperty("user.home") + "/Documents/Macrocosmia/"
		private val windowsMainFolderPath : String = System.getProperty("user.home") + "\\Documents\\Macrocosmia\\"

		enum class FileType { World , Player , Null }

		/**
		 * Gets the World settings, going to be used for loading worlds
		 *
		 * @param worldToLoad The name of the folder you want to access
		 */
		fun getWorldSettings(worldToLoad : String) {
			val file = FileReader(getFileFromFolder("worlds" , worldToLoad , FileType.World)).readText()
			val saveFile = JSONObject(file)

			val name = saveFile.get("name")
			val power = saveFile.get("power")
			val size = saveFile.get("size")
			val difficulty = saveFile.get("difficulty")
			val seed = saveFile.get("seed")

			println()
			println("Name: $name")
			println("Power: $power")
			println("Size: $size")
			println("Difficulty: $difficulty")
			println("Seed: $seed")
		}

		/**
		 * Gets the Player settings, going to be used for loading players
		 *
		 * @param playerToLoad The name of the folder you want to access
		 */
		fun getPlayerSettings(playerToLoad : String) {
			val file = FileReader(getFileFromFolder("players" , playerToLoad , FileType.Player)).readText()
			val saveFile = JSONObject(file)

			val playerName = saveFile.getString("playerName")
			val defense = saveFile.getInt("defense")
			val health = saveFile.getDouble("health")
			val maxHealth = saveFile.getDouble("maxHealth")
			val healthAddModifier = saveFile.getDouble("healthAddModifier")
			val mana = saveFile.getDouble("mana")
			val maxMana = saveFile.getDouble("maxMana")
			val manaAddModifier = saveFile.getDouble("manaAddModifier")
			val moveSpeed = saveFile.getInt("moveSpeed")

			println()
			println("playerName: $playerName")
			println("defense: $defense")
			println("health: $health")
			println("maxHealth: $maxHealth")
			println("healthAddModifier: $healthAddModifier")
			println("mana: $mana")
			println("maxMana: $maxMana")
			println("manaAddModifier: $manaAddModifier")
			println("moveSpeed: $moveSpeed")
		}

		/**
		 * Returns all files in [folderName]
		 *
		 * @param folderName The name of the folder you want to access
		 * @return Array<File>
		 */
		fun getAllFilesFromFolder(folderName : String) : Array<File>? = File(getFolder(folderName)).listFiles()

		/**
		 * Returns [fileName] in [folderName]
		 *
		 * @param folderName The name of the folder you want to access
		 * @param fileName    The name of the file you want to return from [folderName], WITHOUT the file extensions
		 * @param fileType     The type of file you're trying to get, i.e. World, Player
		 * @return File
		 */
		fun getFileFromFolder(folderName : String , fileName : String , fileType : FileType) : File {
			when (fileType) {
				FileType.World  ->
					for (file in File(getFolder(folderName)).listFiles() !!) {
						if (file.name != "$fileName.json") continue
						return file
					}

				FileType.Player ->
					for (file in File(getFolder(folderName)).listFiles() !!) {
						if (file.name != "$fileName.plr") continue
						return file
					}

				FileType.Null   -> TODO()
			}

			throw Exception("File not Found")
		}

		/**
		 * Returns [folderName] from either Mac or Windows location
		 * Typically from the 'Documents/Macrocosmia/'
		 *
		 * @param folderName The name of the folder you want to access
		 * @return String
		 */
		fun getFolder(folderName : String) : String {
			when {
				osName.startsWith("Windows") -> {
					if (! File("$windowsMainFolderPath$folderName\\").exists())
						Files.createDirectories(Path("$windowsMainFolderPath$folderName\\"))
					return "$windowsMainFolderPath$folderName\\"
				}

				osName.startsWith("Mac")     -> {
					if (! File("$macMainFolderPath$folderName/").exists())
						Files.createDirectories(Path("$macMainFolderPath$folderName/"))
					return "$macMainFolderPath$folderName/"
				}
			}

			throw Exception("Operating System not supported")
		}
	}
}