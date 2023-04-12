import org.json.JSONObject
import java.awt.*
import java.io.File
import java.io.FileReader
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.UIManager
import kotlin.system.exitProcess


// variables
var width : Int = 700
var height : Int = 550
val window : JFrame = JFrame()
val mainMenuPanel : JPanel = JPanel()
val singlePlayerPanel : JPanel = JPanel()
val createWorldPanel : JPanel = JPanel()
val allWorldsPanel : ScrollPane = ScrollPane()
val multiplayerPanel : JPanel = JPanel()
val icon : Image = ImageIO.read(File("src/main/resources/sprites/Icon.png"))
val scaledIcon : Image = icon.getScaledInstance(128 , 128 , Image.SCALE_SMOOTH)
var savableWorld : SavableWorld = SavableWorld()


// functions
fun main() {
	// variables
	window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
	window.size = Dimension(width , height)
	window.iconImage = scaledIcon

	try {
		window.iconImage = scaledIcon
		if (System.getProperty("os.name").startsWith("Mac") || System.getProperty("os.name").startsWith("Darwin")) {
			val taskbar = Taskbar.getTaskbar()
			try {
				taskbar.iconImage = scaledIcon
			} catch (e : UnsupportedOperationException) {
				println("Can't set taskbar icon.")
			} catch (e : SecurityException) {
				println("WARNING! Can't set taskbar icon due to security exceptions.")
			}
		}
	} catch (e : NullPointerException) {
		e.printStackTrace()
	}

	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

	val mainMenuGridBag = GridBagLayout()
	val mainMenuConstraints = GridBagConstraints()
	mainMenuPanel.layout = mainMenuGridBag

	panelCreateMainMenu(mainMenuConstraints)
	panelCreateSinglePlayer()
	panelCreateMultiplayer()
	panelCreateNewWorld()

	allWorldsPanel.size = Dimension(400 , 300)

	toggleMenuPanels(mmPanel = true , spPanel = false , mpPanel = false , cwPanel = false , awPanel = false)
	window.setLocationRelativeTo(null)
	window.isVisible = true
}

private fun panelCreateMainMenu(mainMenuConstraints : GridBagConstraints) {
	val title = Label("Macrocosmia: A New Dawn")
	title.font = Font("Monospace" , Font.PLAIN , 24)
	mainMenuConstraints.insets = Insets(0 , 0 , 100 , 0)
	mainMenuConstraints.gridx = 1
	mainMenuConstraints.gridy = 0
	mainMenuPanel.add(title , mainMenuConstraints)

	val singlePlayer = Button("SinglePlayer")
	singlePlayer.addActionListener {
		if (checkForWorldFiles()) {
			toggleMenuPanels(
				mmPanel = false , spPanel = true , mpPanel = false , cwPanel = false , awPanel = false
			)
		} else {
			toggleMenuPanels(
				mmPanel = false , spPanel = false , mpPanel = false , cwPanel = true , awPanel = false
			)
		}
	}
	mainMenuConstraints.insets = Insets(0 , 0 , 0 , 0)
	mainMenuConstraints.gridx = 1
	mainMenuConstraints.gridy = 1
	mainMenuPanel.add(singlePlayer , mainMenuConstraints)

	val multiplayer = Button("Multiplayer")
	multiplayer.addActionListener {
		toggleMenuPanels(
			mmPanel = false , spPanel = false , mpPanel = true , cwPanel = false , awPanel = false
		)
	}
	mainMenuConstraints.gridx = 1
	mainMenuConstraints.gridy = 2
	mainMenuPanel.add(multiplayer , mainMenuConstraints)

	val exit = Button("Quit")
	exit.addActionListener { exitProcess(0) }
	exit.size = Dimension(60 , 30)
	mainMenuConstraints.gridx = 1
	mainMenuConstraints.gridy = 3
	mainMenuPanel.add(exit , mainMenuConstraints)
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

	singlePlayerPanel.add(allWorldsPanel , singlePlayerConstraints)
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 1
	getCreatedWorlds()

	val backButton = Button("Create new World")
	backButton.addActionListener {
		toggleMenuPanels(mmPanel = false , spPanel = false , mpPanel = false , cwPanel = true , awPanel = false)
	}
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 2
	singlePlayerPanel.add(backButton , singlePlayerConstraints)

	val back = Button("Back")
	back.addActionListener {
		toggleMenuPanels(
			mmPanel = true , spPanel = false , mpPanel = false , cwPanel = false , awPanel = false
		)
	}
	back.size = Dimension(60 , 30)
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 3
	singlePlayerPanel.add(back , singlePlayerConstraints)
}

