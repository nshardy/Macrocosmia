import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.UIManager
import kotlin.system.exitProcess


// variables
var width : Int = 700
var height : Int = 550
val window = JFrame()
val mainMenuPanel = JPanel()
val singlePlayerPanel = JPanel()
val worldPanel = JPanel()
val multiplayerPanel = JPanel()
val icon : Image = ImageIO.read(File("src/main/resources/sprites/Icon.png"))
val scaledIcon : Image = icon.getScaledInstance(128 , 128 , Image.SCALE_SMOOTH)
var world : World = World()


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

	createMainMenuPanel(mainMenuConstraints)
	createSinglePlayerPanel()
	createMultiplayerPanel()


	toggleMenuPanels(mmPanel = true , spPanel = false , mpPanel = false)
	window.setLocationRelativeTo(null)
	window.isVisible = true
}

private fun createMultiplayerPanel() {
	val multiplayerGridBag = GridBagLayout()
	val multiplayerConstraints = GridBagConstraints()
	multiplayerPanel.layout = multiplayerGridBag

	val backFromMp = Button("Back")
	backFromMp.addActionListener { toggleMenuPanels(mmPanel = true , spPanel = false , mpPanel = false) }
	backFromMp.size = Dimension(60 , 30)
	multiplayerConstraints.gridx = 1
	multiplayerConstraints.gridy = 2
	multiplayerPanel.add(backFromMp , multiplayerConstraints)
}

