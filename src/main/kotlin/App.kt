import Panels.MenuPanel
import entities.Player
import org.json.JSONObject
import utilities.FileManager
import world.World
import java.awt.*
import java.io.File
import java.io.FileReader
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.UIManager


// variables
var width : Int = 700
var height : Int = 550
val window : JFrame = JFrame()
val mainMenuPanel : JPanel = MenuPanel()
val singlePlayerPanel : JPanel = JPanel()
var createWorldPanel : JPanel = JPanel()
var createPlayerPanel : JPanel = JPanel()
val allWorldsPanel : ScrollPane = ScrollPane()
val allPlayersPanel : ScrollPane = ScrollPane()
val multiplayerPanel : JPanel = JPanel()
val icon : Image = ImageIO.read(File("src/main/resources/sprites/Icon.png"))
val scaledIcon : Image = icon.getScaledInstance(128 , 128 , Image.SCALE_SMOOTH)
var savableWorld : SavableWorld = SavableWorld()
var player : Player = Player()

var worldContainer : JPanel = JPanel()
var playerContainer : JPanel = JPanel()


// functions
fun main() {
	// variables
	window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
	window.size = Dimension(width , height)
	window.iconImage = scaledIcon

	if (System.getProperty("os.name").startsWith("Mac") || System.getProperty("os.name").startsWith("Darwin")) {
		val taskbar = Taskbar.getTaskbar()

		try {
			taskbar.iconImage = scaledIcon
		} catch (e : UnsupportedOperationException) {
			println("Can't set taskbar icon due to UnsupportedOperationException.")
		} catch (e : SecurityException) {
			println("Can't set taskbar icon due to SecurityExceptions.")
		}
	}

	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

	panelCreateSinglePlayer()
	panelCreateMultiplayer()
	panelCreateNewWorld()
	panelCreateNewPlayer()

	toggleMenuPanels(
		mmPanel = true ,
		spPanel = false ,
		mpPanel = false ,
		cwPanel = false ,
		awPanel = false ,
		cpPanel = false ,
		apPanel = false
	)
	window.setLocationRelativeTo(null)
	window.isVisible = true
}

private fun panelCreateSinglePlayer() {
	val singlePlayerGridBag = GridBagLayout()
	val singlePlayerConstraints = GridBagConstraints()
	singlePlayerPanel.layout = singlePlayerGridBag

	val worldsTitle = Label("Worlds")
	worldsTitle.font = Font("Monospace" , Font.PLAIN , 24)
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = - 1
	singlePlayerPanel.add(worldsTitle , singlePlayerConstraints)

	allWorldsPanel.size = Dimension(500 , 300)
	singlePlayerPanel.add(allWorldsPanel , singlePlayerConstraints)
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 1

	val newWorld = Button("Create new World")
	newWorld.addActionListener {
		toggleMenuPanels(
			mmPanel = false ,
			spPanel = false ,
			mpPanel = false ,
			cwPanel = true ,
			awPanel = false ,
			cpPanel = false ,
			apPanel = false
		)
	}
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 2
	singlePlayerPanel.add(newWorld , singlePlayerConstraints)

	val back = Button("Back")
	back.addActionListener {
		toggleMenuPanels(
			mmPanel = false ,
			spPanel = false ,
			mpPanel = false ,
			cwPanel = true ,
			awPanel = false ,
			cpPanel = false ,
			apPanel = false
		)
	}
	back.size = Dimension(60 , 30)
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 4
	singlePlayerPanel.add(back , singlePlayerConstraints)
}

