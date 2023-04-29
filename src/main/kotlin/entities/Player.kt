package entities

import org.json.JSONException
import utilities.InputManager
import org.json.JSONObject
import utilities.FileManager
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.charset.Charset

class Player() {
	// variables
	var playerName : String = ""
	var defense : Int = 0
	var health : Double = 0.0
	var maxHealth : Double = 0.0
	var healthAddModifier : Double = 0.0
	var mana : Double = 0.0
	var maxMana : Double = 0.0
	var manaAddModifier : Double = 0.0
	var moveSpeed : Int = 0

	val InputManager : InputManager = InputManager(this)
	var moveX : Int = 0
	var moveY : Int = 0
	var posX : Int = 0
	var posY : Int = 0


	// functions
	fun move() {
		posX += moveX
		posY += moveY
	}

	fun takeDamage(amount : Int) {
		// checking if amount - defense is greater than 1
		val mod = amount - defense

		health -= if (mod > 1)
			mod
		else 1
	}

	fun addHealth(amount : Int) {
		if (health + amount > maxHealth)
			health = maxHealth
		else health += amount * healthAddModifier
	}

	fun addMana(amount : Int) {
		if (mana + amount > maxMana)
			mana = maxMana
		else mana += amount * manaAddModifier
	}

	fun increaseMaxHealth(amount : Int) {
		maxHealth += amount
	}

	fun increaseMaxMana(amount : Int) {
		maxMana += amount
	}

	fun savePlayerToFile() {
		val path : String = FileManager.getFolder("players")

		// creating the json
		val json = JSONObject()
		try {
			json.run {
				put("playerName" , playerName)
				put("defense" , defense)
				put("health" , health)
				put("maxHealth" , maxHealth)
				put("healthAddModifier" , healthAddModifier)
				put("mana" , mana)
				put("maxMana" , maxMana)
				put("healthAddModifier" , manaAddModifier)
				put("moveSpeed" , moveSpeed)
			}
		} catch (e : JSONException) {
			e.printStackTrace()
		}

		// creating the file
		try {
			PrintWriter(
				FileWriter(
					"$path$playerName.json" ,
					Charset.defaultCharset()
				)
			).use { it.write(json.toString(1)) }

		} catch (e : Exception) {
			e.printStackTrace()
		}
	}

	fun loadSaveFromFile(saveFile : JSONObject) {
		playerName = saveFile.get("playerName") as String
		defense = saveFile.get("defense") as Int
		health = saveFile.get("health") as Double
		maxHealth = saveFile.get("maxHealth") as Double
		healthAddModifier = saveFile.get("healthAddModifier") as Double
		mana = saveFile.get("mana") as Double
		maxMana = saveFile.get("maxMana") as Double
		manaAddModifier = saveFile.get("manaAddModifier") as Double
		moveSpeed = saveFile.get("moveSpeed") as Int
	}

	fun getSaveFile() : JSONObject {
		val file = FileManager.getFileFromFolder("players" , playerName , FileManager.Companion.FileType.Player)
		val data = FileReader(file).readText()
		return JSONObject(data)
	}
}