private fun createMainMenuPanel(mainMenuConstraints : GridBagConstraints) {
	val title = Label("Macrocosmia: A New Dawn")
	title.font = Font("Monospace" , Font.PLAIN , 24)
	mainMenuConstraints.insets = Insets(0 , 0 , 100 , 0)
	mainMenuConstraints.gridx = 1
	mainMenuConstraints.gridy = 0
	mainMenuPanel.add(title , mainMenuConstraints)

	val singlePlayer = Button("SinglePlayer")
	singlePlayer.addActionListener { toggleMenuPanels(mmPanel = false , spPanel = true , mpPanel = false) }
	mainMenuConstraints.insets = Insets(0 , 0 , 0 , 0)
	mainMenuConstraints.gridx = 1
	mainMenuConstraints.gridy = 1
	mainMenuPanel.add(singlePlayer , mainMenuConstraints)

	val multiplayer = Button("Multiplayer")
	multiplayer.addActionListener { toggleMenuPanels(mmPanel = false , spPanel = false , mpPanel = true) }
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

private fun createSinglePlayerPanel() {
	val singlePlayerGridBag = GridBagLayout()
	val singlePlayerConstraints = GridBagConstraints()
	singlePlayerPanel.layout = singlePlayerGridBag

	val worldsTitle = Label("Worlds")
	worldsTitle.font = Font("Monospace" , Font.PLAIN , 24)
	singlePlayerConstraints.insets = Insets(0 , 0 , 150 , 0)
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 0
	singlePlayerPanel.add(worldsTitle , singlePlayerConstraints)


	createWorldPanel()
	singlePlayerConstraints.insets = Insets(0 , 0 , 0 , 0)
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 1
	singlePlayerPanel.add(worldPanel , singlePlayerConstraints)


	val createWorld = Button("Create World")
	createWorld.addActionListener {
		world.saveWorld()
	}
	singlePlayerConstraints.insets = Insets(0 , 0 , 0 , 0)
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 2
	singlePlayerPanel.add(createWorld , singlePlayerConstraints)

	val back = Button("Back")
	back.addActionListener { toggleMenuPanels(mmPanel = true , spPanel = false , mpPanel = false) }
	back.size = Dimension(60 , 30)
	singlePlayerConstraints.gridx = 0
	singlePlayerConstraints.gridy = 3
	singlePlayerPanel.add(back , singlePlayerConstraints)
}

private fun createWorldPanel() {
	// world creation
	val worldCreateGridBag = GridBagLayout()
	val worldCreateConstraints = GridBagConstraints()
	worldPanel.layout = worldCreateGridBag


	val worldSeedLabel = Label("World Seed:")
	worldCreateConstraints.gridx = 3
	worldPanel.add(worldSeedLabel , worldCreateConstraints)

	val worldSeedInput = TextField("1000000000")
	worldCreateConstraints.gridx = 4
	worldSeedInput.text = "${(Math.random() * 1000000000).toInt()}"
	world.setSeed(worldSeedInput)
	worldSeedInput.addTextListener { world.setSeed(worldSeedInput) }
	worldPanel.add(worldSeedInput , worldCreateConstraints)

	val worldNameLabel = Label("World Name:")
	worldCreateConstraints.gridx = 0
	worldPanel.add(worldNameLabel , worldCreateConstraints)

	val worldNameInput = TextField("Name of World...")
	world.setName(worldNameInput)
	worldNameInput.addTextListener { world.setName(worldNameInput) }
	worldCreateConstraints.gridx = 1
	worldPanel.add(worldNameInput , worldCreateConstraints)

	val worldDifficultyLabel = Label("World Difficulty:")
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 1
	worldPanel.add(worldDifficultyLabel , worldCreateConstraints)

	val worldDifficultyLowest = Button("Nomad")
	worldDifficultyLowest.addActionListener { world.setDifficulty(worldDifficultyLowest) }
	worldCreateConstraints.gridx = 1
	worldPanel.add(worldDifficultyLowest , worldCreateConstraints)

	val worldDifficultyLow = Button("Serene")
	worldDifficultyLow.addActionListener { world.setDifficulty(worldDifficultyLow) }
	worldCreateConstraints.gridx = 2
	worldPanel.add(worldDifficultyLow , worldCreateConstraints)

	val worldDifficultyMedium = Button("Grim")
	worldDifficultyMedium.addActionListener { world.setDifficulty(worldDifficultyMedium) }
	worldCreateConstraints.gridx = 3
	worldPanel.add(worldDifficultyMedium , worldCreateConstraints)

	val worldDifficultyHigh = Button("Insufferable")
	worldDifficultyHigh.addActionListener { world.setDifficulty(worldDifficultyHigh) }
	worldCreateConstraints.gridx = 4
	worldPanel.add(worldDifficultyHigh , worldCreateConstraints)

	val worldDifficultyTooHigh = Button("Legendary")
	worldDifficultyTooHigh.addActionListener { world.setDifficulty(worldDifficultyTooHigh) }
	worldCreateConstraints.gridx = 5
	worldPanel.add(worldDifficultyTooHigh , worldCreateConstraints)


	val worldPowerLabel = Label("World Power:")
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 3
	worldPanel.add(worldPowerLabel , worldCreateConstraints)
	val worldPowerFear = Button("Fear")
	worldPowerFear.addActionListener { world.setPower(worldPowerFear) }
	worldCreateConstraints.gridx = 1
	worldPanel.add(worldPowerFear , worldCreateConstraints)
	val worldPowerDesperation = Button("Desperation")
	worldPowerDesperation.addActionListener { world.setPower(worldPowerDesperation) }
	worldCreateConstraints.gridx = 2
	worldPanel.add(worldPowerDesperation , worldCreateConstraints)

	val worldSizeLabel = Label("World Size:")
	worldCreateConstraints.gridx = 0
	worldCreateConstraints.gridy = 2
	worldPanel.add(worldSizeLabel , worldCreateConstraints)
	val worldSizeSmall = Button("Small")
	worldSizeSmall.addActionListener { world.setSize(worldSizeSmall) }
	worldCreateConstraints.gridx = 1
	worldPanel.add(worldSizeSmall , worldCreateConstraints)
	val worldSizeMedium = Button("Medium")
	worldSizeMedium.addActionListener { world.setSize(worldSizeMedium) }
	worldCreateConstraints.gridx = 2
	worldPanel.add(worldSizeMedium , worldCreateConstraints)
	val worldSizeLarge = Button("Large")
	worldSizeLarge.addActionListener { world.setSize(worldSizeLarge) }
	worldCreateConstraints.gridx = 3
	worldPanel.add(worldSizeLarge , worldCreateConstraints)
}

fun toggleMenuPanels(mmPanel : Boolean , spPanel : Boolean , mpPanel : Boolean) {
	when {
		! mmPanel -> window.remove(mainMenuPanel)
		mmPanel   -> window.add(mainMenuPanel)
	}
	when {
		! spPanel -> window.remove(singlePlayerPanel)
		spPanel   -> window.add(singlePlayerPanel)
	}
	when {
		! mpPanel -> window.remove(multiplayerPanel)
		mpPanel   -> window.add(multiplayerPanel)
	}

	validate()
}

fun validate() {
	window.revalidate()
	window.repaint()
}