private fun panelCreateNewWorld() {
	// savableWorld creation
	createWorldPanel = JPanel()
	val worldCreateGridBag = GridBagLayout()
	val worldCreateConstraints = GridBagConstraints()
	createWorldPanel.layout = worldCreateGridBag


	val worldNameLabel = Label("World Name:")
	worldCreateConstraints.gridy = 0
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldNameLabel , worldCreateConstraints)
	val worldNameInput = TextField("indesputable torment")
	savableWorld.setSaveName(worldNameInput)
	worldNameInput.addTextListener { savableWorld.setSaveName(worldNameInput) }
	worldCreateConstraints.gridx = 3
	createWorldPanel.add(worldNameInput , worldCreateConstraints)

	val worldSeedLabel = Label("World Seed:")
	worldCreateConstraints.gridy = 1
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldSeedLabel , worldCreateConstraints)
	val worldSeedInput = TextField("1000000000")
	worldCreateConstraints.gridx = 3
	worldSeedInput.text = "${(Math.random() * 1000000000).toInt()}"
	savableWorld.setSaveSeed(worldSeedInput)
	worldSeedInput.addTextListener { savableWorld.setSaveSeed(worldSeedInput) }
	createWorldPanel.add(worldSeedInput , worldCreateConstraints)


	val worldDifficultyLabel = Label("World Difficulty:")
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 2
	createWorldPanel.add(worldDifficultyLabel , worldCreateConstraints)

	val worldDifficultyLowest = Button("Nomad")
	worldDifficultyLowest.addActionListener { savableWorld.setSaveDifficulty(worldDifficultyLowest) }
	worldCreateConstraints.gridx = 1
	createWorldPanel.add(worldDifficultyLowest , worldCreateConstraints)

	val worldDifficultyLow = Button("Serene")
	worldDifficultyLow.addActionListener { savableWorld.setSaveDifficulty(worldDifficultyLow) }
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldDifficultyLow , worldCreateConstraints)

	val worldDifficultyMedium = Button("Grim")
	worldDifficultyMedium.addActionListener { savableWorld.setSaveDifficulty(worldDifficultyMedium) }
	worldCreateConstraints.gridx = 3
	createWorldPanel.add(worldDifficultyMedium , worldCreateConstraints)

	val worldDifficultyHigh = Button("Insufferable")
	worldDifficultyHigh.addActionListener { savableWorld.setSaveDifficulty(worldDifficultyHigh) }
	worldCreateConstraints.gridx = 4
	createWorldPanel.add(worldDifficultyHigh , worldCreateConstraints)

	val worldDifficultyTooHigh = Button("Legendary")
	worldDifficultyTooHigh.addActionListener { savableWorld.setSaveDifficulty(worldDifficultyTooHigh) }
	worldCreateConstraints.gridx = 5
	createWorldPanel.add(worldDifficultyTooHigh , worldCreateConstraints)


	val worldSizeLabel = Label("World Size:")
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 3
	createWorldPanel.add(worldSizeLabel , worldCreateConstraints)

	val worldSizeSmall = Button("Small")
	worldSizeSmall.addActionListener { savableWorld.setSaveSize(worldSizeSmall) }
	worldCreateConstraints.gridx = 1
	createWorldPanel.add(worldSizeSmall , worldCreateConstraints)

	val worldSizeMedium = Button("Medium")
	worldSizeMedium.addActionListener { savableWorld.setSaveSize(worldSizeMedium) }
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldSizeMedium , worldCreateConstraints)

	val worldSizeLarge = Button("Large")
	worldSizeLarge.addActionListener { savableWorld.setSaveSize(worldSizeLarge) }
	worldCreateConstraints.gridx = 3
	createWorldPanel.add(worldSizeLarge , worldCreateConstraints)

	val worldPowerLabel = Label("World Power:")
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 4
	createWorldPanel.add(worldPowerLabel , worldCreateConstraints)

	val worldPowerFear = Button("Fear")
	worldPowerFear.addActionListener { savableWorld.setSavePower(worldPowerFear) }
	worldCreateConstraints.gridx = 1
	createWorldPanel.add(worldPowerFear , worldCreateConstraints)

	val worldPowerDesperation = Button("Desperation")
	worldPowerDesperation.addActionListener { savableWorld.setSavePower(worldPowerDesperation) }
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldPowerDesperation , worldCreateConstraints)


	val backToWorlds = Button("Back")
	backToWorlds.addActionListener {
		if (checkForWorldFiles()) toggleMenuPanels(
			mmPanel = false ,
			spPanel = true ,
			mpPanel = false ,
			cwPanel = false ,
			awPanel = false ,
			cpPanel = false , apPanel = false
		)
		else toggleMenuPanels(
			mmPanel = true ,
			spPanel = false ,
			mpPanel = false ,
			cwPanel = false ,
			awPanel = false ,
			cpPanel = false ,
			apPanel = false
		)
	}
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 5
	createWorldPanel.add(backToWorlds , worldCreateConstraints)

	val createWorld = Button("Create world")
	createWorld.addActionListener { savableWorld.saveWorldToFile() }
	worldCreateConstraints.gridx = 5
	createWorldPanel.add(createWorld , worldCreateConstraints)
}

