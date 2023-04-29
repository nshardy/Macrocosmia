package world

import SavableWorld
import org.json.JSONObject

class World(saveFile : JSONObject , playerFile : JSONObject) {
	val name : String = saveFile.get("name") as String
	val size : SavableWorld.WorldSize = SavableWorld.WorldSize.valueOf(saveFile.get("size") as String)
	val power : SavableWorld.WorldPower = SavableWorld.WorldPower.valueOf(saveFile.get("power") as String)
	val difficulty : SavableWorld.WorldDifficulty =
		SavableWorld.WorldDifficulty.valueOf(saveFile.get("difficulty") as String)
}