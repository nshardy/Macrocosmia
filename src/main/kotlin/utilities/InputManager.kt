package utilities

import entities.Player
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class InputManager(private val player : Player) : KeyListener {

	override fun keyTyped(e : KeyEvent?) {
		TODO("Not yet implemented")
	}

	override fun keyPressed(e : KeyEvent?) {
		val key = e?.keyCode

		if (key == KeyEvent.VK_W)
			player.moveX += player.moveSpeed

		if (key == KeyEvent.VK_A)
			player.moveX -= player.moveSpeed

		if (key == KeyEvent.VK_D)
			player.moveX += player.moveSpeed

		if (key == KeyEvent.VK_S)
			player.moveX -= player.moveSpeed

		print(key)
	}

	override fun keyReleased(e : KeyEvent?) {
		print("Released ${e?.keyCode}")
	}

}