private fun panelCreateNewPlayer() {
	// savableWorld creation
	createPlayerPanel = JPanel()
	val worldCreateGridBag = GridBagLayout()
	val playerCreateConstraints = GridBagConstraints()
	createPlayerPanel.layout = worldCreateGridBag


	val worldNameLabel = Label("Player Name:")
	playerCreateConstraints.gridx = 2
	playerCreateConstraints.gridy = 0
	createPlayerPanel.add(worldNameLabel , playerCreateConstraints)
	val worldNameInput = TextField("jorge")
	savableWorld.setSaveName(worldNameInput)
	worldNameInput.addTextListener { savableWorld.setSaveName(worldNameInput) }
	playerCreateConstraints.gridx = 3
	createPlayerPanel.add(worldNameInput , playerCreateConstraints)


	val worldDifficultyLabel = Label("Player Difficulty:")
	playerCreateConstraints.gridx = 0
	playerCreateConstraints.gridy = 2
	createPlayerPanel.add(worldDifficultyLabel , playerCreateConstraints)

	val worldDifficultyLowest = Button("Nomad")
	worldDifficultyLowest.addActionListener { savableWorld.setSaveDifficulty(worldDifficultyLowest) }
	playerCreateConstraints.gridx = 1
	createPlayerPanel.add(worldDifficultyLowest , playerCreateConstraints)

	val worldDifficultyLow = Button("Softcore")
	worldDifficultyLow.addActionListener { savableWorld.setSaveDifficulty(worldDifficultyLow) }
	playerCreateConstraints.gridx = 1
	createPlayerPanel.add(worldDifficultyLow , playerCreateConstraints)

	val worldDifficultyMedium = Button("Mediumcore")
	worldDifficultyMedium.addActionListener { savableWorld.setSaveDifficulty(worldDifficultyMedium) }
	playerCreateConstraints.gridx = 2
	createPlayerPanel.add(worldDifficultyMedium , playerCreateConstraints)

	val worldDifficultyHigh = Button("Hardcore")
	worldDifficultyHigh.addActionListener { savableWorld.setSaveDifficulty(worldDifficultyHigh) }
	playerCreateConstraints.gridx = 3
	createPlayerPanel.add(worldDifficultyHigh , playerCreateConstraints)


	val backToWorlds = Button("Back")
	backToWorlds.addActionListener {
		if (checkForWorldFiles()) toggleMenuPanels(
			mmPanel = false ,
			spPanel = true ,
			mpPanel = false ,
			cwPanel = false ,
			awPanel = false ,
			cpPanel = false , apPanel = false
		)
		else toggleMenuPanels(
			mmPanel = true ,
			spPanel = false ,
			mpPanel = false ,
			cwPanel = false ,
			awPanel = false ,
			cpPanel = false ,
			apPanel = false
		)
	}
	playerCreateConstraints.gridx = 0
	playerCreateConstraints.gridy = 5
	createPlayerPanel.add(backToWorlds , playerCreateConstraints)

	val createWorld = Button("Create Player")
	createWorld.addActionListener { player.savePlayerToFile() }
	playerCreateConstraints.gridx = 5
	createPlayerPanel.add(createWorld , playerCreateConstraints)
}

private fun panelCreateMultiplayer() {
	val multiplayerGridBag = GridBagLayout()
	val multiplayerConstraints = GridBagConstraints()
	multiplayerPanel.layout = multiplayerGridBag

	val backFromMp = Button("Back")
	backFromMp.addActionListener {
		toggleMenuPanels(
			mmPanel = true , spPanel = false , mpPanel = false , cwPanel = false , awPanel = false ,
			cpPanel = false ,
			apPanel = false
		)
	}
	backFromMp.size = Dimension(60 , 30)
	multiplayerConstraints.gridx = 1
	multiplayerConstraints.gridy = 2
	multiplayerPanel.add(backFromMp , multiplayerConstraints)
}


fun toggleMenuPanels(
	mmPanel : Boolean ,
	spPanel : Boolean ,
	mpPanel : Boolean ,
	cwPanel : Boolean ,
	awPanel : Boolean ,
	cpPanel : Boolean ,
	apPanel : Boolean ,
) {
	when {
		! mmPanel -> window.remove(mainMenuPanel)
		mmPanel   -> window.add(mainMenuPanel)
	}
	when {
		! spPanel -> window.remove(singlePlayerPanel)
		spPanel   -> {
			window.add(singlePlayerPanel)
			createWorldInfo()
		}
	}
	when {
		! mpPanel -> window.remove(multiplayerPanel)
		mpPanel   -> window.add(multiplayerPanel)
	}
	when {
		! cwPanel -> window.remove(createWorldPanel)
		cwPanel   -> {
			panelCreateNewWorld()
			window.add(createWorldPanel)
		}
	}
	when {
		! awPanel -> singlePlayerPanel.remove(createWorldPanel)
		awPanel   -> singlePlayerPanel.add(createWorldPanel)
	}
	when {
		! cpPanel -> singlePlayerPanel.remove(createPlayerPanel)
		cpPanel   -> {
			panelCreateNewPlayer()
			singlePlayerPanel.add(createPlayerPanel)
		}
	}
	when {
		! apPanel -> singlePlayerPanel.remove(allPlayersPanel)
		apPanel   -> {
			singlePlayerPanel.add(allPlayersPanel)
			createPlayerInfo()
		}
	}

	window.revalidate()
	window.repaint()
}

