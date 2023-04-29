package Panels

import org.json.JSONObject
import toggleMenuPanels
import utilities.FileManager
import java.awt.*
import java.io.FileReader
import javax.swing.JOptionPane
import javax.swing.JPanel
import kotlin.system.exitProcess

class SinglePlayerPanel : JPanel() {
	init {
		val mainMenuGridBag = GridBagLayout()
		val mainMenuConstraints = GridBagConstraints()
		this.layout = mainMenuGridBag

		val title = Label("Macrocosmia: A New Dawn")
		title.font = Font("Monospace" , Font.PLAIN , 24)
		mainMenuConstraints.insets = Insets(0 , 0 , 100 , 0)
		mainMenuConstraints.gridx = 1
		mainMenuConstraints.gridy = 0
		this.add(title , mainMenuConstraints)
		val singlePlayer = Button("SinglePlayer")
		singlePlayer.addActionListener {
			if (checkForPlayerFiles()) {
				toggleMenuPanels(
					mmPanel = false ,
					spPanel = false ,
					mpPanel = false ,
					cwPanel = false ,
					awPanel = false ,
					cpPanel = false ,
					apPanel = true
				)
			} else {

				toggleMenuPanels(
					mmPanel = false ,
					spPanel = false ,
					mpPanel = false ,
					cwPanel = false ,
					awPanel = false ,
					cpPanel = true ,
					apPanel = false
				)
			}
		}
		mainMenuConstraints.insets = Insets(0 , 0 , 0 , 0)
		mainMenuConstraints.gridx = 1
		mainMenuConstraints.gridy = 1
		this.add(singlePlayer , mainMenuConstraints)
		val multiplayer = Button("Multiplayer")
		multiplayer.addActionListener {
			JOptionPane.showMessageDialog(
				this ,
				"multiplayer is not implemented yet.\nthis will be implemented in newer updates."
			)
		}
		mainMenuConstraints.gridx = 1
		mainMenuConstraints.gridy = 2
		this.add(multiplayer , mainMenuConstraints)
		val exit = Button("Quit")
		exit.addActionListener { exitProcess(0) }
		exit.size = Dimension(60 , 30)
		mainMenuConstraints.gridx = 1
		mainMenuConstraints.gridy = 3
		this.add(exit , mainMenuConstraints)
	}

	private fun checkForPlayerFiles() : Boolean {
		var hasPlayers = false

		val players = FileManager.getAllFilesFromFolder("players")
		if (players != null) {
			for (player in players) {
				if (! player.name.contains(".json")) continue

				val file = FileReader(FileManager.getFolder("players") + player.name).readText()
				val saveFile = JSONObject(file)

				// making sure it has a valid seed
				val name = saveFile.get("playerName")
				if (name == "")
					continue
			}
		}

		return hasPlayers
	}
}