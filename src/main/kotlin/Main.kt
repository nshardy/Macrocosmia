import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.UIManager
import kotlin.system.exitProcess


// variables
var width : Int = 600
var height : Int = 400
val window = JFrame()
val mainMenuPanel = JPanel()
val singlePlayerPanel = JPanel()
val multiplayerPanel = JPanel()
val icon : Image = ImageIO.read(File("src/main/resources/sprites/Icon.png"))

// functions
fun main() {
	// variables
	window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
	window.size = Dimension(width , height)
	window.iconImage = icon.getScaledInstance(64 , 64 , Image.SCALE_SMOOTH)

//	if (Taskbar.isTaskbarSupported())
//		Taskbar.getTaskbar().iconImage = icon.getScaledInstance(64 , 64 , Image.SCALE_SMOOTH)

	try {
		window.iconImage = icon.getScaledInstance(64 , 64 , Image.SCALE_SMOOTH)
		if (System.getProperty("os.name").startsWith("Mac") || System.getProperty("os.name").startsWith("Darwin")) {
			val taskbar = Taskbar.getTaskbar()
			try {
				taskbar.iconImage = icon.getScaledInstance(64 , 64 , Image.SCALE_SMOOTH)
			} catch (e : UnsupportedOperationException) {
				println("Can't set taskbar icon.")
			} catch (e : SecurityException) {
				println("Warning. Can't set taskbar icon due to security exceptions.")
			}
		}
	} catch (e : NullPointerException) {
		e.printStackTrace()
	}

	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

	val mainMenuGridBag = GridBagLayout()
	val mainMenuConstraints = GridBagConstraints()
	mainMenuPanel.layout = mainMenuGridBag

	// mainMenu items
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

	// singlePlayer menu
	val singlePlayerGridBag = GridBagLayout()
	val singlePlayerConstraints = GridBagConstraints()
	singlePlayerPanel.layout = singlePlayerGridBag

	val worldsTitle = Label("Worlds")
	worldsTitle.font = Font("Monospace" , Font.PLAIN , 24)
	singlePlayerConstraints.insets = Insets(0 , 0 , 150 , 0)
	singlePlayerConstraints.gridx = 1
	singlePlayerConstraints.gridy = 0
	singlePlayerPanel.add(worldsTitle , singlePlayerConstraints)

	val createWorld = Button("Create World")
	createWorld.addActionListener {
		var world = World()
		world.startNewWorld()
		world.saveWorld()
		/*JOptionPane.showMessageDialog(window , "Creating worlds is not yet implemented.")*/
	}
	singlePlayerConstraints.insets = Insets(0 , 0 , 0 , 0)
	singlePlayerConstraints.gridx = 1
	singlePlayerConstraints.gridy = 1
	singlePlayerPanel.add(createWorld , singlePlayerConstraints)

	val back = Button("Back")
	back.addActionListener { toggleMenuPanels(mmPanel = true , spPanel = false , mpPanel = false) }
	back.size = Dimension(60 , 30)
	singlePlayerConstraints.gridx = 1
	singlePlayerConstraints.gridy = 2
	singlePlayerPanel.add(back , singlePlayerConstraints)

	// multiplayerPanel menu
	val multiplayerGridBag = GridBagLayout()
	val multiplayerConstraints = GridBagConstraints()
	multiplayerPanel.layout = multiplayerGridBag

	val backFromMp = Button("Back")
	backFromMp.addActionListener { toggleMenuPanels(mmPanel = true , spPanel = false , mpPanel = false) }
	backFromMp.size = Dimension(60 , 30)
	multiplayerConstraints.gridx = 1
	multiplayerConstraints.gridy = 2
	multiplayerPanel.add(backFromMp , multiplayerConstraints)


	toggleMenuPanels(mmPanel = true , spPanel = false , mpPanel = false)
	window.setLocationRelativeTo(null)
	window.isVisible = true
	validate()

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