private fun checkForWorldFiles() : Boolean {
	var hasWorlds = false

	val worlds = FileManager.getAllFilesFromFolder("worlds")
	if (worlds != null) {
		for (world in worlds) {
			if (! world.name.contains(".json")) continue

			val file = FileReader(FileManager.getFolder("worlds") + world.name).readText()
			val saveFile = JSONObject(file)

			// making sure it has a valid seed
			val seed = saveFile.get("seed") as String
			val intSeed = seed.slice(IntRange(6 , seed.length - 1)).toIntOrNull()
			if (intSeed is Int)
				hasWorlds = true
		}
	}

	return hasWorlds
}

private fun createWorldInfo() {
	for (i in worldContainer.components) {
		worldContainer.remove(i)
	}

	val files = FileManager.getAllFilesFromFolder("worlds")
	files?.sort()

	if (files == null) return
	for (i in files.indices) {
		// making sure the file is a json
		if (! files[i].name.contains(".json")) continue

		val file = FileReader(FileManager.getFolder("worlds") + files[i].name).readText()
		val saveFile = JSONObject(file)

		// making sure it has a valid seed
		val seedCheck = saveFile.get("seed") as String
		val intSeed = seedCheck.slice(IntRange(6 , seedCheck.length - 1)).toIntOrNull()
		if (intSeed !is Int)
			continue

		addWorldInfoToPanel(saveFile , i)
	}

	allWorldsPanel.add(worldContainer)

}

fun addWorldInfoToPanel(saveFile : JSONObject , i : Int) {
	// creating the single world
	val con = GridBagConstraints()
	val lay = GridBagLayout()
	val singleWorld = JPanel()
	singleWorld.layout = lay

	val viewportCon = GridBagConstraints()
	val viewportLay = GridBagLayout()
	worldContainer.layout = viewportLay


	val selectWorldForPlay = Button("Select")
	selectWorldForPlay.addActionListener { val world = World(saveFile , player.getSaveFile()); /*world.init()*/ }
	con.gridx = 1
	con.gridy = 0
	singleWorld.add(selectWorldForPlay , con)

	val name = Label(saveFile.get("name") as String)
	name.size = Dimension(400 , name.size.height)
	con.gridy = 1
	singleWorld.add(name , con)

	val seed = Label(saveFile.get("seed") as String)
	con.gridy = 2
	singleWorld.add(seed , con)

	viewportCon.gridx = 0
	viewportCon.gridy = i
	worldContainer.add(singleWorld , viewportCon)
}

private fun createPlayerInfo() {
	for (i in playerContainer.components) {
		playerContainer.remove(i)
	}

	val files = FileManager.getAllFilesFromFolder("players")
	files?.sort()

	if (files == null) return
	for (i in files.indices) {
		// making sure the file is a json
		if (! files[i].name.contains(".json")) continue

		val file = FileReader(FileManager.getFolder("players") + files[i].name).readText()
		val saveFile = JSONObject(file)

		addPlayerInfoToPanel(saveFile , i)
	}

	allPlayersPanel.add(playerContainer)
}

fun addPlayerInfoToPanel(saveFile : JSONObject , i : Int) {
	// creating the single world
	val con = GridBagConstraints()
	val lay = GridBagLayout()
	val singlePlayer = JPanel()
	singlePlayer.layout = lay

	val viewportCon = GridBagConstraints()
	val viewportLay = GridBagLayout()
	playerContainer.layout = viewportLay


	val selectWorldForPlay = Button("Select")
	selectWorldForPlay.addActionListener { player = Player(); player.loadSaveFromFile(saveFile) }
	con.gridx = 1
	con.gridy = 0
	singlePlayer.add(selectWorldForPlay , con)

	val name = Label(saveFile.get("playerName") as String)
	name.size = Dimension(400 , name.size.height)
	con.gridy = 1
	singlePlayer.add(name , con)

	val seed = Label(saveFile.get("difficulty") as String)
	con.gridy = 2
	singlePlayer.add(seed , con)

	viewportCon.gridx = 0
	viewportCon.gridy = i
	playerContainer.add(singlePlayer , viewportCon)
}