private fun panelCreateNewWorld() {
	// savableWorld creation
	val worldCreateGridBag = GridBagLayout()
	val worldCreateConstraints = GridBagConstraints()
	createWorldPanel.layout = worldCreateGridBag


	val worldNameLabel = Label("World Name:")
	worldCreateConstraints.gridy = 0
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldNameLabel , worldCreateConstraints)
	val worldNameInput = TextField("Name of World...")
	savableWorld.setName(worldNameInput)
	worldNameInput.addTextListener { savableWorld.setName(worldNameInput) }
	worldCreateConstraints.gridx = 3
	createWorldPanel.add(worldNameInput , worldCreateConstraints)

	val worldSeedLabel = Label("World Seed:")
	worldCreateConstraints.gridy = 1
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldSeedLabel , worldCreateConstraints)
	val worldSeedInput = TextField("1000000000")
	worldCreateConstraints.gridx = 3
	worldSeedInput.text = "${(Math.random() * 1000000000).toInt()}"
	savableWorld.setSeed(worldSeedInput)
	worldSeedInput.addTextListener { savableWorld.setSeed(worldSeedInput) }
	createWorldPanel.add(worldSeedInput , worldCreateConstraints)


	val worldDifficultyLabel = Label("World Difficulty:")
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 2
	createWorldPanel.add(worldDifficultyLabel , worldCreateConstraints)

	val worldDifficultyLowest = Button("Nomad")
	worldDifficultyLowest.addActionListener { savableWorld.setDifficulty(worldDifficultyLowest) }
	worldCreateConstraints.gridx = 1
	createWorldPanel.add(worldDifficultyLowest , worldCreateConstraints)

	val worldDifficultyLow = Button("Serene")
	worldDifficultyLow.addActionListener { savableWorld.setDifficulty(worldDifficultyLow) }
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldDifficultyLow , worldCreateConstraints)

	val worldDifficultyMedium = Button("Grim")
	worldDifficultyMedium.addActionListener { savableWorld.setDifficulty(worldDifficultyMedium) }
	worldCreateConstraints.gridx = 3
	createWorldPanel.add(worldDifficultyMedium , worldCreateConstraints)

	val worldDifficultyHigh = Button("Insufferable")
	worldDifficultyHigh.addActionListener { savableWorld.setDifficulty(worldDifficultyHigh) }
	worldCreateConstraints.gridx = 4
	createWorldPanel.add(worldDifficultyHigh , worldCreateConstraints)

	val worldDifficultyTooHigh = Button("Legendary")
	worldDifficultyTooHigh.addActionListener { savableWorld.setDifficulty(worldDifficultyTooHigh) }
	worldCreateConstraints.gridx = 5
	createWorldPanel.add(worldDifficultyTooHigh , worldCreateConstraints)


	val worldSizeLabel = Label("World Size:")
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 3
	createWorldPanel.add(worldSizeLabel , worldCreateConstraints)

	val worldSizeSmall = Button("Small")
	worldSizeSmall.addActionListener { savableWorld.setSize(worldSizeSmall) }
	worldCreateConstraints.gridx = 1
	createWorldPanel.add(worldSizeSmall , worldCreateConstraints)

	val worldSizeMedium = Button("Medium")
	worldSizeMedium.addActionListener { savableWorld.setSize(worldSizeMedium) }
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldSizeMedium , worldCreateConstraints)

	val worldSizeLarge = Button("Large")
	worldSizeLarge.addActionListener { savableWorld.setSize(worldSizeLarge) }
	worldCreateConstraints.gridx = 3
	createWorldPanel.add(worldSizeLarge , worldCreateConstraints)

	val worldPowerLabel = Label("World Power:")
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 4
	createWorldPanel.add(worldPowerLabel , worldCreateConstraints)

	val worldPowerFear = Button("Fear")
	worldPowerFear.addActionListener { savableWorld.setPower(worldPowerFear) }
	worldCreateConstraints.gridx = 1
	createWorldPanel.add(worldPowerFear , worldCreateConstraints)

	val worldPowerDesperation = Button("Desperation")
	worldPowerDesperation.addActionListener { savableWorld.setPower(worldPowerDesperation) }
	worldCreateConstraints.gridx = 2
	createWorldPanel.add(worldPowerDesperation , worldCreateConstraints)


	val backToWorlds = Button("Back")
	backToWorlds.addActionListener {
		if (checkForWorldFiles()) toggleMenuPanels(
			mmPanel = false ,
			spPanel = true ,
			mpPanel = false ,
			cwPanel = false ,
			awPanel = false
		)
		else toggleMenuPanels(mmPanel = true , spPanel = false , mpPanel = false , cwPanel = false , awPanel = false)
	}
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 5
	createWorldPanel.add(backToWorlds , worldCreateConstraints)

	val createWorld = Button("Create world")
	createWorld.addActionListener { savableWorld.saveWorld() }
	worldCreateConstraints.gridx = 5
	createWorldPanel.add(createWorld , worldCreateConstraints)
}

