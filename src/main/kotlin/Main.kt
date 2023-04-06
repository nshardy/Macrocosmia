import java.awt.*
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.UIManager
import kotlin.system.exitProcess

var width : Int = 600
var height : Int = 400
val mainMenuPanel = JPanel()
val singlePlayerPanel = JPanel()

fun main() {
    // variables
    val frame = JFrame()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(width , height)

//    if (System.getProperty("os.name").contains("Windows"))
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val mainMenuGridBag = GridBagLayout()
    val mainMenuConstraints = GridBagConstraints()
    mainMenuPanel.layout = mainMenuGridBag

    // mainMenu items
    val title = Label()
    title.text = "Macrocosmia: A New Dawn"
    title.font = Font("Monospace" , Font.PLAIN , 24)
    mainMenuConstraints.insets = Insets(0 , 0 , 100 , 0)
    mainMenuConstraints.gridx = 1
    mainMenuConstraints.gridy = 0

    mainMenuPanel.add(title , mainMenuConstraints)
    val singlePlayer = Button()
    singlePlayer.label = "SinglePlayer"
    singlePlayer.addActionListener { frame.remove(mainMenuPanel); frame.add(singlePlayerPanel) }
    mainMenuConstraints.insets = Insets(0 , 0 , 0 , 0)
    mainMenuConstraints.gridx = 1
    mainMenuConstraints.gridy = 1

    mainMenuPanel.add(singlePlayer , mainMenuConstraints)
    val multiplayer = Button()
    multiplayer.label = "Multiplayer"
    multiplayer.addActionListener { JOptionPane.showMessageDialog(frame , "Multiplayer is currently not supported.") }
    mainMenuConstraints.gridx = 1
    mainMenuConstraints.gridy = 2

    mainMenuPanel.add(multiplayer , mainMenuConstraints)
    val exit = Button()
    exit.label = "Quit"
    exit.addActionListener { exitProcess(0) }
    exit.size = Dimension(60 , 30)
    mainMenuConstraints.gridx = 1
    mainMenuConstraints.gridy = 3

    mainMenuPanel.add(exit , mainMenuConstraints)
    frame.add(mainMenuPanel)

    // singlePlayer menu
    val singlePlayerGridBag = GridBagLayout()
    val singlePlayerConstraints = GridBagConstraints()
    singlePlayerPanel.layout = singlePlayerGridBag

    val worldsTitle = Label()
    worldsTitle.text = "Worlds"
    worldsTitle.font = Font("Monospace" , Font.PLAIN , 24)
    singlePlayerConstraints.insets = Insets(0 , 0 , 100 , 0)
    singlePlayerConstraints.gridx = 1
    singlePlayerConstraints.gridy = 0

    singlePlayerPanel.add(worldsTitle , singlePlayerConstraints)
    val createWorld = Button()
    createWorld.label = "Create World"
    createWorld.addActionListener { frame.remove(singlePlayerPanel); frame.add(mainMenuPanel) }
    singlePlayerConstraints.insets = Insets(0 , 0 , 0 , 0)
    singlePlayerConstraints.gridx = 1
    singlePlayerConstraints.gridy = 1


    singlePlayerPanel.add(createWorld , singlePlayerConstraints)
    val back = Button()
    back.label = "Back"
    back.addActionListener { frame.remove(singlePlayerPanel); frame.add(mainMenuPanel) }
    back.size = Dimension(60 , 30)
    singlePlayerConstraints.gridx = 1
    singlePlayerConstraints.gridy = 2

    singlePlayerPanel.add(back , singlePlayerConstraints)

    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}