private fun panelCreateMultiplayer() {
	val multiplayerGridBag = GridBagLayout()
	val multiplayerConstraints = GridBagConstraints()
	multiplayerPanel.layout = multiplayerGridBag

	val backFromMp = Button("Back")
	backFromMp.addActionListener {
		toggleMenuPanels(
			mmPanel = true , spPanel = false , mpPanel = false , cwPanel = false , awPanel = false
		)
	}
	backFromMp.size = Dimension(60 , 30)
	multiplayerConstraints.gridx = 1
	multiplayerConstraints.gridy = 2
	multiplayerPanel.add(backFromMp , multiplayerConstraints)
}


private fun getCreatedWorlds() {
	val files = File(savableWorld.getPath()).listFiles()
	if (files != null) {
		for (i in files.indices) {
			// making sure the file is a json
			if (! files[i].name.contains(".json")) continue

			val file = FileReader(savableWorld.getPath() + files[i].name).readText()
			val saveFile = JSONObject(file)

			// making sure it has a valid seed
			val seed = saveFile.getString("seed")
			val intSeed = seed.slice(IntRange(6 , seed.length - 1)).toIntOrNull()
			if (intSeed !is Int) continue

			playWorldFileButton(saveFile)
		}
	}
}

fun playWorldFileButton(save : JSONObject) {
	val constraints = GridBagConstraints()
	val layout = GridBagLayout()
	val oneWorldPanel = JPanel()
	oneWorldPanel.layout = layout


	val selectWorldForPlay = Button("Select")
	selectWorldForPlay.addActionListener { savableWorld.getWorldSettings(save.getString("name")) }
	constraints.gridx = 1
	constraints.gridy = 0
	oneWorldPanel.add(selectWorldForPlay , constraints)

	val name = Label(save.getString("name"))
	constraints.gridy = 1
	oneWorldPanel.add(name , constraints)

	val seed = Label(save.getString("seed"))
	constraints.gridy = 2
	oneWorldPanel.add(seed , constraints)


	allWorldsPanel.add(oneWorldPanel)
}


fun toggleMenuPanels(
	mmPanel : Boolean ,
	spPanel : Boolean ,
	mpPanel : Boolean ,
	cwPanel : Boolean ,
	awPanel : Boolean ,
) {
	when {
		! mmPanel -> window.remove(mainMenuPanel)
		mmPanel   -> window.add(mainMenuPanel)
	}
	when {
		! spPanel -> window.remove(singlePlayerPanel)
		spPanel   -> {
			window.add(singlePlayerPanel)
			getCreatedWorlds()
		}
	}
	when {
		! mpPanel -> window.remove(multiplayerPanel)
		mpPanel   -> window.add(multiplayerPanel)
	}
	when {
		! cwPanel -> window.remove(createWorldPanel)
		cwPanel   -> window.add(createWorldPanel)
	}
	when {
		! awPanel -> singlePlayerPanel.remove(createWorldPanel)
		awPanel   -> singlePlayerPanel.add(createWorldPanel)
	}

	window.revalidate()
	window.repaint()
}

private fun checkForWorldFiles() : Boolean {
	var hasWorlds = false

	val files = File(savableWorld.getPath()).listFiles()
	for (i in files !!) {
		if (! i.name.contains(".json")) continue

		val file = FileReader(savableWorld.getPath() + i.name).readText()
		val saveFile = JSONObject(file)

		// making sure it has a valid seed
		val seed = saveFile.getString("seed")
		val intSeed = seed.slice(IntRange(6 , seed.length - 1)).toIntOrNull()
		if (intSeed is Int) hasWorlds = true
	}
	return hasWorlds
}